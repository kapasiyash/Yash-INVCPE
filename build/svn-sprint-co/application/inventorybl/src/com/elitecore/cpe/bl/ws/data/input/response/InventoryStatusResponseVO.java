package com.elitecore.cpe.bl.ws.data.input.response;

import java.io.Serializable;

public class InventoryStatusResponseVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String inventoryNumber;
	private String responseCode;
	private String responseMessage;
	private String inventoryStatus;
	public String getInventoryNumber() {
		return inventoryNumber;
	}
	public void setInventoryNumber(String inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getInventoryStatus() {
		return inventoryStatus;
	}
	public void setInventoryStatus(String inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}
	@Override
	public String toString() {
		return "InventoryStatusResponseVO [inventoryNumber=" + inventoryNumber
				+ ", responseCode=" + responseCode + ", responseMessage="
				+ responseMessage + ", inventoryStatus=" + inventoryStatus
				+ "]";
	}

}
