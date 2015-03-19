package com.elitecore.cpe.bl.ws.data.input.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InventoryDetailVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String warehouseName;
	private String resourceName;
	private String resourceType;
	
	private List<InventoryVO> inventoryVOs;

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public List<InventoryVO> getInventoryVOs() {
		return inventoryVOs;
	}

	public void setInventoryVOs(List<InventoryVO> inventoryVOs) {
		this.inventoryVOs = inventoryVOs;
	}

	
	public void addInventory(InventoryVO inventoryVO) {
		if(this.inventoryVOs==null) {
			this.inventoryVOs = new ArrayList<InventoryVO>();
		}
		this.inventoryVOs.add(inventoryVO);
			
	}
	
	@Override
	public String toString() {
		return "InventoryDetailVO [warehouseName=" + warehouseName
				+ ", resourceName=" + resourceName + ", resourceType="
				+ resourceType + ", inventoryVOs=" + inventoryVOs + "]";
	}
	
	

}
