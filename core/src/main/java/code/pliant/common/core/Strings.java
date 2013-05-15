package code.pliant.common.core;

import java.util.ArrayList;

/**
 * Helper utility for dealing with {@link String}s.
 * 
 * @author Daniel Rugg
 */
public class Strings {
	
	/**
	 * A constant empty string.
	 */
	public static final String EMPTY = "";

	/**
	 * Concatenates a variable array of strings together, using a delimiter between them.
	 * 
	 * @param delim The delimiter to put between each non-null, non-empty string.
	 * @param values The values to concatenate together.
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
	 * than whitespace characters.
	 * 
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
	 * Checks if a string is invalid, meaning it is null, empty, or contains only whitespace.
	 * 
	 * @param value The string value to check.
	 * @return {@code true} if the string is null, empty, or contains only whitespace, else {@code false}.
	 */
	public static boolean isInvalid(String value){
		return !isValid(value);
	}
	
	/**
	 * If the value provided is not null, it's toString() result will be returned, else EMPTY will.
	 * 
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
	 * 
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
	 * @param value The value to pull from.
	 * @param len The number of characters to take from the left side of the string.
	 * @return A string.
	 */
	public static String left(String value, int len){
		value = toStringOrEmpty(value);
		if(value.length() <= len){
			return value;
		}
		return value.substring(0, len);
	}
	
	/**
	 * Returns a specified number of characters from the right side of a string.
	 * @param value The value to pull from.
	 * @param len The number of characters to take from the right side of the string.
	 * @return A string.
	 */
	public static String right(String value, int len){
		value = toStringOrEmpty(value);
		if(value.length() <= len){
			return value;
		}
		return value.substring(value.length() - len);
	}

	/**
	 * Creates an array with only the valid values within the array according to the {@link isValid} method.
	 * @param values The values to validate and return.
	 * @return An array of the valid strings.  Can be empty, but never null.
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
