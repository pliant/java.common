package code.pliant.common.core;

/**
 * Interface that tags a class as have a specific sort order.  Commonly used when injecting multiple 
 * services into an injection point and wanting there to be a specific ordering of the services in 
 * how they are called.
 * 
 * @author Daniel Rugg
 */
public interface Orderable {

	/**
	 * Gets the order value.
	 * @return An integer.
	 */
	int getOrder();
}
