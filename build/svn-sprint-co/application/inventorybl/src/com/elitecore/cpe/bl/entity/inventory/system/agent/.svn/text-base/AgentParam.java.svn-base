package com.elitecore.cpe.bl.entity.inventory.system.agent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Yash.Kapasi
 *
 */
@Entity
@Table(name="TBLMAGENTCONFIG")
@NamedQueries({ 
	@NamedQuery(name = "AgentParam.findAgentParamById",query ="select o from AgentParam o where o.agentid = :agentid")
})
public class AgentParam implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String agentid;
	private Long armeConcurrencyLimit;
	private Long areConcurrencyLimit;
	private Long parmeChunkSize;
	private Long armeChunkSizeELimit;
	private Long pareChunkSize;
	private Long areChunkSizeELimit;
	private Character overRuleAMEChunkSize;
	private Character overRuleAEChunkSize;
	private String reason;
	
	
	@Id 

    @Column(name="AGENTID")
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	
	 @Column(name="ARMECONCURRENCYLIMIT")
	public Long getArmeConcurrencyLimit() {
		return armeConcurrencyLimit;
	}
	public void setArmeConcurrencyLimit(Long armeConcurrencyLimit) {
		this.armeConcurrencyLimit = armeConcurrencyLimit;
	}
	
	@Column(name="ARECONCURRENCYLIMIT")
	public Long getAreConcurrencyLimit() {
		return areConcurrencyLimit;
	}
	public void setAreConcurrencyLimit(Long areConcurrencyLimit) {
		this.areConcurrencyLimit = areConcurrencyLimit;
	}
	
	@Column(name="PARMECHUNKSIZE")
	public Long getParmeChunkSize() {
		return parmeChunkSize;
	}
	public void setParmeChunkSize(Long parmeChunkSize) {
		this.parmeChunkSize = parmeChunkSize;
	}
	
	
	@Column(name="ARMECHUNKSIZELIMIT")
	public Long getArmeChunkSizeELimit() {
		return armeChunkSizeELimit;
	}
	public void setArmeChunkSizeELimit(Long armeChunkSizeELimit) {
		this.armeChunkSizeELimit = armeChunkSizeELimit;
	}
	
	
	@Column(name="PARECHUNKSIZE")
	public Long getPareChunkSize() {
		return pareChunkSize;
	}
	public void setPareChunkSize(Long pareChunkSize) {
		this.pareChunkSize = pareChunkSize;
	}
	
	@Column(name="ARECHUNKSIZELIMIT")
	public Long getAreChunkSizeELimit() {
		return areChunkSizeELimit;
	}
	public void setAreChunkSizeELimit(Long areChunkSizeELimit) {
		this.areChunkSizeELimit = areChunkSizeELimit;
	}
	
	@Column(name="OVERRULEAMECHUNKSIZE")
	public Character getOverRuleAMEChunkSize() {
		return overRuleAMEChunkSize;
	}
	public void setOverRuleAMEChunkSize(Character overRuleAMEChunkSize) {
		this.overRuleAMEChunkSize = overRuleAMEChunkSize;
	}
	
	
	@Column(name="OVERRULEAECHUNKSIZE")
	public Character getOverRuleAEChunkSize() {
		return overRuleAEChunkSize;
	}
	public void setOverRuleAEChunkSize(Character overRuleAEChunkSize) {
		this.overRuleAEChunkSize = overRuleAEChunkSize;
	}
	
	
	@Column(name="REASON")
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
	

}
