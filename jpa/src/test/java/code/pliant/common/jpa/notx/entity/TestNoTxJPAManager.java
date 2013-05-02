package code.pliant.common.jpa.notx.entity;

import org.springframework.stereotype.Component;

import code.pliant.common.jpa.AbstractBootstrapJPAManager;


/**
 * Demonstrates usage of the AbstractBootstrapJPAManager in a Spring environment where transaction support 
 * is going to limited to atomic operations or completely manual through the EntityManager.getTransaction() 
 * API.
 * 
 * @author Daniel Rugg
 */
@Component
public class TestNoTxJPAManager extends AbstractBootstrapJPAManager {

	/**
	 * 
	 */
	public TestNoTxJPAManager() {
		super("notx");
	}
}
