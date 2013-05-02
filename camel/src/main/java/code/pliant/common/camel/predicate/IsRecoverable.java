package code.pliant.common.camel.predicate;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import spring.code.pliant.common.ExceptionManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * A predicate component that checks if the thrown exception is recoverable.  This should be used only when in 
 * a catch section of a try/catch/finally block.
 * 
 * @author Daniel Rugg
 */
@Component
public class IsRecoverable implements Predicate {
	
	@Autowired
	ExceptionManagerService exceptionService;

	/* (non-Javadoc)
	 * @see org.apache.camel.Predicate#matches(org.apache.camel.Exchange)
	 */
	@Override
	public boolean matches(Exchange exchange) {
		Exception e = exchange.getException();
		if(e == null){
			e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		}
		return exceptionService.isRecoverable(e);
	}
}
