package com.elitecore.cpe.bl.data.system.audit;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;

public class ConfigureAuditWrapperData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private boolean isAuditable;
	private Long actionId;	
	private boolean isParentAction;	
	private Long sequencenumber;
	private String name;
	private String actionAlias;		
	private List<SystemActionData> childActions;	
	private String zulPageUrl;	
	private Character enableAudit;
	private List<Long> auditableActions;
	Set<SystemActionData> auditableActionsList;
	
	
	
	
	public Set<SystemActionData> getAuditableActionsList() {
		return auditableActionsList;
	}
	public void setAuditableActionsList(Set<SystemActionData> auditableActionsList) {
		this.auditableActionsList = auditableActionsList;
	}
	public List<Long> getAuditableActions() {
		return auditableActions;
	}
	public void setAuditableActions(List<Long> auditableActions) {
		this.auditableActions = auditableActions;
	}
	public boolean isParentAction() {
		return isParentAction;
	}
	public void setParentAction(boolean isParentAction) {
		this.isParentAction = isParentAction;
	}
	public Long getSequencenumber() {
		return sequencenumber;
	}
	public void setSequencenumber(Long sequencenumber) {
		this.sequencenumber = sequencenumber;
	}
	public List<SystemActionData> getChildActions() {
		return childActions;
	}
	public void setChildActions(List<SystemActionData> childActions) {
		this.childActions = childActions;
	}
	public String getZulPageUrl() {
		return zulPageUrl;
	}
	public void setZulPageUrl(String zulPageUrl) {
		this.zulPageUrl = zulPageUrl;
	}
	public Long getActionId() {
		return actionId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getActionAlias() {
		return actionAlias;
	}
	public void setActionAlias(String actionAlias) {
		this.actionAlias = actionAlias;
	}
	public boolean isAuditable() {
		return isAuditable;
	}
	public void setAuditable(boolean isAuditable) {
		this.isAuditable = isAuditable;
	}
	public Character getEnableAudit() {
		return enableAudit;
	}
	public void setEnableAudit(Character enableAudit) {
		this.enableAudit = enableAudit;
	}
	
	
	
}
