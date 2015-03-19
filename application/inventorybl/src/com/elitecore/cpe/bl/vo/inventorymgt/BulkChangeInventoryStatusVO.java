package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;

public class BulkChangeInventoryStatusVO implements Serializable{

	private static final long serialVersionUID = 1L;
    private String resourceNumber;
	private Long statusFromId;
    private Long statusToId;
	private String statusNewName;
    
	
	public String getStatusNewName() {
		return statusNewName;
	}
	public void setStatusNewName(String statusNewName) {
		this.statusNewName = statusNewName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	private String remarks;
	public String getResourceNumber() {
		return resourceNumber;
	}
	public void setResourceNumber(String resourceNumber) {
		this.resourceNumber = resourceNumber;
	}
	public Long getStatusFromId() {
		return statusFromId;
	}
	public void setStatusFromId(Long statusFromId) {
		this.statusFromId = statusFromId;
	}
	public Long getStatusToId() {
		return statusToId;
	}
	public void setStatusToId(Long statusToId) {
		this.statusToId = statusToId;
	}
	@Override
	public String toString() {
		return "BulkChangeInventoryStatusVO [resourceNumber=" + resourceNumber
				+ ", statusFromId=" + statusFromId + ", statusToId="
				+ statusToId + ", statusNewName=" + statusNewName
				+ ", remarks=" + remarks + "]";
	}
	
}
