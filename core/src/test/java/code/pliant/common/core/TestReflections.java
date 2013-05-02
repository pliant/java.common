/**
 * 
 */
package code.pliant.common.core;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Daniel Rugg
 */
public class TestReflections {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {}

	ReflectionsVO o1;
	ReflectionsVO o2;
	
	final String name1 = "Bud";
	final String name2 = "Steve";
	final int age1 = 20;
	final int age2 = 30;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		o1 = new ReflectionsVO(name1, age1);
		o2 = new ReflectionsVO(name2, age2);
	}

	/**
	 * Test method for {@link Reflections#lookupFields(java.lang.Class, java.lang.Class, boolean, java.util.List)}.
	 */
	@Test
	public void testLookupFields() {
		List<Field> fields = Reflections.lookupFields(ReflectionsVO.class, null, false);
		assertEquals(2, fields.size());
	}

	/**
	 * Test method for {@link Reflections#lookupFieldValues(java.lang.Object, java.lang.Class, boolean, java.util.List)}.
	 * @throws Exception 
	 */
	@Test
	public void testLookupFieldValuesObjectClassBooleanListOfString() throws Exception {
		Map<Field, Object> map = Reflections.lookupFieldValues(o1, null, false);
		for(Map.Entry<Field, Object> entry : map.entrySet()){
			if(entry.getKey().getName().equals("age")){
				assertEquals(age1, entry.getValue());
			}
			if(entry.getKey().getName().equals("name")){
				assertEquals(name1, entry.getValue());
			}
		}
	}

	/**
	 * Test method for {@link Reflections#lookupNamedValues(java.lang.Object, java.lang.Class, boolean, java.util.List)}.
	 * @throws Exception 
	 */
	@Test
	public void testLookupNamedValuesObjectClassBooleanListOfString() throws Exception {
		Map<String, Object> map = Reflections.lookupNamedValues(o1, null, false);
		for(Map.Entry<String, Object> entry : map.entrySet()){
			if(entry.getKey().equals("age")){
				assertEquals(age1, entry.getValue());
			}
			if(entry.getKey().equals("name")){
				assertEquals(name1, entry.getValue());
			}
		}
	}

	/**
	 * Test method for {@link Reflections#lookupNamedValues(java.util.List, java.lang.Object)}.
	 * @throws Exception 
	 */
	@Test
	public void testLookupNamedValuesListOfFieldObject() throws Exception {
		List<Field> fields = Reflections.lookupFields(ReflectionsVO.class, null, false);
		
		Map<String, Object> map = Reflections.lookupNamedValues(fields, o1);
		for(Map.Entry<String, Object> entry : map.entrySet()){
			if(entry.getKey().equals("age")){
				assertEquals(age1, entry.getValue());
			}
			if(entry.getKey().equals("name")){
				assertEquals(name1, entry.getValue());
			}
		}
	}

	/**
	 * Test method for {@link Reflections#lookupFieldValues(java.util.List, java.lang.Object)}.
	 * @throws Exception 
	 */
	@Test
	public void testLookupFieldValuesListOfFieldObject() throws Exception {
		List<Field> fields = Reflections.lookupFields(ReflectionsVO.class, null, false);

		Map<Field, Object> map = Reflections.lookupFieldValues(fields, o1);
		for(Map.Entry<Field, Object> entry : map.entrySet()){
			if(entry.getKey().getName().equals("age")){
				assertEquals(age1, entry.getValue());
			}
			if(entry.getKey().getName().equals("name")){
				assertEquals(name1, entry.getValue());
			}
		}
	}

	/**
	 * Test method for {@link Reflections#invoke(java.lang.Object, java.lang.String, java.lang.Object[])}.
	 * @throws Exception 
	 */
	@Test
	public void testInvoke() throws Exception {
		Object value = Reflections.invoke(o1, "doubleAge");
		assertEquals((age1 *2 ), value);
	}

	/**
	 * Test method for {@link Reflections#linkValuesToFields(java.util.Map, java.util.List)}.
	 * @throws Exception 
	 */
	@Test
	public void testLinkValuesToFields() throws Exception {
		List<Field> fields = Reflections.lookupFields(ReflectionsVO.class, null, false);
		
		Map<String, Object> values = Reflections.lookupNamedValues(fields, o1);

		Map<Field, Object> map = Reflections.linkValuesToFields(values, fields);

		for(Map.Entry<Field, Object> entry : map.entrySet()){
			if(entry.getKey().getName().equals("age")){
				assertEquals(age1, entry.getValue());
			}
			if(entry.getKey().getName().equals("name")){
				assertEquals(name1, entry.getValue());
			}
		}
	}

	/**
	 * Test method for {@link Reflections#transfer(java.util.Map, java.lang.Object)}.
	 * @throws Exception 
	 */
	@Test
	public void testTransfer() throws Exception {
		Map<Field, Object> map = Reflections.lookupFieldValues(o1, null, false);
		Reflections.transfer(map, o2);
		assertEquals(name1, o2.getName());
		assertEquals(age1, o2.getAge());
	}
}