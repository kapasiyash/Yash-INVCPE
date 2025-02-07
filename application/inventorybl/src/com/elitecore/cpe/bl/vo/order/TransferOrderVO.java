package com.elitecore.cpe.bl.vo.order;

import java.io.Serializable;
import java.sql.Timestamp;

public class TransferOrderVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long transferOrderId;
	private String transferOrderNo;
	private Timestamp createdate;
	private Timestamp updatedate;
	private String createdby;
	private String updatedby;	
	private Long fromWarehouseId;
	private Long toWarehouseId;
	
	private String inventoryOrderStatusId;
	private String remarks;
	
	private String emailId;
	private String parentEmailId;
	
	
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getParentEmailId() {
		return parentEmailId;
	}
	public void setParentEmailId(String parentEmailId) {
		this.parentEmailId = parentEmailId;
	}
	public Long getTransferOrderId() {
		return transferOrderId;
	}
	public void setTransferOrderId(Long transferOrderId) {
		this.transferOrderId = transferOrderId;
	}
	public String getTransferOrderNo() {
		return transferOrderNo;
	}
	public void setTransferOrderNo(String transferOrderNo) {
		this.transferOrderNo = transferOrderNo;
	}
	public Timestamp getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}
	public Timestamp getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public Long getFromWarehouseId() {
		return fromWarehouseId;
	}
	public void setFromWarehouseId(Long fromWarehouseId) {
		this.fromWarehouseId = fromWarehouseId;
	}
	public Long getToWarehouseId() {
		return toWarehouseId;
	}
	public void setToWarehouseId(Long toWarehouseId) {
		this.toWarehouseId = toWarehouseId;
	}
	public String getInventoryOrderStatusId() {
		return inventoryOrderStatusId;
	}
	public void setInventoryOrderStatusId(String inventoryOrderStatusId) {
		this.inventoryOrderStatusId = inventoryOrderStatusId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "TransferOrderVO [transferOrderId=" + transferOrderId
				+ ", transferOrderNo=" + transferOrderNo + ", createdate="
				+ createdate + ", updatedate=" + updatedate + ", createdby="
				+ createdby + ", updatedby=" + updatedby + ", fromWarehouseId="
				+ fromWarehouseId + ", toWarehouseId=" + toWarehouseId
				+ ", inventoryOrderStatusId=" + inventoryOrderStatusId
				+ ", remarks=" + remarks + "]";
	}
	
	
	

}
