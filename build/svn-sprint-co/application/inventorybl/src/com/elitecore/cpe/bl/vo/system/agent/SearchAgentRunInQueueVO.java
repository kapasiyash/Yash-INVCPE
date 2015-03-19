package com.elitecore.cpe.bl.vo.system.agent;

import java.io.Serializable;
import java.sql.Timestamp;

public class SearchAgentRunInQueueVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long agentRunInQueueId;
	private String agentName;
	private String scheduleName;
	private Timestamp scheduleDueDate;
	private String executionType;
	private String priority;
	private String status;
	
	
	
	public Long getAgentRunInQueueId() {
		return agentRunInQueueId;
	}
	public void setAgentRunInQueueId(Long agentRunInQueueId) {
		this.agentRunInQueueId = agentRunInQueueId;
	}
	
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	public Timestamp getScheduleDueDate() {
		return scheduleDueDate;
	}
	public void setScheduleDueDate(Timestamp scheduleDueDate) {
		this.scheduleDueDate = scheduleDueDate;
	}
	public String getExecutionType() {
		return executionType;
	}
	public void setExecutionType(String executionType) {
		this.executionType = executionType;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
