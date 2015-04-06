package com.elitecore.cpe.bl.ws.data.input.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_FaultyInventory", propOrder = {
    "inventoryNo"
})
public class CPEInventoryVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="inventoryNumber")
	private String inventoryNo;

	public String getInventoryNo() {
		return inventoryNo;
	}

	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}

	@Override
	public String toString() {
		return "CPEInventoryVO [inventoryNo=" + inventoryNo + "]";
	}
	
	
	

	
	
}
