package code.pliant.common.core.sniff;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.Map;

import code.pliant.common.core.NotFoundException;
import code.pliant.common.core.Strings;
import org.junit.Test;

/**
 * @author Daniel Rugg
 */
public class TestRetriever {
	
	private static final String TEST_VAL = "TEST_VAL";

	/**
	 * Test method for {@link Retriever#retrieve()}.
	 */
	@Test(expected=NotFoundException.class)
	public void testRetrieveNotFoundDefault() throws Exception {
		new Retriever().retrieve();
	}

	/**
	 * Test method for {@link Retriever#retrieve()}.
	 */
	@Test(expected=NotFoundException.class)
	public void testRetrieveNotFoundCustom() throws Exception {
		new Retriever("Custom Exception Message").retrieve();
	}

	/**
	 * Test method for {@link Retriever#retrieve()}.
	 */
	@Test(expected=NotFoundException.class)
	public void testRetrieveSystemPropertyNotThere() throws Exception {
		System.getProperties().remove(Sniffer.class.getSimpleName());
		assertEquals(TEST_VAL, new Retriever().systemProperty(Sniffer.class.getSimpleName()).retrieve());
	}

	/**
	 * Test method for {@link Retriever#retrieve()}.
	 */
	@Test
	public void testRetrieveSystemProperty() throws Exception {
		System.setProperty(Sniffer.class.getSimpleName(), TEST_VAL);
		assertEquals(TEST_VAL, new Retriever().systemProperty(Sniffer.class.getSimpleName()).retrieve());
	}

	/**
	 * Test method for {@link Retriever#retrieve()}.
	 */
	@Test(expected=NotFoundException.class)
	public void testRetrieveEnvironmentVariableNotThere() throws Exception {
		assertEquals(TEST_VAL, new Retriever().environmentVariable(Sniffer.class.getSimpleName()).retrieve());
	}

	/**
	 * Test method for {@link Retriever#retrieve()}.
	 */
	@Test
	public void testRetrieveEnvironmentVariable() throws Exception {
		Map<String, String> map = System.getenv();
		for(String key : map.keySet()){
			String value = map.get(key);
			if(Strings.isValid(value)){
				assertEquals(value, new Retriever().environmentVariable(key).retrieve());
			}
		}
	}

	/**
	 * Test method for {@link Retriever#retrieve()}.
	 */
	@Test(expected=NotFoundException.class)
	public void testRetrieveContentNotThere() throws Exception {
		assertEquals(TEST_VAL, new Retriever().content(new URL("http://localhost:58080/this/is/not/a/resource")).retrieve());
	}

	/**
	 * Test method for {@link Retriever#retrieve()}.
	 */
	@Test
	public void testRetrieveContent() throws Exception {
		URL resource = TestRetriever.class.getResource("test.txt");
		assertEquals(TEST_VAL, new Retriever().content(resource).retrieve());
		assertEquals(TEST_VAL, new Retriever().content(resource.toURI()).retrieve());
		assertEquals(TEST_VAL, new Retriever().content(new File(resource.toURI())).retrieve());
		assertEquals(TEST_VAL, new Retriever().content(resource.toExternalForm()).retrieve());
		assertEquals(TEST_VAL, new Retriever().content(TestRetriever.class, "test.txt").retrieve());
	}

	/**
	 * Test method for {@link Retriever#retrieve()}.
	 */
	@Test(expected=NotFoundException.class)
	public void testRetrievePropertyNotThere() throws Exception {
		assertEquals(TEST_VAL, new Retriever().property(new URL("http://localhost:58080/this/is/not/a/resource"), "key1").retrieve());
	}

	/**
	 * Test method for {@link Retriever#retrieve()}.
	 */
	@Test
	public void testRetrieveProperty() throws Exception {
		URL resource = TestRetriever.class.getResource("test.props");
		assertEquals(TEST_VAL, new Retriever().property(resource, "key1").retrieve());
		assertEquals(TEST_VAL, new Retriever().property(resource.toURI(), "key1").retrieve());
		assertEquals(TEST_VAL, new Retriever().property(new File(resource.toURI()), "key1").retrieve());
		assertEquals(TEST_VAL, new Retriever().property(resource.toExternalForm(), "key1").retrieve());
		assertEquals(TEST_VAL, new Retriever().property(TestRetriever.class, "test.props", "key1").retrieve());
	}
}
