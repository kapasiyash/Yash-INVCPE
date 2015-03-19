package com.elitecore.cpe.bl.vo.system.audit;

import java.io.Serializable;
import java.util.List;

public class SearchAuditWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SearchAuditVO> searchAuditVOs;
	private int totalRecords;
	public List<SearchAuditVO> getSearchAuditVOs() {
		return searchAuditVOs;
	}
	public void setSearchAuditVOs(List<SearchAuditVO> searchAuditVOs) {
		this.searchAuditVOs = searchAuditVOs;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	
	
	
}
