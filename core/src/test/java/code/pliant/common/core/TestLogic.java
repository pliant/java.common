package code.pliant.common.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Performs tests all all the methods of the {@link Logic} utility class.
 * 
 * @author Daniel Rugg
 */
public class TestLogic {

	/**
	 * Test method for {@link Logic#bothNullOrNotNull(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	public void testBothNullOrNotNull() {
		assertEquals(true, Logic.bothNullOrNotNull(null, null));
		assertEquals(true, Logic.bothNullOrNotNull("1", "2"));
		assertEquals(false, Logic.bothNullOrNotNull("2", null));
	}

	/**
	 * Test method for {@link Logic#isNotNull(java.lang.Object[])}.
	 */
	@Test
	public void testIsNotNull() {
		assertEquals(false, Logic.isNotNull(null, null, null, null));
		assertEquals(false, Logic.isNotNull(null, null, "2", null));
		assertEquals(false, Logic.isNotNull("1", null, "2", null));
		assertEquals(true, Logic.isNotNull("1", "$", "2", "@"));
	}

	/**
	 * Test method for {@link Logic#bothNotNullAndCompare(java.lang.Object, java.lang.Object, boolean)}.
	 */
	@Test
	public void testisNotNullAndCompare() {
		assertEquals(false, Logic.isNotNullAndCompare(true, null, null));
		assertEquals(false, Logic.isNotNullAndCompare(true, "2", null));
		assertEquals(false, Logic.isNotNullAndCompare(true, "1", "2"));
		assertEquals(false, Logic.isNotNullAndCompare(true, 2, "2"));
		assertEquals(true, Logic.isNotNullAndCompare(true, "2", "2"));
		
		assertEquals(false, Logic.isNotNullAndCompare(false, null, null));
		assertEquals(false, Logic.isNotNullAndCompare(false, "2", null));
		assertEquals(true, Logic.isNotNullAndCompare(false, "1", "2"));
		assertEquals(true, Logic.isNotNullAndCompare(false, 2, "2"));
		assertEquals(false, Logic.isNotNullAndCompare(false, "2", "2"));
	}

	/**
	 * Test method for {@link Logic#compare(boolean, java.lang.Object[])}.
	 */
	@Test
	public void testCompare() {
		assertEquals(false, Logic.compare(true, "2", "2", "1"));
		assertEquals(false, Logic.compare(true, "2", "2", false));
		assertEquals(false, Logic.compare(true, "2", "2", 2));
		assertEquals(true, Logic.compare(true, "2", "2", "2"));

		assertEquals(true, Logic.compare(false, "2", 2, "1"));
		assertEquals(false, Logic.compare(false, "2", "2", "1"));
		assertEquals(false, Logic.compare(false, "2", "2", false));
		assertEquals(false, Logic.compare(false, "2", "2", 2));
		assertEquals(false, Logic.compare(false, "2", "2", "2"));
	}

	/**
	 * Test method for {@link Logic#isNull(java.lang.Object[])}.
	 */
	@Test
	public void testIsNull() {
		assertEquals(true, Logic.isNull(null, null, null, null));
		assertEquals(false, Logic.isNull(null, null, "2", null));
		assertEquals(false, Logic.isNull("1", null, "2", null));
		assertEquals(false, Logic.isNull("1", "$", "2", "@"));
	}

	/**
	 * Test method for {@link Logic#oneNull(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	public void testOneNull() {
		assertEquals(false, Logic.oneNull(null, null));
		assertEquals(true, Logic.oneNull("5", null));
		assertEquals(true, Logic.oneNull(null, "4"));
		assertEquals(false, Logic.oneNull("!", "2"));
	}
}
