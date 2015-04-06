package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.elitecore.cpe.bl.ws.data.input.vo.ReleaseInventoryVO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_ReleaseInventoryList", propOrder = {
    "inventoryList"
})
public class ReleaseInventoryList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "NICE_ReleaseInventory", required = true)
	private List<ReleaseInventoryVO> inventoryList;

	public List<ReleaseInventoryVO> getInventoryList() {
		return inventoryList;
	}

	public void setInventoryList(List<ReleaseInventoryVO> inventoryList) {
		this.inventoryList = inventoryList;
	}

	@Override
	public String toString() {
		return "ReleaseInventoryList [inventoryList=" + inventoryList + "]";
	}


	
	
	
}
