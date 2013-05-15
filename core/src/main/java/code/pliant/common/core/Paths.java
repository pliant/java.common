package code.pliant.common.core;

import java.io.File;
import java.util.Collection;


/**
 * Helper utility for providing standard names for files and resources that will be used by applications.  This 
 * utility is to help establish patterns for external resource names so that configuration of applications has 
 * a standard way of doing things.
 * 
 * @author Daniel Rugg
 */
@SuppressWarnings("rawtypes")
public class Paths {
	
	/**
	 * Determines if a path is absolute or not.
	 * 
	 * @param path The path to check.
	 * @return <code>true</code> if the path starts with a slash '/'
	 */
	public static boolean isAbsolute(String path){
		if(File.separatorChar == '/'){
			return path.startsWith(File.separator);
		}
		else{
			return path.contains(":\\");
		}
	}
	
	/**
	 * Gets the absolute path of a file.  If the given path is absolute, it is returned, else it's absolute 
	 * is calculated from the directory given.
	 * 
	 * @param directory The directory to work from.
	 * @param path The path to make absolute if not already.
	 * @return An absolute path, starting with a slash.
	 */
	public static String getAbsolutePath(File directory, String path){
		if(isAbsolute(path)){
			return path;
		}
		else{
			return new File(directory, path).getAbsolutePath();
		}
	}
	
	/**
	 * Takes a collection of paths and returns an equivalent collection of absolute paths derived 
	 * 
	 * @param <T>
	 * @param directory The directory from which relative paths will be calculated.
	 * @param paths The collection of paths to convert if needed.
	 * @return A collection containing the absolute paths.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Collection<String>> T getAbsolutePaths(File directory, T paths){
		if(paths != null){
			try {
				T absolutePaths = (T) paths.getClass().newInstance();
				for(String path : paths){
					absolutePaths.add(getAbsolutePath(directory, path));
				}
				return absolutePaths;
			}
			catch (Exception e) {
				Message message = new Message("Unable to create an instance of the collection '")
					.add(paths.getClass().getName())
					.add(".");
				throw new RuntimeException(message.toString(), e);
			}
		}
		return null;
	}
	
	/**
	 * Concatenates a list of paths together in the format the java executable would expect the 
	 * ClassPath to be delivered in.
	 * 
	 * @param paths The collection of absolute paths to concatenate together.
	 * @return A string containing the paths separated with the approppriate separator character.
	 */
	public static String toClassPath(Collection<String> paths){
		StringBuilder classPath = new StringBuilder();
		for(String path : paths){
			classPath.append(path).append(File.pathSeparatorChar);
		}
		classPath.deleteCharAt(classPath.length() - 1);
		return classPath.toString();
	}
	
	/**
	 * Concatenates a list of paths together in the format the java executable would expect the 
	 * ClassPath to be delivered in.  Verifies all of the paths are absolute, and any that are 
	 * relative are made absolute using the provided directory.
	 * 
	 * @param directory The directory to use to turn relative paths into absolute paths.
	 * @param paths The collection of absolute paths to concatenate together.
	 * @return A string containing the paths separated with the approppriate separator character.
	 */
	public static String toClassPath(File directory, Collection<String> paths){
		Collection<String> absolutePaths = getAbsolutePaths(directory, paths);
		StringBuilder classPath = new StringBuilder();
		for(String path : absolutePaths){
			classPath.append(path).append(File.pathSeparatorChar);
		}
		classPath.deleteCharAt(classPath.length() - 1);
		return classPath.toString();
	}

    /**
     * Escapes the given file path so that it's safe for inclusion in a
     * Java string literal.
     *
     * @param file A file whose path should be escaped.
     * @return escaped file path, ready for inclusion in a string literal
     */
	public static String escapeFilePath(final File file) {
        return file.getPath().replace("\\", "\\\\");
    }

	/**
	 * Returns the package path as recognized by the Spring Resource URL.  java.util.Map would return <code>/java/util</code>
	 * 
	 * @param klass The class whose package name is transformed.
	 * @return The transformed package name.  If null, and empty string is returned.
	 */
	public static String toPath(Class klass){
		if(klass == null){
			return Strings.EMPTY;
		}
		return klass.getPackage().getName().replaceAll("\\.", Path.SEPARATOR);
	}

	/**
	 * Returns the package path as recognized by the Spring Resource URL.  java.util.Map would return <code>/java/util</code>
	 * 
	 * @param klass The class whose package name is transformed.
	 * @return The transformed package name.  If null, and empty string is returned.
	 */
	public static String toRootPath(Class klass){
		return Path.SEPARATOR + toPath(klass);
	}
	
