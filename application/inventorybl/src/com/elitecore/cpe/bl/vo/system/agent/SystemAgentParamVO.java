package com.elitecore.cpe.bl.vo.system.agent;

import java.io.Serializable;

public class SystemAgentParamVO implements Serializable {


	private static final long serialVersionUID = 1L;
	private String agentId;
	private Long armeConcurrencyLimit;
	private Long areConcurrencyLimit;
	private Long prearmeChunkSize;
	private Long preareChunkSize;
	private Long armeChunkSizeELimit;
	private Long areChunkSizeELimit;
	private boolean overRuleAMEChunkSize;
	private boolean overRuleAEChunkSize;
	private String reason;
	
	
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public Long getArmeConcurrencyLimit() {
		return armeConcurrencyLimit;
	}
	public void setArmeConcurrencyLimit(Long armeConcurrencyLimit) {
		this.armeConcurrencyLimit = armeConcurrencyLimit;
	}
	public Long getAreConcurrencyLimit() {
		return areConcurrencyLimit;
	}
	public void setAreConcurrencyLimit(Long areConcurrencyLimit) {
		this.areConcurrencyLimit = areConcurrencyLimit;
	}
	public Long getPrearmeChunkSize() {
		return prearmeChunkSize;
	}
	public void setPrearmeChunkSize(Long prearmeChunkSize) {
		this.prearmeChunkSize = prearmeChunkSize;
	}
	public Long getPreareChunkSize() {
		return preareChunkSize;
	}
	public void setPreareChunkSize(Long preareChunkSize) {
		this.preareChunkSize = preareChunkSize;
	}
	public Long getArmeChunkSizeELimit() {
		return armeChunkSizeELimit;
	}
	public void setArmeChunkSizeELimit(Long armeChunkSizeELimit) {
		this.armeChunkSizeELimit = armeChunkSizeELimit;
	}
	public Long getAreChunkSizeELimit() {
		return areChunkSizeELimit;
	}
	public void setAreChunkSizeELimit(Long areChunkSizeELimit) {
		this.areChunkSizeELimit = areChunkSizeELimit;
	}

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public boolean isOverRuleAMEChunkSize() {
		return overRuleAMEChunkSize;
	}
	public void setOverRuleAMEChunkSize(boolean overRuleAMEChunkSize) {
		this.overRuleAMEChunkSize = overRuleAMEChunkSize;
	}
	public boolean isOverRuleAEChunkSize() {
		return overRuleAEChunkSize;
	}
	public void setOverRuleAEChunkSize(boolean overRuleAEChunkSize) {
		this.overRuleAEChunkSize = overRuleAEChunkSize;
	}
	
	
	
}
