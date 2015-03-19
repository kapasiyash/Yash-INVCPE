package com.elitecore.cpe.bl.vo.system.agent;

import java.io.Serializable;

/**
 * @author Jekin.Kalariya
 *
 */
public class AgentScheduleParamVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long scheduleId;
	private String paramAlias;
	private String valueField;
	
	
	
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getParamAlias() {
		return paramAlias;
	}
	public void setParamAlias(String paramAlias) {
		this.paramAlias = paramAlias;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	
	
	

}
