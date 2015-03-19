package com.elitecore.cpe.bl.vo.master;

import java.io.Serializable;
import java.sql.Timestamp;

public class AttributeVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Long attributeId;
	
	private String name; 
	private String usedBy;
	
	private Timestamp createdate;
    private Timestamp updatedate;
    private String createdby;
    private String updatedby;
    
    private String systemgenerated;
    private char mandatory,unique;
    private String dataType;
    private String dataValue;
    private String reason;
    
	public Long getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsedBy() {
		return usedBy;
	}

	public void setUsedBy(String usedBy) {
		this.usedBy = usedBy;
	}

	public Timestamp getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	public Timestamp getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public String getSystemgenerated() {
		return systemgenerated;
	}

	public void setSystemgenerated(String systemgenerated) {
		this.systemgenerated = systemgenerated;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public char getMandatory() {
		return mandatory;
	}

	public void setMandatory(char mandatory) {
		this.mandatory = mandatory;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public char getUnique() {
		return unique;
	}

	public void setUnique(char unique) {
		this.unique = unique;
	}

	@Override
	public String toString() {
		return "AttributeVO [attributeId=" + attributeId + ", name=" + name
				+ ", usedBy=" + usedBy + ", createdate=" + createdate
				+ ", updatedate=" + updatedate + ", createdby=" + createdby
				+ ", updatedby=" + updatedby + ", systemgenerated="
				+ systemgenerated + ", mandatory=" + mandatory + ", unique="
				+ unique + ", dataType=" + dataType + ", dataValue="
				+ dataValue + ", reason=" + reason + "]";
	}

    
    
}
