package code.pliant.common.core;


/**
 * Inspects an exception to determin how it could be handled.
 * 
 * @author Daniel Rugg
 */
public interface ExceptionInspector {

	/**
	 * Checks whether an Exception is recoverable.
	 * @param e The Exception to inspect.
	 * @return
	 */
	boolean isRecoverable(Exception e);
}
