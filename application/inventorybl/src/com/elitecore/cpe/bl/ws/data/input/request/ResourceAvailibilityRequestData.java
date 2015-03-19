package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class ResourceAvailibilityRequestData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String warehouseName;
	private String resourceId;  // which will uniquely generated, in our system there is resourceNumber
	private String warehouseCode;
	
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
	@XmlElement(required=true)
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	
	
	
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	/* 
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResourceAvailibilityRequestData [" );
		if(warehouseName!=null){
		builder.append(	"warehouseName=").append(warehouseName);
		}
		if(resourceId!=null){
		builder.append(", resourceId=").append(resourceId);
		}
		
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
