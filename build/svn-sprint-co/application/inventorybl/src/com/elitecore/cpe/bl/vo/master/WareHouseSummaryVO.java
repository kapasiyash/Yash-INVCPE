package com.elitecore.cpe.bl.vo.master;

import java.io.Serializable;
import java.util.Map;

public class WareHouseSummaryVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long wareHouseId;
	private String wareHouseName;
	private String wareHouseType;
	private String resourceName;
	private String resourceType;
	private String resourceSubType;
	private String model;
	private String vendor;
	private Map<String, Long> statusCount;
	
	
	
	
	public String getWareHouseType() {
		return wareHouseType;
	}
	public void setWareHouseType(String wareHouseType) {
		this.wareHouseType = wareHouseType;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public Long getWareHouseId() {
		return wareHouseId;
	}
	public void setWareHouseId(Long wareHouseId) {
		this.wareHouseId = wareHouseId;
	}
	public String getWareHouseName() {
		return wareHouseName;
	}
	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceSubType() {
		return resourceSubType;
	}
	public void setResourceSubType(String resourceSubType) {
		this.resourceSubType = resourceSubType;
	}
	public Map<String, Long> getStatusCount() {
		return statusCount;
	}
	public void setStatusCount(Map<String, Long> statusCount) {
		this.statusCount = statusCount;
	}
	@Override
	public String toString() {
		return "WareHouseSummaryVO [wareHouseId=" + wareHouseId
				+ ", wareHouseName=" + wareHouseName + ", resourceName="
				+ resourceName + ", resourceType=" + resourceType
				+ ", resourceSubType=" + resourceSubType + ", statusCount="
				+ statusCount + "]";
	}
	
	
	

}
