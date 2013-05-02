package code.pliant.common.core;

import java.util.Properties;

import code.pliant.common.core.sniff.Retriever;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.jasypt.util.text.TextEncryptor;

/**
 * Provides helper methods for dealing with encrypted information, such as an encrypted property within a properties file.
 * 
 * @author Daniel Rugg
 */
public class Encryption {
	
	/**
	 * Creates a Properties instance and populates it with the properties file found at the resourcePath location.
	 * 
	 * @param klass A class to used as a relative location of the path.  If null, ResourceHelper.class is used.
	 * @param resourcePath The path to find the resource at.
	 * @param encryptor A {@link StringEncryptor} used to decrypt property values that use the ENC(*) pattern.
	 * @return A Properties file loaded with the properties of the resource indicated.
	 */
	public static Properties getProperties(Class<?> klass, String resourcePath, StringEncryptor encryptor){
		Properties properties = Resources.getResourceAsProperties(klass, resourcePath);
		return new EncryptableProperties(properties, encryptor);
	}
	
	/**
	 * Creates a Properties instance and populates it with the properties file found at the resourcePath location.
	 * 
	 * @param klass A class to used as a relative location of the path.  If null, ResourceHelper.class is used.
	 * @param resourcePath The path to find the resource at.
	 * @param encryptor A {@link TextEncryptor} used to decrypt property values that use the ENC(*) pattern.
	 * @return A Properties file loaded with the properties of the resource indicated.
	 */
	public static Properties getProperties(Class<?> klass, String resourcePath, TextEncryptor encryptor){
		Properties properties = Resources.getResourceAsProperties(klass, resourcePath);
		return new EncryptableProperties(properties, encryptor);
	}
	
	/**
	 * Creates a Properties instance and populates it with the properties file found at the resourcePath location.
	 * 
	 * @param klass A class to used as a relative location of the path.  If null, ResourceHelper.class is used.
	 * @param resourcePath The path to find the resource at.
	 * @param retriever A {@link Retriever} used to decrypt property values that use the ENC(*) pattern.
	 * @return A Properties file loaded with the properties of the resource indicated.\
	 */
	public static Properties getProperties(Class<?> klass, String resourcePath, Retriever retriever){
		Object salt = retriever.retrieve();
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(salt.toString());
		return getProperties(klass, resourcePath, encryptor);
	}
}
