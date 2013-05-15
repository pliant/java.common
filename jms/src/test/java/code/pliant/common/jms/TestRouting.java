/**
 * 
 */
package code.pliant.common.jms;

import javax.naming.NamingException;

import junit.framework.Assert;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import code.pliant.common.test.AnnotationConfigContextLoader;
import code.pliant.common.test.CamelUtils;
import code.pliant.common.test.JNDIUtils;
import code.pliant.common.test.MailUtils;

/**
 * Tests to whether the Camel component for sending and receiving messages between the message queues are
 * functioning properly.
 * 
 * @author Daniel Rugg
 */
@ContextConfiguration(
		locations = {"code.pliant.common", "spring.code.pliant.common"},
		loader = AnnotationConfigContextLoader.class)
public class TestRouting extends AbstractJUnit4SpringContextTests {

	@Produce(uri = NonTransactedRouteBuilder.DIRECT_START)
	protected ProducerTemplate directStart;

	@Produce(uri = TransactedRouteBuilder.DIRECT_START_TRANSACTED)
	protected ProducerTemplate directStartTransacted;

	@EndpointInject(uri = NonTransactedRouteBuilder.MOCK_END)
	protected MockEndpoint mockEnd;

	@EndpointInject(uri = TransactedRouteBuilder.MOCK_END_TRANSACTED)
	protected MockEndpoint mockEndTransacted;

	@EndpointInject(uri = TransactedRouteBuilder.MOCK_ERROR_TRANSACTED)
	protected MockEndpoint mockErrorTransacted;
	
	@Autowired
	ExceptionService service;
	
	@Autowired
	CamelContext camelContext;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			JNDIUtils.registerJTATransactionManager();
		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setupBefore(){
		mockEnd.reset();
		mockErrorTransacted.reset();
	}
	
	private final String headerKey = "foo";
	private final String headerValue = "bar";
	private final String expectedBody = "<matched/>";
	
	// Email Address to Check For
	protected String address = "test@musc.edu";

	@Test
	public void testSendMatchingMessage() throws Exception {
		mockEnd.expectedBodiesReceived(expectedBody);
		directStart.sendBody(expectedBody);

		waitForCamel();
		mockEnd.assertIsSatisfied();
	}

	@Test
	public void testSendMatchingMessageAndHeader() throws Exception {
		mockEnd.expectedBodiesReceived(expectedBody);
		mockEnd.expectedHeaderReceived(headerKey, headerValue);
		directStart.sendBodyAndHeader(expectedBody, headerKey, headerValue);

		waitForCamel();
		mockEnd.assertIsSatisfied();
	}
	
	/**
	 * Test that when Non-Recoverable errors are thrown that they are routed to the error queue.
	 * @throws Exception
	 */
	@Test
	public void testNonRecoverableRouting() throws Exception{
		mockErrorTransacted.expectedBodiesReceived(expectedBody);
		service.setRecoverable(false);
		directStartTransacted.sendBody(expectedBody);
		
		waitForCamel();
		mockErrorTransacted.assertIsSatisfied();
		MailUtils.checks(address, "Test Error", "NonRecoverableException");
		MailUtils.clear(address);
	}
	
	/**
	 * Test that when Non-Recoverable errors are thrown that they are routed to the error queue.
	 * @throws Exception
	 */
	@Test
	public void testRecoverableRouting() throws Exception{
		int calls = service.getCallCount();
		
		// Send Transacted Message
		service.setRecoverable(true);
		directStartTransacted.sendBody(expectedBody);
		
		// Grab First Calls
		waitForCamel();
		assertGreaterThan(service.getCallCount(), calls, "first CallCount");
		calls = service.getCallCount();

		MailUtils.checks(address, "Test Retry", "Doing RecoverableException.class");
		MailUtils.clear(address);
		
		// Stop Route
		waitForCamel();
		camelContext.stop();
		waitForCamel();
		assertGreaterThan(service.getCallCount(), calls, "second CallCount");

		MailUtils.checks(address, "Test Retry", "Doing RecoverableException.class");
		MailUtils.clear(address);
		
		// Resume Route
		camelContext.start();
		waitForCamel();
		assertGreaterThan(service.getCallCount(), calls, "third CallCount");
		calls = service.getCallCount();
		
		// Remove Message from Queue
		camelContext.stop();
		service.setRecoverable(false);
		camelContext.start();
		waitForCamel();
		calls = service.getCallCount();
		waitForCamel();
		assertSameAs(service.getCallCount(), calls, "last CallCount");
	}
	
	private void assertGreaterThan(int greaterValue, int lessValue, String valueName){
		if(!(greaterValue > lessValue)){
			StringBuilder message = new StringBuilder();
			message.append("Expected ").append(valueName)
				.append(" to be greater than ").append(lessValue)
				.append(", but was ").append(greaterValue).append(" instead.");
			Assert.fail(message.toString());
		}
	}
	
	private void assertSameAs(int greaterValue, int lessValue, String valueName){
		if(!(greaterValue == lessValue)){
			StringBuilder message = new StringBuilder();
			message.append("Expected ").append(valueName)
				.append(" to be same as ").append(lessValue)
				.append(", but was ").append(greaterValue).append(" instead.");
			Assert.fail(message.toString());
		}
	}
	
	private void waitForCamel(){
		CamelUtils.waitForRouteComplete(500l);
	}
}
