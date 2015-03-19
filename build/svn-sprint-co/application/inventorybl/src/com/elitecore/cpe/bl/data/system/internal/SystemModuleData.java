package com.elitecore.cpe.bl.data.system.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yash.kapasi
 *
 */
public class SystemModuleData implements Serializable, Comparable<SystemModuleData> {

	private static final long serialVersionUID = 1L;
	private Long moduleId;
	private String name;
	private Long sequenceNumber;
	private String alias;
	private String description;	
	private String panel;
	private List<SystemActionData> systemActions;
	
	
	
	public SystemModuleData(Long moduleId, String name, Long sequenceNumber,
			String alias, String description) {
		super();
		this.moduleId = moduleId;
		this.name = name;
		this.sequenceNumber = sequenceNumber;
		this.alias = alias;
		this.description = description;
	}	
	
	public SystemModuleData(Long moduleId, String name, Long sequenceNumber,
			String alias, String description,
			List<SystemActionData> systemActions) {
		super();
		this.moduleId = moduleId;
		this.name = name;
		this.sequenceNumber = sequenceNumber;
		this.alias = alias;
		this.description = description;
		this.systemActions = systemActions;
	}



	public String getPanel() {
		return panel;
	}

	public void setPanel(String panel) {
		this.panel = panel;
	}

	public SystemModuleData(){
		systemActions = new ArrayList<SystemActionData>();
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SystemActionData> getSystemActions() {
		return systemActions;
	}

	public void setSystemActions(List<SystemActionData> systemActions) {
		this.systemActions = systemActions;
	}
	
	public void addSystemAction(SystemActionData systemActionData){
		if(this.systemActions==null){
			this.systemActions = new ArrayList<SystemActionData>();
		}
		this.systemActions.add(systemActionData);
	}

	@Override
	public String toString() {
		return "SystemModuleData [moduleId=" + moduleId + ", name=" + name
				+ ", sequenceNumber=" + sequenceNumber + ", alias=" + alias
				+ ", description=" + description + "systemActions=" + systemActions +"]";
	}
		
	public int compareTo(SystemModuleData systemModuleData) {
		if(systemModuleData != null){
			return this.sequenceNumber.compareTo(systemModuleData.getSequenceNumber());
		}
		return -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && sequenceNumber!=null && obj instanceof SystemModuleData){
			return this.sequenceNumber.equals(((SystemModuleData)obj).getSequenceNumber());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (this.sequenceNumber != null)?this.sequenceNumber.hashCode():super.hashCode();
	}
	
}