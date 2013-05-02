package code.pliant.common.camel.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;


/**
 * Replaces the current body with that of the original that was provided to the route.
 * 
 * @author Daniel Rugg
 */
@Component
public class ReviveOriginalMessageProcessor implements Processor {

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		Object originalBody = exchange.getUnitOfWork().getOriginalInMessage().getBody();
		exchange.getIn().setBody(originalBody);
	}
}
