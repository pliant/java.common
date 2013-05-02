package code.pliant.common.camel.bean;

import code.pliant.common.camel.CamelConfiguration;
import code.pliant.common.core.Logic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Provides a mechanism to delay a process in Camel when you are in a try/catch/finally block.  Camel provides 
 * a 'delay' component that is configurable within most DSL configurations, but not within try/catch/finally 
 * blocks.  This is targeted to be fixed in version 3.x, but until it is released this will have to do.
 * 
 * 
 * @author Daniel Rugg
 */
@Service(CamelDelayer.COMPONENT_ID)
public class CamelDelayer {
	
	public static final String COMPONENT_ID = "hsscCamelDelayer";

	@Autowired
	CamelConfiguration config;
	
	/**
	 * Performs the delay when retrying a database call.
	 */
	public void delayDatabaseRetry(){
		delay(config.getDatabaseRetryDelay());
	}
	
	/**
	 * Performs the delay when retrying a database call.
	 */
	public void delayServiceRetry(){
		delay(config.getServiceRetryDelay());
	}
	
	/**
	 * Performs the delay when retrying a database call.
	 */
	public void delayQueueRetry(){
		delay(config.getQueueRetryDelay());
	}
	
	/**
	 * Handles the actual delay.  Encapsulated for better reuse.
	 * @param time
	 */
	private void delay(Long delay){
		Long time = Logic.firstNonNull(delay, CamelConfiguration.DEFAULT_DELAY);
		try {
			Thread.sleep(time);
		}
		catch (InterruptedException e) {
			// Ignore Interruption
		}
	}
}
