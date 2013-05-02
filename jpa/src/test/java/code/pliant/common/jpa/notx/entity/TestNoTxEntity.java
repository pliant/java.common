package code.pliant.common.jpa.notx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Entity to use in testing.
 * 
 * @author Daniel Rugg
 */
@Entity
@Table(name = "test_table")
public class TestNoTxEntity {

	private Integer pkey;
	
	private String name;
	
	private Boolean active;

	
	/**
	 * @return the pkey
	 */
	@Id
	@Column(name = "test_pkey", nullable = false)
	public Integer getPkey() {
		return pkey;
	}
	
	/**
	 * @param pkey the pkey to set
	 */
	public void setPkey(Integer pkey) {
		this.pkey = pkey;
	}
	
	/**
	 * @return the name
	 */
	@Column(name = "test_name", nullable = false, length = 64)
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the active
	 */
	@Column(name = "test_active", nullable = false)
	public Boolean getActive() {
		return active;
	}
	
	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
}
