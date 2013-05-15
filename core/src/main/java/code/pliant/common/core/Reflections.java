package code.pliant.common.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Helps inspect the internals of objects that may not be visible within the 
 * scope of the current process.
 * 
 * @author Daniel Rugg
 */
public class Reflections {
	
	/**
	 * Obtains a list of member fields from a class that may contain values to operate on.
	 * 
	 * @param klass The class to inspect. Can not be <code>null</code>.
	 * @param stopClass The parent class to stop inspection at when navigating the inheritance tree.
	 * <code>null</code> is treated as Object.class.
	 * @param includeStopClass If true the fields on the stop class are to be added.  <code>null</code> 
	 * is treated as false.
	 * @param skipFields A list of names of fields to skip.  <code>null</code> is treated as an 
	 * empty list.
	 * @return A list of the Fields that were found.  Will never return <code>null</code> on Class'es.  
	 * Will always return <code>null</code> for Interfaces.
	 */
	@SuppressWarnings("rawtypes")
	public static List<Field> lookupFields(Class klass, Class stopClass, boolean includeStopClass, 
			String... skipFields){
		List<Field> fields = null;
		// Validate Arguments
		if(stopClass == null){
			stopClass = Object.class;
			includeStopClass = false;
		}
		
		List<String> skips = Arrays.asList(skipFields);
		// Process Target.
		if(!klass.isInterface()){
			if(klass.equals(stopClass) && !includeStopClass){
				return new ArrayList<Field>();
			}
			// Get SuperClass Fields
			if(!klass.equals(stopClass)){
				Class parent = klass.getSuperclass();
				if(parent != null){
					fields = lookupFields(parent, stopClass, includeStopClass, skipFields);
				}
			}
			
			// At Last SuperClass
			if(fields == null){
				fields = new ArrayList<Field>();
			}
			
			// Add The Fields Needed
			for(Field field : klass.getDeclaredFields()){
				if(Modifier.isStatic(field.getModifiers()) || skips.contains(field.getName())){
					continue;
				}
				fields.add(field);
			}
		}
		return fields;
	}
	
	/**
	 * Finds the values of the fields on an object.
	 * 
	 * @param target The target object and inspect for fields.
	 * @param stopClass The Class that the search for fields should stop.  The process 
	 * travels up the SuperClass tree, and will naturally stop at Object.
	 * @param includeStopClass Whether to include the fields on the stopClass.
	 * @param skipFields Names of fields to skip.
	 * @return A mapping of fields to values.  Will never be null, but the values in the 
	 * map can be.
	 */
	@SuppressWarnings("rawtypes")
	public static Map<Field, Object> lookupFieldValues(Object target, 
			Class stopClass, boolean includeStopClass, String... skipFields) throws Exception{
		Map<Field, Object> values = null;
		if(target != null){
			// Validate Arguments
			if(stopClass == null){
				stopClass = Object.class;
				includeStopClass = false;
			}
			
			// Process Target.
			List<Field> fields = lookupFields(target.getClass(), stopClass, includeStopClass, skipFields);
			values = lookupFieldValues(fields, target);
		}
		return values;
	}
	
