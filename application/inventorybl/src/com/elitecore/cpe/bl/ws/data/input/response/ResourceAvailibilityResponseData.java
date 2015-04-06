package com.elitecore.cpe.bl.ws.data.input.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "NICE_CheckCPEResourceResponse", propOrder = {
	    "responseCode",
	    "responseMessage",
	    "resourceAvailabilityVOList"
	})
public class ResourceAvailibilityResponseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String responseCode;
	private String responseMessage;
	
	 @XmlElement(name = "NICE_CheckResourceAvailabilityList")
	private ResourceAvailabilityVOList resourceAvailabilityVOList;
//	private List<ResourceAvailibilityVO> resourceAvailibilityVOs; 
	
	
	
	public String getResponseCode() {
		return responseCode;
	}

	
	public ResourceAvailabilityVOList getResourceAvailabilityVOList() {
		return resourceAvailabilityVOList;
	}

	public void setResourceAvailabilityVOList(
			ResourceAvailabilityVOList resourceAvailabilityVOList) {
		this.resourceAvailabilityVOList = resourceAvailabilityVOList;
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


	@Override
	public String toString() {
		return "ResourceAvailibilityResponseData [responseCode=" + responseCode
				+ ", responseMessage=" + responseMessage
				+ ", resourceAvailabilityVOList=" + resourceAvailabilityVOList
				+ "]";
	}

	

	/*@XmlElement(name = "NICE_CheckResourceAvailabilityList")
	public List<ResourceAvailibilityVO> getResourceAvailibilityVOs() {
		return resourceAvailibilityVOs;
	}

	public void setResourceAvailibilityVOs(
			List<ResourceAvailibilityVO> resourceAvailibilityVOs) {
		this.resourceAvailibilityVOs = resourceAvailibilityVOs;
	}*/

	/* 
	 * 
	 */
	
	
}
