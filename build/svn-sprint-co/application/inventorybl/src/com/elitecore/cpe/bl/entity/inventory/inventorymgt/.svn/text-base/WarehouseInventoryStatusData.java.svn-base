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
@Table(name="TBLMWAREHOUSEINVENTORYSTATUS"
)
public class WarehouseInventoryStatusData implements Serializable{

	private Long warehouseInventoryStatusId;
	private WarehouseData warehouseData;
	private ItemData resource;
	private Long uploaded =0L;
	private Long refresh = 0L;
	private Long available = 0L;
	private Long reserved = 0L;
	private Long inUse = 0L;
	private Long delivered= 0L;
	private Long faulty =0L;
	private Long released =0L;
	private Long repaired = 0L;
	private Long scrapped = 0L;
	private Long voided = 0L;
	
	private Timestamp updatedate;
    private String updatedby;
    
    @SequenceGenerator(name="generator", sequenceName="TBLMWHINVENTORYSTATUS_SEQ", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name="WAREHOUSEINVENTORYSTATUSID")
	public Long getWarehouseInventoryStatusId() {
		return warehouseInventoryStatusId;
	}
	public void setWarehouseInventoryStatusId(Long warehouseInventoryStatusId) {
		this.warehouseInventoryStatusId = warehouseInventoryStatusId;
	}
	
	 @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="WAREHOUSEID")
	public WarehouseData getWarehouseData() {
		return warehouseData;
	}
	public void setWarehouseData(WarehouseData warehouseData) {
		this.warehouseData = warehouseData;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RESOURCEID")
	public ItemData getResource() {
		return resource;
	}
	public void setResource(ItemData resource) {
		this.resource = resource;
	}
	
	@Column(name="UPLOADED")
	public Long getUploaded() {
		return uploaded;
	}
	public void setUploaded(Long uploaded) {
		this.uploaded = uploaded;
	}
	
	@Column(name="NEW")
	public Long getRefresh() {
		return refresh;
	}
	public void setRefresh(Long refresh) {
		this.refresh = refresh;
	}
	
	@Column(name="AVAILABLE")
	public Long getAvailable() {
		return available;
	}
	public void setAvailable(Long available) {
		this.available = available;
	}
	
	@Column(name="RESERVED")
	public Long getReserved() {
		return reserved;
	}
	public void setReserved(Long reserved) {
		this.reserved = reserved;
	}
	
	@Column(name="IN_USE")
	public Long getInUse() {
		return inUse;
	}
	public void setInUse(Long inUse) {
		this.inUse = inUse;
	}
	
	@Column(name="DELIVERED")
	public Long getDelivered() {
		return delivered;
	}
	public void setDelivered(Long delivered) {
		this.delivered = delivered;
	}
	
	@Column(name="FAULTY")
	public Long getFaulty() {
		return faulty;
	}
	public void setFaulty(Long faulty) {
		this.faulty = faulty;
	}
	
	@Column(name="RELEASED")
	public Long getReleased() {
		return released;
	}
	public void setReleased(Long released) {
		this.released = released;
	}
	
	@Column(name="REPAIRED")
	public Long getRepaired() {
		return repaired;
	}
	public void setRepaired(Long repaired) {
		this.repaired = repaired;
	}
	
	@Column(name="SCRAPPED")
	public Long getScrapped() {
		return scrapped;
	}
	public void setScrapped(Long scrapped) {
		this.scrapped = scrapped;
	}
	
	@Column(name="VOID")
	public Long getVoided() {
		return voided;
	}
	public void setVoided(Long voided) {
		this.voided = voided;
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
		return "WarehouseInventoryStatusData [warehouseInventoryStatusId="
				+ warehouseInventoryStatusId + ", warehouseData="
				+ warehouseData + ", resource=" + resource + ", uploaded="
				+ uploaded + ", refresh=" + refresh + ", available="
				+ available + ", reserved=" + reserved + ", inUse=" + inUse
				+ ", delivered=" + delivered + ", faulty=" + faulty
				+ ", released=" + released + ", repaired=" + repaired
				+ ", scrapped=" + scrapped + ", voided=" + voided
				+ ", updatedate=" + updatedate + ", updatedby=" + updatedby
				+ "]";
	}
	    
    
    
}
