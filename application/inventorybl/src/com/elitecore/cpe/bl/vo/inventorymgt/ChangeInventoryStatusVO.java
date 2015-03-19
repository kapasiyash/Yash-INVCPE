package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;

public class ChangeInventoryStatusVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String inventoryNo;
	private Long statusId;
	private String statusName;
	private String remarks;
	
	
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getInventoryNo() {
		return inventoryNo;
	}
	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "ChangeInventoryStatusVO [inventoryNo=" + inventoryNo
				+ ", statusId=" + statusId + ", remarks=" + remarks + "]";
	}
	
	
	

}
