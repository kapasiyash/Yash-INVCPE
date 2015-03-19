package com.elitecore.cpe.bl.data.system.audit;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AuditSummaryDetail implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private List<AuditEntryData> auditEntryWrapper;
	
	
	public AuditSummaryDetail(){
		auditEntryWrapper = new ArrayList<AuditEntryData>();
	}
	
	public void appendChanged(String tableName, String fieldName, Long oldValue, Long newValue) {
		if((oldValue != null && newValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null) || (oldValue != null && newValue == null)){
			AuditEntryData auditInfoData = new AuditEntryData(tableName, fieldName, oldValue!=null?oldValue.toString():null, newValue!=null?newValue.toString():null);
			addAuditEntryData(auditInfoData);
		}
	}

	public void appendChanged(String tableName, String fieldName, String oldValue, String newValue) {
		if((oldValue != null && newValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null) || (oldValue != null && newValue == null)){
			AuditEntryData auditInfoData = new AuditEntryData(tableName, fieldName, oldValue, newValue);
			addAuditEntryData(auditInfoData);
		}
	}

	public void appendChanged(String tableName, String fieldName, Double oldValue, Double newValue) {
		if((oldValue != null && newValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null) || (oldValue != null && newValue == null)){
			AuditEntryData auditInfoData = new AuditEntryData(tableName, fieldName, oldValue!=null?oldValue.toString():null, newValue!=null?newValue.toString():null);
			addAuditEntryData(auditInfoData);
		}
	}

	public void appendChanged(String tableName, String fieldName, Integer oldValue, Integer newValue) {
		if((oldValue != null && newValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null) || (oldValue != null && newValue == null)){
			AuditEntryData auditInfoData = new AuditEntryData(tableName, fieldName, oldValue!=null?oldValue.toString():null, newValue!=null?newValue.toString():null);
			addAuditEntryData(auditInfoData);
		}
	}
	
	public void appendChanged(String tableName, String fieldName, Timestamp oldValue, Timestamp newValue) {
		if((oldValue != null && newValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null) || (oldValue != null && newValue == null)){
			AuditEntryData auditInfoData = new AuditEntryData(tableName, fieldName, oldValue!=null?oldValue.toString():null, newValue!=null?newValue.toString():null);
			addAuditEntryData(auditInfoData);
		}
	}
	
	public void addAuditEntryData(AuditEntryData auditEntryData) {
		this.auditEntryWrapper.add(auditEntryData);
	}

	public List<AuditEntryData> getAuditEntryWrapper() {
		return auditEntryWrapper;
	}

	public void setAuditEntryWrapper(List<AuditEntryData> auditEntryWrapper) {
		this.auditEntryWrapper = auditEntryWrapper;
	}
	
	

}
