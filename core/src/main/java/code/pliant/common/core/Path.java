package code.pliant.common.core;

/**
 * Creates a mutable class that can represent a path to a file or resource.  
 * 
 * This is not threadsafe or validating.
 * 
 * @author Daniel Rugg
 */
@SuppressWarnings("rawtypes")
public class Path {
	
	/**
	 * Constant representing a properties file extension of <code>.props</code>.
	 */
	public static final String PROPS_EXTENSION = ".props";
	
	/**
	 * Constant used for configuration/properties file name suffixes.  Value is <code>config</code>.
	 */
	public static final String CONFIG_SUFFIX = "config";
	
	/**
	 * Constant used for separate suffixes.  Value is <code>-</code>.
	 */
	public static final String DASH = "-";
	
	/**
	 * Constant used for represent path separator.  Value is <code>/</code>.
	 */
	public static final String SEPARATOR = "/";
	
	/**
	 * Constant for path URI to represent a path to a first match on the classpath.  
	 * This is the same URI protocol value that is used in Spring for resource lookups on
	 * the classpath. Value is <code>classpath:</code>.
	 */
	public static final String CLASSPATH_FIRST = "classpath:";
	
	/**
	 * Constant for path URI to represent a path to a first match on the classpath.  
	 * This is the same URI protocol value that is used in Spring for resource lookups on
	 * the classpath. Value is <code>classpath*:</code>.
	 */
	public static final String CLASSPATH_ALL = "classpath*:";

	StringBuilder urlBuilder = new StringBuilder();
	
	/**
	 * Creates a new Path instance prefaced with the {@link #CLASSPATH_FIRST} value;
	 * @return A new Path instance.
	 */
	public static Path classPath(){
		Path path = new Path();
		path.urlBuilder.append(CLASSPATH_FIRST);
		return path;
	}
	
	/**
	 * Creates a new Path instance prefaced with the {@link #CLASSPATH_ALL} value;
	 * @return A new Path instance.
	 */
	public static Path classPathAll(){
		Path path = new Path();
		path.urlBuilder.append(CLASSPATH_ALL);
		return path;
	}

	/**
	 * Checks if the last character in the path matches the provided character.
	 * @param c The character to test against.
	 * @return <code>true</code> if it matches, else <code>false</code>
	 */
	public boolean isLastCharacter(char c){
		if(urlBuilder.charAt(urlBuilder.length() -1) == c){
			return true;
		}
		return false;
	}
	
	/**
	 * Adds the {@link #SEPARATOR} value to the path.
	 * @return The current Path instance.
	 */
	public Path addSeparator(){
		urlBuilder.append(SEPARATOR);
		return this;
	}

	
	/**
	 * Adds a variable number of folders onto the path, with each folder having the {@link #SEPARATOR} value 
	 * added after it.
	 * @param names The names of the folders to add.
	 * @return The current Path instance.
	 */
	public Path addFolders(String... names){
		for(String name : names){
			urlBuilder.append(name).append(SEPARATOR);
		}
		return this;
	}
	
	/**
	 * Adds the {@link Paths#toPath(Class)} value to the Path.
	 * @param klass The Class to generate the path segment string from.
	 * @return The current Path instance.
	 */
	public Path addClassToPath(Class klass){
		urlBuilder.append(Paths.toPath(klass));
		return this;
	}
	
	/**
	 * Adds a set of strings to the path, each prefixed with the {@link #DASH}.
	 * @param ids The set of suffixes to add.
	 * @return The current Path instance.
	 */
	public Path addSuffixes(String... suffixes){
		if(suffixes != null){
			for(String suffix : suffixes){
				urlBuilder.append(DASH).append(suffix);
			}
		}
		return this;
	}
	
	/**
	 * Appends a file name suffix of {@link #CONFIG_SUFFIX} and file extension of {@link #PROPS_EXTENSION} 
	 * on to the path.
	 * @return The current Path instance.
	 */
	public Path endAsConfigProperties(){
		if(!isLastCharacter('-')){
			urlBuilder.append(DASH);
		}
		urlBuilder.append(CONFIG_SUFFIX).append(PROPS_EXTENSION);
		return this;
	}

	/**
	 * Returns the String representation of the Path instance.
	 */
	@Override
	public String toString() {
		return urlBuilder.toString();
	}
}
