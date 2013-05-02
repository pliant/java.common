package code.pliant.common.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * The JPA manager is responsible for managing the aspects of utilizing JPA, such as 
 * managing the lifecycle of the EntityManage, bootstrapping installation specific 
 * code, etc.  Implementations of this interface are going to be stateful and not thread 
 * safe.  It should not be used as a Singleton across thread.  It can be used in a pool, 
 * but it is probably just as performant to create a new one for each use.
 * 
 * @author Daniel Rugg
 */
public interface JPAManager {
	
	/**
	 * The type name used in standard configuration file names for JPA properties files.
	 */
	public static final String CONFIG_TYPE = "jpa";

	/**
	 * Gets an instance of the EntityManager from the internal EntityManagerFactory.  
	 * If a manager has not been created, one will be created and initialized appropriately.
	 * 
	 * @return A new EntityManager instance.
	 */
	public EntityManager getEntityManager();
	
	/**
	 * Gets an instance of the EntityManagerFactory that is created by this JPAManager.
	 * @return The EntityManagerFactory that is managed by this.
	 */
	public EntityManagerFactory getEntityManagerFactory();
}
