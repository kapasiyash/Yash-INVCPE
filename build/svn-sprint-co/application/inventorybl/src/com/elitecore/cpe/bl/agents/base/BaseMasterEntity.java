package com.elitecore.cpe.bl.agents.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.elitecore.utility.agentframework.entities.IAgentRunMasterEntity;

/**
 * @author yash.kapasi
 *
 */
public  class BaseMasterEntity  implements IAgentRunMasterEntity,Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String masterEntityId;
	private String name;
	private Long longEntityId;
	
	private Map<String,Object> parameters = new HashMap<String, Object>();
	
	public BaseMasterEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public BaseMasterEntity(String masterEntityId,String name){
		setMasterEntityId(masterEntityId);
		setName(name);
    }

	public String getMasterEntityId() {
		return masterEntityId;
	}

	public void setMasterEntityId(String masterEntityId) {
		this.masterEntityId = masterEntityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	

	

	@Override
	public String toString() {
		return "BaseMasterEntity [masterEntityId=" + masterEntityId + ", name="
				+ name + ", longEntityId=" + longEntityId + "]";
	}

	@Override
	public int hashCode() {
		if(masterEntityId!=null)
			return masterEntityId.hashCode();
		else
			return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		if(obj instanceof BaseMasterEntity && ((BaseMasterEntity) obj).getMasterEntityId()!=null){
			return ((BaseMasterEntity) obj).getMasterEntityId().equals(getMasterEntityId());
		}else{
			return false;
		}
	}

	public Long getLongEntityId() {
		return longEntityId;
	}

	public void setLongEntityId(Long longEntityId) {
		this.longEntityId = longEntityId;
	}
	
	public void addParameter(String key,Object value){
		this.parameters.put(key, value);
	}
	
	public Object getParameter(String key){
		return this.parameters.get(key);
	}
	
	public Map<String,Object> getParametersMap() {
		return this.parameters;
	}
	
}
