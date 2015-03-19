package com.elitecore.cpe.bl.ws.data.input.response;

import java.io.Serializable;
import java.util.List;

import com.elitecore.cpe.bl.ws.data.input.request.ResourceAvailibilityRequestData;
import com.elitecore.cpe.bl.ws.data.input.vo.ResourceAvailibilityVO;

public class ResourceAvailibilityResponseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String responseCode;
	private String responseMessage;
	private List<ResourceAvailibilityVO> resourceAvailibilityVOs; 
	
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

	public List<ResourceAvailibilityVO> getResourceAvailibilityVOs() {
		return resourceAvailibilityVOs;
	}

	public void setResourceAvailibilityVOs(
			List<ResourceAvailibilityVO> resourceAvailibilityVOs) {
		this.resourceAvailibilityVOs = resourceAvailibilityVOs;
	}

	/* 
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResourceAvailibilityResponseData [" );
		if(responseCode!=null){
		builder.append("responseCode=").append(responseCode);
		}
		if(responseMessage!=null){
		builder.append(", responseMessage=").append(responseMessage);
		}
		if(resourceAvailibilityVOs!=null){
		builder.append(", resourceAvailibilityVOs=").append(resourceAvailibilityVOs);
		}
			
		builder.append("]");
		return builder.toString();
	}
	
}
