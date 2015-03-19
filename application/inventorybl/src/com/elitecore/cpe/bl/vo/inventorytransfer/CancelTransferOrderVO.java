package com.elitecore.cpe.bl.vo.inventorytransfer;

import java.io.Serializable;

public class CancelTransferOrderVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tarnsferNo;
	private String remarks;
	public String getTarnsferNo() {
		return tarnsferNo;
	}
	public void setTarnsferNo(String tarnsferNo) {
		this.tarnsferNo = tarnsferNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "CancelTransferOrderVO [tarnsferNo=" + tarnsferNo + ", remarks="
				+ remarks + "]";
	}
	
	

}
