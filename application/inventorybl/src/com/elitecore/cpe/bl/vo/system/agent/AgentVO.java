package com.elitecore.cpe.bl.vo.system.agent;

import java.io.Serializable;
import java.sql.Timestamp;

public class AgentVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String agentId;
	private String name;
	private String description;
	private String createdBy;
	private String modifiedBy;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String armeConcurrencyLimit;
	private String areConcurrencyLimit;
	private String parmeChunkSize;
	private String armeChunkSizeELimit;
	private String pareChunkSize;
	private String areChunkSizeELimit;
	private Character overRuleAMEChunkSize;
	private Character overRuleAEChunkSize;
	private String reason;
	
	public AgentVO() {
		
	}
	
	public AgentVO(String agentId,String name,String description) {
		this.agentId=agentId;
		this.name=name;
		this.description=description;
	}
	
	
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getArmeConcurrencyLimit() {
		return armeConcurrencyLimit;
	}

	public void setArmeConcurrencyLimit(String armeConcurrencyLimit) {
		this.armeConcurrencyLimit = armeConcurrencyLimit;
	}

	public String getAreConcurrencyLimit() {
		return areConcurrencyLimit;
	}

	public void setAreConcurrencyLimit(String areConcurrencyLimit) {
		this.areConcurrencyLimit = areConcurrencyLimit;
	}

	public String getParmeChunkSize() {
		return parmeChunkSize;
	}

	public void setParmeChunkSize(String parmeChunkSize) {
		this.parmeChunkSize = parmeChunkSize;
	}

	public String getArmeChunkSizeELimit() {
		return armeChunkSizeELimit;
	}

	public void setArmeChunkSizeELimit(String armeChunkSizeELimit) {
		this.armeChunkSizeELimit = armeChunkSizeELimit;
	}

	public String getPareChunkSize() {
		return pareChunkSize;
	}

	public void setPareChunkSize(String pareChunkSize) {
		this.pareChunkSize = pareChunkSize;
	}

	public String getAreChunkSizeELimit() {
		return areChunkSizeELimit;
	}

	public void setAreChunkSizeELimit(String areChunkSizeELimit) {
		this.areChunkSizeELimit = areChunkSizeELimit;
	}

	public Character getOverRuleAMEChunkSize() {
		return overRuleAMEChunkSize;
	}

	public void setOverRuleAMEChunkSize(Character overRuleAMEChunkSize) {
		this.overRuleAMEChunkSize = overRuleAMEChunkSize;
	}

	public Character getOverRuleAEChunkSize() {
		return overRuleAEChunkSize;
	}

	public void setOverRuleAEChunkSize(Character overRuleAEChunkSize) {
		this.overRuleAEChunkSize = overRuleAEChunkSize;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	/* 
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AgentVO [agentId=");
		builder.append(agentId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", modifiedBy=");
		builder.append(modifiedBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", modifiedDate=");
		builder.append(modifiedDate);
		builder.append(", armeConcurrencyLimit=");
		builder.append(armeConcurrencyLimit);
		builder.append(", areConcurrencyLimit=");
		builder.append(areConcurrencyLimit);
		builder.append(", parmeChunkSize=");
		builder.append(parmeChunkSize);
		builder.append(", armeChunkSizeELimit=");
		builder.append(armeChunkSizeELimit);
		builder.append(", pareChunkSize=");
		builder.append(pareChunkSize);
		builder.append(", areChunkSizeELimit=");
		builder.append(areChunkSizeELimit);
		builder.append(", overRuleAMEChunkSize=");
		builder.append(overRuleAMEChunkSize);
		builder.append(", overRuleAEChunkSize=");
		builder.append(overRuleAEChunkSize);
		builder.append(", reason=");
		builder.append(reason);
		builder.append("]");
		return builder.toString();
	}
	
	

}
