package code.pliant.common.jpa.springtx.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import code.pliant.common.jpa.dao.AbstractDAOService;


/**
 * Test implementation a DaoService.
 * 
 * @author Daniel Rugg
 */
@Repository
@Transactional(propagation=Propagation.SUPPORTS)
public class TestSpringTxDAOServiceImpl extends AbstractDAOService<TestSpringTxEntity>
implements TestSpringTxDAOService {

	@PersistenceContext(unitName="springtx")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/* (non-Javadoc)
	 * @see org.healthsciencessc.jpa.entity.TestDaoService#findByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public TestSpringTxEntity findByPrimaryKey(Integer pkey) {
		return entityManager.find(TestSpringTxEntity.class, pkey);
	}
}
