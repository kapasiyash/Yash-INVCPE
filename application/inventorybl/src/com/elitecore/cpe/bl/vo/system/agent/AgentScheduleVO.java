package com.elitecore.cpe.bl.vo.system.agent;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class AgentScheduleVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long agentScheduleId;
	private String agentId;
	private String scheduleName;
	private String priority;
	private Long executionInterval; //In minutes
	private String executionType;
	private Long requiredNoOfExecutions;
	private Timestamp executionStartDate;
	private String reason;
	
	private String schedulePattern;
	
	private List<AgentScheduleParamVO> scheduleParamVO;
	
	
	
	
	
	public Long getAgentScheduleId() {
		return agentScheduleId;
	}
	public void setAgentScheduleId(Long agentScheduleId) {
		this.agentScheduleId = agentScheduleId;
	}
	public List<AgentScheduleParamVO> getScheduleParamVO() {
		return scheduleParamVO;
	}
	public void setScheduleParamVO(List<AgentScheduleParamVO> scheduleParamVO) {
		this.scheduleParamVO = scheduleParamVO;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	
	public Long getExecutionInterval() {
		return executionInterval;
	}
	public void setExecutionInterval(Long executionInterval) {
		this.executionInterval = executionInterval;
	}
	
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getExecutionType() {
		return executionType;
	}
	public void setExecutionType(String executionType) {
		this.executionType = executionType;
	}
	public Long getRequiredNoOfExecutions() {
		return requiredNoOfExecutions;
	}
	public void setRequiredNoOfExecutions(Long requiredNoOfExecutions) {
		this.requiredNoOfExecutions = requiredNoOfExecutions;
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
	public String getSchedulePattern() {
		return schedulePattern;
	}
	public void setSchedulePattern(String schedulePattern) {
		this.schedulePattern = schedulePattern;
	}
}
