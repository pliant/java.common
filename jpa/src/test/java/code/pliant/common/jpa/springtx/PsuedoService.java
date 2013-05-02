package code.pliant.common.jpa.springtx;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import code.pliant.common.jpa.springtx.entity.TestSpringTxEntity;

/**
 * Created an interface so that proxy based AOP can be used to control the transaction.  If not using 
 * interfaces then the AspectJ AOP dependencies need to be included.
 * 
 * @author Daniel Rugg
 */
public interface PsuedoService {

	@Transactional(propagation = Propagation.REQUIRED)
	public abstract void doCreate(TestSpringTxEntity entity);

	@Transactional(propagation = Propagation.REQUIRED)
	public abstract void retrieveAndUpdate(TestSpringTxEntity entity);

	@Transactional(propagation = Propagation.REQUIRED)
	public abstract void retrieveAndDelete(TestSpringTxEntity entity);

}
