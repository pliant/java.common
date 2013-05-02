package code.pliant.common.core;


/**
 * <p>
 * A NonRecoverableException represents exceptions that are thrown when events occur outside the normal scope of 
 * expected processing.  NonRecoverableExceptions are typically the result of factors outside the control of the 
 * application, and indicate the the process or application can not be continued with for any reason.
 * </p>
 * <p>
 * Examples that would cause a NonRecoverableException to be thrown are:
 * <ul>
 * <li>Database connection fails.</li>
 * <li>Runtime exception that was not expected or handled.</li>
 * </ul>
 * As a rule, all NonRecoverableExceptions must have an explanation of what failed.  While in some cases a provided 
 * {@link java.lang.Throwable} may suffice, provided context is highly suggested.
 * 
 * </p>
 * 
 * @author Daniel Rugg
 */
public class NonRecoverableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NonRecoverableException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NonRecoverableException(Message message, Throwable cause) {
		super(message.toString(), cause);
	}

	/**
	 * @param message
	 */
	public NonRecoverableException(Message message) {
		super(message.toString());
	}

	/**
	 * 
	 * @param message
	 */
	public NonRecoverableException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NonRecoverableException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NonRecoverableException(String message, Throwable cause) {
		super(message, cause);
	}
}
