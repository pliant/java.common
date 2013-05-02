/**
 * 
 */
package spring.code.pliant.common;

import java.util.List;

import code.pliant.common.core.ExceptionInspector;
import code.pliant.common.core.ExceptionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Exposes the {@link ExceptionManager} as a service bean, automatically injecting any spring component 
 * implementing the {@link ExceptionInspector} interface into it.  To use, declare 
 * <code>&amp;Import()</code> with this class in you application configuration classes.
 * 
 * @author Daniel Rugg
 */
@Service
public class ExceptionManagerService extends ExceptionManager {

	/* (non-Javadoc)
	 * @see org.healthsciencessc.core.common.ExceptionManager#addExceptionInspectors(java.util.List)
	 */
	@Override
	@Autowired(required=false)
	public void addExceptionInspectors(List<ExceptionInspector> inspectors) {
		super.addExceptionInspectors(inspectors);
	}
}
