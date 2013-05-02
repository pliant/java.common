package code.pliant.common.jpa.notx.entity;

import code.pliant.common.jpa.dao.DAOService;


/**
 * DAO interface for HashEntity.
 * 
 * @author Daniel Rugg
 */
public interface TestNoTxDAOService extends DAOService<TestNoTxEntity> {

	/**
	 * Gets a HashEntity by it's hash id.
	 * @param hash
	 * @return
	 */
	TestNoTxEntity findByPrimaryKey(Integer pkey);
}
