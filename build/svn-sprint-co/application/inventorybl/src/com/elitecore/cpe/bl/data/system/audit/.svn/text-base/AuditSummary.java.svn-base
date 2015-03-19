package com.elitecore.cpe.bl.data.system.audit;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuditSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String auditTypeId;
	private Timestamp auditDate;
	private String actionAlias;
	private String userId;
	private String remarks;
	private String reason;
	private String ipAddress;
	private List<AuditEntryData> auditEntryWrapper;
//	private Map<String,AuditEntryData> changeDataMap;
	private Map<String,Object> tagMap;
	
	public AuditSummary() {
		super();
//		this.auditEntryWrapper = new ArrayList<AuditEntryData>();
//		this.changeDataMap = new HashMap<String, AuditEntryData>();
	}

	
	public AuditSummary(String auditTypeId, Timestamp auditDate, String actionAlias,
			String userId, String remarks, String reason) {
		this();
		this.auditTypeId = auditTypeId;
		this.auditDate = auditDate;
		this.actionAlias = actionAlias;
		this.userId = userId;
		this.remarks = remarks;
		this.reason = reason;
	}

	
	/*public void appendChanged(String tableName, String fieldName, Long oldValue, Long newValue) {
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

	public void appendNew(String tableName, String fieldName, Long newValue) {
		if(newValue!=null){
			AuditEntryData auditInfoData = new AuditEntryData(tableName, fieldName, null, newValue!=null?newValue.toString():null);
			addAuditEntryData(auditInfoData);
		}
	}

	public void appendNew(String tableName, String fieldName, String newValue) {
		if(newValue!=null){
			AuditEntryData auditInfoData = new AuditEntryData(tableName, fieldName, null, newValue);
			addAuditEntryData(auditInfoData);
		}
	}

	public void appendNew(String tableName, String fieldName, Double newValue) {
		if(newValue!=null){
			AuditEntryData auditInfoData = new AuditEntryData(tableName, fieldName, null, newValue!=null?newValue.toString():null);
			addAuditEntryData(auditInfoData);
		}
	}

	
	public void appendNew(String tableName, String fieldName, Integer newValue) {
		if(newValue!=null){
			AuditEntryData auditInfoData = new AuditEntryData(tableName, fieldName, null, newValue!=null?newValue.toString():null);
			addAuditEntryData(auditInfoData);
		}
	}
	
	public void appendNew(String tableName, String fieldName, Timestamp newValue) {
		if(newValue!=null){
			AuditEntryData auditInfoData = new AuditEntryData(tableName, fieldName, null, newValue!=null?newValue.toString():null);
			addAuditEntryData(auditInfoData);
		}
	}
	
	public void addAuditEntryData(AuditEntryData auditEntryData) {
		this.auditEntryWrapper.add(auditEntryData);
	}*/
	
	
	public String getAuditTypeId() {
		return auditTypeId;
	}


	public void setAuditTypeId(String auditTypeId) {
		this.auditTypeId = auditTypeId;
	}


	public Timestamp getAuditDate() {
		return auditDate;
	}


	public void setAuditDate(Timestamp auditDate) {
		this.auditDate = auditDate;
	}


	public String getActionAlias() {
		return actionAlias;
	}


	public void setActionAlias(String actionAlias) {
		this.actionAlias = actionAlias;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public List<AuditEntryData> getAuditEntryDatas() {
		return auditEntryWrapper;
	}


	public void setAuditEntryDatas(List<AuditEntryData> auditEntryWrapper) {
		this.auditEntryWrapper = auditEntryWrapper;
	}
	
	/*
	public void addDataEntry(String key,AuditEntryData entryData){
		this.changeDataMap.put(key, entryData);
	}

	public AuditEntryData getDataEntry(String key){
		return this.changeDataMap.get(key);
	}
	
	public void mergeDataEntry(){
		if(!this.changeDataMap.isEmpty()){
			for(Map.Entry<String, AuditEntryData> item : this.changeDataMap.entrySet()){
				AuditEntryData dataEntry = item.getValue();
				appendChanged(dataEntry.getTableName(), dataEntry.getFieldName(), dataEntry.getOldValue(), dataEntry.getNewValue());
			}
		}
	}*/

	public Map<String, Object> getTagMap() {
		return tagMap;
	}


	public void setTagMap(Map<String, Object> tagMap) {
		this.tagMap = tagMap;
	}


	@Override
	public String toString() {
		return "AuditSummary [auditTypeId=" + auditTypeId + ", auditDate="
				+ auditDate + ", actionAlias=" + actionAlias + ", userId="
				+ userId + ", remarks=" + remarks + ", reason=" + reason
				+ ", ipAddress=" + ipAddress + ", auditEntryWrapper="
				+ auditEntryWrapper 
				+ ", tagMap=" + tagMap + "]";
	}
	
	
}
