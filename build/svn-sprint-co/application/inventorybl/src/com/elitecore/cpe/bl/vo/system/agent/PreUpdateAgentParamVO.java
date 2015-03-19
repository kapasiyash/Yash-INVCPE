package com.elitecore.cpe.bl.vo.system.agent;

import java.io.Serializable;
import java.util.Map;

import com.elitecore.cpe.bl.vo.core.expr.ConstraintExpressionVO;


public class PreUpdateAgentParamVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long armeConcurrencyLimit;
	private Long areConcurrencyLimit;
	private Long parmeChunkSize;
	private Long armeChunkSizeELimit;
	private Long pareChunkSize;
	private Long areChunkSizeELimit;
	private Character overRuleAMEChunkSize;
	private Character overRuleAEChunkSize;
	private String reason;
	
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
	public Long getParmeChunkSize() {
		return parmeChunkSize;
	}
	public void setParmeChunkSize(Long parmeChunkSize) {
		this.parmeChunkSize = parmeChunkSize;
	}
	public Long getArmeChunkSizeELimit() {
		return armeChunkSizeELimit;
	}
	public void setArmeChunkSizeELimit(Long armeChunkSizeELimit) {
		this.armeChunkSizeELimit = armeChunkSizeELimit;
	}
	public Long getPareChunkSize() {
		return pareChunkSize;
	}
	public void setPareChunkSize(Long pareChunkSize) {
		this.pareChunkSize = pareChunkSize;
	}
	public Long getAreChunkSizeELimit() {
		return areChunkSizeELimit;
	}
	public void setAreChunkSizeELimit(Long areChunkSizeELimit) {
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
    
}
