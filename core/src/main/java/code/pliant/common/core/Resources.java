package code.pliant.common.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;


/**
 * @author Daniel Rugg, OBIS
 *
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
		StringBuilder output = new StringBuilder();
		output.append("ResourcePath: ").append(resourcePath).append(Message.NEWLINE);
		output.append("Class Loader: ").append(klass.getName()).append(Message.NEWLINE);
		
		// Validate URL
		URL resource = klass.getResource(resourcePath);
		output.append("Resource URL: ").append(resource).append(Message.NEWLINE);
		
		// Validate Contents
		output.append("Resource: ");
		try {
			output.append(getResourceAsString(klass, resourcePath));
		}
		catch (NotFoundException e) {
			output.append(e.getMessage());
		}
		return output.toString();
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
			StringBuilder output = new StringBuilder();
			InputStreamReader reader = new InputStreamReader(stream);
			int len = 1024;
			char[] buffer = new char[len];
			try{
				while((len = reader.read(buffer)) >= 0){
					output.append(buffer, 0, len);
				}
			}
			catch(IOException e){
				output.append(e.getMessage());
			}
			finally{
				try {
					reader.close();
				}
				catch (IOException e) {}
			}
			return output.toString();
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
				final char[] buffer = new char[0x10000];
				StringBuilder output = new StringBuilder();
				InputStreamReader reader = new InputStreamReader(stream);
				int read;
				do {
					read = reader.read(buffer, 0, buffer.length);
					if (read > 0) {
						output.append(buffer, 0, read);
					}
				} while (read >= 0);
				return output.toString();
			}
			throw new NotFoundException(url);
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
