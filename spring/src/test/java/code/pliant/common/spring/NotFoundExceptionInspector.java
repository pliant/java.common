package code.pliant.common.spring;

import code.pliant.common.core.NotFoundException;
import code.pliant.common.core.ExceptionInspector;
import org.springframework.stereotype.Component;


/**
 * A componentized ExceptionInspector for automatic injection.
 * 
 * @author Daniel Rugg
 */
@Component
public class NotFoundExceptionInspector implements ExceptionInspector {

	/* (non-Javadoc)
	 * @see org.healthsciencessc.core.common.ExceptionInspector#isRecoverable(java.lang.Exception)
	 */
	@Override
	public boolean isRecoverable(Exception e) {
		return e instanceof NotFoundException;
	}
}
