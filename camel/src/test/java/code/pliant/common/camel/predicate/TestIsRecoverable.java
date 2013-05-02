package code.pliant.common.camel.predicate;

import junit.framework.Assert;

import org.apache.camel.CamelContext;
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
 * Test that the is recoverable predicate works.
 * 
 * @author Daniel Rugg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = { "code.pliant.common", "spring.code.pliant.common" },
		loader = AnnotationConfigContextLoader.class)
public class TestIsRecoverable {

	@Autowired
	CamelContext context;

	@Autowired
	PredicateTestService service;

	@Produce(uri = PredicateRouteBuilder.DIRECT_START_RECOVER)
	protected ProducerTemplate recoverTemplate;

	@Produce(uri = PredicateRouteBuilder.DIRECT_START_NONRECOVER)
	protected ProducerTemplate nonRecoverTemplate;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		service.reset();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testRecoverable() throws Exception {
		recoverTemplate.sendBody("TEST");
		Thread.yield();
		
		Assert.assertTrue(service.isRecovers());
		Assert.assertFalse(service.isNonRecovers());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testNonRecoverable() throws Exception {
		nonRecoverTemplate.sendBody("TEST");
		Thread.yield();
		
		Assert.assertFalse(service.isRecovers());
		Assert.assertTrue(service.isNonRecovers());
	}
}
