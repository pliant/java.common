package code.pliant.common.test;

/**
 * Utilities to use when testing Camel routes.
 * 
 * @author Daniel Rugg
 */
public class CamelUtils {

	/**
	 * Some bogus sleep methods.  Will hook into content/route event when there is time.
	 * When testing routes, you will need to have the current thread sleep for a bit 
	 * so that the routes can be run on other threads.
	 */
	public static void waitForRouteComplete(){
		waitForRouteComplete(500l);
	}
	
	/**
	 * Some bogus sleep methods.  Will hook into content/route event when there is time.
	 * When testing routes, you will need to have the current thread sleep for a bit 
	 * so that the routes can be run on other threads.
	 * @param millis
	 */
	public static void waitForRouteComplete(long millis){
		Thread.yield();
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {}
		Thread.yield();
	}
}
