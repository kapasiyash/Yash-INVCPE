package com.elitecore.cpe.bl.ws.data.input.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_BookResourceAvailability", propOrder = {
	    "warehouseName",
	    "resourceName",
	    "resourceType",
	    "resourceSubType",
	    "model",
	    "vendor",
	    "resourceId",
	    "availableResourceCount",
	    "warehouseCode",
	    "inventoryList"
	})
public class BookResourceAvailibilityVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String warehouseName;
	private String warehouseCode;
	@XmlElement(name="rfsID")
	private String resourceId;
	@XmlElement(name="productName")
	private String resourceName;
	private String resourceType;
	private String resourceSubType;
	@XmlElement(name="noOfResource")
	private Long availableResourceCount;
	private String model;
	private String vendor;
	
	@XmlElement(name = "NICE_InventoryList")
	private InventoryVOList inventoryList;
	
	
	
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
	public InventoryVOList getInventoryList() {
		return inventoryList;
	}
	public void setInventoryList(InventoryVOList inventoryList) {
		this.inventoryList = inventoryList;
	}
	@Override
	public String toString() {
		return "BookResourceAvailibilityVO [warehouseName=" + warehouseName
				+ ", warehouseCode=" + warehouseCode + ", resourceId="
				+ resourceId + ", resourceName=" + resourceName
				+ ", resourceType=" + resourceType + ", resourceSubType="
				+ resourceSubType + ", availableResourceCount="
				+ availableResourceCount + ", model=" + model + ", vendor="
				+ vendor + ", inventoryList=" + inventoryList + "]";
	}
	
	
	
	
}
