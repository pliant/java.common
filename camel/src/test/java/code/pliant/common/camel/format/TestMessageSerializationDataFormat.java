package code.pliant.common.camel.format;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import code.pliant.common.test.AnnotationConfigContextLoader;
import code.pliant.common.test.CamelUtils;


/**
 * Test the Camel formatting addons.
 * 
 * @author Daniel Rugg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {"code.pliant.common", "spring.code.pliant.common"},
		loader = AnnotationConfigContextLoader.class)
public class TestMessageSerializationDataFormat {
	
    @Produce(uri = FormatRouteBuilder.DIRECT_START_MARSHAL)
    protected ProducerTemplate directStartMarshal;
	
    @Produce(uri = FormatRouteBuilder.DIRECT_START_UNMARSHAL)
    protected ProducerTemplate directStartUnmarshal;
	
	@EndpointInject(uri = FormatRouteBuilder.MOCK_END_MARSHAL)
	protected MockEndpoint mockEndMarshal;
	
	@EndpointInject(uri = FormatRouteBuilder.MOCK_END_UNMARSHAL)
	protected MockEndpoint mockEndUnmarshal;

	final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><message><headers><Date type=\"java.util.Date\">Sun Apr 08 23:49:10 EDT 2012</Date><TransactionID type=\"java.lang.String\">ABC</TransactionID><breadcrumbId type=\"java.lang.String\">ID-Daniel-Ruggs-MacBook-Pro-254-local-50099-1333943348964-0-2</breadcrumbId><Count type=\"java.lang.Integer\">36</Count></headers><body><![CDATA[<body><person><id>ABC123</id></person></body>]]></body></message>";
	
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

	@Test
	public void testMarshal() throws Exception{
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("TransactionID", "ABC");
		headers.put("Count", 36);
		headers.put("Date", new Date());
		
		String body = "<body><person><id>ABC123</id></person></body>";
		directStartMarshal.sendBodyAndHeaders(body, headers);
		CamelUtils.waitForRouteComplete();
		mockEndMarshal.expectedMessageCount(1);
		mockEndMarshal.assertIsSatisfied();
		List<Exchange> exchanges = mockEndMarshal.getReceivedExchanges();
		for(Exchange exchange : exchanges){
			Message in = exchange.getIn();
			System.out.println("Marshal EXCHANGE");
			System.out.println(in.getBody(String.class));
		}
	}
	
	@Test
	public void testUnmarshal() throws Exception{
		directStartUnmarshal.sendBody(xml);
		CamelUtils.waitForRouteComplete();
		mockEndUnmarshal.expectedMessageCount(1);
		mockEndUnmarshal.expectedHeaderReceived("TransactionID", "ABC");
		mockEndUnmarshal.expectedHeaderReceived("Count", 36);
		mockEndUnmarshal.assertIsSatisfied();
		List<Exchange> exchanges = mockEndUnmarshal.getReceivedExchanges();
		for(Exchange exchange : exchanges){
			Message in = exchange.getIn();
			System.out.println("UnMarshal EXCHANGE");
			System.out.println(in.getBody(String.class));
		}
	}
}
