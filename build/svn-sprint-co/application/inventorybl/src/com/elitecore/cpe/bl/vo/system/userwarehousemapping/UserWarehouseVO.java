package com.elitecore.cpe.bl.vo.system.userwarehousemapping;

import java.io.Serializable;

import com.elitecore.cpe.bl.data.common.ComboData;

public class UserWarehouseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ComboData warehouseDatas;
	private ComboData userWarehouseDatas;
	public ComboData getWarehouseDatas() {
		return warehouseDatas;
	}
	public void setWarehouseDatas(ComboData warehouseDatas) {
		this.warehouseDatas = warehouseDatas;
	}
	public ComboData getUserWarehouseDatas() {
		return userWarehouseDatas;
	}
	public void setUserWarehouseDatas(ComboData userWarehouseDatas) {
		this.userWarehouseDatas = userWarehouseDatas;
	}
	@Override
	public String toString() {
		return "UserWarehouseVO [warehouseDatas=" + warehouseDatas
				+ ", userWarehouseDatas=" + userWarehouseDatas + "]";
	}
	
	

}
