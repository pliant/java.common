/**
 * 
 */
package code.pliant.common.jpa.dao;


/**
 * Provides a generic interface for a dao service.
 * 
 * @author dfr2
 */
public interface DAOService<T> {
	
	/**
	 * Saves an entity to the datastore.
	 * @param entity The entity to act upon.
	 */
	public void create(T entity);

	/**
	 * Updates an entity in the datastore.
	 * @param entity The entity to act upon.
	 * @return The entity with the merged values.
	 */
	public T update(T entity);

	/**
	 * Deletes an entity from the datastore.
	 * @param entity The entity to act upon.
	 */
	public void delete(T entity);
}
