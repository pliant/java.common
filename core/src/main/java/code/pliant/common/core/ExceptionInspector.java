package code.pliant.common.core;


/**
 * Inspects an exception to determine how it could be handled.
 * 
 * @author Daniel Rugg
 */
public interface ExceptionInspector {

	/**
	 * Checks whether an Exception is recoverable.
	 * @param e The Exception to inspect.
	 * @return {@code true} if and exception is recoverable, else {@code false}.
	 */
	boolean isRecoverable(Exception e);
}
