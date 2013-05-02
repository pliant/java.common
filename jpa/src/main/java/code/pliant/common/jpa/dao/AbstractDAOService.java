/**
 * 
 */
package code.pliant.common.jpa.dao;

import javax.persistence.EntityManager;

import code.pliant.common.jpa.JPAUtils;


/**
 * 
 * @author dfr2
 */
public abstract class AbstractDAOService<T> implements DAOService<T> {

	protected EntityManager entityManager;
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/* (non-Javadoc)
	 * @see org.healthsciencessc.jpa.dao.DaoService#create(java.lang.Object)
	 */
	@Override
	public void create(T entity) {
		entityManager.persist(entity);
	}

	/* (non-Javadoc)
	 * @see org.healthsciencessc.jpa.dao.DaoService#update(java.lang.Object)
	 */
	public T update(T entity) {
		return entityManager.merge(entity);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.healthsciencessc.jpa.dao.DAOService#delete(java.lang.Object)
	 */
	public void delete(T entity){
		JPAUtils.delete(entityManager, entity);
	}
}
