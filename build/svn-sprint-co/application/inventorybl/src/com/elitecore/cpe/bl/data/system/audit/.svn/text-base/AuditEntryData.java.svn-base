package com.elitecore.cpe.bl.data.system.audit;

import java.io.Serializable;

public class AuditEntryData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tableName;
	private String fieldName;
	private String oldValue;
	private String newValue;
	
	public AuditEntryData() {
		super();
	}
	
	public AuditEntryData(String tableName, String fieldName, String oldValue) {
		this();
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.oldValue = oldValue;
		
	}
	
	public AuditEntryData(String tableName, String fieldName, String oldValue,
			String newValue) {
		this();
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.oldValue = oldValue;
		this.newValue = newValue;
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
	
	@Override
	public String toString() {
		return "AuditEntryWrapperData [tableName=" + tableName + ", fieldName="
				+ fieldName + ", oldValue=" + oldValue + ", newValue="
				+ newValue + "]";
	}
	
}
