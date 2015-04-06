package com.elitecore.cpe.bl.ws.data.input.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_ChangeInventoryStatus", propOrder = {
    "inventoryNo",
    "oldStatus",
    "newStatus",
    "remarks",
    "serialNumber",
    "orderLineItemID"
})
public class InventoryRequestVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String serialNumber;
	
	@XmlElement(name="inventoryNumber")
	private String inventoryNo;
	private Integer oldStatus;
	private Integer newStatus;
	private String remarks;
	//added start
	private String orderLineItemID;
 
	
	public String getOrderLineItemID() {
		return orderLineItemID;
	}

	public void setOrderLineItemID(String orderLineItemID) {
		this.orderLineItemID = orderLineItemID;
	}
	
	//added end
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getInventoryNo() {
		return inventoryNo;
	}

	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}

	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

	public Integer getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(Integer newStatus) {
		this.newStatus = newStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "InventoryRequestVO [serialNumber=" + serialNumber
				+ ", inventoryNo=" + inventoryNo + ", oldStatus=" + oldStatus
				+ ", newStatus=" + newStatus + ", remarks=" + remarks
				+ ", orderLineItemID=" + orderLineItemID + "]";
	}

	/* 
	 * 
	 */
	
	
	
}
