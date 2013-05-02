package code.pliant.common.spring;

import java.io.IOException;
import java.util.ArrayList;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.PBEConfig;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.spring.properties.EncryptablePropertyPlaceholderConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import code.pliant.common.core.NotFoundException;
import code.pliant.common.core.Paths;
import code.pliant.common.core.Strings;
import code.pliant.common.core.sniff.Retriever;


/**
 * Provides a base class that can register PropertyPlaceholders in an annotated fashion in a simple way.  
 * Creation methods for both normal and encrypted properties are available through extending this class.
 * 
 * @author Daniel Rugg
 */
public abstract class PropertyPlaceholder implements ApplicationContextAware{


	public static final String PREFIX_START = "$";
	
	public static final String PREFIX_END = "{";

	/**
	 * Creates a type of PropertyPlaceholderConfigurer based on the available values.  If the implementing Class and 
	 * lookupKey value can be used to find a value using the {@link Retriever} default lookup pattern, 
	 * an {@link EncryptablePropertyPlaceholderConfigurer} is created using that value, else a 
	 * {@link PropertyPlaceholderConfigurer} is created.
	 * 
	 * @param lookupKey The lookup key used to search for a decryption key.
	 * @param prefix The property prefix that is used to reference the properties found.
	 * @param resources Optional resource URLs to use to find the properties files.  If none are provided then 
	 * the default configuration URLs are used.
	 * @return A PropertyPlaceholderConfigurer
	 */
	protected PropertyPlaceholderConfigurer create(String lookupKey, String prefix, String...resources){
		return create(this.getClass(), lookupKey, prefix, resources);
	}

	/**
	 * Creates a type of PropertyPlaceholderConfigurer based on the available values.  If the Class and 
	 * lookupKey values can be used to find a value using the {@link Retriever} default lookup pattern, 
	 * an {@link EncryptablePropertyPlaceholderConfigurer} is created using that value, else a 
	 * {@link PropertyPlaceholderConfigurer} is created.
	 * 
	 * @param klass The Class used to search for a decryption key.
	 * @param lookupKey The lookup key used to search for a decryption key.
	 * @param prefix The property prefix that is used to reference the properties found.
	 * @param resources Optional resource URLs to use to find the properties files.  If none are provided then 
	 * the default configuration URLs are used.
	 * @return A PropertyPlaceholderConfigurer
	 */
	@SuppressWarnings("rawtypes")
	protected PropertyPlaceholderConfigurer create(Class klass, String lookupKey, String prefix, String...resources){
		Object value;
		try {
			if(lookupKey != null){
				value = Retriever.basicRetrieve(klass, lookupKey);
				return createEncryptablePlaceholder(prefix, value.toString(), resources);
			}
		}
		catch (NotFoundException e) {}
		return createPlaceholder(prefix, resources);
	}
	
	/**
	 * Creates a placeholder using a prefix and 1 to many Resource URLs
	 * @param prefix A prefix how the properties are identified.  It is validated using the validatePrefix
	 * method.
	 * @param resources Optional resource URLs to use to find the properties files.  If none are provided then 
	 * the default configuration URLs are used.
	 * @return A PropertyPlaceholderConfigurer
	 */
	protected PropertyPlaceholderConfigurer createPlaceholder(String prefix, String...resources){
		PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
		configurer.setPlaceholderPrefix(validatePrefix(prefix));
		configurer.setLocations(resolveResources(resources));
		return configurer;
	}
	
	/**
	 * Creates a EncryptablePropertyPlaceholderConfigurer that can decrypt properties surrounded by the ENC(...) markup.
	 * @param prefix The prefix the properties will be identified by.
	 * @param privateKey The password value to use for encrypting and decrypting.
	 * @param resources Optional resource URLs to use to find the properties files.  If none are provided then 
	 * the default configuration URLs are used.
	 * @return A EncryptablePropertyPlaceholderConfigurer instance.
	 */
	protected EncryptablePropertyPlaceholderConfigurer createEncryptablePlaceholder(String prefix, String privateKey, String...resources){
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(privateKey);
		return createEncryptablePlaceholder(prefix, config, resources);
	}
	
	/**
	 * Creates a EncryptablePropertyPlaceholderConfigurer that can decrypt properties surrounded by the ENC(...) markup.
	 * @param prefix The prefix the properties will be identified by.
	 * @param config The PBEConfig that is use to configure the default StandardPBEStringEncryptor used.
	 * @param resources The URL strings that point to the properties files to be loaded.
	 * @return A EncryptablePropertyPlaceholderConfigurer instance.
	 */
	protected EncryptablePropertyPlaceholderConfigurer createEncryptablePlaceholder(String prefix, PBEConfig config, String...resources){
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setConfig(config);
		return createEncryptablePlaceholder(prefix, encryptor, resources);
	}
	
	/**
	 * Creates a EncryptablePropertyPlaceholderConfigurer that can decrypt properties surrounded by the ENC(...) markup.
	 * @param prefix The prefix the properties will be identified by.
	 * @param encryptor The StringEncrypter that is use to determine how to decrypt encrypted properties.
	 * @param resources The URL strings that point to the properties files to be loaded.
	 * @return A EncryptablePropertyPlaceholderConfigurer instance.
	 */
	protected EncryptablePropertyPlaceholderConfigurer createEncryptablePlaceholder(String prefix, StringEncryptor encryptor, String...resources){
		EncryptablePropertyPlaceholderConfigurer configurer = new EncryptablePropertyPlaceholderConfigurer(encryptor);
		configurer.setPlaceholderPrefix(validatePrefix(prefix));
		configurer.setLocations(resolveResources(resources));
		return configurer;
	}
	
	/**
	 * Validates the prefix, insuring that the $ and { are the first and last characters of the prefix.
	 * @param prefix
	 * @return A prefix that is guaranteed to have the $ and { on the edges.
	 */
	protected String validatePrefix(String prefix){
		if(!Strings.isValid(prefix)){
			return PropertyPlaceholderConfigurer.DEFAULT_PLACEHOLDER_PREFIX;
		}
		if(!prefix.startsWith(PREFIX_START)){
			prefix = PREFIX_START + prefix;
		}
		if(!prefix.endsWith(PREFIX_END)){
			prefix = prefix + PREFIX_END;
		}
		return prefix;
	}
	
	/**
	 * Resolves the resource URL strings into resources.
	 * @param urls
	 * @return Array of {@link Resource} pointers.
	 */
	protected Resource[] resolveResources(String...urls){
		if(urls == null || urls.length < 1){
			urls = new String[]{createDefaultResource()};
		}
		ArrayList<Resource> list = new ArrayList<Resource>();
		for(String url : urls){
			try {
				Resource[] resources = context.getResources(url);
				if(resources != null && resources.length > 0){
					for(Resource resource : resources){
						list.add(resource);
					}
				}
			}
			catch (IOException e) {
				// Warn When You Get To It.
			}
		}
		return list.toArray(new Resource[list.size()]);
	}
	
	/**
	 * Creates a Resource URL based on the package name where the implementing class is.  The value 
	 * returned consists of the 'classpath:' + the package name + '-config.props'.
	 * @return A String
	 */
	protected String createDefaultResource(){
		return Paths.classPathConfigFileName(getClass());
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
	ApplicationContext context;
}
