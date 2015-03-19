package com.elitecore.cpe.bl.vo.master;

import java.io.Serializable;

public class ResourceSubTypeVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long resourceSubTypeId;
	
	
	private Long resourceTypeId;
	private String resourceSubTypeName;
	private String description;
	
	
	
	
	public Long getResourceTypeId() {
		return resourceTypeId;
	}


	public void setResourceTypeId(Long resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}


	public Long getResourceSubTypeId() {
		return resourceSubTypeId;
	}
	
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setResourceSubTypeId(Long resourceSubTypeId) {
		this.resourceSubTypeId = resourceSubTypeId;
	}
	public String getResourceSubTypeName() {
		return resourceSubTypeName;
	}
	public void setResourceSubTypeName(String resourceSubTypeName) {
		this.resourceSubTypeName = resourceSubTypeName;
	}
	@Override
	public String toString() {
		return "ResourceSubTypeVO [resourceSubTypeId=" + resourceSubTypeId
				+ ", resourceSubTypeName=" + resourceSubTypeName + "]";
	}
	
	
		

}
