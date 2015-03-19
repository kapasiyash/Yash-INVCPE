package com.elitecore.cpe.bl.vo.system.agent;

import java.io.Serializable;
import java.sql.Timestamp;

public class ViewAgentScheduleVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String scheduleName;
	private String priority;
	private String executionType;
	private Timestamp executionStartDate;
	private Timestamp lastExecutionDate,nextExecutionDate;
	private Long requiredNumOfExecutions;
	private String reason;
	private Timestamp createdDate;
	private Timestamp lastModifiedDate;
	private String createdBy;
	private String lastModifiedBy;
	
	
	
	public String getExecutionType() {
		return executionType;
	}
	public void setExecutionType(String executionType) {
		this.executionType = executionType;
	}
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Timestamp getExecutionStartDate() {
		return executionStartDate;
	}
	public void setExecutionStartDate(Timestamp executionStartDate) {
		this.executionStartDate = executionStartDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Timestamp getLastExecutionDate() {
		return lastExecutionDate;
	}
	public void setLastExecutionDate(Timestamp lastExecutionDate) {
		this.lastExecutionDate = lastExecutionDate;
	}
	public Timestamp getNextExecutionDate() {
		return nextExecutionDate;
	}
	public void setNextExecutionDate(Timestamp nextExecutionDate) {
		this.nextExecutionDate = nextExecutionDate;
	}
	public Long getRequiredNumOfExecutions() {
		return requiredNumOfExecutions;
	}
	public void setRequiredNumOfExecutions(Long requiredNumOfExecutions) {
		this.requiredNumOfExecutions = requiredNumOfExecutions;
	}
	
	
	
}
