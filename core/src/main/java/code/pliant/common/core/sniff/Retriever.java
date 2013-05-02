package code.pliant.common.core.sniff;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import code.pliant.common.core.Message;
import code.pliant.common.core.NotFoundException;
import code.pliant.common.core.Paths;


/**
 * A Retriever attempts to find a value that is being sought by looking in a chain of places it has been told 
 * to look.  The first place that has a non-null value, it's value will be returned.  If a value value can't be 
 * found then then an exception is thrown.
 * 
 * There is no guarantee this class is thread-safe as it relies on Sniffer implementations to be thread-safe as well.
 * If the Sniffer's are all thread-safe, this will be thread-safe as well.
 * 
 * @author Daniel Rugg
 */
@SuppressWarnings("rawtypes")
public class Retriever implements Sniffer{
	
	static final Logger log = LoggerFactory.getLogger(Retriever.class);
	
	/**
	 * Default key to use when one is not provided.
	 */
	public static final String PLIANT_PKEY = "PLIANT_PKEY";
	
	private final String errorMessage;
	

	static void inform(Class klass, Object value, String... vars){
		if(log.isInfoEnabled()){
			Message message = new Message("Found Value: ")
				.add("Sniffer", klass.getName())
				.add("Arguments", vars)
				.add("Value", value);
			log.info(message.toString());
		}
	}
	
	private Collection<Sniffer> sniffers = Collections.synchronizedCollection(new ArrayList<Sniffer>());
	
	/**
	 * Creates a basic retriever tree that looks in the following areas for the value, 
	 * where 'key' is the value passed for the key argument and 'klass' is the value 
	 * passed for the klass argument:
	 * <ol>
	 * <li>Environment Variable Named 'key'</li>
	 * <li>System Property Named 'key'</li>
	 * <li>Property 'key' found in the standard configuration file for 'klass'. @see ${link Paths} </li>
	 * <li>Property 'key' found in the configuration file for 'klass' with the id of 'key'. @see ${link Paths} </li>
	 * <li>JNDI Value Named java:comp/env/'key'</li>
	 * </ol>
	 * 
	 * @param klass The Class to use in assembling the Sniffer tree.
	 * @param key The key to use in assembling the Sniffer tree.
	 * @return An instance of Retriever.
	 */
	public static Retriever basicRetriever(Class klass, String key){
		return new Retriever()
			.environmentVariable(key)
			.systemProperty(key)
			.property(klass, Paths.rootConfigFileName(klass), key)
			.property(klass, Paths.rootConfigFileName(klass, key), key)
			.initialContext("java:comp/env/" + key);
	}

	/**
	 * Creates a basic Retriever according to @{link Retriever#basicRetriever} and retrieves the value.
	 * 
	 * @param klass The Class to use in assembling the Sniffer tree.
	 * @param key The key to use in assembling the Sniffer tree.
	 * @return The value found using a new basic Retriever.
	 */
	public static Object basicRetrieve(Class klass, String key){
		return basicRetriever(klass, key).retrieve();
	}
	
	/**
	 * Creates a basic retriever tree that looks in the following areas for the value, 
	 * where 'klass' is the value  passed for the klass argument:
	 * <ol>
	 * <li>Environment Variable Named 'PLIANT_PKEY'</li>
	 * <li>System Property Named 'PLIANT_PKEY'</li>
	 * <li>Property 'PLIANT_PKEY' found in the standard configuration file for 'klass'. @see ${link Paths} </li>
	 * <li>Property 'PLIANT_PKEY' found in the configuration file for 'klass' with the id of 'key'. @see ${link Paths} </li>
	 * <li>JNDI Value Named 'java:comp/env/PLIANT_PKEY'</li>
	 * </ol>
	 * 
	 * @param klass The Class to use in assembling the Sniffer tree.
	 * @return An instance of Retriever.
	 */
	public static Retriever basicRetriever(Class klass){
		return basicRetriever(klass, PLIANT_PKEY);
	}

	/**
	 * Creates a basic Retriever according to @{link Retriever#basicRetriever} and retrieves the value.
	 * 
	 * @param klass The Class to use in assembling the Sniffer tree.
	 * @return The value found using a basic Retriever and the PLIANT_PKEY.
	 */
	public static Object basicRetrieve(Class klass){
		return basicRetriever(klass, PLIANT_PKEY).retrieve();
	}
	
	/**
	 * Empty Constructor
	 */
	public Retriever() {
		super();
		errorMessage = "Failed to retrieve the intended value.";
	}
	
