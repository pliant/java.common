package code.pliant.common.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;


/**
 * Helper utility for getting reseources off of the classpath.
 * 
 * @author Daniel Rugg, OBIS
 */
public class Resources {
	
	/**
	 * Creates a debug-like output for the indicated resource.
	 * 
	 * @param klass A class to used as a relative location of the path.  If null, ResourceHelper.class is used.
	 * @param resourcePath The path to find the resource at.
	 * @return A string containing information about the resource that is being inspected, including it's contents.
	 */
	public static String inspectResource(Class<?> klass, String resourcePath){
		Message message = new Message();
		message.add("ResourcePath", resourcePath)
			.add("Class Loader", klass.getName());
		
		// Validate URL
		URL resource = klass.getResource(resourcePath);
		message.add("Resource URL", resource);
		
		// Validate Contents
		try {
			message.add("Resource", getResourceAsString(klass, resourcePath));
		}
		catch (NotFoundException e) {
			message.add("Resource Exception", e.getMessage());
		}
		return message.toString();
	}
	
	/**
	 * Gets a resource in the classpath and returns it's contents as a string.
	 * 
	 * @param klass A class to used as a relative location of the path.  If null, ResourceHelper.class is used.
	 * @param resourcePath The path to find the resource at.
	 * @return The contents of the resource in String form.
	 */
	public static String getResourceAsString(Class<?> klass, String resourcePath){
		if(klass == null){
			klass = Resources.class;
		}
		InputStream stream = klass.getResourceAsStream(resourcePath);
		if(stream != null){
			return IO.toStringOrNull(stream);
		}
		throw new NotFoundException(klass, resourcePath);
	}

	
	/**
	 * Gets a resource located at a URL and returns it's contents as a string.
	 * 
	 * @param url The URL that points to the content to serve up
	 * @return The contents of the resource in String form.
	 */
	public static String getResourceAsString(URL url){
		try {
			InputStream stream = url.openConnection().getInputStream();
			if(stream != null){
				return IO.toStringOrNull(stream);
			}
			throw new NotFoundException(url);
		}
		catch (NotFoundException e) {
			throw e;
		}
		catch (Exception e) {
			throw new NotFoundException(url, e);
		}
	}
	
	/**
	 * Creates a Properties instance and populates it with the properties file found at the resourcePath location.
	 * 
	 * @param klass A class to used as a relative location of the path.  If null, ResourceHelper.class is used.
	 * @param resourcePath The path to find the resource at.
	 * @return A Properties file loaded with the properties of the resource indicated.
	 */
	public static Properties getResourceAsProperties(Class<?> klass, String resourcePath){
		if(klass == null){
			klass = Resources.class;
		}
		InputStream stream = klass.getResourceAsStream(resourcePath);
		if(stream != null){
			Properties props = new Properties();
			try {
				props.load(stream);
			}
			catch (IOException e){
				
			}
			return props;
		}
		throw new NotFoundException(klass, resourcePath);
	}
}
