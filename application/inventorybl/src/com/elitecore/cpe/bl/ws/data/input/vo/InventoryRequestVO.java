package com.elitecore.cpe.bl.ws.data.input.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class InventoryRequestVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String serialNumber;
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

	/* 
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryRequestVO [" );
		if(inventoryNo!=null){
		builder.append("inventoryNo=").append(inventoryNo);
		}
		if(oldStatus!=null){
		builder.append(", oldStatus=").append(oldStatus);
		}
		if(newStatus!=null){
		builder.append(", newStatus=").append(newStatus);
		}
		if(remarks!=null){
		builder.append(", remarks=").append(remarks);
		}
		builder.append("]");
		return builder.toString();
	}

	
	
}
