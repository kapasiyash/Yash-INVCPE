
package com.elitecore.cpe.bl.entity.inventory.inventorymgt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TBLSINVENTORYSUBSTATUS")
public class InventorySubStatusData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long inventorySubStatusId;
	private String name;
	private String systemGenerated;
	private String alias;
	private String discription;

	/**
	 * @return the inventoryStatusId
	 */
	@Id
	@Column(name = "INVENTORYSUBSTATUSID")
	public Long getInventorySubStatusId() {
		return inventorySubStatusId;
	}

	public void setInventorySubStatusId(Long inventorySubStatusId) {
		this.inventorySubStatusId = inventorySubStatusId;
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
	public String getSystemGenerated() {
		return systemGenerated;
	}

	public void setSystemGenerated(String systemGenerated) {
		this.systemGenerated = systemGenerated;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	

	/**
	 * @param discription
	 *            the discription to set
	 */
	public void setDiscription(String discription) {
		this.discription = discription;
	}

	@Override
	public String toString() {
		return "InventorySubStatusData [inventorySubStatusId="
				+ inventorySubStatusId + ", name=" + name
				+ ", systemGenerated=" + systemGenerated + ", alias=" + alias
				+ ", discription=" + discription + "]";
	}

	
	
	
}
