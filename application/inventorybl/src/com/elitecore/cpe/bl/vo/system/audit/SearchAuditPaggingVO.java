package com.elitecore.cpe.bl.vo.system.audit;

import java.io.Serializable;
import java.sql.Timestamp;

public class SearchAuditPaggingVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Timestamp fromDate;
	private Timestamp toDate;
	private String actionAlias;
	private Long moduleId;
	private int itemStartNumber;
	private int pageSize;
	public Timestamp getFromDate() {
		return fromDate;
	}
	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}
	public Timestamp getToDate() {
		return toDate;
	}
	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}
	public String getActionAlias() {
		return actionAlias;
	}
	public void setActionAlias(String actionAlias) {
		this.actionAlias = actionAlias;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public int getItemStartNumber() {
		return itemStartNumber;
	}
	public void setItemStartNumber(int itemStartNumber) {
		this.itemStartNumber = itemStartNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
	

}
