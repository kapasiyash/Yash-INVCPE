package com.elitecore.cpe.bl.vo.system.agent;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yash.kapasi
 *
 */
public class SearchAgentScheduleVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long agentScheduleId;
	private String scheduleName;
	private String agentName;
	private Timestamp createdDate;
	private Timestamp lastModifiedDate;
	private Timestamp lastExecutionDate;
	private Timestamp nextScheduleDate;
	private Timestamp executionStartDate;
	private String status;
	private String executionType;
	private boolean isActive;
	
	
	
	
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Timestamp getExecutionStartDate() {
		return executionStartDate;
	}
	public void setExecutionStartDate(Timestamp executionStartDate) {
		this.executionStartDate = executionStartDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getAgentScheduleId() {
		return agentScheduleId;
	}
	public void setAgentScheduleId(Long agentScheduleId) {
		this.agentScheduleId = agentScheduleId;
	}
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
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
	public Timestamp getLastExecutionDate() {
		return lastExecutionDate;
	}
	public void setLastExecutionDate(Timestamp lastExecutionDate) {
		this.lastExecutionDate = lastExecutionDate;
	}
	public String getExecutionType() {
		return executionType;
	}
	public void setExecutionType(String executionType) {
		this.executionType = executionType;
	}
	public Timestamp getNextScheduleDate() {
		return nextScheduleDate;
	}
	public void setNextScheduleDate(Timestamp nextScheduleDate) {
		this.nextScheduleDate = nextScheduleDate;
	}
	/* 
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SearchAgentScheduleVO [agentScheduleId=");
		builder.append(agentScheduleId);
		builder.append(", scheduleName=");
		builder.append(scheduleName);
		builder.append(", agentName=");
		builder.append(agentName);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", lastModifiedDate=");
		builder.append(lastModifiedDate);
		builder.append(", lastExecutionDate=");
		builder.append(lastExecutionDate);
		builder.append(", nextScheduleDate=");
		builder.append(nextScheduleDate);
		builder.append(", executionStartDate=");
		builder.append(executionStartDate);
		builder.append(", status=");
		builder.append(status);
		builder.append(", executionType=");
		builder.append(executionType);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append("]");
		return builder.toString();
	}
	
}
