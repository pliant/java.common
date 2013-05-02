package code.pliant.common.jpa.springtx.entity;

import code.pliant.common.jpa.dao.DAOService;

/**
 * DAO interface for HashEntity.
 * 
 * @author Daniel Rugg
 */
public interface TestSpringTxDAOService extends DAOService<TestSpringTxEntity> {

	/**
	 * Gets a HashEntity by it's hash id.
	 * @param hash
	 * @return
	 */
	TestSpringTxEntity findByPrimaryKey(Integer pkey);
}
