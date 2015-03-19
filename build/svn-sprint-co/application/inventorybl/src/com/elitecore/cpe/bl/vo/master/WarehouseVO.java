package com.elitecore.cpe.bl.vo.master;

import java.io.Serializable;
import java.sql.Timestamp;

public class WarehouseVO implements Serializable{

	private Long warehouseId; 
	private Long warehouseTypeId; 
	private String name; 
	private String warehouseCode;
	private String alias; 
	private String description;
	
    private String createdby;
    private String updatedby;
    
    private String systemgenerated;
    private String editable;
    
    private String location;
    private Long parentWarehouseId;
    private String parentWarehouseName;
    private String reason;
    private Timestamp createDate;
	private Timestamp updatedDate;
    
    private WarehouseTypeVO warehouseType;
    
    
  
    
    private String owner;
    private String contactNo;
    private String emailId;
    
    
    
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getParentWarehouseId() {
		return parentWarehouseId;
	}

	public void setParentWarehouseId(Long parentWarehouseId) {
		this.parentWarehouseId = parentWarehouseId;
	}

	
	public String getParentWarehouseName() {
		return parentWarehouseName;
	}

	public void setParentWarehouseName(String parentWarehouseName) {
		this.parentWarehouseName = parentWarehouseName;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public WarehouseTypeVO getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(WarehouseTypeVO warehouseType) {
		this.warehouseType = warehouseType;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	

	/**
	 * 
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * 
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * 
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * 
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * 
	 */
	public Long getWarehouseTypeId() {
		return warehouseTypeId;
	}

	/**
	 * 
	 */
	public void setWarehouseTypeId(Long warehouseTypeId) {
		this.warehouseTypeId = warehouseTypeId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WarehouseVO [warehouseId=");
		builder.append(warehouseId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", alias=");
		builder.append(alias);
		builder.append(", description=");
		builder.append(description);
		builder.append(", createdby=");
		builder.append(createdby);
		builder.append(", updatedby=");
		builder.append(updatedby);
		builder.append(", systemgenerated=");
		builder.append(systemgenerated);
		builder.append(", editable=");
		builder.append(editable);
		builder.append(", location=");
		builder.append(location);
		builder.append(", parentWarehouseId=");
		builder.append(parentWarehouseId);
		builder.append(", parentWarehouseName=");
		builder.append(parentWarehouseName);
		builder.append(", reason=");
		builder.append(reason);
		builder.append(", warehouseType=");
		builder.append(warehouseType);
		builder.append(", owner=");
		builder.append(owner);
		builder.append(", contactNo=");
		builder.append(contactNo);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", emailId=");
		builder.append(emailId);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append(", warehouseTypeId=");
		builder.append(warehouseTypeId);
		
		builder.append("]");
		return builder.toString();
	}
	
    
    
}
