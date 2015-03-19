package com.elitecore.cpe.bl.ws.data.input.vo;

import java.io.Serializable;

public class InventoryAttributeVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String attributeName;
	private String attributeValue;
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	/* 
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryAttributeVO [" );
		if(attributeName!=null){
		builder.append(	"attributeName=").append(attributeName);
		}
		if(attributeValue!=null){
		builder.append(", attributeValue=").append(attributeValue);
		}
		builder.append("]");
		return builder.toString();
	}
	

	 
	
	
	
}
