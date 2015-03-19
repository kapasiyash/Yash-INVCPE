package com.elitecore.cpe.bl.ws.data.input.response;

import java.io.Serializable;

public class InventoryStatusVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String inventoryNumber;
	private String responseCode;
	private String responseMessage;
	private String inventoryStaus;
	public String getInventoryStaus() {
		return inventoryStaus;
	}



	public void setInventoryStaus(String inventoryStaus) {
		this.inventoryStaus = inventoryStaus;
	}



	public InventoryStatusVO() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public InventoryStatusVO(String responseCode, String responseMessage) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}



	
	public InventoryStatusVO(String inventoryNumber, String responseCode,
			String responseMessage) {
		super();
		this.inventoryNumber = inventoryNumber;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}



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
	/* 
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryStatusVO [ ");
		if(inventoryNumber!=null){
		builder.append("inventoryNumber=").append(inventoryNumber);
		}
		if(responseCode!=null){
		builder.append(", responseCode=").append(responseCode);
		}
		if(responseMessage!=null){
		builder.append(", responseMessage=").append(responseMessage);
		}
		if(inventoryStaus!=null){
			builder.append(", inventoryStaus=").append(inventoryStaus);
			}

		builder.append("]");
		return builder.toString();
	}
	
	
}
