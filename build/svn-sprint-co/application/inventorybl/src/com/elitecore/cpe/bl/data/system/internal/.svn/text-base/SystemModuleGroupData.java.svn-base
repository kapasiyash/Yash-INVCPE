package com.elitecore.cpe.bl.data.system.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Yash.Kapasi
 *
 */
public class SystemModuleGroupData implements Serializable, Comparable<SystemModuleGroupData> {

	private static final long serialVersionUID = 1L;
	private Long moduleGroupId;
	private Long sequenceNumber;
	private String name;
	private String alias;
	private String description;	
	private List<SystemModuleData> systemModules;	
	private String homeURL;
	private String iconUrl;
	private String activeIconUrl;
	
	public SystemModuleGroupData(Long moduleGroupId, Long sequenceNumber, String name, String alias, String description) {
		super();
		this.moduleGroupId = moduleGroupId;
		this.sequenceNumber = sequenceNumber;
		this.name = name;
		this.alias = alias;
		this.description = description;
	}	
	
	public SystemModuleGroupData(Long moduleGroupId, Long sequenceNumber,
			String name, String alias, String description,
			List<SystemModuleData> systemModules) {
		super();
		this.moduleGroupId = moduleGroupId;
		this.sequenceNumber = sequenceNumber;
		this.name = name;
		this.alias = alias;
		this.description = description;
		this.systemModules = systemModules;
	}



	public SystemModuleGroupData(){
		systemModules = new ArrayList<SystemModuleData>();
	}
	
	public Long getModuleGroupId() {
		return moduleGroupId;
	}
	public void setModuleGroupId(Long moduleGroupId) {
		this.moduleGroupId = moduleGroupId;
	}
	public Long getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public void addSystemModule(SystemModuleData systemModuleData){
		this.systemModules.add(systemModuleData);
	}
	
	public List<SystemModuleData> getSystemModules() {
		return systemModules;
	}

	public void setSystemModules(List<SystemModuleData> systemModules) {
		this.systemModules = systemModules;
	}

	
	public String getHomeURL() {
		return homeURL;
	}

	public void setHomeURL(String homeURL) {
		this.homeURL = homeURL;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getActiveIconUrl() {
		return activeIconUrl;
	}

	public void setActiveIconUrl(String activeIconUrl) {
		this.activeIconUrl = activeIconUrl;
	}

	@Override
	public String toString() {
		return "SystemModuleGroupData [moduleGroupId=" + moduleGroupId
				+ ", sequenceNumber=" + sequenceNumber + ", name=" + name
				+ ", alias=" + alias + ", description=" + description + "systemModules=" + systemModules + "]";
	}

	
	
	public int compareTo(SystemModuleGroupData systemModuleGroupData) {
		if(systemModuleGroupData != null){
			return this.sequenceNumber.compareTo(systemModuleGroupData.getSequenceNumber());
		}
		return -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && sequenceNumber!=null && obj instanceof SystemModuleGroupData){
			return this.sequenceNumber.equals(((SystemModuleGroupData)obj).getSequenceNumber());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (this.sequenceNumber != null)?this.sequenceNumber.hashCode():super.hashCode();
	}	
} 