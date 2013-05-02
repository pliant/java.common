package code.pliant.common.camel;

import code.pliant.common.spring.PropertyPlaceholder;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * PropertyPlaceholder for the camel properties.
 * 
 * @author Daniel Rugg
 */
@Component
public class CamelPropertyPlaceholder extends PropertyPlaceholder {

	@Bean
	public PropertyPlaceholderConfigurer consentPropertyPlaceholderConfigurer(){
		return create(null, "$camel{");
	}
}
