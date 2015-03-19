package com.elitecore.cpe.bl.agents.base;

import java.io.Serializable;

import com.elitecore.utility.agentframework.IAgent;

public abstract class BaseAgent implements IAgent ,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String className;

	public BaseAgent(String className) {
		super();
		this.className = className;
	}
	
	public String getClassName(){
		return this.className;
	}
}
