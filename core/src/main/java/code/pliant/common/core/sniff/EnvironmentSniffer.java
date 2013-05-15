package code.pliant.common.core.sniff;

import code.pliant.common.core.Strings;

/**
 * A Sniffer that looks for an environmental variable that has been set on the host OS.
 * 
 * @author Daniel Rugg
 */
public class EnvironmentSniffer implements Sniffer {
	
	String name;
	
	/**
	 * 
	 * @param name The name of the environment variable to look for.
	 */
	public EnvironmentSniffer(String name) {
		super();
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.healthsciencessc.core.sniff.Sniffer#retrieve()
	 */
	@Override
	public Object retrieve() {
		String value = System.getenv(name);
		if(Strings.isValid(value)){
			Retriever.inform(getClass(), value, name);
			return value;
		}
		throw Sniffer.NFE;
	}
}
