/**
 * 
 */
package code.pliant.common.core;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

/**
 * @author Daniel Rugg
 */
public class TestPaths {

	/**
	 * Test method for {@link Paths#configFileName(java.lang.Class, java.lang.String[])}.
	 */
	@Test
	public void testConfigFilename() {
		assertEquals("config.props", Paths.configFileName(null));
		assertEquals("bob-config.props", Paths.configFileName(null, "bob"));
		assertEquals("yo-bob-config.props", Paths.configFileName(null, "yo", "bob"));
		assertEquals("java.util-config.props", Paths.configFileName(Map.class));
		assertEquals("java.util-bob-config.props", Paths.configFileName(Map.class, "bob"));
		assertEquals("java.util-yo-bob-config.props", Paths.configFileName(Map.class, "yo", "bob"));
	}


	/**
	 * Test method for {@link Paths#rootConfigFileName(java.lang.Class, java.lang.String[])}.
	 */
	@Test
	public void testRootConfigFilename() {
		assertEquals("/config.props", Paths.rootConfigFileName(null));
		assertEquals("/bob-config.props", Paths.rootConfigFileName(null, "bob"));
		assertEquals("/yo-bob-config.props", Paths.rootConfigFileName(null, "yo", "bob"));
		assertEquals("/java.util-config.props", Paths.rootConfigFileName(Map.class));
		assertEquals("/java.util-bob-config.props", Paths.rootConfigFileName(Map.class, "bob"));
		assertEquals("/java.util-yo-bob-config.props", Paths.rootConfigFileName(Map.class, "yo", "bob"));
	}
}
