/**
 * 
 */
package code.pliant.common.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author Daniel Rugg
 *
 */
@Component
public class ComponentValues {

	/**
	 * The name of the realm to use if one is not provide.
	 */
	@Value("$test{test.key1}")
	private String testKey1;
	
	/**
	 * The name of the realm to use if one is not provide.
	 */
	@Value("$test{test.key2}")
	private String testKey2;
	
	/**
	 * The name of the realm to use if one is not provide.
	 */
	@Value("$over{test.key1}")
	private String testOverKey1;
	
	/**
	 * The name of the realm to use if one is not provide.
	 */
	@Value("$over{test.key2}")
	private String testOverKey2;

	/**
	 * The name of the realm to use if one is not provide.
	 */
	@Value("$encrypt{test.key1}")
	private String testEncryptKey1;
	
	/**
	 * The name of the realm to use if one is not provide.
	 */
	@Value("$encrypt{test.key2}")
	private String testEncryptKey2;

	/**
	 * The name of the realm to use if one is not provide.
	 */
	@Value("$encryptOver{test.key1}")
	private String testEncryptOverKey1;
	
	/**
	 * The name of the realm to use if one is not provide.
	 */
	@Value("$encryptOver{test.key2}")
	private String testEncryptOverKey2;	
	
	/**
	 * @return the testKey1
	 */
	public String getTestKey1() {
		return testKey1;
	}
	
	/**
	 * @return the testKey2
	 */
	public String getTestKey2() {
		return testKey2;
	}
	
	/**
	 * @return the testOverKey1
	 */
	public String getTestOverKey1() {
		return testOverKey1;
	}
	
	/**
	 * @return the testOverKey2
	 */
	public String getTestOverKey2() {
		return testOverKey2;
	}
	
	/**
	 * @return the testEncryptKey1
	 */
	public String getTestEncryptKey1() {
		return testEncryptKey1;
	}
	
	/**
	 * @return the testEncryptKey2
	 */
	public String getTestEncryptKey2() {
		return testEncryptKey2;
	}
	
	/**
	 * @return the testEncryptOverKey1
	 */
	public String getTestEncryptOverKey1() {
		return testEncryptOverKey1;
	}

	/**
	 * @return the testEncryptOverKey2
	 */
	public String getTestEncryptOverKey2() {
		return testEncryptOverKey2;
	}
}
