package code.pliant.common.xml;


/**
 * Thrown from {@link Transformer}s when a transformation fails.
 * 
 * @author Daniel Rugg
 */
public class TransformException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public TransformException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public TransformException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public TransformException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TransformException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
