package code.pliant.common.jpa.notx;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import code.pliant.common.jpa.notx.entity.TestNoTxDAOService;
import code.pliant.common.jpa.notx.entity.TestNoTxEntity;
import code.pliant.common.test.AnnotationConfigContextLoader;
import code.pliant.common.test.JNDIUtils;


/**
 * This is an example of setting up and using the JPAManager without requiring transaction support beyond 
 * atomic actions.
 * 
 * @author Daniel Rugg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={"code.pliant.common.jpa.notx"},
		loader=AnnotationConfigContextLoader.class
)
public class TestNoTransaction {
	
	// Create JNDI DataSource
	static{
		JNDIUtils.registerDatabase("jdbc/test", "testdb");
	}

	@Autowired
	TestNoTxDAOService testDaoService;

	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testCRUD() throws Exception {
		TestNoTxEntity entity = new TestNoTxEntity();
		entity.setPkey(1);
		entity.setName("BOB");
		entity.setActive(true);

		// Create and Retrieve
		testDaoService.create(entity);
		TestNoTxEntity pulled = testDaoService.findByPrimaryKey(1);
		Assert.assertEquals(entity.getPkey(), pulled.getPkey());
		Assert.assertEquals(entity.getName(), pulled.getName());
		Assert.assertEquals(entity.getActive(), pulled.getActive());
		
		// Update and Retrieve
		entity.setName("DAVE");
		entity = testDaoService.update(entity);
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
