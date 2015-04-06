package com.elitecore.cpe.bl.ws.data.input.response;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.elitecore.cpe.bl.ws.data.input.vo.ResourceAvailibilityVO;

@XmlType(name = "NICE_CheckResourceAvailabilityList", propOrder = {
	    "resourceAvailibilityVOs"
	})
public class ResourceAvailabilityVOList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private List<ResourceAvailibilityVO> resourceAvailibilityVOs;
	
	
	 @XmlElement(name = "NICE_CheckResourceAvailability", required = true)
	public List<ResourceAvailibilityVO> getResourceAvailibilityVOs() {
		return resourceAvailibilityVOs;
	}

	public void setResourceAvailibilityVOs(
			List<ResourceAvailibilityVO> resourceAvailibilityVOs) {
		this.resourceAvailibilityVOs = resourceAvailibilityVOs;
	}

	@Override
	public String toString() {
		return "ResourceAvailabilityVOList [resourceAvailibilityVOs="
				+ resourceAvailibilityVOs + "]";
	}
	
	
	

}
