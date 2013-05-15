package code.pliant.common.core.sniff;

import java.io.File;
import java.net.URI;
import java.net.URL;

import code.pliant.common.core.Resources;
import code.pliant.common.core.Strings;

/**
 * A Sniffer that looks for content in a resource found at a specific URL.  Can be a file on the file system, 
 * on the ClassPath, or on another server.  Any valid URL can be used.
 * 
 * @author Daniel Rugg
 */
public class ContentSniffer implements Sniffer {
	
	URL url = null;
	
	/**
	 * @param url
	 */
	public ContentSniffer(URL url) {
		super();
		this.url = url;
	}

	/**
	 * 
	 * @param uri
	 */
	public ContentSniffer(URI uri){
		super();
		try {
			this.url = uri.toURL();
		}
		catch (Exception e) {
			throw new RuntimeException(new StringBuilder("Unable to get URL from URI '").append(uri).append("'.").toString(), e);
		}
	}

	/**
	 * 
	 * @param file
	 */
	public ContentSniffer(File file){
		super();
		try {
			this.url = file.toURI().toURL();
		}
		catch (Exception e) {
			throw new RuntimeException(new StringBuilder("Unable to get URL from File '").append(file).append("'.").toString(), e);
		}
	}

	/**
	 * 
	 * @param resourcePath
	 */
	public ContentSniffer(String resourcePath){
		super();
		try {
			this.url = new URL(resourcePath);
		}
		catch (Exception e) {
			throw new RuntimeException(new StringBuilder("Unable to get URL from String '").append(resourcePath).append("'.").toString(), e);
		}
	}

	/**
	 * 
	 * @param klass
	 * @param path
	 */
	@SuppressWarnings("rawtypes")
	public ContentSniffer(Class klass, String path){
		super();
		if(klass == null){
			klass = ContentSniffer.class;
		}
		this.url = klass.getResource(path);
	}

	/* (non-Javadoc)
	 * @see org.healthsciencessc.core.sniff.Sniffer#retrieve()
	 */
	@Override
	public Object retrieve(){
		if(url != null){
			String value = Resources.getResourceAsString(url);
			if(Strings.isValid(value)){
				Retriever.inform(getClass(), value, url.toString());
				return value;
			}
		}
		throw Sniffer.NFE;
	}
}
