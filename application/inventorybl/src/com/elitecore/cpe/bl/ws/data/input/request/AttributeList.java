package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.elitecore.cpe.bl.ws.data.input.vo.InventoryAttributeVO;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_AttributeList", propOrder = {
    "attributeVOs"
})
public class AttributeList implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "NICE_Attribute", required = true)
	private List<InventoryAttributeVO> attributeVOs;


	public List<InventoryAttributeVO> getAttributeVOs() {
		return attributeVOs;
	}


	public void setAttributeVOs(List<InventoryAttributeVO> attributeVOs) {
		this.attributeVOs = attributeVOs;
	}
	
	
	
}