	/**
	 * Finds the values of the fields on an object.
	 * 
	 * @param target The target object and inspect for fields.
	 * @param stopClass The Class that the search for fields should stop.  The process 
	 * travels up the SuperClass tree, and will naturally stop at Object.
	 * @param includeStopClass Whether to include the fields on the stopClass.
	 * @param skipFields Names of fields to skip.
	 * @return A mapping of field names to values.  Will never be null, but the values in the 
	 * map can be.
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> lookupNamedValues(Object target, 
			Class stopClass, boolean includeStopClass, String... skipFields) throws Exception{
		Map<String, Object> values = null;
		if(target != null){
			// Validate Arguments
			if(stopClass == null){
				stopClass = Object.class;
				includeStopClass = false;
			}
			
			// Process Target.
			List<Field> fields = lookupFields(target.getClass(), stopClass, includeStopClass, skipFields);
			values = lookupNamedValues(fields, target);
		}
		return values;
		
	}

	/**
	 * Finds the values on an object for a list of fields.
	 * 
	 * @param fields The fields whose values are to be returned.
	 * @param source The Object to inspect for values.
	 * @return A mapping of the field name to values.
	 */
	public static Map<String, Object> lookupNamedValues(List<Field> fields, Object source) throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		for(Field field : fields){
			field.setAccessible(true);
			Object value;
			try {
				value = field.get(source);
				values.put(field.getName(), value);
			}
			catch(IllegalAccessException e) {
				Message message = new Message("Failed to lookup field values on class instance, but should never get here: ")
					.add("target", source.getClass().getName());
				throw new Exception(message.toString(), e);
			}
		}
		return values;
	}
	
	/**
	 * Finds the values on an object for a collection of Fields.
	 * 
	 * @param fields A list of fields to lookup on the object.
	 * @param source The object to inspect.
	 * @return A map matching a field with it's value on an object.
	 */
	public static Map<Field, Object> lookupFieldValues(List<Field> fields, Object source) throws Exception{
		Map<Field, Object> values = new HashMap<Field, Object>();
		for(Field field : fields){
			field.setAccessible(true);
			Object value;
			try {
				value = field.get(source);
				values.put(field, value);
			}
			catch(IllegalAccessException e) {
				Message message = new Message("Failed to lookup field values on class instance, but should never get here: ")
					.add("target", source.getClass().getName());
				throw new Exception(message.toString(), e);
			}
		}
		return values;
	}
	
	/**
	 * Invokes a method on the target object to get a value, allowing for a soft fail 
	 * if the invocation is not able to complete.
	 * 
	 * @param target The object to inspect.
	 * @param methodName The name of the method.  The method takes the form of a 
	 * getter, where it returns a value and takes no arguments.
	 * @return A value or null if the method does not exist or the method returned 
	 * null.
	 */
	 @SuppressWarnings("rawtypes")
	public static Object invoke(Object target, String methodName, Object... values) throws Exception{
		if(target != null){
			Class[] parms = null;
			if(values != null && values.length > 0){
				parms = new Class[values.length];
				for(int i = 0; i < values.length; i++){
					parms[i] = values[i].getClass();
				}
			}
			try {
				Method method = target.getClass().getMethod(methodName, parms);
				return method.invoke(target, values);
			}
			catch(Exception e) {
				Message message = new Message("Failed to invoke method on class instance: ")
					.add("target", target.getClass().getName())
					.add("methodName", methodName)
					.add("values", values);
				throw new Exception(message.toString(), e);
			}
		}
		return null;	
	}
	
	/**
	 * Aligns the values from the source map with the fields in the target map into a new mapping.
	 * 
	 * @param source A map of field names and their value.
	 * @param fields A list of Fields.
	 * @return A map matching Fields to their values.
	 */
	public static Map<Field, Object> linkValuesToFields(Map<String, Object> source, List<Field> fields){
		HashMap<Field, Object> values = new HashMap<Field, Object>();
		for(Field field : fields){
			if(source.containsKey(field.getName())){
				values.put(field, source.get(field.getName()));
			}
		}
		return values;
	}
	
	/**
	 * Transfers the values found in one object to another object.  The values are transfered if 
	 * the name and type of the field are the same.
	 * 
	 * @param source The Field/Value mapping to get the values from.
	 * @param target The object to transfer the values to.
	 */
	public static <T> T transfer(Map<Field, Object> source, T target){
		for(Field field : source.keySet()){
			field.setAccessible(true);
			Object value = source.get(field);
			try {
				field.set(target, value);
			}
			catch (Exception e) {
				// Provide a soft fail, where if the field doesn't exist it won't matter.
			}
		}
		return target;
	}
	
	/**
	 * Copies the values from found in one object to another, except fields whose values are indicated in the 
	 * skipFields arguments.
	 * 
	 * @param source The object to copy values from.
	 * @param target The object to copy the values to.
	 * @param skipFields Names of fields to not copy the values of.
	 * @return The target object after the values have been passed to it.
	 * @throws Exception 
	 */
	public static <T> T copy(Object source, T target, String... skipFields) throws Exception{
		Map<Field, Object> values = lookupFieldValues(source, null, false, skipFields);
		return transfer(values, target);
	}

	
	/**
	 * Makes a copy of the object that is passed in, including it's values except fields whose values are indicated in the 
	 * skipFields arguments.
	 * 
	 * @param source The object to copy values from.
	 * @param skipFields Names of fields to not copy the values of.
	 * @return The copy that was created.
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copy(T source, String... skipFields) throws Exception{
		T target = (T)source.getClass().newInstance();
		return copy(source, target, skipFields);
	}
	/**
	 * Obtains a list of member fields from a class that may contain values to operate on.
	 * 
	 * @param klass The class to inspect. Can not be <code>null</code>.
	 * @param stopClasses The parent class to stop inspection at when navigating the inheritance tree.
	 * <code>null</code> is treated as Object.class.
	 * @param includeStopClass If true the fields on the stop class are to be added.  <code>null</code> 
	 * is treated as false.
	 * @param skipMethods A list of names of methods to skip.  <code>null</code> is treated as an 
	 * empty list.
	 * @return A list of the Fields that were found.  Will never return <code>null</code> on Class'es.  
	 * Will always return <code>null</code> for Interfaces.
	 */
	@SuppressWarnings("rawtypes")
	public static List<Method> lookupGetters(Class klass, List<Class> stopClasses, boolean includeStopClass, 
			String... skipMethods){
		List<Method> methods = null;
		// Validate Arguments
		if(stopClasses == null){
			stopClasses = new ArrayList<Class>();
			stopClasses.add(Object.class);
			includeStopClass = false;
		}
		
		List<String> skips = Arrays.asList(skipMethods);
		// Process Target.
		if(stopClasses.contains(klass) && !includeStopClass){
			return new ArrayList<Method>();
		}
		// Get SuperClass Fields
		if(!stopClasses.contains(klass)){
			Class parent = klass.getSuperclass();
			if(parent != null){
				methods = lookupGetters(parent, stopClasses, includeStopClass, skipMethods);
			}
		}
		
		// At Last SuperClass
		if(methods == null){
			methods = new ArrayList<Method>();
		}
		
		// Add The Fields Needed
		final String get = "get";
		for(Method method : klass.getDeclaredMethods()){
			if(Modifier.isStatic(method.getModifiers()) || !Modifier.isPublic(method.getModifiers()) || 
					method.getParameterTypes().length > 0 || method.getReturnType() == null ||
					!method.getName().startsWith(get) || skips.contains(method.getName())){
				continue;
			}
			methods.add(method);
		}
		return methods;
	}
	
	/**
	 * Finds the values of the fields on an object.
	 * 
	 * @param target The target object and inspect for fields.
	 * @param stopClasses The Class that the search for fields should stop.  The process 
	 * travels up the SuperClass tree, and will naturally stop at Object.
	 * @param includeStopClass Whether to include the fields on the stopClass.
	 * @param skipMethods Names of methods to skip.
	 * @return A mapping of fields to values.  Will never be null, but the values in the 
	 * map can be.
	 */
	@SuppressWarnings("rawtypes")
	public static Map<Method, Object> lookupGetterValues(Object target, 
			List<Class> stopClasses, boolean includeStopClass, String... skipMethods) throws Exception{
		Map<Method, Object> values = null;
		if(target != null){
			// Validate Arguments
			if(stopClasses == null){
				stopClasses = new ArrayList<Class>();
				stopClasses.add(Object.class);
				includeStopClass = false;
			}
			
			// Process Target.
			List<Method> methods = lookupGetters(target.getClass(), stopClasses, includeStopClass, skipMethods);
			values = lookupGetterValues(methods, target);
		}
		return values;
	}

	
	/**
	 * Finds the getter methods within a list of methods and maps them to the values they return.
	 * 
	 * @param methods
	 * @param source
	 * @return A mapping of the methods and the values they return.
	 * @throws Exception
	 */
	public static Map<Method, Object> lookupGetterValues(List<Method> methods, Object source) throws Exception{
		Map<Method, Object> values = new HashMap<Method, Object>();
		for(Method method : methods){
			if(Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 0  && method.getReturnType() != null){
				Object value;
				try {
					value = method.invoke(source);
					values.put(method, value);
				}
				catch(IllegalAccessException e) {
					Message message = new Message("Failed to lookup method values on class instance, but should never get here: ")
						.add("target", source.getClass().getName());
					throw new Exception(message.toString(), e);
				}
			}
		}
		return values;
	}
	/**
	 * Gets all of the values of any field or getter method that is annotated with a specific annotation.
	 * 
	 * @param target
	 * @param annotation
	 * @return A list of the found values.
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Object> findAnnotatedValue(Object target, Class annotation) throws Exception{
		ArrayList<Object> values = new ArrayList<Object>();
		Map<Field, Object> fieldValues = lookupFieldValues(target, Object.class, false);
		for(Entry<Field, Object> entry : fieldValues.entrySet()){
			if(entry.getKey().getAnnotation(annotation) != null){
				values.add(entry.getValue());
			}
		}
		Map<Method, Object> getterValues = lookupGetterValues(target, null, true);
		for(Entry<Method, Object> entry : getterValues.entrySet()){
			if(entry.getKey().getAnnotation(annotation) != null){
				values.add(entry.getValue());
			}
		}
		return values;
	}
}
