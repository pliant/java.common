package code.pliant.common.camel;

import org.apache.camel.builder.RouteBuilder;
import code.pliant.common.camel.process.SystemMessageLimiterProcessor;
import code.pliant.common.camel.process.SystemMessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Contains route that are reused system wide.  Examples of system wide routes would be:
 * 
 * <ul>
 * <li>Route that processes messages to send to support.</li>
 * <li>Routes that record metrics. </li>
 * <li>Routes that perform maintenance.</li>
 * </ul>
 * 
 * @author Daniel Rugg
 */
@Component
public class SystemRouteBuilder extends RouteBuilder {
	
	@Autowired
	CamelConfiguration config;
	
	@Autowired
	SystemMessageProcessor messageProcessor;

	/* (non-Javadoc)
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
		// Route To Notify Of Errors
		from(URIs.NOTIFY_ERROR)
			.filter(header(SystemMessageLimiterProcessor.IGNORE).isNull())
				.setHeader(SystemMessageProcessor.TYPE).constant(SystemMessageProcessor.TYPE_ERROR)
				.process(messageProcessor)
				.to(config.getSupportErrorEmailUri());
		
		// Route To Notify Of Retries
		from(URIs.NOTIFY_RETRY)
			.filter(header(SystemMessageLimiterProcessor.IGNORE).isNull())
				.setHeader(SystemMessageProcessor.TYPE).constant(SystemMessageProcessor.TYPE_RETRY)
				.process(messageProcessor)
				.to(config.getSupportRetryEmailUri());
		
		// Route Just To Notify, Would Expect That A Message Is Sent Along With This.
		from(URIs.NOTIFY)
			.filter(header(SystemMessageLimiterProcessor.IGNORE).isNull())
				.process(messageProcessor)
				.to(config.getSupportEmailUri());
	}
}
