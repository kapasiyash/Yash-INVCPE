package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;

public class SearchWarehouseInventoryStatusVO implements Serializable{

	private Long currentWarehouseId;
	private Long newWarehouseId;
	private Long resourceId;
	private Long totalQty;
	
	public Long getCurrentWarehouseId() {
		return currentWarehouseId;
	}
	public void setCurrentWarehouseId(Long currentWarehouseId) {
		this.currentWarehouseId = currentWarehouseId;
	}
	public Long getNewWarehouseId() {
		return newWarehouseId;
	}
	public void setNewWarehouseId(Long newWarehouseId) {
		this.newWarehouseId = newWarehouseId;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public Long getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(Long totalQty) {
		this.totalQty = totalQty;
	}
	
	
	
}
