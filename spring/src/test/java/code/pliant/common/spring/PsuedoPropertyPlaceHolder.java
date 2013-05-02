package code.pliant.common.spring;

import code.pliant.common.core.NotFoundException;
import code.pliant.common.core.Paths;
import code.pliant.common.core.sniff.Retriever;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * Configuration for use with the test classes
 * 
 * @author Daniel Rugg
 */
@Component
public class PsuedoPropertyPlaceHolder extends PropertyPlaceholder {

	/**
	 * Create the properties placeholder for the application.
	 * @return
	 */
	@Bean(name="testConfiguration")
	public PropertyPlaceholderConfigurer testConfiguration(){
		return createPlaceholder("$test{");
	}
	
	/**
	 * Create the properties placeholder for the application.
	 * @return
	 */
	@Bean(name="testOverrideConfiguration")
	public PropertyPlaceholderConfigurer testOverrideConfiguration(){
		return createPlaceholder("$over{", new String[]{
				Paths.classPathAllConfigFileName(getClass()),
				Paths.classPathAllConfigFileName(getClass(), "over")
		});
	}
	/**
	 * Create the properties placeholder for the application.
	 * @return
	 * @throws NotFoundException 
	 */
	@Bean(name="testEncryptConfiguration")
	public PropertyPlaceholderConfigurer testEncryptConfiguration() throws NotFoundException{
		Object value = Retriever.basicRetrieve(PsuedoPropertyPlaceHolder.class, "PKEY");
		return createEncryptablePlaceholder("$encrypt{", value.toString());
	}
	
	/**
	 * Create the properties placeholder for the application.
	 * @return
	 */
	@Bean(name="testEncryptOverrideConfiguration")
	public PropertyPlaceholderConfigurer testEncryptOverrideConfiguration() throws NotFoundException{
		Object value = Retriever.basicRetrieve(PsuedoPropertyPlaceHolder.class, "PKEY");
		return createEncryptablePlaceholder("$encryptOver{", value.toString(), new String[]{
				Paths.classPathAllConfigFileName(getClass()),
				Paths.classPathAllConfigFileName(getClass(), "over")
		});
	}
}
