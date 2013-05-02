package code.pliant.common.camel;


import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.mock_javamail.Mailbox;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import code.pliant.common.camel.process.SystemMessageProcessor;
import code.pliant.common.test.AnnotationConfigContextLoader;
import code.pliant.common.test.CamelUtils;
import code.pliant.common.test.MailUtils;


/**
 * Tests all of the routes that are made available through the SystemRouteBuilder
 * 
 * @author Daniel Rugg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {"code.pliant.common", "spring.code.pliant.common"},
		loader = AnnotationConfigContextLoader.class)
public class TestSystemRouteBuilder {

	@Produce(uri = URIs.NOTIFY_ERROR)
    protected ProducerTemplate notifyError;
	
	@Produce(uri = URIs.NOTIFY_RETRY)
    protected ProducerTemplate notifyRetry;
	
	@Produce(uri = URIs.NOTIFY)
    protected ProducerTemplate notify;
	
	@Produce(uri = CamelRouteBuilder.DIRECT_START_MESSAGE_LOOP)
    protected ProducerTemplate directStartMessageLoop;
	
	@Produce(uri = CamelRouteBuilder.DIRECT_START_MESSAGE_LOOP_SENT)
    protected ProducerTemplate directStartMessageLoopSent;
	
	@Produce(uri = CamelRouteBuilder.DIRECT_START_MESSAGE_LOOP_IGNORE)
    protected ProducerTemplate directStartMessageLoopIgnore;
	
	@Produce(uri = CamelRouteBuilder.DIRECT_START_EXCEPTION)
    protected ProducerTemplate directStartException;
	
	@Produce(uri = CamelRouteBuilder.DIRECT_START_EXCEPTION_REVIVE)
    protected ProducerTemplate directStartExceptionRevive;
	
	@EndpointInject(uri = CamelRouteBuilder.MOCK_END_EXCEPTION)
	protected MockEndpoint mockEndException;
	
	@EndpointInject(uri = CamelRouteBuilder.MOCK_END_EXCEPTION_REVIVE)
	protected MockEndpoint mockEndExceptionRevive;
	
	// Email Address to Check For
	protected String address = "recipient@company.com";
	
	
	@Before
	public void setup(){
		Mailbox.clearAll();
	}
	
	/**
	 * Tests default error message.
	 * @throws Exception
	 */
	@Test
	public void testNotifyError() throws Exception{
		notifyError.sendBody("THIS IS A MESSAGE.");
		CamelUtils.waitForRouteComplete();
		MailUtils.check(address, "Test Error", "An error occurred while processing a message.  Please inform the technical and data leads to the situation.");
	}
	
	/**
	 * Tests the default retry message.
	 * @throws Exception
	 */
	@Test
	public void testNotifyRetry() throws Exception{
		notifyRetry.sendBody("THIS IS A MESSAGE.");
		CamelUtils.waitForRouteComplete();
		MailUtils.check(address, "Test Retry", "A message is being reprocessed due to infrastructure not providing the appropriate availability.  Please inform the support team immediately to resolve this issue.");
	}
	
	/**
	 * Test the default message.
	 * @throws Exception
	 */
	@Test
	public void testNotify() throws Exception{
		notify.sendBody("THIS IS A MESSAGE.");
		CamelUtils.waitForRouteComplete();
		MailUtils.check(address, "Test Generic", "A message is being sent but no content was provided.");
	}
	
	/**
	 * Tests providing own message.
	 * @throws Exception
	 */
	@Test
	public void testNotifyWithMessage() throws Exception{
		final String message = "Lo And Behold...Magic!";
		notify.sendBodyAndHeader("This Is The Body", SystemMessageProcessor.MESSAGE, message);
		CamelUtils.waitForRouteComplete();
		MailUtils.check(address, "Test Generic", message);
	}
	
	/**
	 * Tests providing message content in a file.
	 * @throws Exception
	 */
	@Test
	public void testNotifyWithMessageFile() throws Exception{
		notify.sendBodyAndHeader("This Is The Body", SystemMessageProcessor.MESSAGE_FILE, "/custom.txt");
		CamelUtils.waitForRouteComplete();
		MailUtils.check(address, "Test Generic", "This is a custom error message", "providing separate paragraphs");
	}
	
	/**
	 * Tests providing message content in a file.
	 * @throws Exception
	 */
	@Test
	public void testNotifyErrorLoop() throws Exception{
		final String message = "Lo And Behold...Magic!";
		directStartMessageLoop.sendBodyAndHeader("This Is The Body", SystemMessageProcessor.MESSAGE, message);
		CamelUtils.waitForRouteComplete();
		MailUtils.checks(address, "Test Error", message);
		MailUtils.checkMessageCount(address, 5);
	}
	
	/**
	 * Tests providing message content in a file.
	 * @throws Exception
	 */
	@Test
	public void testNotifyErrorLoopSent() throws Exception{
		final String message = "Lo And Behold...Magic!";
		directStartMessageLoopSent.sendBodyAndHeader("This Is The Body", SystemMessageProcessor.MESSAGE, message);
		CamelUtils.waitForRouteComplete();
		MailUtils.checks(address, "Test Error", message);
		MailUtils.checkMessageCount(address, 2);
	}
	
	/**
	 * Tests providing message content in a file.
	 * @throws Exception
	 */
	@Test
	public void testNotifyErrorLoopIgnore() throws Exception{
		final String message = "Lo And Behold...Magic!";
		directStartMessageLoopIgnore.sendBodyAndHeader("This Is The Body", SystemMessageProcessor.MESSAGE, message);
		CamelUtils.waitForRouteComplete();
		MailUtils.checkMessageCount(address, 0);
	}
	
	@Test
	public void testBodyOnException() throws Exception{
		final String message = "<body-matched />";
		mockEndException.expectedMessageCount(1);
		mockEndException.expectedBodiesReceived(message);
		directStartException.sendBody(message);
		CamelUtils.waitForRouteComplete();
		mockEndException.assertIsSatisfied();
	}
	
	@Test
	public void testBodyRevivedOnException() throws Exception{
		final String message = "<body-matched />";
		mockEndExceptionRevive.expectedMessageCount(1);
		mockEndExceptionRevive.expectedBodiesReceived(message);
		directStartExceptionRevive.sendBody(message);
		CamelUtils.waitForRouteComplete();
		mockEndExceptionRevive.assertIsSatisfied();
	}
}
