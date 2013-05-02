package code.pliant.common.jms;

import javax.annotation.Resource;

import jms.code.pliant.common.ActiveMQConfiguration;

import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.pliant.common.camel.predicate.IsRecoverable;

/**
 * Creates two basic routes that use MQ as a go between.
 * 
 * @author Daniel Rugg
 */
@Component()
public class NonTransactedRouteBuilder extends SpringRouteBuilder {
	
	@Autowired
	ExceptionService service;
	
	@Autowired
	IsRecoverable predicate;
	
	@Resource(name=ActiveMQConfiguration.JMS_SPRING_TRANSACTION_POLICY)
	SpringTransactionPolicy transactionPolicy;
	
	/**
	 * The starting endpoint of the first route..
	 */
	public static final String DIRECT_START = "direct:start";
	
	/**
	 * The MQ endpoint that acts as the end of the first route and start of the next.
	 */
	public static final String QUEUE = "mq:q.test.in";
	
	/**
	 * The endpoint that is the final destination of the message.
	 */
	public static final String MOCK_END = "mock:end";
	
	/**
	 * 
	 */
	public NonTransactedRouteBuilder() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
		
		// Create A Route From Direct:Start To A Queue
		from(DIRECT_START)
			//.log("Routing message in Route From DIRECT")
			.to(QUEUE);
		
		// Create A Route From Queue To Mock End
		from(QUEUE)
			//.log("Routing message in Route To MOCK")
			.to(MOCK_END);
	}
}
