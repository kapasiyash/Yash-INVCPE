package com.elitecore.cpe.bl.data.notification;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class NotificationData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String alias;
	private List<String> toEmails;
	private List<String> ccEmails;
	private Map<String, String> valueMap;
	
	//Used only for Threshold Notification
	private Long notificationHistoryId;
	
	
	

	public Long getNotificationHistoryId() {
		return notificationHistoryId;
	}
	public void setNotificationHistoryId(Long notificationHistoryId) {
		this.notificationHistoryId = notificationHistoryId;
	}

	//--added start
	private List<String> mobilenumbers;

	public List<String> getMobilenumbers() {
		return mobilenumbers;
	}
	public void setMobilenumbers(List<String> mobilenumbers) {
		this.mobilenumbers = mobilenumbers;
	}
	private String mobileNumber;
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	//--added end
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public List<String> getToEmails() {
		return toEmails;
	}
	public void setToEmails(List<String> toEmails) {
		this.toEmails = toEmails;
	}
	public List<String> getCcEmails() {
		return ccEmails;
	}
	public void setCcEmails(List<String> ccEmails) {
		this.ccEmails = ccEmails;
	}
	public Map<String, String> getValueMap() {
		return valueMap;
	}
	public void setValueMap(Map<String, String> valueMap) {
		this.valueMap = valueMap;
	}
	@Override
	public String toString() {
		return "NotificationData [alias=" + alias + ", toEmails=" + toEmails
				+ ", ccEmails=" + ccEmails + ", valueMap=" + valueMap + ", mobileNumber"+mobileNumber+"]";
	}
	
	
	
	
	
	

}
