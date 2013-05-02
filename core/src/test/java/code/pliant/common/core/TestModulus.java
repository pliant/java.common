package code.pliant.common.core;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * 
 * @author Daniel Rugg
 */
public class TestModulus {

	static HashMap<String, Integer> mod9Values = new HashMap<String, Integer>();

	static HashMap<String, Integer> mod10Values = new HashMap<String, Integer>();

	@BeforeClass
	public static void setModValues(){
		// 
		mod9Values.put("45598315", 5);
		mod9Values.put("90000000", 0);
		mod9Values.put("90000001", 8);
		mod9Values.put("90000002", 7);
		mod9Values.put("90000003", 6);
		mod9Values.put("90000004", 5);
		mod9Values.put("90000005", 4);
		mod9Values.put("90000006", 3);
		mod9Values.put("90000007", 2);
		mod9Values.put("90000008", 1);
		mod9Values.put("90000009", 0);
		
		
		mod10Values.put("28437726", 4);
		mod10Values.put("10445168", 7);
		mod10Values.put("10445181", 0);
		mod10Values.put("10445215", 6);
		mod10Values.put("49636099", 1);
		mod10Values.put("49640210", 8);
		mod10Values.put("49641193", 5);
		mod10Values.put("49641752", 8);
		mod10Values.put("49641767", 6);
	}
	
	@Test
	public void testModulus9(){
		for(String val : mod9Values.keySet()){
			int check = mod9Values.get(val);
			int modCheck = Modulus.modulus9(val);
			Assert.assertEquals("Value '" + val + "' expecting '" + check + "', but got '" + modCheck + "'", check, modCheck);
		}
	}
	
	@Test
	public void testModulus10Plus1(){
		for(String val : mod10Values.keySet()){
			int check = mod10Values.get(val);
			int modCheck = Modulus.modulus10Plus1(val);
			Assert.assertEquals("Value '" + val + "' expecting '" + check + "', but got '" + modCheck + "'", check, modCheck);
		}
	}
}
