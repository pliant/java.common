package code.pliant.common.test;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

/**
 * Empty {@link Transaction} that does nothing.  It's no more than a placeholder.
 * 
 * @author Daniel Rugg
 */
public class PsuedoTransaction implements Transaction {

	/* (non-Javadoc)
	 * @see javax.transaction.Transaction#commit()
	 */
	@Override
	public void commit() throws RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SecurityException,
			IllegalStateException, SystemException {

	}

	/* (non-Javadoc)
	 * @see javax.transaction.Transaction#delistResource(javax.transaction.xa.XAResource, int)
	 */
	@Override
	public boolean delistResource(XAResource arg0, int arg1)
			throws IllegalStateException, SystemException {
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.transaction.Transaction#enlistResource(javax.transaction.xa.XAResource)
	 */
	@Override
	public boolean enlistResource(XAResource arg0) throws RollbackException,
			IllegalStateException, SystemException {
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.transaction.Transaction#getStatus()
	 */
	@Override
	public int getStatus() throws SystemException {
		return Status.STATUS_NO_TRANSACTION;
	}

	/* (non-Javadoc)
	 * @see javax.transaction.Transaction#registerSynchronization(javax.transaction.Synchronization)
	 */
	@Override
	public void registerSynchronization(Synchronization arg0)
			throws RollbackException, IllegalStateException, SystemException {

	}

	/* (non-Javadoc)
	 * @see javax.transaction.Transaction#rollback()
	 */
	@Override
	public void rollback() throws IllegalStateException, SystemException {

	}

	/* (non-Javadoc)
	 * @see javax.transaction.Transaction#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {

	}

}
