package code.pliant.common.core;

/**
 * @author Daniel Rugg
 *
 */
@SuppressWarnings("rawtypes")
public class Path {
	
	public static final String PROPS_EXTENSION = ".props";
	
	public static final String CONFIG_SUFFIX = "config";
	
	public static final String SEPARATOR = "-";
	
	public static final String ROOT = "/";

	public static final String CLASSPATH_FIRST = "classpath:";

	public static final String CLASSPATH_ALL = "classpath*:";

	StringBuilder urlBuilder = new StringBuilder();
	
	void allowRoot() throws IllegalArgumentException{
		if(urlBuilder.length() > 0){
			throw new IllegalArgumentException();
		}
	}

	public Path root(){
		urlBuilder.append(ROOT);
		return this;
	}
	
	public Path classPath(){
		allowRoot();
		urlBuilder.append(CLASSPATH_ALL);
		return this;
	}
	
	public Path classPathAll(){
		allowRoot();
		urlBuilder.append(CLASSPATH_ALL);
		return this;
	}
	
	public Path path(Class path){
		urlBuilder.append(Paths.toPath(path));
		return this;
	}
	
	public Path ids(String...ids){
		if(ids != null){
			for(String id : ids){
				urlBuilder.append(id).append(SEPARATOR);
			}
		}
		return this;
	}
	
	public String configProps(){
		urlBuilder.append(CONFIG_SUFFIX).append(PROPS_EXTENSION);
		return urlBuilder.toString();
	}
}
