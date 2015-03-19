/**
 * 
 */
package com.elitecore.cpe.bl.entity.inventory.inventorymgt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Joel.Macwan
 * 
 */

@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TBLSINVENTORYSTATUS")
public class InventoryStatusData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long inventoryStatusId;
	private String name;
	private String systemGenrated;
	private String alias;
	private String discription;
	private Character transferrable;
		
	
	/**
	 * @return the inventoryStatusId
	 */
	@Id
	@Column(name = "INVENTORYSTATUSID")
	public Long getInventoryStatusId() {
		return inventoryStatusId;
	}

	/**
	 * @param inventoryStatusId
	 *            the inventoryStatusId to set
	 */
	public void setInventoryStatusId(Long inventoryStatusId) {
		this.inventoryStatusId = inventoryStatusId;
	}

	/**
	 * @return the name
	 */
	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the systemGenrated
	 */
	@Column(name = "SYSTEMGENERATED")
	public String getSystemGenrated() {
		return systemGenrated;
	}

	/**
	 * @param systemGenrated
	 *            the systemGenrated to set
	 */
	public void setSystemGenrated(String systemGenrated) {
		this.systemGenrated = systemGenrated;
	}

	/**
	 * @return the alias
	 */
	@Column(name = "ALIAS")
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the discription
	 */
	@Column(name = "DESCRIPTION")
	public String getDiscription() {
		return discription;
	}

	
	
	
	
	@Column(name = "TRANSFERRABLE")
	public Character getTransferrable() {
		return transferrable;
	}

	public void setTransferrable(Character transferrable) {
		this.transferrable = transferrable;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InventoryStatusData [inventoryStatusId=" + inventoryStatusId
				+ ", name=" + name + ", systemGenrated=" + systemGenrated
				+ ", alias=" + alias + ", discription=" + discription + "]";
	}

	/**
	 * @param discription
	 *            the discription to set
	 */
	public void setDiscription(String discription) {
		this.discription = discription;
	}
}
