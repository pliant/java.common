package code.pliant.common.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import code.pliant.common.core.NotFoundException;
import code.pliant.common.core.Paths;
import code.pliant.common.core.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * This abstract implementation of the JPAManager will use a properties file 
 * to override the default properties of the persistence unit being managed by
 * the EntityManager.  The configuration file is looked for on the classpath using 
 * the configFile property.
 * </p>
 * <p>
 * By default, the configuration file uses the naming convention of 
 * <code>persistenceUnit + '-jpa-config.props'</code>, and found in the same package as the
 * implementation class.  You can optionally set the full path of the 
 * configuration file by calling in to the appropriate constructor.
 * </p>
 * 
 * @author Daniel Rugg
 */
public abstract class AbstractBootstrapJPAManager implements JPAManager {
	
	private static Logger logger = LoggerFactory.getLogger(AbstractBootstrapJPAManager.class);
	
	/**
	 * The bootstrapped factory.
	 */
	private EntityManagerFactory emf = null;
	
	/**
	 * The full path to the configuration file that is bootstrapped.
	 */
	private List<String> configFilePaths = null;
	
	/**
	 * The name of the persistence unit which is supplied by the implementation.
	 */
	private String persistenceUnit = null;
	
	/**
	 * 
	 * @param persistenceUnit The name of the persistence unit to associate with the 
	 * EntityManagerFactory that is created.
	 */
	public AbstractBootstrapJPAManager(String persistenceUnit){
		super();
		this.persistenceUnit = persistenceUnit;
		if(configFilePaths == null){
			configFilePaths = new ArrayList<String>();
		}
		configFilePaths.add(Paths.rootConfigFileName(JPAManager.class, JPAManager.CONFIG_TYPE));
		configFilePaths.add(Paths.rootConfigFileName(JPAManager.class, persistenceUnit, JPAManager.CONFIG_TYPE));
		configFilePaths.add(Paths.rootConfigFileName(getClass(), persistenceUnit, JPAManager.CONFIG_TYPE));
	}
	
	/**
	 * 
	 * @param persistenceUnit The name of the persistence unit to associate with the 
	 * EntityManagerFactory that is created.
	 * @param configFilePath The path to the properties file used to override the default 
	 * properties of the persistence unit.  Relative paths start from the package of the 
	 * implementation class.
	 */
	public AbstractBootstrapJPAManager(String persistenceUnit, String configFilePath){
		super();
		this.persistenceUnit = persistenceUnit;
		if(this.configFilePaths == null){
			this.configFilePaths = new ArrayList<String>();
		}
		this.configFilePaths.add(configFilePath);
	}

	/* (non-Javadoc)
	 * @see JPAManager#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager(){
		return emf.createEntityManager();
	}
	
	/* (non-Javadoc)
	 * @see org.healthsciencessc.jpa.JPAManager#getEntityManagerFactory()
	 */
	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}

	@PostConstruct
	public void init(){
		Properties all = new Properties();
		if(configFilePaths != null){
			for(String path : configFilePaths){
				try {
					all.putAll(Resources.getResourceAsProperties(this.getClass(), path));
				}
				catch (NotFoundException e) {
					logger.warn("Unable to find JPA properties file at " + path);
				}
			}
		}
		if(all.size() > 0){
			emf = Persistence.createEntityManagerFactory(persistenceUnit, all);
		}
		else{
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
		}
	}
	
	@PreDestroy
	public void destroy(){
		if(emf != null){
			emf.close();
		}
	}

	/**
	 * @return the persistenceUnit
	 */
	public String getPersistenceUnit() {
		return persistenceUnit;
	}

	/**
	 * @param persistenceUnit the persistenceUnit to set
	 */
	public void setPersistenceUnit(String persistenceUnit) {
		this.persistenceUnit = persistenceUnit;
	}

	/**
	 * @return the configFilePath
	 */
	public List<String> getConfigFilePath() {
		return configFilePaths;
	}

	/**
	 * @param configFilePath the configFilePath to set
	 */
	public void setConfigFilePath( List<String> configFilePath) {
		this.configFilePaths = configFilePath;
	}
}
