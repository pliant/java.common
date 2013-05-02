package code.pliant.common.core;

import java.util.ArrayList;
import java.util.List;


/**
 * A registry for Exceptions classes that helps determine if the Exception is recoverable.  Useful 
 * for message routing/retries.
 * 
 * @author Daniel Rugg
 */
public class ExceptionManager {

	protected List<ExceptionInspector> inspectors = new ArrayList<ExceptionInspector>();

	/**
	 * 
	 */
	public ExceptionManager() {
		super();
		registerRecoverableException();
	}
	
	/**
	 * 
	 * @param register If false, the default ExpectionInspector will not be registered.
	 */
	public ExceptionManager(boolean register) {
		super();
		if(register) registerRecoverableException();
	}

	/**
	 * Checks if an Exception is recoverable according to the registered ExceptionInspectors.
	 * @param e The exception to inspect.
	 * @return
	 */
	public boolean isRecoverable(Exception e){
		for(ExceptionInspector inspector : inspectors){
			if(inspector.isRecoverable(e)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a single ExceptionInspector to the manager.
	 * @param inspector
	 */
	public void addExceptionInspector(ExceptionInspector inspector){
		inspectors.add(inspector);
	}
	
	/**
	 * Adds a list of ExceptionInspectors to the manager.
	 * @param inspectors
	 */
	public void addExceptionInspectors(List<ExceptionInspector> inspectors){
		this.inspectors.addAll(inspectors);
	}
	
	/**
	 * Adds a generic inspector that 
	 */
	protected void registerRecoverableException(){
		inspectors.add(new ExceptionInspector() {
			public boolean isRecoverable(Exception e) {
				return e instanceof RecoverableException;
			}
		});
	}
}
