package code.pliant.common.jpa.springtx;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import code.pliant.common.jpa.springtx.entity.TestSpringTxDAOService;
import code.pliant.common.jpa.springtx.entity.TestSpringTxEntity;


/**
 * 
 * 
 * @author Daniel Rugg
 */
@Service
public class PsuedoServiceImpl implements PsuedoService {

	@Autowired
	TestSpringTxDAOService testDaoService;

	/* (non-Javadoc)
	 * @see org.healthsciencessc.jpa.springtx.PsuedoService#doCreate(org.healthsciencessc.jpa.springtx.entity.TestSpringTxEntity)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void doCreate(TestSpringTxEntity entity){
		testDaoService.create(entity);
	}
	
	/* (non-Javadoc)
	 * @see org.healthsciencessc.jpa.springtx.PsuedoService#retrieveAndUpdate(org.healthsciencessc.jpa.springtx.entity.TestSpringTxEntity)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void retrieveAndUpdate(TestSpringTxEntity entity){
		TestSpringTxEntity pulled = testDaoService.findByPrimaryKey(entity.getPkey());
		Assert.assertEquals(entity.getPkey(), pulled.getPkey());
		Assert.assertEquals(entity.getName(), pulled.getName());
		Assert.assertEquals(entity.getActive(), pulled.getActive());
		
		// Update and Retrieve
		entity.setName("DAVE");
		entity = testDaoService.update(entity);
	}

	/* (non-Javadoc)
	 * @see org.healthsciencessc.jpa.springtx.PsuedoService#retrieveAndDelete(org.healthsciencessc.jpa.springtx.entity.TestSpringTxEntity)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void retrieveAndDelete(TestSpringTxEntity entity){
		TestSpringTxEntity 
		pulled = testDaoService.findByPrimaryKey(1);
		Assert.assertEquals(entity.getPkey(), pulled.getPkey());
		Assert.assertEquals(entity.getName(), pulled.getName());
		Assert.assertEquals(entity.getActive(), pulled.getActive());
		
		// Delete and Retrieve
		testDaoService.delete(entity);
		pulled = testDaoService.findByPrimaryKey(1);
		Assert.assertNull(pulled);
	}
}
