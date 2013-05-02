/**
 * 
 */
package code.pliant.common.camel;

import org.apache.camel.CamelContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import code.pliant.common.test.AnnotationConfigContextLoader;


/**
 * Provides tests to validate the automated camel configuration.
 * 
 * @author Daniel Rugg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={"code.pliant.common", "spring.code.pliant.common"},
		loader=AnnotationConfigContextLoader.class
)
public class TestCamelConfiguration {

	@Autowired
	CamelContext context;

	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testCamelContext() throws Exception {
		Assert.assertNotNull(context);
		Assert.assertTrue(context.isAutoStartup());
	}
}
