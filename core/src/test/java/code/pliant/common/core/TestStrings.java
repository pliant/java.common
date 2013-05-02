/**
 * 
 */
package code.pliant.common.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Daniel Rugg
 */
public class TestStrings {

	/**
	 * Test method for {@link Strings#concat(java.lang.String, java.lang.String[])}.
	 */
	@Test
	public void testConcat() {
		assertEquals("BOB+BETTY", Strings.concat("+", "BOB", "BETTY"));
		assertEquals("BOB+BETTY", Strings.concat("+", null, "BOB", "BETTY"));
		assertEquals("BOB+BETTY", Strings.concat("+", "BOB", null, "BETTY"));
		assertEquals("BOB+BETTY", Strings.concat("+", "BOB", "BETTY", null));
	}

	/**
	 * Test method for {@link Strings#isValid(java.lang.String)}.
	 */
	@Test
	public void testIsValid() {
		assertFalse(Strings.isValid(null));
		assertFalse(Strings.isValid(Strings.EMPTY));
		assertFalse(Strings.isValid(" "));
		assertTrue(Strings.isValid(" A"));
	}

	/**
	 * Test method for {@link Strings#toStringOrEmpty(java.lang.Object)}.
	 */
	@Test
	public void testToStringOrEmpty() {
		String value = "TEST";
		Integer i = 3;
		assertEquals(value, Strings.toStringOrEmpty(value));
		assertEquals("3", Strings.toStringOrEmpty(i));
		assertEquals(Strings.EMPTY, Strings.toStringOrEmpty(null));
	}

}
