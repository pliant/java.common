package code.pliant.common.core;

import java.util.ArrayList;

/**
 * Used to co-locate logic test functionality. 
 * 
 * @author drugg
 */
public class Logic {

	/**
	 * Checks if both parameters are either null or not null.
	 * @param a A value.
	 * @param b Another value.
	 * @return <code>true</code> or <code>false</code>
	 */
	public static boolean bothNullOrNotNull(Object a, Object b){
		if(isNull(a,b) || isNotNull(a,b)){
			return true;
		}
		return false;
	}

	/**
	 * Checks if all passed values are not null.
	 * @param os A variable argument list of values.
	 * @return <code>true</code> or <code>false</code>
	 */
	public static boolean isNotNull(Object... os){
		for(Object o : os){
			if(o == null) return false;
		}
		return true;
	}

	/**
	 * Checks if all passed values are not null and either equal or not equal to each other.
	 * @param equal If <code>true</code> the objects must equal each other, 
	 * else they must all not equal each other.
	 * @param os The values to check and compare against each other.
	 * @return <code>true</code> or <code>false</code>
	 */
	public static boolean isNotNullAndCompare(boolean equal , Object... os){
		if(os.length < 1){
			return false;
		}
		for(Object o : os){
			if(o == null){
				return false;
			}
		}
		return compare(equal, os);
	}

	/**
	 * Checks if all the passed values are either equal or not equal to each other.
	 * @param equal If <code>true</code> the objects must equal each other, 
	 * else they must not equal each other.
	 * @param os The values to compare.
	 * @return <code>true</code> or <code>false</code>
	 */
	public static boolean compare(boolean equal, Object... os){
		if(os.length < 1){
			return false;
		}
		
		int nulls = 0;
		ArrayList<Object> processed = new ArrayList<Object>();
		
		for(Object o : os){
			if(o == null){
				nulls++;
				if(equal && processed.size() > 0){
					return false;
				}
				else{
					if(nulls > 1) return false;
				}
			}
			else{
				for(Object p : processed){
					if(equal != o.equals(p)){
						return false;
					}
				}
				processed.add(o);
			}
		}
		return true;
	}
	
	/**
	 * Checks if all of the values passed are null.
	 * @param os Variable arguments length of values.
	 * @return <code>true</code> or <code>false</code>
	 */
	public static boolean isNull(Object...os){
		for(Object o : os){
			if(o != null) return false;
		}
		return true;
	}

	/**
	 * Checks if only one parameter is null.
	 * @param os The values to check
	 * @return <code>true</code> or <code>false</code>
	 */
	public static boolean oneNull(Object... os){
		int count = 0;
		for(Object o : os){
			if(o == null) count++;
		}
		return count == 1;
	}
	
	/**
	 * Takes any number of parameters and returns the first non-null value.
	 * @param <T> The type of parameter.
	 * @param ts The parameters.
	 * @return The first non-null value passed, or null if they are all null.
	 */
	public static <T> T firstNonNull(T...ts){
		for(T t : ts){
			if(t != null){
				return t;
			}
		}
		return null;
	}
}
