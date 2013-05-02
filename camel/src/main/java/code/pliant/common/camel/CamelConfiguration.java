package code.pliant.common.camel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


/**
 * Provides the configuration to run camel.  Loads a CamelContext automatically that looks for routes in the Spring ApplicationContext.
 * 
 * @author Daniel Rugg
 */
@Configuration
@ImportResource("classpath:code/pliant/common/camel/context.xml")
public class CamelConfiguration {
	
	/**
	 * The default value of any retry delays.
	 */
	public static final Long DEFAULT_DELAY = 5000L;

	/**
	 * The time of delay for a database retry.
	 */
	@Value("$camel{camel.database.retry.delay}")
	private Long databaseRetryDelay = DEFAULT_DELAY;

	/**
	 * The time of delay for a service call retry.
	 */
	@Value("$camel{camel.service.retry.delay}")
	private Long serviceRetryDelay = DEFAULT_DELAY;

	/**
	 * The time of delay for a message queue push/pull retry.
	 */
	@Value("$camel{camel.queue.retry.delay}")
	private Long queueRetryDelay = DEFAULT_DELAY;

	/**
	 * URI to send messages about errors that occur.
	 */
	@Value("$camel{support.error.email.uri}")
	private String supportErrorEmailUri = null;

	/**
	 * URI to send messages about recoverable errors that are retried.
	 */
	@Value("$camel{support.retry.email.uri}")
	private String supportRetryEmailUri = null;

	/**
	 * URI for emails that are neither errors or retries.
	 */
	@Value("$camel{support.email.uri}")
	private String supportEmailUri = null;
	
	/**
	 * @return the databaseRetryDelay
	 */
	public Long getDatabaseRetryDelay() {
		return databaseRetryDelay;
	}
	
	/**
	 * @return the serviceRetryDelay
	 */
	public Long getServiceRetryDelay() {
		return serviceRetryDelay;
	}

	/**
	 * @return the queueRetryDelay
	 */
	public Long getQueueRetryDelay() {
		return queueRetryDelay;
	}
	
	/**
	 * @return the supportErrorEmailUri
	 */
	public String getSupportErrorEmailUri() {
		return supportErrorEmailUri;
	}
	
	/**
	 * @return the supportRetryEmailUri
	 */
	public String getSupportRetryEmailUri() {
		return supportRetryEmailUri;
	}
	
	/**
	 * @return the supportEmailUri
	 */
	public String getSupportEmailUri() {
		return supportEmailUri;
	}
}
