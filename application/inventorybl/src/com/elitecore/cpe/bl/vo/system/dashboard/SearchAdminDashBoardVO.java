package com.elitecore.cpe.bl.vo.system.dashboard;

import java.io.Serializable;
import java.sql.Timestamp;

public class SearchAdminDashBoardVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String glCodeId;
	private String glCodeName;
	private String transactionDate;
	private double creditAmount;
	private double debitAmount;
	public String getGlCodeId() {
		return glCodeId;
	}
	public void setGlCodeId(String glCodeId) {
		this.glCodeId = glCodeId;
	}
	public String getGlCodeName() {
		return glCodeName;
	}
	public void setGlCodeName(String glCodeName) {
		this.glCodeName = glCodeName;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public double getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}

	
	
}
