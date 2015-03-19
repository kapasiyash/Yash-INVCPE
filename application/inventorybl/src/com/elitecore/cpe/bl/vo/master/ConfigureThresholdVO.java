/**
 * 
 */
package com.elitecore.cpe.bl.vo.master;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Joel.Macwan
 *
 */
public class ConfigureThresholdVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long thresholdID;
	private Long warehouseID;
	//private Long resourceID;
	
	private Long resourceTypeID;
	private Long resourceSubTypeID;
	private Long thresholdValue;
	private Timestamp createdate;
	private Timestamp updatedate;
	 private String createdby;
	 private String updatedby;
	 private String systemgenerated;
	 private String resourceTypeName;
	 private String resourceSubTypeName;
	 private String thresholdType;
	 
	 private String email;
	 private String mobile;
	 private Integer quantity;
	 
	 
	 
	 
	 
//	 private String resourceName;
	 
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the thresholdID
	 */
	public Long getThresholdID() {
		return thresholdID;
	}
	/**
	 * @param thresholdID the thresholdID to set
	 */
	public void setThresholdID(Long thresholdID) {
		this.thresholdID = thresholdID;
	}
	/**
	 * @return the warehouseID
	 */
	public Long getWarehouseID() {
		return warehouseID;
	}
	/**
	 * @param warehouseID the warehouseID to set
	 */
	public void setWarehouseID(Long warehouseID) {
		this.warehouseID = warehouseID;
	}
	
	/**
	 * @return the thresholdValue
	 */
	public Long getThresholdValue() {
		return thresholdValue;
	}
	/**
	 * @param thresholdValue the thresholdValue to set
	 */
	public void setThresholdValue(Long thresholdValue) {
		this.thresholdValue = thresholdValue;
	}
	/**
	 * @return the createdate
	 */
	public Timestamp getCreatedate() {
		return createdate;
	}
	/**
	 * @param createdate the createdate to set
	 */
	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}
	/**
	 * @return the updatedate
	 */
	public Timestamp getUpdatedate() {
		return updatedate;
	}
	/**
	 * @param updatedate the updatedate to set
	 */
	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}
	/**
	 * @return the createdby
	 */
	public String getCreatedby() {
		return createdby;
	}
	/**
	 * @param createdby the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	/**
	 * @return the updatedby
	 */
	public String getUpdatedby() {
		return updatedby;
	}
	/**
	 * @param updatedby the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	/**
	 * @return the systemgenerated
	 */
	public String getSystemgenerated() {
		return systemgenerated;
	}
	/**
	 * @param systemgenerated the systemgenerated to set
	 */
	public void setSystemgenerated(String systemgenerated) {
		this.systemgenerated = systemgenerated;
	}
	/**
	 * @return the resourceTypeName
	 */
	public String getResourceTypeName() {
		return resourceTypeName;
	}
	/**
	 * @param resourceTypeName the resourceTypeName to set
	 */
	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}
	/**
	 * @return the resourceName
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * @return the resourceTypeID
	 */
	public Long getResourceTypeID() {
		return resourceTypeID;
	}
	/**
	 * @param resourceTypeID the resourceTypeID to set
	 */
	public void setResourceTypeID(Long resourceTypeID) {
		this.resourceTypeID = resourceTypeID;
	}
	/**
	 * @return the resourceSubTypeID
	 */
	public Long getResourceSubTypeID() {
		return resourceSubTypeID;
	}
	/**
	 * @param resourceSubTypeID the resourceSubTypeID to set
	 */
	public void setResourceSubTypeID(Long resourceSubTypeID) {
		this.resourceSubTypeID = resourceSubTypeID;
	}
	/**
	 * @return the resourceSubTypeName
	 */
	public String getResourceSubTypeName() {
		return resourceSubTypeName;
	}
	/**
	 * @param resourceSubTypeName the resourceSubTypeName to set
	 */
	public void setResourceSubTypeName(String resourceSubTypeName) {
		this.resourceSubTypeName = resourceSubTypeName;
	}
	
	/**
	 * @return the thresholdType
	 */
	public String getThresholdType() {
		return thresholdType;
	}
	/**
	 * @param thresholdType the thresholdType to set
	 */
	public void setThresholdType(String thresholdType) {
		this.thresholdType = thresholdType;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConfigureThresholdVO [thresholdID=" + thresholdID
				+ ", warehouseID=" + warehouseID + ", resourceTypeID="
				+ resourceTypeID + ", resourceSubTypeID=" + resourceSubTypeID
				+ ", thresholdValue=" + thresholdValue + ", createdate="
				+ createdate + ", updatedate=" + updatedate + ", createdby="
				+ createdby + ", updatedby=" + updatedby + ", systemgenerated="
				+ systemgenerated + ", resourceTypeName=" + resourceTypeName
				+ ", resourceSubTypeName=" + resourceSubTypeName
				+ ", thresholdType=" + thresholdType + ", email=" + email
				+ ", mobile=" + mobile + ", quantity=" + quantity + "]";
	}
	

}
