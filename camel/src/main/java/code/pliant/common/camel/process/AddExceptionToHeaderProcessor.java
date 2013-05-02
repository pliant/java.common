package code.pliant.common.camel.process;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import code.pliant.common.camel.Constants;


/**
 * Looks for the details of any exception thrown and adds it to the message header.  This 
 * is used when you are routing messages to an error queue and you want to persiste the 
 * reason why it is going there.
 * 
 * @author Daniel Rugg
 */
@Component
public class AddExceptionToHeaderProcessor implements Processor {

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		
		// Look for an exchange exception
		Exception e = exchange.getException();
		if(e != null){
			in.setHeader(Constants.EXCHANGE_EXCEPTION_TYPE, e.getClass().getName());
		}
		
		// Look for a message exception
		e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		if(e != null){
			in.setHeader(Constants.MESSAGE_EXCEPTION_TYPE, e.getClass().getName());
		}
	}
}
