package code.pliant.common.jpa;

import code.pliant.common.core.Message;


/**
 * Thrown when any persistence operation fails.  Persistence operations fail due to non-normal parameters, 
 * such as the database is offline or the code is just wrong, hence this is a unchecked exception that 
 * should not be caught except for those operations which control transaction or final user interaction.
 * 
 * 
 * @author Daniel Rugg
 */
public class PersistenceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String defaultMessage = "Persistence operation failed.";
	/**
	 * 
	 */
	public PersistenceException() {
		super(defaultMessage);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PersistenceException(Message message, Throwable cause) {
		super(message.toString(), cause);
	}

	/**
	 * @param message
	 */
	public PersistenceException(Message message) {
		super(message.toString());
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public PersistenceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PersistenceException(Throwable cause) {
		super(defaultMessage, cause);
	}
}
