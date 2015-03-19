package com.elitecore.cpe.bl.ws.data.input.vo;

import java.io.Serializable;

public class CPEInventoryVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String inventoryNo;

	public String getInventoryNo() {
		return inventoryNo;
	}

	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}

	@Override
	public String toString() {
		return "CPEInventoryVO [inventoryNo=" + inventoryNo + "]";
	}

	
	
}
