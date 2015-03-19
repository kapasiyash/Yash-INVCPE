package com.elitecore.cpe.bl.vo.system.audit;

import java.io.Serializable;
import java.sql.Timestamp;

public class SearchAuditVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long systemAuditId;
	private String reason;
	private long userId;
	private String auditType;
	private String ipAddress;
	private Timestamp auditDate;
	private String auditAction;
	private String userName;
	
	private Timestamp createdDate;
	private Timestamp lastModifyDate;
	
	private String createdBy;
	private String lastModifyBy;
	
	
	public Timestamp getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Timestamp auditDate) {
		this.auditDate = auditDate;
	}
	public String getAuditAction() {
		return auditAction;
	}
	public void setAuditAction(String auditAction) {
		this.auditAction = auditAction;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getSystemAuditId() {
		return systemAuditId;
	}
	public void setSystemAuditId(long systemAuditId) {
		this.systemAuditId = systemAuditId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Timestamp lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifyBy() {
		return lastModifyBy;
	}
	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}
	/* 
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SearchAuditVO [systemAuditId=");
		builder.append(systemAuditId);
		builder.append(", reason=");
		builder.append(reason);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", auditType=");
		builder.append(auditType);
		builder.append(", ipAddress=");
		builder.append(ipAddress);
		builder.append(", auditDate=");
		builder.append(auditDate);
		builder.append(", auditAction=");
		builder.append(auditAction);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", lastModifyDate=");
		builder.append(lastModifyDate);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifyBy=");
		builder.append(lastModifyBy);
		builder.append("]");
		return builder.toString();
	}

	
	
}
