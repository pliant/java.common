package code.pliant.common.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.pliant.common.camel.format.XJCDataFormat;
import code.pliant.common.camel.process.ReviveOriginalMessageProcessor;
import code.pliant.common.camel.process.SystemMessageLimiterProcessor;
import code.pliant.common.camel.process.SystemTransactionIDProcessor;

/**
 * Creates two basic routes that use MQ as a go between.
 * 
 * @author Daniel Rugg
 */
@Component
public class CamelRouteBuilder extends RouteBuilder {
	
	@Autowired
	JAXBBodyTest bean;
	
	@Autowired
	XJCDataFormat format;
	
	@Autowired
	PatternTestService service;
	
	@Autowired
	SystemMessageLimiterProcessor processor;
	
	@Autowired
	SystemTransactionIDProcessor idProcessor;
	
	@Autowired
	ReviveOriginalMessageProcessor reviveProcessor;
	
	/**
	 * The starting endpoint of the first route..
	 */
	public static final String DIRECT_START_OBJ = "direct:start.obj";
	
	/**
	 * The endpoint that is the final destination of the message.
	 */
	public static final String MOCK_END_XML = "mock:end.xml";
	
	/**
	 * The starting endpoint of the first route..
	 */
	public static final String DIRECT_START_XML = "direct:start.xml";
	
	/**
	 * The endpoint that is the final destination of the message.
	 */
	public static final String MOCK_END_OBJ = "mock:end.obj";
	
	
	public static final String DIRECT_START_LOOP = "direct:start.loop";
	
	public static final String DIRECT_START_MESSAGE_LOOP = "direct:start.message.loop";
	
	public static final String DIRECT_START_MESSAGE_LOOP_SENT = "direct:start.message.loop.sent";
	
	public static final String DIRECT_START_MESSAGE_LOOP_IGNORE = "direct:start.message.loop.ignore";
	
	public static final String DIRECT_START_EXCEPTION = "direct:start.exception";
	
	public static final String MOCK_END_EXCEPTION = "mock:end.exception";
	
	public static final String DIRECT_START_EXCEPTION_REVIVE = "direct:start.exception.revive";
	
	public static final String MOCK_END_EXCEPTION_REVIVE = "mock:end.exception.revive";
	
	/**
	 * 
	 */
	public CamelRouteBuilder() {
		super();
	}

	/**
	 * @param context
	 */
	public CamelRouteBuilder(CamelContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
		// Create A Route From Direct:Start To A Queue
		from(DIRECT_START_OBJ)
			.process(idProcessor)
			.marshal(format)
			.bean(bean, "setBody");
		
		// Create A Route From Queue To Mock End
		from(DIRECT_START_XML)
			.process(idProcessor)
			.unmarshal(format)
			.bean(bean, "setBody");
		
		// Create A Loop Test
		from(DIRECT_START_LOOP)
			.process(idProcessor)
			.loop(3)
				.bean(service, "uptick")
			.end();
		
		// Create A Loop Test
		from(DIRECT_START_MESSAGE_LOOP)
		.process(idProcessor)
			.loop(10)
				.process(processor)
				.to(URIs.NOTIFY_ERROR)
			.end();
		
		// Create A Loop Test
		from(DIRECT_START_MESSAGE_LOOP_SENT)
			.process(idProcessor)
			.setHeader(SystemMessageLimiterProcessor.LAST_SENT).constant(System.currentTimeMillis())
			.setHeader(SystemMessageLimiterProcessor.SENT_COUNT).constant(3)
			.loop(10)
				.process(processor)
				.to(URIs.NOTIFY_ERROR)
			.end();

		// Create A Loop Test
		from(DIRECT_START_MESSAGE_LOOP_IGNORE)
			.process(idProcessor)
			.setHeader(SystemMessageLimiterProcessor.LAST_SENT).constant(System.currentTimeMillis())
			.setHeader(SystemMessageLimiterProcessor.IGNORE).constant(SystemMessageLimiterProcessor.IGNORE)
			.loop(10)
				.process(processor)
				.to(URIs.NOTIFY_ERROR)
			.end();
		
		from(DIRECT_START_EXCEPTION)
			.process(idProcessor)
			.doTry()
				.bean(service, "throwException")
			.doCatch(Exception.class)
				.to(MOCK_END_EXCEPTION);
		
		from(DIRECT_START_EXCEPTION_REVIVE)
			.process(idProcessor)
			.doTry()
				.bean(service, "throwException")
			.doCatch(Exception.class)
				.process(reviveProcessor)
				.to(MOCK_END_EXCEPTION_REVIVE);
			
	}
}
