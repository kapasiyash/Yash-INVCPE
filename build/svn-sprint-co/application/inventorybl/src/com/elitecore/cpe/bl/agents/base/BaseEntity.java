package com.elitecore.cpe.bl.agents.base;

import java.io.Serializable;

import com.elitecore.utility.agentframework.entities.IAgentRunEntity;


public  class BaseEntity implements IAgentRunEntity,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String entityId;
	
	private Object object;
	
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public BaseEntity(String entityId){
        this.entityId=entityId;
    }
	@Override
	public String getEntityId() {
		return this.entityId;
	}
	@Override
	public void setEntityId(String entityId) throws Exception {
		this.entityId=entityId;
	}
	@Override
	public int hashCode() {
		if(entityId!=null)
			return entityId.hashCode();
		else
			return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		if(obj instanceof BaseEntity && ((BaseEntity) obj).getEntityId()!=null){
			return ((BaseEntity) obj).getEntityId().equals(getEntityId());
		}else{
			return false;
		}
	}
	
	
	

}
