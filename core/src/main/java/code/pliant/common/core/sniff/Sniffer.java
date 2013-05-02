package code.pliant.common.core.sniff;

import code.pliant.common.core.NotFoundException;
import code.pliant.common.core.Strings;

/**
 * A Sniffer is used to find a value in a specific location.
 * 
 * @author Daniel Rugg
 */
interface Sniffer{
	
	/**
	 * Common NotFoundException thrown by sniffers when they don't find a value.
	 */
	public static final NotFoundException NFE = new NotFoundException(Strings.EMPTY);
	
	/**
	 * Tries to retrieve the value that the Sniffer implementation looks for.
	 * 
	 * @return The value that was found.
	 */
	Object retrieve();
}