package code.pliant.common.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.junit.Test;

/**
 * Performs tests all all the methods of the {@link Collections} utility class.
 * 
 * @author Daniel Rugg
 */
public class TestCollections {

	/**
	 * Test method for {@link Collections#notNullNotEmpty(java.util.List, java.lang.String[])}.
	 * @throws Exception 
	 */
	@Test
	public void testNotNullNotEmpty() throws Exception {
		// Test Null With No Exception
		assertEquals(false, Collections.notNullNotEmpty(null));
		
		// Test Empty
		assertEquals(false, Collections.notNullNotEmpty(new ArrayList<Object>()));
		
		// Test Valid
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(list);
		assertEquals(true, Collections.notNullNotEmpty(list));
	}	
	
	/**
	 * Test method for {@link Collections#notNullNotEmpty(java.util.List, java.lang.String[])}.
	 * @throws Exception 
	 */
	@Test(expected=Exception.class)
	public void testNotNullNotEmptyWhenEmpty() throws Exception {
		Collections.notNullNotEmpty(new ArrayList<Object>(), "");
	}
	
	/**
	 * Test method for {@link Collections#notNullNotEmpty(java.util.List, java.lang.String[])}.
	 * @throws Exception 
	 */
	@Test(expected=Exception.class)
	public void testNotNullNotEmptyWhenNull() throws Exception {
		Collections.notNullNotEmpty(null, "");
	}

	/**
	 * Test method for {@link Collections#hasOnlyOne(java.util.List, java.lang.String[])}.
	 * @throws Exception 
	 */
	@Test
	public void testHasOnlyOne() throws Exception {
		// Test Null
		assertEquals(false, Collections.hasOnlyOne(null));
		
		// Test Empty
		ArrayList<Object> list = new ArrayList<Object>();
		assertEquals(false, Collections.hasOnlyOne(list));
		
		// Test One
		list.add("1");
		assertEquals(true, Collections.hasOnlyOne(list));
		
		// Test More Than One
		list.add("2");
		assertEquals(false, Collections.hasOnlyOne(list));
	}

	/**
	 * Test method for {@link Collections#hasOnlyOne(java.util.List, java.lang.String[])}.
	 * @throws Exception 
	 */
	@Test(expected=Exception.class)
	public void testHasOnlyOneWhenEmpty() throws Exception {
		Collections.hasOnlyOne(new ArrayList<Object>(), "Exception Message");
	}

	/**
	 * Test method for {@link Collections#hasOnlyOne(java.util.List, java.lang.String[])}.
	 * @throws Exception 
	 */
	@Test(expected=Exception.class)
	public void testHasOnlyOneWhenNull() throws Exception {
		Collections.hasOnlyOne(null, "Exception Message");
	}

	/**
	 * Test method for {@link Collections#hasOnlyOne(java.util.List, java.lang.String[])}.
	 * @throws Exception 
	 */
	@Test(expected=Exception.class)
	public void testHasOnlyOneWhenMoreThanOne() throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("1");
		list.add("2");
		Collections.hasOnlyOne(list, "Exception Message");
	}

	/**
	 * Test method for {@link Collections#addToNewCollection(java.lang.Object, java.lang.Class)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testAddToNewCollection() {
		String value = "Value";
		Collection<String> collection = Collections.addToNewCollection(value, ArrayList.class);
		assertEquals(collection.getClass(), ArrayList.class);
		assertEquals(value, collection.iterator().next());
	}

	/**
	 * Test method for {@link Collections#addToSystemProperties(java.util.Properties)}.
	 */
	@Test
	public void testAddToSystemProperties() {
		String key1 = this.getClass().getName() + "key1";
		String value1 = this.getClass().getName() + "key1";
		String key2 = this.getClass().getName() + "key1";
		String value2 = this.getClass().getName() + "key1";
		
		Properties props = new Properties();
		props.put(key1, value1);
		props.put(key2, value2);
		Collections.addToSystemProperties(props);
		
		assertEquals(value1, System.getProperty(key1));
		assertEquals(value2, System.getProperty(key2));
	}
}
