package com.elitecore.cpe.bl.ws.data.input.response;

import java.io.Serializable;

public class InventoryResponseData implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private Long responseCode;
	private String responseMessage;
	
//	private Object responseObj;

	public Long getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Long responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	

	@Override
	public String toString() {
		return "InventoryResponseData [responseCode=" + responseCode
				+ ", responseMessage=" + responseMessage + "]";
	}
	
	
	
}
