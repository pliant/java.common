package code.pliant.common.jms;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.pool.XaPooledConnectionFactory;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * Abstract implementation to provide configurations for ActiveMQ connections.
 * 
 * @author Daniel Rugg
 */
public abstract class AbstractActiveMQConfiguration {

	/**
	 * Creates a basic {@link ActiveMQComponent}
	 * @param configuration
	 * @return
	 */
	protected ActiveMQComponent createActiveMQComponent(JmsConfiguration configuration){
		ActiveMQComponent component = new ActiveMQComponent();
		component.setConfiguration(configuration);
		return component;
	}
	
	/**
	 * Creates a basic {@link JmsConfiguration}
	 * @param connectionFactory
	 * @param transacted
	 * @param transactionManager
	 * @param concurrentConsumers
	 * @return
	 */
	protected JmsConfiguration createJmsConfiguration(ConnectionFactory connectionFactory, 
			boolean transacted, PlatformTransactionManager transactionManager, int concurrentConsumers){
		JmsConfiguration config = createJmsConfiguration(connectionFactory);
		config.setTransacted(transacted);
		config.setTransactionManager(transactionManager);
		config.setConcurrentConsumers(concurrentConsumers);
		return config;
	}
	
	/**
	 * Creates a basic {@link JmsConfiguration}
	 * @param connectionFactory
	 * @return
	 */
	protected JmsConfiguration createJmsConfiguration(ConnectionFactory connectionFactory){
		JmsConfiguration config = new JmsConfiguration();
		config.setConnectionFactory(connectionFactory);
		return config;
	}
	
	/**
	 * Creates a basic {@link PooledConnectionFactory}
	 * @param connectionFactory
	 * @param maxConnections
	 * @param maximumActive
	 * @return
	 */
	protected PooledConnectionFactory createPooledConnectionFactory(ConnectionFactory connectionFactory, 
			int maxConnections, int maximumActive){
		PooledConnectionFactory pool = new PooledConnectionFactory();
		pool.setConnectionFactory(connectionFactory);
		pool.setMaxConnections(maxConnections);
		pool.setMaximumActive(maximumActive);
		return pool;
	}

	/**
	 * Creates a basic {@link XaPooledConnectionFactory}
	 * @param connectionFactory
	 * @param maxConnections
	 * @param maximumActive
	 * @return
	 */
	protected XaPooledConnectionFactory createXaPooledConnectionFactory(ConnectionFactory connectionFactory, 
			int maxConnections, int maximumActive){
		XaPooledConnectionFactory pool = new XaPooledConnectionFactory();
		pool.setConnectionFactory(connectionFactory);
		pool.setMaxConnections(maxConnections);
		pool.setMaximumActive(maximumActive);
		return pool;
	}
	
	/**
	 * Creates a basic {@link ActiveMQConnectionFactory}.
	 * @param brokerURL
	 * @param userName
	 * @param password
	 * @param clientIDPrefix
	 * @return
	 */
	protected ActiveMQConnectionFactory createActiveMQConnectionFactory(String brokerURL, 
			String userName, String password, String clientIDPrefix){
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		factory.setBrokerURL(brokerURL);
		factory.setUserName(userName);
		factory.setPassword(password);
		factory.setClientIDPrefix(clientIDPrefix);
		return factory;
	}

	
	/**
	 * Creates a basic {@link ActiveMQXAConnectionFactory}.
	 * @param brokerURL
	 * @param userName
	 * @param password
	 * @param clientIDPrefix
	 * @return
	 */
	protected ActiveMQXAConnectionFactory createActiveMQXAConnectionFactory(String brokerURL, 
			String userName, String password, String clientIDPrefix){
		ActiveMQXAConnectionFactory factory = new ActiveMQXAConnectionFactory();
		factory.setBrokerURL(brokerURL);
		factory.setUserName(userName);
		factory.setPassword(password);
		factory.setClientIDPrefix(clientIDPrefix);
		return factory;
	}
	
	/**
	 * Creates a basic {@link JmsTransactionManager}.
	 * @param nestedTransactionAllowed
	 * @param validateExistingTransaction
	 * @return
	 */
	protected JmsTransactionManager createJmsTransactionManager(ConnectionFactory connectionFactory, boolean nestedTransactionAllowed, boolean validateExistingTransaction){
		JmsTransactionManager manager = createJmsTransactionManager(connectionFactory);
		manager.setValidateExistingTransaction(validateExistingTransaction);
		manager.setNestedTransactionAllowed(nestedTransactionAllowed);
		return manager;
	}
	
	/**
	 * Creates a basic {@link JmsTransactionManager}.
	 * @return
	 */
	protected JmsTransactionManager createJmsTransactionManager(ConnectionFactory connectionFactory){
		JmsTransactionManager manager = new JmsTransactionManager();
		manager.setConnectionFactory(connectionFactory);
		return manager;
	}
}
