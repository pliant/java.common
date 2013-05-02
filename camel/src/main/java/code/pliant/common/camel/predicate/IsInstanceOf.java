package code.pliant.common.camel.predicate;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;


/**
 * A predicate component that checks if the incoming message body is of a certain type..
 * 
 * @author Daniel Rugg
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class IsInstanceOf implements Predicate {
	
	private Class klass = Object.class;
	

	/**
	 * @param klass The Class to check against the message body.
	 */
	public IsInstanceOf(Class klass) {
		super();
		this.klass = klass;
	}


	/* (non-Javadoc)
	 * @see org.apache.camel.Predicate#matches(org.apache.camel.Exchange)
	 */
	@Override
	public boolean matches(Exchange exchange) {
		Object body = exchange.getIn().getBody();
		if(body != null){
			return klass.isAssignableFrom(body.getClass());
		}
		return false;
	}
}
