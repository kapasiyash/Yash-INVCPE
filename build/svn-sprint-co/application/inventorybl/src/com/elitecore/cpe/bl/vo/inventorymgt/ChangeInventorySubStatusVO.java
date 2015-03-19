package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;

public class ChangeInventorySubStatusVO implements Serializable{

	
	
	private static final long serialVersionUID = 1L;
	
	private String subStatus;
	private String status;
	private String inventoryNo;
	private String reason;
 
//Added By Rinkal-start
	private Long substatusId;
	

	public Long getSubstatusId() {
		return substatusId;
	}
	public void setSubstatusId(Long substatusId) {
		this.substatusId = substatusId;
	}
//Added By Rinkal-End
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInventoryNo() {
		return inventoryNo;
	}
	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChangeInventorySubStatusVO [subStatus=");
		builder.append(subStatus);
		builder.append(", status=");
		builder.append(status);
		builder.append(", inventoryNo=");
		builder.append(inventoryNo);
		builder.append(", reason=");
		builder.append(reason);
		builder.append(", substatusId=");
		builder.append(substatusId);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
