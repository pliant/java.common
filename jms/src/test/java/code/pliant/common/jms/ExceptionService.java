package code.pliant.common.jms;

import code.pliant.common.core.NonRecoverableException;
import code.pliant.common.core.RecoverableException;
import org.springframework.stereotype.Service;


/**
 * Service for throwing exceptions.
 * 
 * @author Daniel Rugg
 */
@Service
public class ExceptionService {
	
	private boolean recoverable = false;
	
	private int callCount = 0;
	
	/**
	 * @param recoverable the recoverable to set
	 */
	public void setRecoverable(boolean recoverable) {
		this.recoverable = recoverable;
	}


	public void throwIt() throws Exception{
		if(recoverable){
			throw new RecoverableException();
		}
		else{
			throw new NonRecoverableException();
		}
	}
	
	public void upCount(){
		callCount++;
	}
	
	public int getCallCount(){
		return callCount;
	}
}
