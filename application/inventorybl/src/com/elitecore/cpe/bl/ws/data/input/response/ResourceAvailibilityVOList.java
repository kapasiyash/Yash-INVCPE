package com.elitecore.cpe.bl.ws.data.input.response;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.elitecore.cpe.bl.ws.data.input.vo.BookResourceAvailibilityVO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_BookResourceAvailabilityList", propOrder = {
    "resourceAvailibilityVO"
})
public class ResourceAvailibilityVOList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "NICE_BookResourceAvailability", required = true)
	private List<BookResourceAvailibilityVO> resourceAvailibilityVO;


	public List<BookResourceAvailibilityVO> getResourceAvailibilityVO() {
		return resourceAvailibilityVO;
	}


	public void setResourceAvailibilityVO(
			List<BookResourceAvailibilityVO> resourceAvailibilityVO) {
		this.resourceAvailibilityVO = resourceAvailibilityVO;
	}


	@Override
	public String toString() {
		return "ResourceAvailibilityVOList [resourceAvailibilityVO="
				+ resourceAvailibilityVO + "]";
	}
	
	
}
