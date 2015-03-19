package com.elitecore.cpe.bl.vo.master.warehouse;

import java.io.Serializable;
import java.util.List;

public class CreateWareHouseTreeVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String wareHouseName;
	private Long warehouseId;
	private String wareHouseTypeName;
	
	private List<CreateWareHouseTreeVO> childWareHouses;

	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	
	
	public String getWareHouseTypeName() {
		return wareHouseTypeName;
	}

	public void setWareHouseTypeName(String wareHouseTypeName) {
		this.wareHouseTypeName = wareHouseTypeName;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public List<CreateWareHouseTreeVO> getChildWareHouses() {
		return childWareHouses;
	}

	public void setChildWareHouses(List<CreateWareHouseTreeVO> childWareHouses) {
		this.childWareHouses = childWareHouses;
	}
	
	

}
