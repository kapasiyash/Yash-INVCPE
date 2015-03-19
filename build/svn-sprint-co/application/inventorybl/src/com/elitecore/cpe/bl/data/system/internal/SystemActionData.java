package com.elitecore.cpe.bl.data.system.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yash.kapasi
 *
 */
public class SystemActionData  implements Serializable, Comparable<SystemActionData> {

	private static final long serialVersionUID = 1L;
	private Long actionId;	
	private boolean isParentAction;	
	private Long sequencenumber;
	private String name;
	private String actionAlias;		
	private List<SystemActionData> childActions;	
	private String zulPageUrl;	
	private Character enableAudit;
	private Character isAuditable;
	 private Character enableVisible;
	
	public SystemActionData(Long actionId, boolean isParentAction,
			Long sequencenumber, String name, String actionAlias,
			List<SystemActionData> childActions, String zulPageUrl) {
		super();
		this.actionId = actionId;
		this.isParentAction = isParentAction;
		this.sequencenumber = sequencenumber;
		this.name = name;
		this.actionAlias = actionAlias;
		this.childActions = childActions;
		this.zulPageUrl = zulPageUrl;
	}

	public SystemActionData(){
		childActions = new ArrayList<SystemActionData>();
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
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

	public List<SystemActionData> getChildActions() {
		return childActions;
	}

	public void setChildActions(List<SystemActionData> childActions) {
		this.childActions = childActions;
	}

	public void addChildAction(SystemActionData systemActionData){
		this.childActions.add(systemActionData);
	}
	
	public String getZulPageUrl() {
		return zulPageUrl;
	}

	public void setZulPageUrl(String zulPageUrl) {
		this.zulPageUrl = zulPageUrl;
	}

	public Character getIsAuditable() {
		return isAuditable;
	}

	public void setIsAuditable(Character isAuditable) {
		this.isAuditable = isAuditable;
	}

	
	
	public Character getEnableVisible() {
		return enableVisible;
	}

	public void setEnableVisible(Character enableVisible) {
		this.enableVisible = enableVisible;
	}

	@Override
	public String toString() {
		return "SystemActionData [actionId=" + actionId + ", sequencenumber="
				+ sequencenumber + ", name=" + name + ", actionAlias="
				+ actionAlias + ", zulPageUrl=" + zulPageUrl + "]";
	}
	
	public int compareTo(SystemActionData systemActionData) {
		if(systemActionData != null){
			return this.sequencenumber.compareTo(systemActionData.getSequencenumber());
		}
		return -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && sequencenumber!=null && obj instanceof SystemActionData){
			return this.sequencenumber.equals(((SystemActionData)obj).getSequencenumber());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (this.sequencenumber != null)?this.sequencenumber.hashCode():super.hashCode();
	}

	public Character getEnableAudit() {
		return enableAudit;
	}

	public void setEnableAudit(Character enableAudit) {
		this.enableAudit = enableAudit;
	}

	
	
	
	
}