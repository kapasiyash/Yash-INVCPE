package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;

public class TransferInventorySummaryViewVO implements Serializable,Comparable<TransferInventorySummaryViewVO>{


	private static final long serialVersionUID = 1L;

	private String orderNo;
	private Long fromWarehouseId;
	private String fromWarehouseName;
	private String toWarehouseName;
	private int total;
	private int accepted;
	private int rejected;
	
	private String orderStatus;
	
	//For Accept rejected Flow
	private boolean  isRejected;
	
	
	public boolean isRejected() {
		return isRejected;
	}
	public void setRejected(boolean isRejected) {
		this.isRejected = isRejected;
	}
	public Long getFromWarehouseId() {
		return fromWarehouseId;
	}
	public void setFromWarehouseId(Long fromWarehouseId) {
		this.fromWarehouseId = fromWarehouseId;
	}
	public String getToWarehouseName() {
		return toWarehouseName;
	}
	public void setToWarehouseName(String toWarehouseName) {
		this.toWarehouseName = toWarehouseName;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getAccepted() {
		return accepted;
	}
	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}
	public int getRejected() {
		return rejected;
	}
	public void setRejected(int rejected) {
		this.rejected = rejected;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getFromWarehouseName() {
		return fromWarehouseName;
	}
	public void setFromWarehouseName(String fromWarehouseName) {
		this.fromWarehouseName = fromWarehouseName;
	}
	
	
	
	
	
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ViewTransferInventoryDetailVO [orderNo=");
		builder.append(orderNo);
		builder.append(", fromWarehouseId=");
		builder.append(fromWarehouseId);
		builder.append(", fromWarehouseName=");
		builder.append(fromWarehouseName);
		builder.append(", toWarehouseName=");
		builder.append(toWarehouseName);
		builder.append(", total=");
		builder.append(total);
		builder.append(", accepted=");
		builder.append(accepted);
		builder.append(", rejected=");
		builder.append(rejected);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int compareTo(TransferInventorySummaryViewVO o) {
		
		if(o!=null) {
			return o.getOrderNo().compareTo(this.getOrderNo());
		}
		
		return 0;
	}
	
	
}
