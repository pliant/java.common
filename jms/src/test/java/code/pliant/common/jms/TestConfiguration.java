/**
 * 
 */
package code.pliant.common.jms;


import javax.naming.NamingException;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import code.pliant.common.test.AnnotationConfigContextLoader;
import code.pliant.common.test.JNDIHelper;

/**
 * Tests whether the Spring configuration files are properly wired with the JMS components.
 * 
 * @author dfr2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={"code.pliant.common", "spring.code.pliant.common"},
		loader=AnnotationConfigContextLoader.class
)
public class TestConfiguration {
	
	@Autowired
	private ApplicationContext context;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		try {
			JNDIHelper.registerJTATransactionManager();
		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Test()
	public void testIfMQExists(){
		Assert.assertNotNull(context.getBean("mq"));
	}
	
	@Test()
	public void testIfConnectionFactoryExists(){
		Assert.assertNotNull(context.getBean("mqActiveMQConnectionFactory"));
	}
	
	@Test()
	public void testIfPooledConnectionFactoryExists(){
		Assert.assertNotNull(context.getBean("mqPooledConnectionFactory"));
	}
	
	@Test()
	public void testIfJMSConfigurationExists(){
		Assert.assertNotNull(context.getBean("mqJmsConfiguration"));
	}
}
