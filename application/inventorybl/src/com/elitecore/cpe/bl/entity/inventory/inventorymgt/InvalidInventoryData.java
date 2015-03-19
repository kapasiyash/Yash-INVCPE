package com.elitecore.cpe.bl.entity.inventory.inventorymgt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache (usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name="TBLMINVALIDINVENTORY"
)
public class InvalidInventoryData  implements Serializable{

	private Long id;
	private Long batchId;
	private int inventoryStatusId;
	private String errordesc;
	
	private String warehouseName;
	private String itemRefName;
	
	 @SequenceGenerator(name="generator", sequenceName="TBLMINVALIDINVENTORY_SEQ", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name="id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="BATCHID")
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	
	@Column(name="INVENTORYSTATUSID")
	public int getInventoryStatusId() {
		return inventoryStatusId;
	}
	public void setInventoryStatusId(int inventoryStatusId) {
		this.inventoryStatusId = inventoryStatusId;
	}
	
	@Column(name="ERRORDESC")
	public String getErrordesc() {
		return errordesc;
	}
	public void setErrordesc(String errordesc) {
		this.errordesc = errordesc;
	}
	
	@Column(name="WAREHOUSENAME")
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
	@Column(name="ITEMREFNAME")
	public String getItemRefName() {
		return itemRefName;
	}
	public void setItemRefName(String itemRefName) {
		this.itemRefName = itemRefName;
	}
	
	@Override
	public String toString() {
		return "InvalidInventoryData [id=" + id + ", batchId=" + batchId
				+ ", inventoryStatusId=" + inventoryStatusId + ", errordesc="
				+ errordesc + ", warehouseName=" + warehouseName
				+ ", itemRefName=" + itemRefName + "]";
	}
	
	
	
}
