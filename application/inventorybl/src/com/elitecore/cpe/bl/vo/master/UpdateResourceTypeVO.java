package com.elitecore.cpe.bl.vo.master;

import java.io.Serializable;

public class UpdateResourceTypeVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long resourceTypeId;
	private String resourceTypeName;
	private String description;
	private String reason;
	
	private ResourceSubTypeVO resourceSubTypeVO;
	
	
	
	/**
	 * @return the resourceTypeId
	 */
	public Long getResourceTypeId() {
		return resourceTypeId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @param resourceTypeId the resourceTypeId to set
	 */
	public void setResourceTypeId(Long resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the resourceTypeName
	 */
	public String getResourceTypeName() {
		return resourceTypeName;
	}
	/**
	 * @param resourceTypeName the resourceTypeName to set
	 */
	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}
	
	
	
	public ResourceSubTypeVO getResourceSubTypeVO() {
		return resourceSubTypeVO;
	}
	public void setResourceSubTypeVO(ResourceSubTypeVO resourceSubTypeVO) {
		this.resourceSubTypeVO = resourceSubTypeVO;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResourceTypeVO [resourceTypeId=" + resourceTypeId
				+ ", resourceTypeName=" + resourceTypeName + "]";
	}
	

}
