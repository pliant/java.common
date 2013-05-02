package code.pliant.common.camel.predicate;

import code.pliant.common.core.NonRecoverableException;
import code.pliant.common.core.NotFoundException;
import code.pliant.common.core.RecoverableException;
import org.springframework.stereotype.Service;


/**
 * Service To Throw Exceptions
 * 
 * @author Daniel Rugg
 */
@Service
public class PredicateTestService {
	
	

	boolean recovers = false;
	
	boolean nonRecovers = false;
	
	public void reset(){
		recovers = false;
		nonRecovers = false;
	}
	
	
	/**
	 * @return the recovers
	 */
	public boolean isRecovers() {
		return recovers;
	}

	
	/**
	 * @param recovers the recovers to set
	 */
	public void doRecovers() {
		this.recovers = true;
	}

	
	/**
	 * @return the nonRecovers
	 */
	public boolean isNonRecovers() {
		return nonRecovers;
	}

	
	/**
	 * @param nonRecovers the nonRecovers to set
	 */
	public void doNonRecovers() {
		this.nonRecovers = true;
	}

	public void throwRecoverable() throws RecoverableException{
		throw new RecoverableException();
	}
	
	public void throwNonRecoverable() throws NonRecoverableException{
		throw new NonRecoverableException();
	}
	
	public void throwNotFound() throws NotFoundException{
		throw new NotFoundException();
	}
}
