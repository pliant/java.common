package code.pliant.common.core.sniff;

import code.pliant.common.core.Strings;

/**
 * A Sniffer that looks for an system property that has been set when the JVM started.
 * 
 * @author Daniel Rugg
 */
public class SystemPropertySniffer implements Sniffer {
	
	private String name;

	/**
	 * 
	 * @param name The name of the system property to look for.
	 */
	public SystemPropertySniffer(String name) {
		super();
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.healthsciencessc.core.sniff.Sniffer#retrieve()
	 */
	@Override
	public Object retrieve() {
		String value = System.getProperty(name);
		if(Strings.isValid(value)){
			Retriever.inform(getClass(), value, name);
			return value;
		}
		throw Sniffer.NFE;
	}
}
