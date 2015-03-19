package com.elitecore.cpe.bl.entity.inventory.transfer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TBLSINVENTORYORDERSTATUS")
@NamedQueries({ 
	@NamedQuery(name = "InventoryTransferOrderStatus.searchOrderStatusByAlias",query ="select o from InventoryTransferOrderStatus o where o.alias=:alias ")
})
public class InventoryTransferOrderStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String orderStatusId;
	private String name;
	private String alias;
	private String description;
	private String systemGenrated;
	
	
	@Id
	@Column(name = "ORDERSTATUSID")
	public String getOrderStatusId() {
		return orderStatusId;
	}
	public void setOrderStatusId(String orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Column(name = "ALIAS")
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Column(name = "SYSTEMGENERATED")
	public String getSystemGenrated() {
		return systemGenrated;
	}
	public void setSystemGenrated(String systemGenrated) {
		this.systemGenrated = systemGenrated;
	}
	@Override
	public String toString() {
		return "InventoryTransferOrderStatus [orderStatusId=" + orderStatusId
				+ ", name=" + name + ", alias=" + alias + ", description="
				+ description + ", systemGenrated=" + systemGenrated + "]";
	}
	
	
	
	

}
