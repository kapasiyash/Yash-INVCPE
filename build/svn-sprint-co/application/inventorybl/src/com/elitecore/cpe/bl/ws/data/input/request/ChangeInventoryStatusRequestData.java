package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;
import java.util.List;

import com.elitecore.cpe.bl.ws.data.input.vo.InventoryRequestVO;

public class ChangeInventoryStatusRequestData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<InventoryRequestVO> inventoryVOs;

	public List<InventoryRequestVO> getInventoryVOs() {
		return inventoryVOs;
	}

	public void setInventoryVOs(List<InventoryRequestVO> inventoryVOs) {
		this.inventoryVOs = inventoryVOs;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryRequestData [inventoryVOs=");
		builder.append(inventoryVOs);
		builder.append("]");
		return builder.toString();
	}

	
	
}
