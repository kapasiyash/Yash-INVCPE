package com.elitecore.cpe.bl.vo.master;

import java.io.Serializable;
import java.sql.Timestamp;

public class WarehouseTypeVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long warehouseTypeId; 
	private String name; 
	private String alias; 
	private String description;
	
    private String createdby;
    private String updatedby;
    
    private String systemgenerated;
    
    private String reason;
    private Timestamp createDate;
	private Timestamp updatedDate;
	private String warehouseTypename; 


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

	public Long getWarehouseTypeId() {
		return warehouseTypeId;
	}

	public void setWarehouseTypeId(Long warehouseTypeId) {
		this.warehouseTypeId = warehouseTypeId;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	public String getWarehouseTypename() {
		return warehouseTypename;
	}

	/**
	 * 
	 */
	public void setWarehouseTypename(String warehouseTypename) {
		this.warehouseTypename = warehouseTypename;
	}

	/* 
	 * 
	 */
	@Override
	public String toString() {
		return "WarehouseTypeVO [warehouseTypeId=" + warehouseTypeId
				+ ", name=" + name + ", alias=" + alias + ", description="
				+ description + ", createdby=" + createdby + ", updatedby="
				+ updatedby + ", systemgenerated=" + systemgenerated
				+ ", reason=" + reason + ", createDate=" + createDate
				+ ", updatedDate=" + updatedDate + ", warehouseTypename="
				+ warehouseTypename + "]";
	}
	
    
    
}
