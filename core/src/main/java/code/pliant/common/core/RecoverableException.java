package code.pliant.common.core;


/**
 * <p>
 * A RecoverableException represents exceptions that are thrown when an exception occurs which stops a current process 
 * tree, but can be recovered from to finish the process tree through some action by a user or process.  When a 
 * RecoverableException is thrown, a process by which the recovery take place should be enacted.
 * </p>
 * <p>
 * Examples that would cause a RecoverableException to be thrown are:
 * <ul>
 * <li>A provided value exceeds a specified limit.</li>
 * <li>A provided value is incorrectly formatted.</li>
 * <li>Security authorization was not given for the requested process.</li> 
 * </ul>
 * As a rule, all RecoverableExceptions must have an explanation of what failed that is comprehensible to an end user 
 * if the exception is thrown back to one, to 
 * understand both what happened and how to remedy in order to proceed.
 * 
 * </p>
 * 
 * @author Daniel Rugg
 */
public class RecoverableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public RecoverableException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RecoverableException(Message message, Throwable cause) {
		super(message.toString(), cause);
	}

	/**
	 * @param message
	 */
	public RecoverableException(Message message) {
		super(message.toString());
	}

	/**
	 * 
	 * @param message
	 */
	public RecoverableException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public RecoverableException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RecoverableException(String message, Throwable cause) {
		super(message, cause);
	}
}
