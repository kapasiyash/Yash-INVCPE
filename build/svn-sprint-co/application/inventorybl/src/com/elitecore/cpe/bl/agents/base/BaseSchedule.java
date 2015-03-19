package com.elitecore.cpe.bl.agents.base;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.elitecore.utility.agentframework.IAgent;
import com.elitecore.utility.agentframework.IAgentSchedule;

public  class BaseSchedule implements IAgentSchedule,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String agentScheduleId;
	private String agentScheduleName;
	private Long priority;
	private String[] parameters;
	private Map<String,String> scheduleValues;
	private Timestamp executiondueDate;
	private IAgent agent;
	private Long agentRunQueueId;
	
	
	public BaseSchedule(String agentScheduleId, String agentScheduleName, BaseAgent agent,Long priority) {
		super();
		this.agentScheduleId = agentScheduleId;
		this.agentScheduleName = agentScheduleName;
		this.agent = agent;
		this.priority=priority;
		scheduleValues=  new HashMap<String, String>();
	}

	@Override
	public IAgent getAgent() {
		return agent;
	}

	@Override
	public void setAgent(IAgent agent) {
		this.agent = agent;
	}

	public String getAgentScheduleId() {
		return agentScheduleId;
	}

	public void setAgentScheduleId(String agentScheduleId) {
		this.agentScheduleId = agentScheduleId;
	}

	public String getAgentScheduleName() {
		return agentScheduleName;
	}

	public void setAgentScheduleName(String agentScheduleName) {
		this.agentScheduleName = agentScheduleName;
	}

	/**
	 * Use getScheduleParameter instead.
	 * 
	 * */
	@Deprecated
	public String[] getParameters() {
		return parameters;
	}
	
	/**
	 * Use addScheduleParameter instead.
	 * 
	 * */
	@Deprecated
	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}
	
	

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}
	
	public void addScheduleParameter(String key , String value){
		this.scheduleValues.put(key, value);
	}

	public String getScheduleParameter(String key){
		return this.scheduleValues.get(key);
	}
	
	

	public Map<String, String> getScheduleValues() {
		return scheduleValues;
	}

	

	public Timestamp getExecutiondueDate() {
		return executiondueDate;
	}

	public void setExecutiondueDate(Timestamp executiondueDate) {
		this.executiondueDate = executiondueDate;
	}

	public Long getAgentRunQueueId() {
		return agentRunQueueId;
	}

	public void setAgentRunQueueId(Long agentRunQueueId) {
		this.agentRunQueueId = agentRunQueueId;
	}

	@Override
	public String toString() {
		return "BaseSchedule [agentScheduleId=" + agentScheduleId
				+ ", agentScheduleName=" + agentScheduleName + ", priority="
				+ priority + ", scheduleValues=" + scheduleValues + ", agent="
				+ agent + ", agentRunQueueId=" + agentRunQueueId + "]";
	}

	

	
}
