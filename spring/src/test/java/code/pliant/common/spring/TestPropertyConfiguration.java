/**
 * 
 */
package code.pliant.common.spring;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import code.pliant.common.test.AnnotationConfigContextLoader;


/**
 * @author dfr2
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={"code.pliant.common"},
		loader=AnnotationConfigContextLoader.class
)
public class TestPropertyConfiguration {

	private final String key1Key2OverDecrypted = "GOOD";

	private final String key2 = "OVERRIDE";
	
	private final String key2OverEncrypted = "ENC(jk3Y+exYzXb5hgJJwuO1bQ==)";
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	List<PropertyPlaceholderConfigurer> ppc;
	
	@Autowired
	ComponentValues values;
	
	@Test
	public void testPropertyConfiguration(){
		Assert.assertEquals(key1Key2OverDecrypted, values.getTestKey1());
		Assert.assertEquals(key2, values.getTestKey2());
		Assert.assertEquals(key1Key2OverDecrypted, values.getTestOverKey1());
		Assert.assertEquals(key2OverEncrypted, values.getTestOverKey2());
	}

	@Test
	public void testEncryptedPropertyConfiguration(){
		Assert.assertEquals(key1Key2OverDecrypted, values.getTestEncryptKey1());
		Assert.assertEquals(key2, values.getTestEncryptKey2());
		Assert.assertEquals(key1Key2OverDecrypted, values.getTestEncryptOverKey1());
		Assert.assertEquals(key1Key2OverDecrypted, values.getTestEncryptOverKey2());
	}
}
