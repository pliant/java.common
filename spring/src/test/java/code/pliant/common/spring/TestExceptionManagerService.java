package code.pliant.common.spring;


import code.pliant.common.core.NonRecoverableException;
import code.pliant.common.core.NotFoundException;
import code.pliant.common.core.RecoverableException;
import code.pliant.common.test.AnnotationConfigContextLoader;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spring.code.pliant.common.ExceptionManagerService;


/**
 * Tests the automatic injection of InspectorService components.
 * 
 * @author Daniel Rugg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={"code.pliant.common", "spring.code.pliant.common"},
		loader=AnnotationConfigContextLoader.class
)
public class TestExceptionManagerService {
	
	@Autowired
	ExceptionManagerService manager;
	
	@Test
	public void testPropertyConfiguration(){
		Assert.assertTrue(manager.isRecoverable(new RecoverableException()));
		Assert.assertFalse(manager.isRecoverable(new NonRecoverableException()));
		
		// Not Found Is Injected By Spring
		Assert.assertTrue(manager.isRecoverable(new NotFoundException()));
		Assert.assertFalse(manager.isRecoverable(new RuntimeException()));
	}
}
