package com.elitecore.cpe.bl.vo.system.agent;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * @author Yash.Kapasi
 *
 */
public class UpdateAgentScheduleVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long scheduleId;
	private String cronExpression;
	private String reason;
	
	
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	

	
}
