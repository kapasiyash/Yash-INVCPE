/**
 * 
 */
package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Joel.Macwan
 *
 */
public class InventoryStatusLogVO implements Serializable,Comparable<InventoryStatusLogVO> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String oldStatus;
	private String newStatus;
	private String remarks;
	private Long inventoryId;
	private String changedby;
	private Timestamp changeDate;
	/**
	 * 
	 */
	public String getOldStatus() {
		return oldStatus;
	}
	/**
	 * 
	 */
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	/**
	 * 
	 */
	public String getNewStatus() {
		return newStatus;
	}
	/**
	 * 
	 */
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}
	/**
	 * 
	 */
	public Long getInventoryId() {
		return inventoryId;
	}
	/**
	 * 
	 */
	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}
	/**
	 * 
	 */
	public String getChangedby() {
		return changedby;
	}
	/**
	 * 
	 */
	public void setChangedby(String changedby) {
		this.changedby = changedby;
	}
	/**
	 * 
	 */
	public Timestamp getChangeDate() {
		return changeDate;
	}
	/**
	 * 
	 */
	public void setChangeDate(Timestamp changeDate) {
		this.changeDate = changeDate;
	}
	
	/**
	 * 
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/* 
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryStatusLogVO [oldStatus=");
		builder.append(oldStatus);
		builder.append(", newStatus=");
		builder.append(newStatus);
		builder.append(", remarks=");
		builder.append(remarks);
		builder.append(", inventoryId=");
		builder.append(inventoryId);
		builder.append(", changedby=");
		builder.append(changedby);
		builder.append(", changeDate=");
		builder.append(changeDate);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int compareTo(InventoryStatusLogVO o) {
		
		if(o!=null) {
			return o.getChangeDate().compareTo(changeDate);
		}
		
		return 0;
	}

}
