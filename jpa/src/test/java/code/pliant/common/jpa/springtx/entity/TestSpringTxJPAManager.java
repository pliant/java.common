package code.pliant.common.jpa.springtx.entity;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import code.pliant.common.jpa.AbstractBootstrapJPAManager;

/**
 * Spring based implementation of the AbstractBootstrapJPAManager.
 * 
 * @author Daniel Rugg
 */
@Configuration
@ImportResource(value="classpath*:springtx-context.xml")
public class TestSpringTxJPAManager extends AbstractBootstrapJPAManager {

	/**
	 * 
	 */
	public TestSpringTxJPAManager() {
		super("springtx");
	}

	/**
	 * Provides the managed EntityManagerFactory as a \@Bean configuration. The name of the bean
	 * has to be the same as the name of the persistenceUnit assigned in the constructor.
	 */
	@Bean(name = "springtx")
	public EntityManagerFactory entityManagerFactory() {
		return super.getEntityManagerFactory();
	}

	
	@Bean(name="jpaTransactionManager")
	public PlatformTransactionManager jpaTransactionManager() {
		return new JpaTransactionManager(entityManagerFactory());
	}
}
