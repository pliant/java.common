package code.pliant.common.core;

import java.util.ArrayList;

/**
 * Helper utility for dealing with {@link String}s.
 * 
 * @author Daniel Rugg
 */
public class Strings {
	
	public static final String EMPTY = "";

	/**
	 * Concats a collects of strings together, using a default delimiter string between them.
	 * @param delim The delimiter to put between each non-null, non-empty string.
	 * @param values The values to concat together.
	 * @return A string
	 */
	public static String concat(String delim, String...values){
		if(delim == null){
			delim = EMPTY;
		}
		
		StringBuilder value = new StringBuilder();
		for(String val : values){
			if(isValid(val)){
				if(value.length() > 0){
					value.append(delim);
				}
				value.append(val);
			}
		}
		return value.toString();
	}
	
	/**
	 * Checks if a String value is valid, which means a non-null string containing more 
	 * than white characters.
	 * @param value The string value to check.
	 * @return {@code true} if the value is not null and has non-whitespace characters.
	 */
	public static boolean isValid(String value){
		if(value != null && value.trim().length() > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * If the value provided is not null, it's toString() result will be returned, else EMPTY will.
	 * @param value The value to check.
	 * @return The value passed if it is valid, else an empty string.
	 */
	public static String toStringOrEmpty(Object value){
		try{
			return value.toString();
		}
		catch(Throwable t){
			return EMPTY;
		}
	}
	
	/**
	 * If the value provided is not null, it's toString() result will be returned, else null will.
	 * @param value The value to check.
	 * @return The value passed if it is valid, else a null.
	 */
	public static String toStringOrNull(Object value){
		try{
			return value.toString();
		}
		catch(Throwable t){
			return null;
		}
	}
	
	/**
	 * Returns a specified number of characters from the left side.
	 * @param value
	 * @param len
	 * @return 
	 */
	public static String left(String value, int len){
		value = toStringOrEmpty(value);
		if(value.length() <= len){
			return value;
		}
		return value.substring(0, len);
	}

	/**
	 * Creates an array with only the valid values within the array.
	 * @param values
	 * @return
	 */
	public static String[] toValidArray(String[] values){
		ArrayList<String> list = new ArrayList<String>();
		for(String value : values){
			if(isValid(value)){
				list.add(value);
			}
		}
		return list.toArray(new String[list.size()]);
	}
}
