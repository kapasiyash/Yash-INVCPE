package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

public class SearchTransferInventory implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long orderId;
	private String orderNo;
	private Timestamp fromDate;
	private Timestamp toDate;
	private Long warehouseId;
	private String orderStatus;
	private boolean partial;
	private String remark;
	private byte[] uploadbyteData;
	private String fromWarehouseName;
	private String toWarehouseName;
	private Long acceptQuantity;
	private Timestamp accepRejectDate;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public Timestamp getToDate() {
		return toDate;
	}
	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}
	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	public Long getWarehouseId() {
		return warehouseId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public boolean isPartial() {
		return partial;
	}
	public void setPartial(boolean partial) {
		this.partial = partial;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public byte[] getUploadbyteData() {
		return uploadbyteData;
	}
	public void setUploadbyteData(byte[] uploadbyteData) {
		this.uploadbyteData = uploadbyteData;
	}
	
	public String getFromWarehouseName() {
		return fromWarehouseName;
	}
	public void setFromWarehouseName(String fromWarehouseName) {
		this.fromWarehouseName = fromWarehouseName;
	}
	public String getToWarehouseName() {
		return toWarehouseName;
	}
	public void setToWarehouseName(String toWarehouseName) {
		this.toWarehouseName = toWarehouseName;
	}
	
	
	
	public Long getAcceptQuantity() {
		return acceptQuantity;
	}
	public void setAcceptQuantity(Long acceptQuantity) {
		this.acceptQuantity = acceptQuantity;
	}
	public Timestamp getAccepRejectDate() {
		return accepRejectDate;
	}
	public void setAccepRejectDate(Timestamp accepRejectDate) {
		this.accepRejectDate = accepRejectDate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SearchTransferInventory [orderId=");
		builder.append(orderId);
		builder.append(", orderNo=");
		builder.append(orderNo);
		builder.append(", fromDate=");
		builder.append(fromDate);
		builder.append(", toDate=");
		builder.append(toDate);
		builder.append(", warehouseId=");
		builder.append(warehouseId);
		builder.append(", orderStatus=");
		builder.append(orderStatus);
		builder.append(", partial=");
		builder.append(partial);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", uploadbyteData=");
		builder.append(Arrays.toString(uploadbyteData));
		builder.append(", fromWarehouseName=");
		builder.append(fromWarehouseName);
		builder.append(", toWarehouseName=");
		builder.append(toWarehouseName);
		builder.append(", acceptQuantity=");
		builder.append(acceptQuantity);
		builder.append(", accepRejectDate=");
		builder.append(accepRejectDate);
		builder.append("]");
		return builder.toString();
	}
	
	
}
