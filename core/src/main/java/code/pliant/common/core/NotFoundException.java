package code.pliant.common.core;

import java.net.URL;


/**
 * <p>
 * A NotFoundException is a generic exception that is thrown when something that is being looked for is not found.  You will 
 * typically use this when providing lookup services to data objects that are unable to return a value based on the 
 * criteria given.
 * </p>
 * <p>
 * Examples that would cause a NotFoundException to be thrown are:
 * <ul>
 * <li>Data object being looked up in a database by it's ID is not found to exist.</li>
 * <li>Local service looking for a resource that does not exist in the filesystem or classpath.</li>
 * </ul>
 * 
 * </p>
 * 
 * @author Daniel Rugg
 */
public class NotFoundException extends RuntimeException {

	/**
	 * Quick and easy NotFoundException creator.
	 * @param item The item that was being sought.
	 * @param values Additional information that is added to the message as a comma delimited list.
	 * @return A new NotFoundException instance.
	 */
	public static NotFoundException nfe(String item, String... values){
		Message message = new Message("Unable to find ").add(item);
		if(values != null){
			message.add(". Values: ");
			for(String value : values){
				message.add(value).add(", ");
			}
		}
		return new NotFoundException();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public NotFoundException() {
		super();
	}

	/**
	 * 
	 * @param message
	 */
	public NotFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NotFoundException(Message message, Throwable cause) {
		super(message.toString(), cause);
	}

	/**
	 * @param message
	 */
	public NotFoundException(Message message) {
		super(message.toString());
	}

	/**
	 * 
	 * @param url
	 */
	public NotFoundException(URL url){
		super(new StringBuilder("Could not find content at ").append(url.toExternalForm()).toString());
	}
	
	/**
	 * 
	 * @param url
	 * @param cause 
	 */
	public NotFoundException(URL url, Throwable cause){
		super(new StringBuilder("Could not find content at ").append(url == null ? Message.NULL: url.toExternalForm()).toString(), cause);
	}
	
	/**
	 * 
	 * @param klass
	 * @param resourcePath
	 */
	@SuppressWarnings("rawtypes")
	public NotFoundException(Class klass, String resourcePath){
		super(new StringBuilder("Could not find content on the class path at ").append(resourcePath)
				.append(" using ").append(klass.getName()).toString());
	}
	
	/**
	 * 
	 * @param klass
	 * @param resourcePath
	 * @param cause 
	 */
	@SuppressWarnings("rawtypes")
	public NotFoundException(Class klass, String resourcePath, Throwable cause){
		super(new StringBuilder("Could not find content on the class path at ").append(resourcePath)
				.append(" using ").append(klass.getName()).toString(), cause);
	}
}
