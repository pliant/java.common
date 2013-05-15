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
	 * Constructor for an ExceptionManager with no built-in assumptions for any Exceptions.
	 */
	public ExceptionManager() {
		super();
		registerRecoverableException();
	}
	
	/**
	 * Constructor that has the ability to assume any RecoverableException is considered recoverable.
	 * Seems silly, yes, but this is here to allow creating an ExceptionManager without that assumption.
	 * 
	 * @param register If false, the default ExpectionInspector will not be registered.
	 */
	public ExceptionManager(boolean register) {
		super();
		if(register) registerRecoverableException();
	}

	/**
	 * Checks if an Exception is recoverable according to the registered ExceptionInspectors.
	 * 
	 * @param e The exception to inspect.
	 * @return {@code true} if the provided Exception is recoverable, else {@code false};
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
	 * Adds a single {@link ExceptionInspector} to the manager.
	 * 
	 * @param inspector The {@link ExceptionInspector} to add to the registry.
	 */
	public void addExceptionInspector(ExceptionInspector inspector){
		inspectors.add(inspector);
	}
	
	/**
	 * Adds a list of {@link ExceptionInspector}s to the manager.
	 * @param inspectors The list of {@link ExceptionInspector}s to add to the registry.
	 */
	public void addExceptionInspectors(List<ExceptionInspector> inspectors){
		this.inspectors.addAll(inspectors);
	}
	
	/**
	 * Adds a {@link ExceptionInspector} that will consider {@link RecoverableException}s recoverable.
	 */
	protected void registerRecoverableException(){
		inspectors.add(new ExceptionInspector() {
			public boolean isRecoverable(Exception e) {
				return e instanceof RecoverableException;
			}
		});
	}
}