	/**
	 * Generates a name for a typed configuration file.  The format consists of the FQN of the klass package provided, the 
	 * ids provided, if any, in order, and 'config.props' all concatenated with '-'.  So, a call of 
	 * configFileName(java.util.Map.class, 'yo', 'dave') will create the name 'java.util-yo-dave-config.props'.
	 * 
	 * @param prefixer The Class whose package to use in the name of the file.
	 * @param ids Any additional identifiers that are wanted in the name of the file.
	 * @return The final string name.
	 */
	public static String configFileName(Class prefixer, String... ids){
		StringBuilder name = new StringBuilder();
		if(prefixer != null){
			name.append(prefixer.getPackage().getName()).append(Path.DASH);
		}
		if(ids != null && ids.length > 0){
			for(String id : ids){
				if(Strings.isValid(id)){
					name.append(id).append(Path.DASH);
				}
			}
		}
		name.append(Path.CONFIG_SUFFIX).append(Path.PROPS_EXTENSION);
		return name.toString();
	}

	/**
	 * Generates a name for a typed configuration file.  The format consists of the FQN of the klass package provided, the 
	 * ids provided, if any, in order, and 'config.props' all concatenated with '-'.  So, a call of 
	 * configFileName(java.lang.String, java.util.Map.class, 'yo', 'dave') will create the name 'java/lang/java.util-yo-dave-config.props'.
	 * 
	 * @param klass The class to use as a director path to the file.
	 * @param prefixer The Class whose package to use as the prefix of the name of the file.
	 * @param ids Any additional identifiers that are wanted in the name of the file.
	 * @return The final string name.
	 */
	public static String configFileName(Class klass, Class prefixer, String...ids){
		if(klass == null){
			configFileName(prefixer, ids);
		}
		return toPath(klass) + rootConfigFileName(prefixer, ids);
	}
	
	/**
	 * Generates a name for a typed configuration file.  The format consists of '/', the FQN of the klass package provided, the 
	 * ids provided, if any, in order, and 'config.props' all concatenated with '-'.  So, a call of 
	 * configFileName(java.util.Map.class, 'yo', 'dave') will create the name '/java.util-yo-dave-config.props'.
	 * 
	 * @param prefixer The Class whose package to use as the prefix of the name of the file.
	 * @param ids Any additional identifiers that are wanted in the name of the file.
	 * @return The final string name.
	 */
	public static String rootConfigFileName(Class prefixer, String...ids){
		return Path.SEPARATOR + configFileName(prefixer, ids);
	}
	
	/**
	 * Generates a name for a typed configuration file.  The format consists of '/', the FQN of the klass package provided, the 
	 * ids provided, if any, in order, and 'config.props' all concatenated with '-'.  So, a call of 
	 * configFileName(java.lang.String, java.util.Map.class, 'yo', 'dave') will create the name 
	 * '/java/lang/java.util-yo-dave-config.props'.
	 * 
	 * @param klass The class to use as a director path to the file.
	 * @param prefixer The Class whose package to use as the prefix of the name of the file.
	 * @param ids Any additional identifiers that are wanted in the name of the file.
	 * @return The final string name.
	 */
	public static String rootConfigFileName(Class klass, Class prefixer, String...ids){
		return toRootPath(klass) + rootConfigFileName(prefixer, ids);
	}
	
	/**
	 * Generates a name for a typed configuration file.  The format consists of '/', the FQN of the klass package provided, the 
	 * ids provided, if any, in order, and 'config.props' all concatenated with '-'.  So, a call of 
	 * configFileName(java.util.Map.class, 'yo', 'dave') will create the name 'classpath:java.util-yo-dave-config.props'.
	 * 
	 * @param prefixer The Class whose package to use as the prefix of the name of the file.
	 * @param ids Any additional identifiers that are wanted in the name of the file.
	 * @return The final string name.
	 */
	public static String classPathConfigFileName(Class prefixer, String...ids){
		return Path.CLASSPATH_FIRST + configFileName(prefixer, ids);
	}
	
	/**
	 * Generates a name for a typed configuration file.  The format consists of '/', the FQN of the klass package provided, the 
	 * ids provided, if any, in order, and 'config.props' all concatenated with '-'.  So, a call of 
	 * configFileName(java.lang.String, java.util.Map.class, 'yo', 'dave') will create the name 
	 * 'classpath:java/lang/java.util-yo-dave-config.props'.
	 * 
	 * @param klass The class to use as a director path to the file.
	 * @param prefixer The Class whose package to use as the prefix of the name of the file.
	 * @param ids Any additional identifiers that are wanted in the name of the file.
	 * @return The final string name.
	 */
	public static String classPathConfigFileName(Class klass, Class prefixer, String...ids){
		return Path.CLASSPATH_FIRST + toPath(klass) + rootConfigFileName(prefixer, ids);
	}
	
