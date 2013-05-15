package jms.code.pliant.common;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;

import code.pliant.common.jms.AbstractActiveMQConfiguration;


/**
 * <p>
 * Provided an example of how to use the {@link AbstractActiveMQConfiguration} class.  Also serves 
 * as a default implementation of the {@link AbstractActiveMQConfiguration} class.
 * </p>
 * <p>
 * To enable this component, use the @Import() annotation with this class referenced in it.
 * </p>
 * 
 * @author Daniel Rugg
 */
@Configuration
public class ActiveMQConfiguration extends AbstractActiveMQConfiguration {
	
	public static final String JMS_SPRING_TRANSACTION_POLICY = "jmsSpringTransactionPolicy";
	public static final String JMS_TRANSACTION_MANAGER = "jmsTransactionManager";

	public static final String MQ_COMPONENT = "mq";
	public static final String MQ_JMS_CONFIGURATION = "mqJmsConfiguration";
	public static final String MQ_POOLED_CONNECTION_FACTORY = "mqPooledConnectionFactory";
	public static final String MQ_CONNECTION_FACTORY = "mqActiveMQConnectionFactory";
	
	@Value("$jms{activemq.broker.url}") String brokerURL;
	@Value("$jms{activemq.username}") String userName;
	@Value("$jms{activemq.password}") String password; 
	@Value("$jms{activemq.client.id.prefix}") String clientIDPrefix;
	@Value("$jms{activemq.pool.max.connections}") int maxConnections;
	@Value("$jms{activemq.pool.max.sessions.per}") int maximumActive;
	@Value("$jms{activemq.transacted}") boolean transacted;
	
	@Autowired(required=false)
	RedeliveryPolicy redeliveryPolicy;
	
	@Bean(name=MQ_COMPONENT)
	public ActiveMQComponent mqActiveMQComponent(){
		return createActiveMQComponent(mqJmsConfiguration());
	}
	
	@Bean(name=MQ_JMS_CONFIGURATION)
	public JmsConfiguration mqJmsConfiguration(){
		if(transacted){
			return createJmsConfiguration(mqActiveMQConnectionFactory(), true, jmsTransactionManager(), 1);
		}
		else{
			return createJmsConfiguration(mqPooledConnectionFactory());
		}
	}
	
	@Bean(name=MQ_POOLED_CONNECTION_FACTORY)
	public PooledConnectionFactory mqPooledConnectionFactory(){
		return createPooledConnectionFactory(mqActiveMQConnectionFactory(), maxConnections, maximumActive);
	}
	
	@Bean(name=MQ_CONNECTION_FACTORY)
	public ActiveMQConnectionFactory mqActiveMQConnectionFactory(){
		ActiveMQConnectionFactory factory = createActiveMQConnectionFactory(brokerURL, userName, password, clientIDPrefix);
		if(redeliveryPolicy != null){
			factory.setRedeliveryPolicy(redeliveryPolicy);
		}
		return factory;
	}
	
	@Bean(name=JMS_TRANSACTION_MANAGER)
	public JmsTransactionManager jmsTransactionManager(){
		return createJmsTransactionManager(mqPooledConnectionFactory());
	}
	
	@Bean(name=JMS_SPRING_TRANSACTION_POLICY)
	public SpringTransactionPolicy jmsSpringTransactionPolicy(){
		SpringTransactionPolicy policy = new SpringTransactionPolicy();
		policy.setTransactionManager(jmsTransactionManager());
		policy.setPropagationBehaviorName("PROPAGATION_REQUIRED");
		return policy;
	}
}
