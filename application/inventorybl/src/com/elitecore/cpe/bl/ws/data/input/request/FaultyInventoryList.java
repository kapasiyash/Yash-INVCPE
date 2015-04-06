package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.elitecore.cpe.bl.ws.data.input.vo.CPEInventoryVO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_FaultyInventoryList", propOrder = {
    "listCpeInventoryVOs"
})
public class FaultyInventoryList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "NICE_FaultyInventory", required = true)
	private List<CPEInventoryVO> listCpeInventoryVOs;

	public List<CPEInventoryVO> getListCpeInventoryVOs() {
		return listCpeInventoryVOs;
	}

	public void setListCpeInventoryVOs(List<CPEInventoryVO> listCpeInventoryVOs) {
		this.listCpeInventoryVOs = listCpeInventoryVOs;
	}
	
	
	

}
