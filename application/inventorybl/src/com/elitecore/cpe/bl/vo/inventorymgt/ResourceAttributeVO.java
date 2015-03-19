package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;

public class ResourceAttributeVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long attributeId;
	private String attributeName;
	
	
	public ResourceAttributeVO() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ResourceAttributeVO(Long attributeId, String attributeName) {
		super();
		this.attributeId = attributeId;
		this.attributeName = attributeName;
	}



	public Long getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	@Override
	public String toString() {
		return "ResourceAttributeVO [attributeId=" + attributeId
				+ ", attributeName=" + attributeName + "]";
	}
	
	
	

}
