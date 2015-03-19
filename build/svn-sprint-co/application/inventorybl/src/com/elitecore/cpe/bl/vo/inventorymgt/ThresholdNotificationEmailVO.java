package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;

public class ThresholdNotificationEmailVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long wareHouseId;
	private String wareHouseName;
	private Long thresholdValue;
	private String resourceName;
	private Long available;
	private boolean isThreshold;
	private String emailId;
	
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public boolean isThreshold() {
		return isThreshold;
	}
	public void setThreshold(boolean isThreshold) {
		this.isThreshold = isThreshold;
	}
	public Long getAvailable() {
		return available;
	}
	public void setAvailable(Long available) {
		this.available = available;
	}
	public Long getWareHouseId() {
		return wareHouseId;
	}
	public void setWareHouseId(Long wareHouseId) {
		this.wareHouseId = wareHouseId;
	}
	public String getWareHouseName() {
		return wareHouseName;
	}
	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}
	public Long getThresholdValue() {
		return thresholdValue;
	}
	public void setThresholdValue(Long thresholdValue) {
		this.thresholdValue = thresholdValue;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	@Override
	public String toString() {
		return "ThresholdNotificationEmailVO [wareHouseId=" + wareHouseId
				+ ", wareHouseName=" + wareHouseName + ", thresholdValue="
				+ thresholdValue + ", resourceName=" + resourceName + "]";
	}
	
	
	

}
