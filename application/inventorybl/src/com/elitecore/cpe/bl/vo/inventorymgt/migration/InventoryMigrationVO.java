package com.elitecore.cpe.bl.vo.inventorymgt.migration;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class InventoryMigrationVO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String batchNumber;
	private String wareHouseName;
	private String resourceName;
	private String resourceType;
	private String resourceSubType;
	private String resourceNumber;
	private String inventoryNumber;
	private String GuaranteeWarrantyMode;
	private String warrantyDate; // dd/mm/yyyy
	private String warrantyType;
	
	private String status;
	private String externalReferenceId;
	private String accepted;
	private String refurnished;
	private String standBy;
	private String New;
	private Date reservedDate;
	
	private Date createDate;
	private String createdByStaffId;
	private String lastModifiedBy;
	private List<AttributeMigrationVO> attributeMigrationVOs;
	
	
	
	public String getGuaranteeWarrantyMode() {
		return GuaranteeWarrantyMode;
	}
	public void setGuaranteeWarrantyMode(String guaranteeWarrantyMode) {
		GuaranteeWarrantyMode = guaranteeWarrantyMode;
	}
	
	
	
	public String getWarrantyDate() {
		return warrantyDate;
	}
	public void setWarrantyDate(String warrantyDate) {
		this.warrantyDate = warrantyDate;
	}
	public String getWarrantyType() {
		return warrantyType;
	}
	public void setWarrantyType(String warrantyType) {
		this.warrantyType = warrantyType;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWareHouseName() {
		return wareHouseName;
	}
	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
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
	public String getResourceSubType() {
		return resourceSubType;
	}
	public void setResourceSubType(String resourceSubType) {
		this.resourceSubType = resourceSubType;
	}
	public String getResourceNumber() {
		return resourceNumber;
	}
	public void setResourceNumber(String resourceNumber) {
		this.resourceNumber = resourceNumber;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreatedByStaffId() {
		return createdByStaffId;
	}
	public void setCreatedByStaffId(String createdByStaffId) {
		this.createdByStaffId = createdByStaffId;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getInventoryNumber() {
		return inventoryNumber;
	}
	public void setInventoryNumber(String inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}
	public String getExternalReferenceId() {
		return externalReferenceId;
	}
	public void setExternalReferenceId(String externalReferenceId) {
		this.externalReferenceId = externalReferenceId;
	}
	public String getAccepted() {
		return accepted;
	}
	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}
	public String getRefurnished() {
		return refurnished;
	}
	public void setRefurnished(String refurnished) {
		this.refurnished = refurnished;
	}
	public String getStandBy() {
		return standBy;
	}
	public void setStandBy(String standBy) {
		this.standBy = standBy;
	}
	public String getNew() {
		return New;
	}
	public void setNew(String new1) {
		New = new1;
	}
	public Date getReservedDate() {
		return reservedDate;
	}
	public void setReservedDate(Date reservedDate) {
		this.reservedDate = reservedDate;
	}
	public List<AttributeMigrationVO> getAttributeMigrationVOs() {
		return attributeMigrationVOs;
	}
	public void setAttributeMigrationVOs(
			List<AttributeMigrationVO> attributeMigrationVOs) {
		this.attributeMigrationVOs = attributeMigrationVOs;
	}
	@Override
	public String toString() {
		return "InventoryMigrationVO [batchNumber=" + batchNumber
				+ ", wareHouseName=" + wareHouseName + ", resourceName="
				+ resourceName + ", resourceType=" + resourceType
				+ ", resourceSubType=" + resourceSubType + ", resourceNumber="
				+ resourceNumber + ", inventoryNumber=" + inventoryNumber
				+ ", status=" + status + ", externalReferenceId="
				+ externalReferenceId + ", accepted=" + accepted
				+ ", refurnished=" + refurnished + ", standBy=" + standBy
				+ ", New=" + New + ", reservedDate=" + reservedDate
				+ ", createDate=" + createDate + ", createdByStaffId="
				+ createdByStaffId + ", lastModifiedBy=" + lastModifiedBy
				+ ", attributeMigrationVOs=" + attributeMigrationVOs + "]";
	}
	
	
	
	

}
