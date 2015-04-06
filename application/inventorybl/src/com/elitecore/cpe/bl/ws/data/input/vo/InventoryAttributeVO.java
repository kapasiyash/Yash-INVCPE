package com.elitecore.cpe.bl.ws.data.input.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_Attribute", propOrder = {
    "attributeName",
    "attributeValue"
})
public class InventoryAttributeVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="name")
	private String attributeName;
	@XmlElement(name="value")
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
