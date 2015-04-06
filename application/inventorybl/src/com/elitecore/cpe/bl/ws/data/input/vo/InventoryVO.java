package com.elitecore.cpe.bl.ws.data.input.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.elitecore.cpe.bl.ws.data.input.request.AttributeList;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_Inventory", propOrder = {
    "inventoryNumber",
    "inventoryStaus",
    "batchNumber",
    "attributeList"
})
public class InventoryVO implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String inventoryNumber;
	@XmlElement(name="inventoryStatus")
	private String inventoryStaus;
	private String batchNumber;
	@XmlElement(name = "NICE_AttributeList")
	private AttributeList attributeList;

	public String getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(String inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public String getInventoryStaus() {
		return inventoryStaus;
	}

	public void setInventoryStaus(String inventoryStaus) {
		this.inventoryStaus = inventoryStaus;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public AttributeList getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(AttributeList attributeList) {
		this.attributeList = attributeList;
	}

	@Override
	public String toString() {
		return "InventoryVO [inventoryNumber=" + inventoryNumber
				+ ", inventoryStaus=" + inventoryStaus + ", batchNumber="
				+ batchNumber + ", attributeList=" + attributeList + "]";
	}

	

	

}
