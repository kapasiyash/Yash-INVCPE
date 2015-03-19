package com.elitecore.cpe.bl.ws.data.input.response;

import java.io.Serializable;

public class ValidateFinancialPeriodResponseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean result;
	private String responseMessage;
	private String responseCode;
	
	
	public ValidateFinancialPeriodResponseData() {
		// TODO Auto-generated constructor stub
	}
	
	public ValidateFinancialPeriodResponseData(String responseMessage,
			String responseCode) {
		super();
		this.responseMessage = responseMessage;
		this.responseCode = responseCode;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	@Override
	public String toString() {
		return "ValidateFinancialPeriodResponseData [result=" + result
				+ ", responseMessage=" + responseMessage + ", responseCode="
				+ responseCode + "]";
	}
	
	
	

}
