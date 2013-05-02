package code.pliant.common.core;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test the {@link ExceptionManager}.
 * 
 * @author Daniel Rugg
 */
public class TestExceptionManager {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.healthsciencessc.core.common.ExceptionManager#isRecoverable(java.lang.Exception)}.
	 */
	@Test
	public void testIsRecoverable() {
		ExceptionManager manager = new ExceptionManager();
		Assert.assertTrue(manager.isRecoverable(new RecoverableException()));
		Assert.assertFalse(manager.isRecoverable(new NonRecoverableException()));
		
		manager = new ExceptionManager(true);
		Assert.assertTrue(manager.isRecoverable(new RecoverableException()));
		Assert.assertFalse(manager.isRecoverable(new NonRecoverableException()));
		
		manager = new ExceptionManager(false);
		Assert.assertFalse(manager.isRecoverable(new RecoverableException()));
		Assert.assertFalse(manager.isRecoverable(new NonRecoverableException()));
	}

}
