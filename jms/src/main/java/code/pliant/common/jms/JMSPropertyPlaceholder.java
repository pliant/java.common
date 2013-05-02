package code.pliant.common.jms;

import org.jasypt.spring.properties.EncryptablePreferencesPlaceholderConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import code.pliant.common.core.sniff.Retriever;
import code.pliant.common.spring.PropertyPlaceholder;


/**
 * Creates a {@link PropertyPlaceholderConfigurer} for a properties file at 
 * classpath:code.pliant.common.jms-config.props.  If a key named 'JMSPKEY' is found in the 
 * default {@link Retriever#basicRetriever(Class)} path for this class, an 
 * {@link EncryptablePreferencesPlaceholderConfigurer} is returned.
 * 
 * @author Daniel Rugg
 */
@Component
public class JMSPropertyPlaceholder extends PropertyPlaceholder {

	@Bean(name="jmsPropertyPlaceholder")
	public PropertyPlaceholderConfigurer jmsPropertyPlaceholder(){
		return create("JMSPKEY", "$jms{");
	}
}
