package jpa.code.pliant.common;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Component;

import code.pliant.common.core.ExceptionInspector;


/**
 * Inspects JPA Exceptions to see if they are recoverable Hibernate connections.  Connection open exceptions are considered
 * recoverable with this inspector.  Use a <code>@Import</code> annotation with this class to enable.
 * 
 * @author Daniel Rugg
 */
@Component
public class HibernateExceptionInspector implements ExceptionInspector {
	
	private final String CONNECTION_MESSAGE = "Cannot open connection";

	/* (non-Javadoc)
	 * @see org.healthsciencessc.core.common.ExceptionInspector#isRecoverable(java.lang.Exception)
	 */
	@Override
	public boolean isRecoverable(Exception e) {
		if(e instanceof HibernateException){
			for(Throwable t : ((HibernateException)e).getThrowables()){
				if(CONNECTION_MESSAGE.equals(t.getMessage())){
					return true;
				}
			}
			return false;
		}
		else{
			return CONNECTION_MESSAGE.equals(e.getMessage());
		}
	}
}
