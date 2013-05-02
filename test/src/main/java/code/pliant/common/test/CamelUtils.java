package code.pliant.common.test;

/**
 * Utilities to use with camel testing.
 * 
 * @author Daniel Rugg
 */
public class CamelUtils {

	/**
	 * Some bogus sleep methods.  Will hook into content/route event when there is time.
	 */
	public static void waitForRouteComplete(){
		waitForRouteComplete(500l);
	}
	
	/**
	 * Some bogus sleep methods.  Will hook into content/route event when there is time.
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
