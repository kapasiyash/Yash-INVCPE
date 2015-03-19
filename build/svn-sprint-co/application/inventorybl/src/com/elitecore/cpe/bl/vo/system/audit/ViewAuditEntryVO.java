package com.elitecore.cpe.bl.vo.system.audit;

import java.io.Serializable;

public class ViewAuditEntryVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long auditEntryId;
	private Long systemAuditId;
	private String tableName;
	private String fieldName;
	private String oldValue;
	private String newValue;
	
	
	public Long getAuditEntryId() {
		return auditEntryId;
	}
	public void setAuditEntryId(Long auditEntryId) {
		this.auditEntryId = auditEntryId;
	}
	public Long getSystemAuditId() {
		return systemAuditId;
	}
	public void setSystemAuditId(Long systemAuditId) {
		this.systemAuditId = systemAuditId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	

}
