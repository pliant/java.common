package jms.code.pliant.common;

import org.apache.activemq.RedeliveryPolicy;
import org.springframework.stereotype.Component;


/**
 * <p>
 * Provides a component that will force all messages to their original queue when in 
 * a transaction and rollbacked, eliminating the use of the dead letter queue(ActiveMQ.DLQ).  This 
 * should be used only when all exceptions are handled in their routing except for the 
 * RollbackExchangeException that is thrown when rollback() is called.
 * </p>
 * <p>
 * To enable this component, use the @Import() annotation with this class referenced in it.
 * </p>
 * 
 * @author Daniel Rugg
 */
@Component
public class NoDLQRedeliverPolicy extends RedeliveryPolicy{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NoDLQRedeliverPolicy() {
		super();
		this.setMaximumRedeliveries(-1);
		this.setUseExponentialBackOff(true);
		this.setBackOffMultiplier(2);
		this.setRedeliveryDelay(1000l);
		this.setMaximumRedeliveryDelay(5000l);
	}
}
