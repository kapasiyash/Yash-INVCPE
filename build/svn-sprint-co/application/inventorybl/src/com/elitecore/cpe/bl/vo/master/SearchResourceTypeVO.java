package com.elitecore.cpe.bl.vo.master;

import java.io.Serializable;
import java.sql.Timestamp;

public class SearchResourceTypeVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long resourceTypeId;
	private String name;
	private String alias;
	private String description;
	private Timestamp createDate;
	private Timestamp updatedDate;
	private String createdBy;
	private String updatedBy;
	
	public SearchResourceTypeVO() {
		// TODO Auto-generated constructor stub
	}
	
	
	public SearchResourceTypeVO(Long resourceTypeId, String name, String alias,
			String description,Timestamp timestamp) {
		super();
		this.resourceTypeId = resourceTypeId;
		this.name = name;
		this.alias = alias;
		this.description = description;
		this.createDate = timestamp;
	}
	
	
	
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Long getResourceTypeId() {
		return resourceTypeId;
	}
	public void setResourceTypeId(Long resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
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
	
	
	public Timestamp getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}


	@Override
	public String toString() {
		return "SearchResourceTypeVO [resourceTypeId=" + resourceTypeId
				+ ", name=" + name + ", alias=" + alias + ", description="
				+ description + "]";
	}
	
	
	

}
