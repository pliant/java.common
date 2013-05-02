package code.pliant.common.jpa.notx.entity;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import code.pliant.common.jpa.JPAUtils;


/**
 * Test implementation a DaoService.
 * 
 * @author Daniel Rugg
 */
@Repository
public class TestNoTxDAOServiceImpl implements TestNoTxDAOService {
	
	/* (non-Javadoc)
	 * @see org.healthsciencessc.jpa.entity.TestDaoService#findByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public TestNoTxEntity findByPrimaryKey(Integer pkey) {
		EntityManager entityManager = jpaManager.getEntityManager();
		return entityManager.find(TestNoTxEntity.class, pkey);
	}
	
	@Autowired
	TestNoTxJPAManager jpaManager;

	/* (non-Javadoc)
	 * @see org.healthsciencessc.jpa.dao.DaoService#create(java.lang.Object)
	 */
	@Override
	public void create(TestNoTxEntity entity){
		JPAUtils.createAndCommit(jpaManager.getEntityManager(), entity);
	}

	/* (non-Javadoc)
	 * @see org.healthsciencessc.jpa.dao.DaoService#update(java.lang.Object)
	 */
	@Override
	public TestNoTxEntity update(TestNoTxEntity entity) {
		return JPAUtils.updateAndCommit(jpaManager.getEntityManager(), entity);
	}

	/* (non-Javadoc)
	 * @see org.healthsciencessc.jpa.dao.DaoService#delete(java.lang.Object)
	 */
	@Override
	public void delete(TestNoTxEntity entity) {
		JPAUtils.deleteAndCommit(jpaManager.getEntityManager(), entity, entity.getPkey());
	}
}
