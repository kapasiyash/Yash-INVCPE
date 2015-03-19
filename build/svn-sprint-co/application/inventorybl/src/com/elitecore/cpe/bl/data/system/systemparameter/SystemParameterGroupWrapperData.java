package com.elitecore.cpe.bl.data.system.systemparameter;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @author yash.kapasi
 *
 */
public class SystemParameterGroupWrapperData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long systemParameterGroupId;
    private String name;
    private String alias;
    private String description;
    private char isVisible;
    private Set<SystemParameterWrapperData> systemParameterWrapperDatas;
    private Map<Long, SystemParameterWrapperData> systemParameterMap;
    
	public SystemParameterGroupWrapperData() {

	}
	public SystemParameterGroupWrapperData(Long systemParameterGroupId, String name, String alias, String description, char isVisible, Set<SystemParameterWrapperData> systemParameterWrapperDatas) {
		this.systemParameterGroupId = systemParameterGroupId;
		this.name = name;
		this.alias = alias;
		this.description = description;
		this.isVisible = isVisible;
		this.systemParameterWrapperDatas = systemParameterWrapperDatas;
	}
	public Long getSystemParameterGroupId() {
		return systemParameterGroupId;
	}
	public void setSystemParameterGroupId(Long systemParameterGroupId) {
		this.systemParameterGroupId = systemParameterGroupId;
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
	public char getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(char isVisible) {
		this.isVisible = isVisible;
	}
	public Set<SystemParameterWrapperData> getSystemParameterWrapperDatas() {
		return systemParameterWrapperDatas;
	}
	public void setSystemParameterWrapperDatas(Set<SystemParameterWrapperData> systemParameterWrapperDatas) {
		this.systemParameterWrapperDatas = systemParameterWrapperDatas;
	}
	@Override
	public String toString() {
		return "SystemParameterGroupWrapperData [systemParameterGroupId="
				+ systemParameterGroupId + ", name=" + name + ", alias="
				+ alias + ", description=" + description + ", isVisible="
				+ isVisible + "]";
	}
	public Map<Long, SystemParameterWrapperData> getSystemParameterMap() {
		return systemParameterMap;
	}
	public void setSystemParameterMap(Map<Long, SystemParameterWrapperData> systemParameterMap) {
		this.systemParameterMap = systemParameterMap;
	}
    
}
