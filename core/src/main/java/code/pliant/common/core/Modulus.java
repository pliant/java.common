package code.pliant.common.core;


/**
 * Provides Modulus calculations that are sometimes common for validating data values.
 * 
 * @author Daniel Rugg
 */
public class Modulus {

	
	/**
	 * Generates the check digit for a given value
	 * @param value A string with a valid long or int value within it.
	 * @return The check digit value.  Will be between 0 - 9.
	 */
	public static int modulus9(String value){
		int mod = (int)(Long.parseLong(value) % 9);
		if(mod > 0){
			return 9 - mod;
		}
		return 0;
	}
	
	/**
	 * Generates a modulus 10 based check digit where:
	 * <ul>
	 * <li>Numbers in odd positions are doubles(position is zero based)</li>
	 * <li>Any doubled number that has a result greater than 10 has a 1 added to it's value.</li>
	 * </ul>
	 * @param value
	 * @return
	 */
	public static int modulus10Plus1(String value){
		int total = 0;
		for(int i = 0; i < value.length(); i++){
			String val = value.substring(i, i + 1);
			int num = Integer.parseInt(val);
			if((i % 2) > 0){
				num = num * 2;
				if(num >= 10){
					num = num + 1;
				}
			}
			total = total + num;
		}
		int mod = total % 10;
		if(mod > 0){
			return 10 - mod;
		}
		return 0;
	}
}
