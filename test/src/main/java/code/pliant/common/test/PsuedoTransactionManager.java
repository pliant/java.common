package code.pliant.common.test;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

/**
 * Empty {@link TransactionManager} that does nothing.  Is no more than a placeholder.
 * 
 * @author Daniel Rugg
 */
public class PsuedoTransactionManager implements TransactionManager {

	/* (non-Javadoc)
	 * @see javax.transaction.TransactionManager#begin()
	 */
	@Override
	public void begin() throws NotSupportedException, SystemException {
		
	}

	/* (non-Javadoc)
	 * @see javax.transaction.TransactionManager#commit()
	 */
	@Override
	public void commit() throws RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SecurityException,
			IllegalStateException, SystemException {
		
	}

	/* (non-Javadoc)
	 * @see javax.transaction.TransactionManager#getStatus()
	 */
	@Override
	public int getStatus() throws SystemException {
		return Status.STATUS_NO_TRANSACTION;
	}

	/* (non-Javadoc)
	 * @see javax.transaction.TransactionManager#getTransaction()
	 */
	@Override
	public Transaction getTransaction() throws SystemException {
		return new PsuedoTransaction();
	}

	/* (non-Javadoc)
	 * @see javax.transaction.TransactionManager#resume(javax.transaction.Transaction)
	 */
	@Override
	public void resume(Transaction arg0) throws InvalidTransactionException,
			IllegalStateException, SystemException {

	}

	/* (non-Javadoc)
	 * @see javax.transaction.TransactionManager#rollback()
	 */
	@Override
	public void rollback() throws IllegalStateException, SecurityException,
			SystemException {
	}

	/* (non-Javadoc)
	 * @see javax.transaction.TransactionManager#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		
	}

	/* (non-Javadoc)
	 * @see javax.transaction.TransactionManager#setTransactionTimeout(int)
	 */
	@Override
	public void setTransactionTimeout(int arg0) throws SystemException {

	}

	/* (non-Javadoc)
	 * @see javax.transaction.TransactionManager#suspend()
	 */
	@Override
	public Transaction suspend() throws SystemException {
		return new PsuedoTransaction();
	}

}
