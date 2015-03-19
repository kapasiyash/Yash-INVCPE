package com.elitecore.cpe.bl.agents.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.elitecore.utility.agentframework.utils.IAgentRunEnvParameterList;

/**
 * @author yash.kapasi
 *
 */
public  class BaseEnityEnvParameter implements IAgentRunEnvParameterList,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String agentId;

	public BaseEnityEnvParameter(String agentId){
		dataMap = new HashMap<String,Object>();
    }
	
	private Map<String,Object> dataMap;
	

	
	
	
	@Override
	public Object getParameter(String paramName) {
		return dataMap.get(paramName);
	}

	public void setParameter(String key,Object value){
		this.dataMap.put(key, value);
	}

	@Override
	public String toString() {
		return "BaseEnityEnvParameter [dataMap=" + dataMap
				+ ", agentrunid=" + getAgentRunId() + "]";
	}

	@Override
	public String getAgentRunId() {
		return this.agentId;
	}

	@Override
	public void setAgentRunId(String agentId) {
		this.agentId=agentId;
	}

	
}
