/**
 * 
 */
package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;
import java.util.List;

/**
 * @author Joel.Macwan
 *
 */
public class SearchInventoryVO implements Serializable{
	private String wareHouseName; 
	private Long wareHouseId;
	private String batchId; 
	private String inventoryId; 
	private Long inventoryStatusId;
	private String inventoryStatus;

	private String externalBatchNumber;
	private Long resourceTypeId;
	private Long resourceSubtypeId;

	private String resourceNumber;
	private String resourceName;
	
	private String transferInventoryStatus;
	private Long attributeId;
	private String attributeValue;

	// Used in Transfer Status
	private List<Integer> inventoryStatuses;
	
	
	
	
	
	public String getResourceNumber() {
		return resourceNumber;
	}
	public void setResourceNumber(String resourceNumber) {
		this.resourceNumber = resourceNumber;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getExternalBatchNumber() {
		return externalBatchNumber;
	}
	public void setExternalBatchNumber(String externalBatchNumber) {
		this.externalBatchNumber = externalBatchNumber;
	}
	public List<Integer> getInventoryStatuses() {
		return inventoryStatuses;
	}
	public void setInventoryStatuses(List<Integer> inventoryStatuses) {
		this.inventoryStatuses = inventoryStatuses;
	}
	/**
	 * @return the wareHouseName
	 */
	public String getWareHouseName() {
		return wareHouseName;
	}
	/**
	 * @param wareHouseName the wareHouseName to set
	 */
	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}
	/**
	 * @return the wareHouseId
	 */
	public Long getWareHouseId() {
		return wareHouseId;
	}
	/**
	 * @param wareHouseId the wareHouseId to set
	 */
	public void setWareHouseId(Long wareHouseId) {
		this.wareHouseId = wareHouseId;
	}
	/**
	 * @return the batchId
	 */
	public String getBatchId() {
		return batchId;
	}
	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	/**
	 * @return the inventoryId
	 */
	public String getInventoryId() {
		return inventoryId;
	}
	/**
	 * @param inventoryId the inventoryId to set
	 */
	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}
	/**
	 * @return the inventoryStatusId
	 */
	public Long getInventoryStatusId() {
		return inventoryStatusId;
	}
	/**
	 * @param inventoryStatusId the inventoryStatusId to set
	 */
	public void setInventoryStatusId(Long inventoryStatusId) {
		this.inventoryStatusId = inventoryStatusId;
	}
	/**
	 * @return the inventoryStatus
	 */
	public String getInventoryStatus() {
		return inventoryStatus;
	}
	/**
	 * @param inventoryStatus the inventoryStatus to set
	 */
	public void setInventoryStatus(String inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}
	
	/**
	 * @return the resourceTypeId
	 */
	public Long getResourceTypeId() {
		return resourceTypeId;
	}
	/**
	 * @param resourceTypeId the resourceTypeId to set
	 */
	public void setResourceTypeId(Long resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}
	/**
	 * @return the resourceSubtypeId
	 */
	public Long getResourceSubtypeId() {
		return resourceSubtypeId;
	}
	/**
	 * @param resourceSubtypeId the resourceSubtypeId to set
	 */
	public void setResourceSubtypeId(Long resourceSubtypeId) {
		this.resourceSubtypeId = resourceSubtypeId;
	}
	/**
	 * 
	 */
	public String getTransferInventoryStatus() {
		return transferInventoryStatus;
	}
	/**
	 * 
	 */
	public void setTransferInventoryStatus(String transferInventoryStatus) {
		this.transferInventoryStatus = transferInventoryStatus;
	}
	
	
	public Long getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SearchInventoryVO [wareHouseName=");
		builder.append(wareHouseName);
		builder.append(", wareHouseId=");
		builder.append(wareHouseId);
		builder.append(", batchId=");
		builder.append(batchId);
		builder.append(", inventoryId=");
		builder.append(inventoryId);
		builder.append(", inventoryStatusId=");
		builder.append(inventoryStatusId);
		builder.append(", inventoryStatus=");
		builder.append(inventoryStatus);
		builder.append(", resourceTypeId=");
		builder.append(resourceTypeId);
		builder.append(", resourceSubtypeId=");
		builder.append(resourceSubtypeId);
		builder.append(", transferInventoryStatus=");
		builder.append(transferInventoryStatus);
		builder.append(", attributeId=");
		builder.append(attributeId);
		builder.append(", attributeValue=");
		builder.append(attributeValue);
		builder.append("]");
		return builder.toString();
	}
	

}
