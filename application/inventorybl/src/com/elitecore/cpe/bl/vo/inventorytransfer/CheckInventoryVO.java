package com.elitecore.cpe.bl.vo.inventorytransfer;

import java.io.Serializable;

public class CheckInventoryVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long responseCode;
	private String responseMessage;
	
	private String batchNumber;
	private String inventoryNumber;
	private String inventoryStatus;
	private String warehouseName;
	private String resourceType;
	private String resourceSubtype;
	private String resource;
	
	//For Rejected Inventory
	private String remark;
	
	
	
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getInventoryNumber() {
		return inventoryNumber;
	}
	public void setInventoryNumber(String inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}
	public String getInventoryStatus() {
		return inventoryStatus;
	}
	public void setInventoryStatus(String inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceSubtype() {
		return resourceSubtype;
	}
	public void setResourceSubtype(String resourceSubtype) {
		this.resourceSubtype = resourceSubtype;
	}
	public Long getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(Long responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	
	

}
