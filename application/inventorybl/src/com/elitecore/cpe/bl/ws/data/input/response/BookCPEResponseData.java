package com.elitecore.cpe.bl.ws.data.input.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_BookCPEResourceResponse", propOrder = {
    "responseCode",
    "responseMessage",
    "resourceAvailibilityVOList"
})
public class BookCPEResponseData implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private String responseCode;
	private String responseMessage;
	
	 @XmlElement(name = "NICE_BookResourceAvailabilityList")
	private ResourceAvailibilityVOList resourceAvailibilityVOList;
	
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
	public ResourceAvailibilityVOList getResourceAvailibilityVOList() {
		return resourceAvailibilityVOList;
	}
	public void setResourceAvailibilityVOList(
			ResourceAvailibilityVOList resourceAvailibilityVOList) {
		this.resourceAvailibilityVOList = resourceAvailibilityVOList;
	}
	@Override
	public String toString() {
		return "BookCPEResponseData [responseCode=" + responseCode
				+ ", responseMessage=" + responseMessage
				+ ", resourceAvailibilityVOList=" + resourceAvailibilityVOList
				+ "]";
	}
	
	
}
