/**
 * 
 */
package code.pliant.common.camel;

import org.springframework.stereotype.Service;


/**
 * 
 * @author Daniel Rugg
 */
@Service
public class PatternTestService {

	int loopCount = 0;
	
	/**
	 * @return the loopCount
	 */
	public int getLoopCount() {
		return loopCount;
	}

	/**
	 * @param loopCount the loopCount to set
	 */
	public void reset() {
		loopCount = 0;
	}

	public boolean continueLoop(){
		return loopCount > 10;
	}
	
	public void uptick(){
		loopCount++;
	}
	
	public void throwException() throws Exception{
		throw new Exception("This is an Exception.");
	}
}
