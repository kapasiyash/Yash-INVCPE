package com.elitecore.cpe.bl.ws.data.input.vo;

import java.io.Serializable;
import java.util.List;

public class ResourceAvailibilityVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String warehouseName;
	private String warehouseCode;
	private String resourceId;
	private String resourceName;
	private String resourceType;
	private String resourceSubType;
	private Long availableResourceCount;
	private String model;
	private String vendor;
	private List<InventoryVO> inventoryList;
	
	
	
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getResourceSubType() {
		return resourceSubType;
	}
	public void setResourceSubType(String resourceSubType) {
		this.resourceSubType = resourceSubType;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public Long getAvailableResourceCount() {
		return availableResourceCount;
	}
	public void setAvailableResourceCount(Long availableResourceCount) {
		this.availableResourceCount = availableResourceCount;
	}
	
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public List<InventoryVO> getInventoryList() {
		return inventoryList;
	}
	public void setInventoryList(List<InventoryVO> inventoryList) {
		this.inventoryList = inventoryList;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResourceAvailibilityVO [" );
		if(warehouseName!=null){
		builder.append(	"warehouseName=").append(warehouseName);
		}
		if(resourceName!=null){
		builder.append(", resourceName=").append(resourceName);
		}
		if(resourceType!=null){
		builder.append(", resourceType=").append(resourceType);
		}
		if(model!=null){
		builder.append(", model=").append(model);
		}
		if(vendor!=null){
		builder.append(", vendor=").append(vendor);
		}
		if(availableResourceCount!=null){
		builder.append(", availableResourceCount=").append(availableResourceCount);
		}
		if(resourceId!=null){
		builder.append(", resourceId=").append(resourceId);
		}
		if(inventoryList!=null){
		builder.append(", inventoryList=").append(inventoryList);
		}
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
