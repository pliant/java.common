package code.pliant.common.core.sniff;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import code.pliant.common.core.Strings;


/**
 * A sniffer that does a lookup in the InitialContext of the container it is running in.
 * 
 * @author Daniel Rugg
 */
public class InitialContextSniffer implements Sniffer {
	
	String name = null;
	
	/**
	 * @param name The name to use to lookup the resource in the InitialContext.
	 */
	public InitialContextSniffer(String name) {
		super();
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.healthsciencessc.core.sniff.Sniffer#retrieve()
	 */
	@Override
	public Object retrieve(){
		try {
			if(Strings.isValid(name)){
				Object value = new InitialContext().lookup(name);
				Retriever.inform(getClass(), value, name);
				return value;
			}
		}
		catch (NamingException e) {}
		throw Sniffer.NFE;
	}
}
