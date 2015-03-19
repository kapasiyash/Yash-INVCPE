package com.elitecore.cpe.bl.vo.system.agent;

import java.io.Serializable;
import java.sql.Timestamp;

public class ViewAgentRunHistoryVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long agentRunId;
	private Timestamp executionstartdate;
	private Timestamp executionstopate;
	private Long totalMasterEntities;
	private Long totalEntities;
	private Long totalSuccessMasterEntities;
	private Long totalSuccessEntities;
	private String paramDetail;
	private String status;
	private String remarks;
	
	
	
	
	
	public Long getAgentRunId() {
		return agentRunId;
	}
	public void setAgentRunId(Long agentRunId) {
		this.agentRunId = agentRunId;
	}
	public Timestamp getExecutionstartdate() {
		return executionstartdate;
	}
	public void setExecutionstartdate(Timestamp executionstartdate) {
		this.executionstartdate = executionstartdate;
	}
	public Timestamp getExecutionstopate() {
		return executionstopate;
	}
	public void setExecutionstopate(Timestamp executionstopate) {
		this.executionstopate = executionstopate;
	}
	public Long getTotalMasterEntities() {
		return totalMasterEntities;
	}
	public void setTotalMasterEntities(Long totalMasterEntities) {
		this.totalMasterEntities = totalMasterEntities;
	}
	public Long getTotalEntities() {
		return totalEntities;
	}
	public void setTotalEntities(Long totalEntities) {
		this.totalEntities = totalEntities;
	}
	public Long getTotalSuccessMasterEntities() {
		return totalSuccessMasterEntities;
	}
	public void setTotalSuccessMasterEntities(Long totalSuccessMasterEntities) {
		this.totalSuccessMasterEntities = totalSuccessMasterEntities;
	}
	public Long getTotalSuccessEntities() {
		return totalSuccessEntities;
	}
	public void setTotalSuccessEntities(Long totalSuccessEntities) {
		this.totalSuccessEntities = totalSuccessEntities;
	}
	public String getParamDetail() {
		return paramDetail;
	}
	public void setParamDetail(String paramDetail) {
		this.paramDetail = paramDetail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

}