	/**
	 * Empty Constructor
	 */
	public Retriever(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	/* (non-Javadoc)
	 * @see org.healthsciencessc.core.sniff.Sniffer#retrieve()
	 */
	@Override
	public Object retrieve() {
		for(Sniffer sniffer : sniffers){
			try{
				return sniffer.retrieve();
			}
			catch(NotFoundException e){}
		}
		throw new NotFoundException(errorMessage);
	}
	
	/**
	 * Adds a new {@link Sniffer} to this Retriever.
	 * @param sniffer The {@link Sniffer} to 
	 * @return This Retriever.
	 */
	public Retriever addSniffer(Sniffer sniffer){
		sniffers.add(sniffer);
		return this;
	}
	
	/**
	 * Adds a sniffer for a environment variable.
	 * @param name The name of the variable.
	 * @return This Retriever.
	 */
	public Retriever environmentVariable(String name){
		addSniffer(new EnvironmentSniffer(name));
		return this;
	}
	
	/**
	 * Adds a sniffer for a system property.
	 * @param name The name of the property.
	 * @return This Retriever.
	 */
	public Retriever systemProperty(String name){
		addSniffer(new SystemPropertySniffer(name));
		return this;
	}
	
	/**
	 * Adds a sniffer for a container value from the InitialContext.
	 * @param name The name of the value.
	 * @return This Retriever.
	 */
	public Retriever initialContext(String name){
		addSniffer(new InitialContextSniffer(name));
		return this;
	}
	
	/**
	 * Adds a sniffer for a content from a resource.
	 * @param url The URL to the content.
	 * @return This Retriever.
	 */
	public Retriever content(URL url){
		addSniffer(new ContentSniffer(url));
		return this;
	}
	
	/**
	 * Adds a sniffer for a content from a resource.
	 * @param uri The URI to the content.
	 * @return This Retriever.
	 */
	public Retriever content(URI uri){
		addSniffer(new ContentSniffer(uri));
		return this;
	}
	
	/**
	 * Adds a sniffer for a content from a resource.
	 * @param file The File to the content.
	 * @return This Retriever.
	 */
	public Retriever content(File file){
		addSniffer(new ContentSniffer(file));
		return this;
	}
	
	/**
	 * Adds a sniffer for a content from a resource.
	 * @param pathToResource A String containing the complete URL spec to the content.
	 * @return This Retriever.
	 */
	public Retriever content(String pathToResource){
		addSniffer(new ContentSniffer(pathToResource));
		return this;
	}
	
	/**
	 * Adds a sniffer for a content from a resource.
	 * @param klass The Class to use to load the resource from the classpath.  If null, the ContentSniffer.class is used.
	 * @param path The path through the classpath to the resource.  Follows the {@link Class#getResource(String)} spec.
	 * @return This Retriever.
	 */
	public Retriever content(Class klass, String path){
		addSniffer(new ContentSniffer(klass, path));
		return this;
	}
	
	/**
	 * Adds a sniffer for a property value in a property file.
	 * @param url The URL to the properties file.
	 * @return This Retriever.
	 */
	public Retriever property(URL url, String property){
		addSniffer(new PropertySniffer(url, property));
		return this;
	}
	
	/**
	 * Adds a sniffer for a property value in a property file.
	 * @param uri The URI to the properties file.
	 * @return This Retriever.
	 */
	public Retriever property(URI uri, String property) {
		addSniffer(new PropertySniffer(uri, property));
		return this;
	}
	
	/**
	 * Adds a sniffer for a property value in a property file.
	 * @param file The File to the properties file.
	 * @return This Retriever.
	 */
	public Retriever property(File file, String property) {
		addSniffer(new PropertySniffer(file, property));
		return this;
	}
	
	/**
	 * Adds a sniffer for a property value in a property file.
	 * @param pathToResource A String containing the complete URL spec to the properties file.
	 * @return This Retriever.
	 */
	public Retriever property(String pathToResource, String property) {
		addSniffer(new PropertySniffer(pathToResource, property));
		return this;
	}
	
	/**
	 * Adds a sniffer for a property value in a property file.
	 * @param klass The Class to use to load the properties file from the classpath.  If null, the PropertySniffer.class is used.
	 * @param path The path through the classpath to the properties file.  Follows the {@link Class#getResource(String)} spec.
	 * @return This Retriever.
	 */
	public Retriever property(Class klass, String path, String property){
		addSniffer(new PropertySniffer(klass, path, property));
		return this;
	}
}
