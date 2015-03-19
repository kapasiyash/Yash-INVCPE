package com.elitecore.cpe.bl.data.system.systemparameter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @author yash.kapasi
 *
 */
public class SystemParameterWrapperData implements Serializable, Comparable<SystemParameterWrapperData> {

	private static final long serialVersionUID = 1L;

	private Long systemParameterId;
    private String customFieldTypeId;
    private String name;
    private String alias;
    private String value;    
    private String description;
    private char allowOverride;
    private Timestamp lastmodidate;
	private Map <String , String> comboBoxMap;
    private char isVisible;
    private String regEx;
    
	public SystemParameterWrapperData() {

	}
	public SystemParameterWrapperData(Long systemParameterId, String customFieldTypeId, String name, String alias, String value, String description, char allowOverride, Timestamp lastmodidate,
			char isVisible) {
		this.systemParameterId = systemParameterId;
		this.customFieldTypeId = customFieldTypeId;
		this.name = name;
		this.alias = alias;
		this.value = value;
		this.description = description;
		this.allowOverride = allowOverride;
		this.lastmodidate = lastmodidate;
		this.isVisible = isVisible;
	}
	public Long getSystemParameterId() {
		return systemParameterId;
	}
	public void setSystemParameterId(Long systemParameterId) {
		this.systemParameterId = systemParameterId;
	}
	public String getCustomFieldTypeId() {
		return customFieldTypeId;
	}
	public void setCustomFieldTypeId(String customFieldTypeId) {
		this.customFieldTypeId = customFieldTypeId;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public char getAllowOverride() {
		return allowOverride;
	}
	public void setAllowOverride(char allowOverride) {
		this.allowOverride = allowOverride;
	}
	public Timestamp getLastmodidate() {
		return lastmodidate;
	}
	public void setLastmodidate(Timestamp lastmodidate) {
		this.lastmodidate = lastmodidate;
	}
	public char getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(char isVisible) {
		this.isVisible = isVisible;
	}
	
	
	@Override
	public String toString() {
		return "SystemParameterWrapperData [systemParameterId="
				+ systemParameterId + ", customFieldTypeId="
				+ customFieldTypeId + ", name=" + name + ", alias=" + alias
				+ ", value=" + value + ", description=" + description
				+ ", allowOverride=" + allowOverride + ", lastmodidate="
				+ lastmodidate + ", isVisible=" + isVisible + "]";
	}
	@Override
	public int hashCode() {
		return (this.systemParameterId == null)?super.hashCode():this.systemParameterId.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemParameterWrapperData other = (SystemParameterWrapperData) obj;
		if (systemParameterId == null) {
			if (other.systemParameterId != null)
				return false;
		} else if (!systemParameterId.equals(other.systemParameterId))
			return false;
		return true;
	}
	@Override
	public int compareTo(SystemParameterWrapperData o) {
		if(getSystemParameterId() != null && o != null) {
			return getSystemParameterId().compareTo(o.getSystemParameterId());
		}
		return -1;
	}
	public Map<String, String> getComboBoxMap() {
		return comboBoxMap;
	}
	public void setComboBoxMap(Map<String, String> comboBoxMap) {
		this.comboBoxMap = comboBoxMap;
	}
	public String getRegEx() {
		return regEx;
	}
	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}

	
	 
}
