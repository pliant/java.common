package code.pliant.common.camel.process;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;


/**
 * Processes messages that are about to be sent to the notification/email service.
 * 
 * @author Daniel Rugg
 */
@Component
public class SystemMessageLimiterProcessor implements Processor{
	
	/**
	 * Used as a header key to let the message processor know it should just ignore the request.
	 * The value is SystemMessageLimiterProcessor.IGNORE.
	 */
	public static final String IGNORE = SystemMessageLimiterProcessor.class.getSimpleName() + ".IGNORE";

	/**
	 * Used as a header key to let the message processor know the last time a message was sent.
	 * The value is SystemMessageLimiterProcessor.LAST_SENT.
	 */
	public static final String LAST_SENT = SystemMessageLimiterProcessor.class.getSimpleName() + ".LAST_SENT";

	/**
	 * Used as a header key to let the message processor know how many messages have been sent in the current grouping.
	 * The value is SystemMessageLimiterProcessor.SENT_COUNT.
	 */
	public static final String SENT_COUNT = SystemMessageLimiterProcessor.class.getSimpleName() + ".SENT_COUNT";
	
	
	
	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		long now = System.currentTimeMillis();
		Long lastSent = (Long)in.getHeader(LAST_SENT);
		if(lastSent == null){
			in.setHeader(LAST_SENT, now);
			in.setHeader(SENT_COUNT, 1);
			return;
		}
		
		if(in.getHeader(IGNORE) == null){
			Integer count = (Integer)in.getHeader(SENT_COUNT);
			if(count >= messagesPerGroup){
				in.setHeader(IGNORE, IGNORE);
				return;
			}
			else{
				in.setHeader(SENT_COUNT, count + 1);
				in.setHeader(LAST_SENT, now);
			}
		}
		else{
			// Determine Last Time Messages Were Sent
			if(lastSent.longValue() + durationBetweenMessages < now){
				in.removeHeader(IGNORE);
				in.setHeader(LAST_SENT, now);
				in.setHeader(SENT_COUNT, 1);
				return;
			}
		}
	}
	
	int durationBetweenMessages = 1000 * 60 * 5;
	
	int messagesPerGroup = 5;
}
