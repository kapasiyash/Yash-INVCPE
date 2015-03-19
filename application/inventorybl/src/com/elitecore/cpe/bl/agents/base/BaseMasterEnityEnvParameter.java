package com.elitecore.cpe.bl.agents.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.elitecore.utility.agentframework.utils.IAgentMasterEntityEnvParameterList;

public  class BaseMasterEnityEnvParameter implements IAgentMasterEntityEnvParameterList,Serializable{
	private static final long serialVersionUID = 1L;
	
	private Map<String,Object> paramMap;
	private String agentRunMasterEntityDesc;
	
	public BaseMasterEnityEnvParameter(){
		paramMap = new HashMap<String, Object>();
    }

	public Map<String,Object> getParameterMap() {
		return this.paramMap;
	}
	

	@Override
	public String getAgentRunMasterEntityDesc() {
		return this.agentRunMasterEntityDesc;
	}

	@Override
	public Object getParameter(String key) {
		return paramMap.get(key);
	}

	public void setParameter(String key,Object value){
		this.paramMap.put(key, value);
	}
	
	@Override
	public void setAgentRunMasterEntityDesc(String arg0) {
		this.agentRunMasterEntityDesc =arg0;
	}


	@Override
	public String toString() {
		return "BaseMasterEnityEnvParameter [paramMap=" + paramMap
				+ ", agentRunMasterEntityDesc=" + agentRunMasterEntityDesc
				+ "]";
	}
	
	
}
