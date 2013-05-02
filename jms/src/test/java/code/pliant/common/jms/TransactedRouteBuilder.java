package code.pliant.common.jms;

import javax.annotation.Resource;

import jms.code.pliant.common.ActiveMQConfiguration;

import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.pliant.common.camel.URIs;
import code.pliant.common.camel.predicate.IsRecoverable;
import code.pliant.common.camel.process.AddExceptionToHeaderProcessor;
import code.pliant.common.camel.process.ExchangeLoggerProcessor;
import code.pliant.common.camel.process.ReviveOriginalMessageProcessor;
import code.pliant.common.camel.process.SystemMessageLimiterProcessor;
import code.pliant.common.camel.process.SystemMessageProcessor;
import code.pliant.common.camel.process.SystemTransactionIDProcessor;
import code.pliant.common.core.NonRecoverableException;
import code.pliant.common.core.RecoverableException;

/**
 * Creates two basic routes that use MQ as a go between.
 * 
 * @author Daniel Rugg
 */
@Component()
public class TransactedRouteBuilder extends SpringRouteBuilder {
	
	@Autowired
	ExceptionService service;
	
	@Autowired
	IsRecoverable predicate;
	
	@Resource(name=ActiveMQConfiguration.JMS_SPRING_TRANSACTION_POLICY)
	SpringTransactionPolicy transactionPolicy;
	
	@Autowired
	SystemMessageLimiterProcessor messageLimiterProcessor;
	
	@Autowired
	SystemTransactionIDProcessor transactionIDProcessor;
	
	@Autowired
	ReviveOriginalMessageProcessor reviveOriginalMessageProcessor;
	
	@Autowired
	ExchangeLoggerProcessor exchangeLogger;
	
	@Autowired
	AddExceptionToHeaderProcessor addExceptionToHeaderProcessor;
	
	public static final String DIRECT_START_TRANSACTED = "direct:start.transacted";
	public static final String QUEUE_TRANSACTED = "mq:q.test.transacted.in";
	public static final String QUEUE_TRANSACTED_CONSUME = QUEUE_TRANSACTED;// + "?transacted=true";
	public static final String MOCK_ERROR_TRANSACTED = "mock:error.transacted";
	public static final String MOCK_END_TRANSACTED = "mock:end.transacted";

	public static final String TRANSACTED_ID = "TRANSROUTE";
	public static final String TRANSACTED_TO_MOCK_ID = "TRANSMOCKROUTE";
	
	/**
	 * 
	 */
	public TransactedRouteBuilder() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
	
		// Required so that the JMSTransactionManager Is Used
		errorHandler(transactionErrorHandler(transactionPolicy));
		
		from(DIRECT_START_TRANSACTED)
			.process(transactionIDProcessor)
			//.log("Routing message in Route From DIRECT")
			.to(QUEUE_TRANSACTED);
		
		from(QUEUE_TRANSACTED_CONSUME).id(TRANSACTED_ID).transacted(ActiveMQConfiguration.JMS_SPRING_TRANSACTION_POLICY)
			.doTry()
				.bean(service, "throwIt")
			.doCatch(RecoverableException.class)
				.process(messageLimiterProcessor)
				.setHeader(SystemMessageProcessor.MESSAGE).constant("Doing RecoverableException.class")
				.to(URIs.NOTIFY_RETRY)
				.bean(service, "upCount")
				.rollback()
				//.rollback()
			.doCatch(NonRecoverableException.class)
				//.process(exchangeLogger)
				.process(addExceptionToHeaderProcessor)
				.process(reviveOriginalMessageProcessor)
				.to(MOCK_ERROR_TRANSACTED)
				.setHeader(SystemMessageProcessor.MESSAGE).constant("Doing NonRecoverableException.class")
				.to(URIs.NOTIFY_ERROR)
			.doCatch(Exception.class)
				.choice()
					// Process Recoverable
					.when(predicate)
						.process(messageLimiterProcessor)
						.setHeader(SystemMessageProcessor.MESSAGE).constant("Doing Exception.class recoverable")
						.to(URIs.NOTIFY_RETRY)
						.bean(service, "upCount")
						.rollback()
						//.rollback()
					// Process Non Recoverable
					.otherwise()
						//.process(exchangeLogger)
						.process(addExceptionToHeaderProcessor)
						.process(reviveOriginalMessageProcessor)
						.to(MOCK_ERROR_TRANSACTED)
						.setHeader(SystemMessageProcessor.MESSAGE).constant("Doing Exception.class nonrecoverable")
						.to(URIs.NOTIFY_ERROR)
				.end()
			.end();
	}
}
