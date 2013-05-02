package code.pliant.common.camel;


/**
 * Provides base URIs to common endpoints in the infrastructure.
 * 
 * @author Daniel Rugg
 */
public class URIs {

	/**
	 * The endpoint to send messages to be archived.  Value is 'mq:queue:q.archive'.
	 */
	public static final String ARCHIVE = "mq:queue:q.archive";
	
	/**
	 * Endpoint for notifying that a message has failed to process and will be sent to an error queue.  
	 * Value is 'seda:hssc.sys.support.notify.error'.
	 */
	public static final String NOTIFY_ERROR = "seda:hssc.sys.support.notify.error";
	
	/**
	 * Endpoint for notifying that a message has failed to process but will retry automatically.  
	 * Value is 'seda:hssc.sys.support.notify.error'.
	 */
	public static final String NOTIFY_RETRY = "seda:hssc.sys.support.notify.retry";
	
	/**
	 * Endpoint for notifying that something has happened, but it is not a fatal error or self-retrying.  
	 * Value is 'seda:hssc.sys.support.notify'.
	 */
	public static final String NOTIFY = "seda:hssc.sys.support.notify";
}
