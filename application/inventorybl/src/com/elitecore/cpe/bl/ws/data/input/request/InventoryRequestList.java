package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.elitecore.cpe.bl.ws.data.input.vo.CPEInventoryVO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_InventoryList", propOrder = {
    "inventoryVOs"
})
public class InventoryRequestList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "NICE_Inventory", required = true)
	private List<CPEInventoryVO> inventoryVOs;

	public List<CPEInventoryVO> getInventoryVOs() {
		return inventoryVOs;
	}

	public void setInventoryVOs(List<CPEInventoryVO> inventoryVOs) {
		this.inventoryVOs = inventoryVOs;
	}
	
	

}
