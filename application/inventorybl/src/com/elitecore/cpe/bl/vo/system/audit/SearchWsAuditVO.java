package com.elitecore.cpe.bl.vo.system.audit;

import java.io.Serializable;
import java.util.Date;

public class SearchWsAuditVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long wsAuditId;
	private String eventName;
	private String status;
	private String responseMessage;
	private Date processedDate;
	private String responseCode;
	public Long getWsAuditId() {
		return wsAuditId;
	}
	public void setWsAuditId(Long wsAuditId) {
		this.wsAuditId = wsAuditId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public Date getProcessedDate() {
		return processedDate;
	}
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}
	
	/**
	 * 
	 */
	public String getResponseCode() {
		return responseCode;
	}
	/**
	 * 
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	/* 
	 * 
	 */
	@Override
	public String toString() {
		return "SearchWsAuditVO [wsAuditId=" + wsAuditId + ", eventName="
				+ eventName + ", status=" + status + ", responseMessage="
				+ responseMessage + ", processedDate=" + processedDate
				+ ", responseCode=" + responseCode + "]";
	}
	
	
	
	
}
