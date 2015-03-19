package com.elitecore.cpe.bl.ws.data.input.response;

import java.io.Serializable;

import com.elitecore.cpe.bl.ws.data.input.vo.ResourceAvailibilityVO;

public class BookCPEResponseData implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private String responseCode;
	private String responseMessage;
	private ResourceAvailibilityVO resourceAvailibilityVO;
	
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
	public ResourceAvailibilityVO getResourceAvailibilityVO() {
		return resourceAvailibilityVO;
	}
	public void setResourceAvailibilityVO(
			ResourceAvailibilityVO resourceAvailibilityVO) {
		this.resourceAvailibilityVO = resourceAvailibilityVO;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BookCPEResponseData [");
		if(responseCode!=null){
		builder.append("responseCode=").append(responseCode);
		}
		if(responseMessage!=null){
		builder.append(", responseMessage=").append(responseMessage);
		}
		if(resourceAvailibilityVO!=null){
		builder.append(", resourceAvailibilityVO=").append(resourceAvailibilityVO);
		}
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
