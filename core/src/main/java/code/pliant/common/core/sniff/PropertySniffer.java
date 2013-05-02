package code.pliant.common.core.sniff;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

import code.pliant.common.core.NotFoundException;
import code.pliant.common.core.Strings;


/**
 * A Sniffer that looks for the value of a property in a properties file.
 * 
 * @author dfr2
 */
public class PropertySniffer implements Sniffer {
	
	URL url = null;
	
	String property = null;
	
	/**
	 * @param url The location of the property file.
	 * @param property The key of the property to lookup.
	 */
	public PropertySniffer(URL url, String property) {
		super();
		this.url = url;
		this.property = property;
	}

	/**
	 * 
	 * @param uri The location of the property file.
	 * @param property The key of the property to lookup.
	 */
	public PropertySniffer(URI uri, String property){
		super();
		try {
			this.url = uri.toURL();
			this.property = property;
		}
		catch (Exception e) {
			throw new RuntimeException(new StringBuilder("Unable to get URL from URI '").append(uri).append("'.").toString(), e);
		}
	}

	/**
	 * 
	 * @param file The location of the property file.
	 * @param property The key of the property to lookup.
	 */
	public PropertySniffer(File file, String property){
		super();
		try {
			this.url = file.toURI().toURL();
			this.property = property;
		}
		catch (Exception e) {
			throw new RuntimeException(new StringBuilder("Unable to get URL from File '").append(file).append("'.").toString(), e);
		}
	}

	/**
	 * 
	 * @param resourcePath The location of the property file.
	 * @param property The key of the property to lookup.
	 */
	public PropertySniffer(String resourcePath, String property){
		super();
		try {
			this.url = new URL(resourcePath);
			this.property = property;
		}
		catch (Exception e) {
			throw new RuntimeException(new StringBuilder("Unable to get URL from String '").append(resourcePath).append("'.").toString(), e);
		}
	}

	/**
	 * 
	 * @param klass A class to use to lookup the property file with.
	 * @param path The location of the property file relative to the Class.
	 * @param property The key of the property to lookup.
	 */
	@SuppressWarnings("rawtypes")
	public PropertySniffer(Class klass, String path, String property){
		super();
		if(klass == null){
			klass = PropertySniffer.class;
		}
		this.url = klass.getResource(path);
		this.property = property;
	}

	/* (non-Javadoc)
	 * @see org.healthsciencessc.core.sniff.Sniffer#retrieve()
	 */
	@Override
	public Object retrieve() {
		Properties props = new Properties();
		if(url != null){
			try {
				props.load(url.openStream());
				String value = props.getProperty(property);
				if(Strings.isValid(value)){
					Retriever.inform(getClass(), value, url.toString());
					return value;
				}
			}
			catch (IOException e) {
				throw new NotFoundException(e);
			}
		}
		throw Sniffer.NFE;
	}
}
