package code.pliant.common.camel;


import junit.framework.Assert;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import code.pliant.common.test.AnnotationConfigContextLoader;


/**
 * Test Specific Patterns To Show How To Use Them.
 * 
 * @author Daniel Rugg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {"code.pliant.common", "spring.code.pliant.common"},
		loader = AnnotationConfigContextLoader.class)
public class TestPatterns {
	
	@Autowired
	PatternTestService service;
	
    @Produce(uri = CamelRouteBuilder.DIRECT_START_LOOP)
    protected ProducerTemplate loop;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testLoop() throws Exception {
		loop.sendBody("LOOPIT");
		Thread.yield();
		Assert.assertEquals(3, service.getLoopCount());
	}

}
