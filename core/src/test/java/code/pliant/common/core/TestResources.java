/**
 * 
 */
package code.pliant.common.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.Properties;

import org.junit.Test;

/**
 * Performs tests all all the methods of the {@link Resources} utility class.
 * 
 * 
 * @author Daniel Rugg
 *
 */
public class TestResources {

	/**
	 * Test method for {@link Resources#inspectResource(java.lang.String, java.lang.Class)}.
	 */
	@Test
	public void testInspectResource() {
		String string = Resources.inspectResource(TestResources.class, "test.txt");
		assertNotNull(string);
	}

	/**
	 * Test method for {@link Resources#getResourceAsString(java.lang.String, java.lang.Class)}.
	 */
	@Test
	public void testGetResourceAsString() throws Exception {
		String string = Resources.getResourceAsString(TestResources.class, "test.txt");
		assertNotNull(string);
		assertTrue(string.startsWith("DO"));
	}

	/**
	 * Test method for {@link Resources#getResourceAsString(java.lang.String, java.lang.Class)}.
	 */
	@Test
	public void testGetResourceAsStringURL() throws Exception {
		URL resource = TestResources.class.getResource("test.txt");
		String string = Resources.getResourceAsString(resource);
		assertNotNull(string);
		assertTrue(string.startsWith("DO"));
	}

	/**
	 * Test method for {@link Resources#getResourceAsProperties(java.lang.String, java.lang.Class)}.
	 */
	@Test
	public void testGetResourceAsProperties() throws Exception {
		 Properties props = Resources.getResourceAsProperties(TestResources.class, "test.props");
		 assertNotNull(props);
		 assertEquals("value1", props.getProperty("key1"));
		 assertEquals("value2", props.getProperty("key2"));
		 assertEquals("value3", props.getProperty("key3"));
	}

	/**
	 * Test method for {@link Resources#getResourceAsString(java.lang.String, java.lang.Class)}.
	 */
	@Test(expected=NotFoundException.class)
	public void testGetResourceAsStringNotFound() throws Exception {
		Resources.getResourceAsString(TestResources.class, "Not-test.txt");
	}

	/**
	 * Test method for {@link Resources#getResourceAsString(java.lang.String, java.lang.Class)}.
	 */
	@Test(expected=NotFoundException.class)
	public void testGetResourceAsStringURLNotFound() throws Exception {
		URL resource = TestResources.class.getResource("Not-test.txt");
		Resources.getResourceAsString(resource);
	}

	/**
	 * Test method for {@link Resources#getResourceAsProperties(java.lang.String, java.lang.Class)}.
	 */
	@Test(expected=NotFoundException.class)
	public void testGetResourceAsPropertiesNotFound() throws Exception {
		 Resources.getResourceAsProperties(TestResources.class, "not-test.props");
	}
}