	/**
	 * Generates a name for a typed configuration file.  The format consists of '/', the FQN of the klass package provided, the 
	 * ids provided, if any, in order, and 'config.props' all concatenated with '-'.  So, a call of 
	 * configFileName(java.util.Map.class, 'yo', 'dave') will create the name 'classpath:/java.util-yo-dave-config.props'.
	 * 
	 * @param prefixer The Class whose package to use as the prefix of the name of the file.
	 * @param ids Any additional identifiers that are wanted in the name of the file.
	 * @return The final string name.
	 */
	public static String classPathRootConfigFileName(Class prefixer, String...ids){
		return Path.CLASSPATH_FIRST + rootConfigFileName(prefixer, ids);
	}
	
	/**
	 * Generates a name for a typed configuration file.  The format consists of '/', the FQN of the klass package provided, the 
	 * ids provided, if any, in order, and 'config.props' all concatenated with '-'.  So, a call of 
	 * configFileName(java.lang.String, java.util.Map.class, 'yo', 'dave') will create the name 
	 * 'classpath:/java/lang/java.util-yo-dave-config.props'.
	 * 
	 * @param path The class to use as a director path to the file.
	 * @param prefixer The Class whose package to use as the prefix of the name of the file.
	 * @param ids Any additional identifiers that are wanted in the name of the file.
	 * @return The final string name.
	 */
	public static String classPathRootConfigFileName(Class path, Class prefixer, String...ids){
		return Path.CLASSPATH_FIRST + toRootPath(path) + rootConfigFileName(prefixer, ids);
	}
	
	/**
	 * Generates a name for a typed configuration file.  The format consists of '/', the FQN of the klass package provided, the 
	 * ids provided, if any, in order, and 'config.props' all concatenated with '-'.  So, a call of 
	 * configFileName(java.util.Map.class, 'yo', 'dave') will create the name 'classpath*:java.util-yo-dave-config.props'.
	 * 
	 * @param prefixer The Class whose package to use as the prefix of the name of the file.
	 * @param ids Any additional identifiers that are wanted in the name of the file.
	 * @return The final string name.
	 */
	public static String classPathAllConfigFileName(Class prefixer, String...ids){
		return Path.CLASSPATH_ALL + configFileName(prefixer, ids);
	}
	
	/**
	 * Generates a name for a typed configuration file.  The format consists of '/', the FQN of the klass package provided, the 
	 * ids provided, if any, in order, and 'config.props' all concatenated with '-'.  So, a call of 
	 * configFileName(java.lang.String, java.util.Map.class, 'yo', 'dave') will create the name 
	 * 'classpath*:java/lang/java.util-yo-dave-config.props'.
	 * 
	 * @param path The class to use as a director path to the file.
	 * @param prefixer The Class whose package to use as the prefix of the name of the file.
	 * @param ids Any additional identifiers that are wanted in the name of the file.
	 * @return The final string name.
	 */
	public static String classPathAllConfigFileName(Class path, Class prefixer, String...ids){
		return Path.CLASSPATH_ALL + toPath(path) + rootConfigFileName(prefixer, ids);
	}
	
	/**
	 * Generates a name for a typed configuration file.  The format consists of '/', the FQN of the klass package provided, the 
	 * ids provided, if any, in order, and 'config.props' all concatenated with '-'.  So, a call of 
	 * configFileName(java.util.Map.class, 'yo', 'dave') will create the name 'classpath*:/java.util-yo-dave-config.props'.
	 * 
	 * @param prefixer The Class whose package to use as the prefix of the name of the file.
	 * @param ids Any additional identifiers that are wanted in the name of the file.
	 * @return The final string name.
	 */
	public static String classPathAllRootConfigFileName(Class prefixer, String...ids){
		return Path.CLASSPATH_ALL + rootConfigFileName(prefixer, ids);
	}
	
	/**
	 * Generates a name for a typed configuration file.  The format consists of '/', the FQN of the klass package provided, the 
	 * ids provided, if any, in order, and 'config.props' all concatenated with '-'.  So, a call of 
	 * configFileName(java.lang.String, java.util.Map.class, 'yo', 'dave') will create the name 
	 * 'classpath*:/java/lang/java.util-yo-dave-config.props'.
	 * 
	 * @param path The class to use as a director path to the file.
	 * @param prefixer The Class whose package to use as the prefix of the name of the file.
	 * @param ids Any additional identifiers that are wanted in the name of the file.
	 * @return The final string name.
	 */
	public static String classPathAllRootConfigFileName(Class path, Class prefixer, String...ids){
		return Path.CLASSPATH_ALL + toRootPath(path) + rootConfigFileName(prefixer, ids);
	}
}
