package code.pliant.common.jpa.springtx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import code.pliant.common.jpa.springtx.entity.TestSpringTxEntity;
import code.pliant.common.test.AnnotationConfigContextLoader;
import code.pliant.common.test.JNDIUtils;


/**
 * Test the usage of the JPAManager through Spring.  Requires that a JNDI Context be bootstrapped and returns
 * 
 * @author Daniel Rugg
 */
@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(
		locations={"code.pliant.common.jpa.springtx"},
		loader=AnnotationConfigContextLoader.class
)

public class TestSpringTransaction {

	// Create JNDI DataSource
	static{
		JNDIUtils.registerDatabase("jdbc/test", "testdb");
	}
	
	@Autowired
	PsuedoService service;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testCRUD() throws Exception {
		TestSpringTxEntity entity = new TestSpringTxEntity();
		entity.setPkey(1);
		entity.setName("BOB");
		entity.setActive(true);
		service.doCreate(entity);
		service.retrieveAndUpdate(entity);
		service.retrieveAndDelete(entity);
	}
}
