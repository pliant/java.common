package code.pliant.common.camel.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import code.pliant.common.core.NotFoundException;
import code.pliant.common.core.Resources;
import code.pliant.common.core.Strings;


/**
 * Processes messages that are about to be sent to the notification/email service.
 * 
 * @author Daniel Rugg
 */
@Component
public class SystemMessageProcessor implements Processor{

	/**
	 * Used as a header key to set and get the value of the actual message to use in emails and notifications.
	 * The value is SystemMessageProcessor.MESSAGE.
	 */
	public static final String MESSAGE = SystemMessageProcessor.class.getSimpleName() + ".MESSAGE";
	
	/**
	 * Used as a header key to set and get the value of the actual message to use in emails and notifications.
	 * The value is SystemMessageProcessor.MESSAGE_FILE.
	 */
	public static final String MESSAGE_FILE = SystemMessageProcessor.class.getSimpleName() + ".MESSAGE_FILE";
	
	/**
	 * The type of message that is being processed. The value is SystemMessageProcessor.TYPE.
	 */
	public static final String TYPE = SystemMessageProcessor.class.getSimpleName() + ".TYPE";
	
	/**
	 * Value indicating an error message.  Value is 'ERROR'
	 */
	public static final String TYPE_ERROR = "ERROR";
	
	/**
	 * Value indicating a retry message.  Value is 'RETRY'.
	 */
	public static final String TYPE_RETRY = "RETRY";
	
	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		// Look for a message to send directly in the header.
		String message = Strings.toStringOrNull(exchange.getIn().getHeader(MESSAGE));
		if(message != null){
			exchange.getIn().setBody(message);
			return;
		}
		
		// Look for a message file URL with the message in it.
		String file = Strings.toStringOrNull(exchange.getIn().getHeader(MESSAGE_FILE));
		if(file != null){
			try{
				message = Resources.getResourceAsString(SystemMessageProcessor.class, file);
				exchange.getIn().setBody(message);
				return;
			}
			catch(NotFoundException e){}
		}
		
		// Load Default Message Content
		String type = Strings.toStringOrEmpty(exchange.getIn().getHeader(TYPE));
		if(TYPE_ERROR.equals(type)){
			file = "error.txt";
		}
		else if(TYPE_RETRY.equals(type)){
			file = "retry.txt";
		}
		else{
			file = "unknown.txt";
		}
		try{
			message = Resources.getResourceAsString(SystemMessageProcessor.class, file);
		}
		catch(NotFoundException e){
			message = "No Message Content Found.  If you get this message something is seriously wrong.";
		}
		exchange.getIn().setBody(message);
	}
}
