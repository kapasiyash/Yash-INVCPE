package com.elitecore.cpe.bl.ws.data.input.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_BookInventory", propOrder = {
    "inventoryNo",
    "serialNumber"
})
public class ReserveAllocateRequestVO implements Serializable{

	private static final long serialVersionUID = 1L;

	@XmlElement(name="inventoryNumber")
	private String inventoryNo;
	private String serialNumber;

	public String getInventoryNo() {
		return inventoryNo;
	}

	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReserveAllocateRequestVO [");
		if(inventoryNo!=null)
		builder.append("inventoryNo=").append(inventoryNo);
		builder.append("]");
		return builder.toString();
	}
	
	
}
