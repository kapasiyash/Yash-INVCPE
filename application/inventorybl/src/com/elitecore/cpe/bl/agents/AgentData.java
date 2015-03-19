package com.elitecore.cpe.bl.agents;

import com.elitecore.cpe.bl.agents.base.BaseAgent;


public class AgentData extends BaseAgent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String agentId;
	private String agentName;
	
	public AgentData(String agentId, String agentName,String className) {
		super(className);
		this.agentId = agentId;
		this.agentName = agentName;
	}

	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@Override
	public String toString() {
		return "AgentData [agentId=" + agentId + ", agentName=" + agentName
				+ ", getClassName()=" + getClassName() + "]";
	}
	
	
	
}
