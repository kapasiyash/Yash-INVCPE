package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_CheckCPEResourceRequest", propOrder = {
	    "warehouseName",
	    "resourceId",
	    "warehouseCode"
	})
public class ResourceAvailibilityRequestData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	
	private String warehouseName;
	
	
	@XmlElement(required=true,name="rfsCode")
	private String resourceId;  // which will uniquely generated, in our system there is resourceNumber
	private String warehouseCode;
	
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
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
		return "ResourceAvailibilityRequestData [warehouseName="
				+ warehouseName + ", resourceId=" + resourceId
				+ ", warehouseCode=" + warehouseCode + "]";
	}
	
	
	
}
