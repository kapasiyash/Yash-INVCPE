package com.elitecore.cpe.bl.entity.inventory.inventorymgt;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;

@Entity
@Cache (usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name="TBLTWHINVENTORYSTATUSHISTORY"
)
public class WarehouseInventoryStatusHistoryData implements Serializable{

	private Long id;
	private WarehouseData fromWarehouse;
	private WarehouseData toWarehouse;
	private ItemData resource;
	private WarehouseInventoryStatusData warehouseInventoryStatus;
	
	private String remark;
	private Long numbercount;
	
	private Timestamp updatedate;
    private String updatedby;
    
    @SequenceGenerator(name="generator", sequenceName="TBLTWHINSTATUSHISTORY_SEQ", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name="ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	 @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="FROMWAREHOUSEID")
	public WarehouseData getFromWarehouse() {
		return fromWarehouse;
	}
	public void setFromWarehouse(WarehouseData fromWarehouse) {
		this.fromWarehouse = fromWarehouse;
	}
	
	 @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="TOWAREHOUSEID")
	public WarehouseData getToWarehouse() {
		return toWarehouse;
	}
	public void setToWarehouse(WarehouseData toWarehouse) {
		this.toWarehouse = toWarehouse;
	}
	
	 @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="RESOURCEID")
	public ItemData getResource() {
		return resource;
	}
	public void setResource(ItemData resource) {
		this.resource = resource;
	}
	
	 @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="WAREHOUSEINVENTORYSTATUSID")
	public WarehouseInventoryStatusData getWarehouseInventoryStatus() {
		return warehouseInventoryStatus;
	}
	public void setWarehouseInventoryStatus(
			WarehouseInventoryStatusData warehouseInventoryStatus) {
		this.warehouseInventoryStatus = warehouseInventoryStatus;
	}
	
	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="NUMBERCOUNT")
	public Long getNumbercount() {
		return numbercount;
	}
	public void setNumbercount(Long numbercount) {
		this.numbercount = numbercount;
	}
	
	@Column(name="UPDATEDDATE")
	public Timestamp getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}
	
	@Column(name="UPDATEDBYSTAFFID")
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	
	
	@Override
	public String toString() {
		return "WarehouseInventoryStatusHistoryData [id=" + id
				+ ", fromWarehouse=" + fromWarehouse + ", toWarehouse="
				+ toWarehouse + ", resource=" + resource
				+ ", warehouseInventoryStatus=" + warehouseInventoryStatus
				+ ", remark=" + remark + ", numbercount=" + numbercount
				+ ", updatedate=" + updatedate + ", updatedby=" + updatedby
				+ "]";
	}
    
    
    
}
