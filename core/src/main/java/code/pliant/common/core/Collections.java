package code.pliant.common.core;

import java.util.Collection;
import java.util.List;
import java.util.Properties;


/**
 * Helper class for validating collections.
 *
 * @author Daniel Rugg
 */

public class Collections {

    /**
     * Helper method to check if list has items. The {@code varargs} exceptionMessage is used as an optional parameter
     * that contains the Exception message.
     *
     * @param list the list to check
     * @param exceptionMessage used as an optional parameter to that contains the Exception message.
     * @return if message is empty return {@code true} if list is empty or null; {@code false} otherwise.
     * @throws Exception when message is not empty.
     */
    @SuppressWarnings("rawtypes")
	public static boolean notNullNotEmpty( List list, String... exceptionMessage) throws Exception {
        if (list == null || list.isEmpty()) {
            if (exceptionMessage.length > 0) throw new Exception(exceptionMessage[0]);
            return false;
        }
        return true;
    }

    /**
     * Helper method to check if the list contains only one item. The {@code varargs} exceptionMessage is used as an optional parameter
     * that contains the Exception message.
     *
     * @param list             the list to check
     * @param exceptionMessage used as an optional parameter to that contains the Exception message.
     * @return if message is empty return {@code true} if list is empty, null or has more than 1 item; {@code false} otherwise.
     * @throws Exception when message is not empty.
     */
    @SuppressWarnings("rawtypes")
    public static boolean hasOnlyOne(List list, String... exceptionMessage) throws Exception {
        if (!notNullNotEmpty(list) || list.size() > 1) {
            if (exceptionMessage.length > 0) throw new Exception(exceptionMessage[0]);
            return false;
        }
        return true;
    }
    
    /**
     * Creates a Collection of a specific type, adds the provided value to it, and 
     * returns the collection.
     * @param <T> The type of values the collection will contain.
     * @param <C> The type of collection to be created.
     * @param value The value to put in the collection.
     * @param type The type of collection to create.
     * @return The new collection with the value added to it.
     */
    public static <T, C extends Collection<T>> C addToNewCollection(T value, Class<C> type){
    	try {
			C collection = type.newInstance();
			collection.add(value);
			return collection;
		}
    	catch(Exception e) {
    		Message message = new Message("Unable to create the provided collection type using a default constructor: ")
    			.add(type);
			throw new IllegalArgumentException(message.toString(), e);
		}
    }
    
    /**
     * Adds a given properties map to the system properties map.
     * 
     * @param properties
     */
    public static void addToSystemProperties(Properties properties){
    	if(properties != null){
    		for(Object key : properties.keySet()){
    			System.setProperty(key.toString(), properties.getProperty(key.toString()));
    		}
    	}
    }
}
