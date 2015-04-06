package com.elitecore.cpe.bl.ws.data.input.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_InventoryList", propOrder = {
    "inventoryList"
})
public class InventoryVOList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@XmlElement(name = "NICE_Inventory", required = true)
	private List<InventoryVO> inventoryList;


	public List<InventoryVO> getInventoryList() {
		return inventoryList;
	}


	public void setInventoryList(List<InventoryVO> inventoryList) {
		this.inventoryList = inventoryList;
	}


	@Override
	public String toString() {
		return "InventoryVOList [inventoryList=" + inventoryList + "]";
	}
	
	
	

}
