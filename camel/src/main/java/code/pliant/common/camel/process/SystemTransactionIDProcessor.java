package code.pliant.common.camel.process;

import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import code.pliant.common.camel.Constants;
import org.springframework.stereotype.Component;


/**
 * Provides a processor that will tag each incoming message transaction with a unique system ID 
 * that can be used to track that message and it's all of it's actions/offspring messages.  A 
 * message should only be tagged with a System Transaction ID if it is coming from an external 
 * source.  Any messages that are the result of processing an existing message should continue 
 * to use the same System Transaction ID.  In addition, the time in milliseconds that the ID 
 * is applied is also set.
 * 
 * @author Daniel Rugg
 */
@Component
public class SystemTransactionIDProcessor implements Processor {
	
	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		if(exchange.getIn().getHeader(Constants.TRANSACTION_ID) == null){
			exchange.getIn().setHeader(Constants.TRANSACTION_ID, UUID.randomUUID().toString());
			exchange.getIn().setHeader(Constants.TRANSACTION_ID_TIMESTAMP, System.currentTimeMillis());
		}
	}
}
