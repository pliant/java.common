package code.pliant.common.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;

import code.pliant.common.core.Message;
import code.pliant.common.core.Reflections;


/**
 * 
 * @author Daniel Rugg
 */
public class JPAUtils {
	
	/**
	 * 
	 * @param <T>
	 * @param entityManager
	 * @param entity
	 */
	public static <T> void createAndCommit(EntityManager entityManager, T entity){
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		try{
			entityManager.persist(entity);
			transaction.commit();
		}
		catch(Exception e){
			transaction.rollback();
			Message message = new Message("Unable to create an entity using the service:")
				.add(entity.getClass())
				.add(", entity: ").add(entity);
			throw new PersistenceException(message.toString(), e);
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param entityManager
	 * @param entity
	 * @return The updated entity.
	 */
	public static<T> T updateAndCommit(EntityManager entityManager, T entity) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		try{
			entity = entityManager.merge(entity);
			transaction.commit();
			return entity;
		}
		catch(Exception e){
			transaction.rollback();
			Message message = new Message("Unable to update an entity using the service:")
				.add(entity.getClass())
				.add(", entity: ").add(entity);
			throw new PersistenceException(message.toString(), e);
		}
	}

	/**
	 * 
	 * @param entityManager
	 * @param entity
	 */
	public static <T> void deleteAndCommit(EntityManager entityManager, T entity, Object key) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		try{
			Object deleteable = entityManager.find(entity.getClass(), key);
			entityManager.remove(deleteable);
			transaction.commit();
		}
		catch(Exception e){
			transaction.rollback();
			Message message = new Message("Unable to delete an entity using the service:")
				.add(entity.getClass())
				.add(", entity: ").add(entity);
			throw new PersistenceException(message.toString(), e);
		}
	}

	/**
	 * 
	 * @param entityManager
	 * @param entity
	 */
	public static <T> void delete(EntityManager entityManager, T entity, Object key){
		Object deleteable = entityManager.find(entity.getClass(), key);
		entityManager.remove(deleteable);
	}

	/**
	 * 
	 * @param entityManager
	 * @param entity
	 */
	public static <T> void delete(EntityManager entityManager, T entity){
		Object deleteable = entityManager.find(entity.getClass(), getKeyValue(entity));
		entityManager.remove(deleteable);
	}
	
	/**
	 * 
	 * @param entity
	 * @return The value of a property that has been annotated with the Id marker.
	 */
	public static Object getKeyValue(Object entity){
		try {
			List<Object> list = Reflections.findAnnotatedValue(entity, Id.class);
			if(list.size() > 0){
				if(list.size() == 1){
					return list.get(0);
				}
				Message message = new Message("More than one Id field was found on entity ").add(entity.getClass());
				throw new PersistenceException(message.toString());
			}
			Message message = new Message("Unable to find an Id field on entity ").add(entity.getClass());
			throw new PersistenceException(message.toString());
		}
		catch (Exception e) {
			Message message = new Message("Unable to find an Id field on entity ").add(entity.getClass());
			throw new PersistenceException(message.toString(), e);
		}
	}
}
