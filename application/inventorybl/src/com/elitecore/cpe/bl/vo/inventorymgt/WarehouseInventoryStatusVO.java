package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;

public class WarehouseInventoryStatusVO implements Serializable{

	private Long warehouseId;
	private Long resourceId;
	private Long uploaded = 0L;
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
	
	private String updatedby;

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getUploaded() {
		return uploaded;
	}

	public void setUploaded(Long uploaded) {
		this.uploaded = uploaded;
	}

	public Long getRefresh() {
		return refresh;
	}

	public void setRefresh(Long refresh) {
		this.refresh = refresh;
	}

	public Long getAvailable() {
		return available;
	}

	public void setAvailable(Long available) {
		this.available = available;
	}

	public Long getReserved() {
		return reserved;
	}

	public void setReserved(Long reserved) {
		this.reserved = reserved;
	}

	public Long getInUse() {
		return inUse;
	}

	public void setInUse(Long inUse) {
		this.inUse = inUse;
	}

	public Long getDelivered() {
		return delivered;
	}

	public void setDelivered(Long delivered) {
		this.delivered = delivered;
	}

	public Long getFaulty() {
		return faulty;
	}

	public void setFaulty(Long faulty) {
		this.faulty = faulty;
	}

	public Long getReleased() {
		return released;
	}

	public void setReleased(Long released) {
		this.released = released;
	}

	public Long getRepaired() {
		return repaired;
	}

	public void setRepaired(Long repaired) {
		this.repaired = repaired;
	}

	public Long getScrapped() {
		return scrapped;
	}

	public void setScrapped(Long scrapped) {
		this.scrapped = scrapped;
	}

	public Long getVoided() {
		return voided;
	}

	public void setVoided(Long voided) {
		this.voided = voided;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	@Override
	public String toString() {
		return "WarehouseInventoryStatusVO [warehouseId=" + warehouseId
				+ ", resourceId=" + resourceId + ", uploaded=" + uploaded
				+ ", refresh=" + refresh + ", available=" + available
				+ ", reserved=" + reserved + ", inUse=" + inUse
				+ ", delivered=" + delivered + ", faulty=" + faulty
				+ ", released=" + released + ", repaired=" + repaired
				+ ", scrapped=" + scrapped + ", voided=" + voided
				+ ", updatedby=" + updatedby + "]";
	}
	
	
	
}
