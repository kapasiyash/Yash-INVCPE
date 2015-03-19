package com.elitecore.cpe.bl.data.system.audit;

import java.io.Serializable;
import java.util.Date;

public class WebServiceAuditData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long entityTypeId;
	private String eventName;
	
	private String inputParam;
	private String outputParam;
	
	private String eventProcessStatus;
	private String responseCode;
	private String responseMessage;
	private String actionAlias;
	
	
	public Long getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getInputParam() {
		return inputParam;
	}
	public void setInputParam(String inputParam) {
		this.inputParam = inputParam;
	}
	public String getOutputParam() {
		return outputParam;
	}
	public void setOutputParam(String outputParam) {
		this.outputParam = outputParam;
	}
	public String getEventProcessStatus() {
		return eventProcessStatus;
	}
	public void setEventProcessStatus(String eventProcessStatus) {
		this.eventProcessStatus = eventProcessStatus;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getActionAlias() {
		return actionAlias;
	}
	public void setActionAlias(String actionAlias) {
		this.actionAlias = actionAlias;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WebServiceAuditData [entityTypeId=");
		builder.append(entityTypeId);
		builder.append(", eventName=");
		builder.append(eventName);
		builder.append(", inputParam=");
		builder.append(inputParam);
		builder.append(", outputParam=");
		builder.append(outputParam);
		builder.append(", eventProcessStatus=");
		builder.append(eventProcessStatus);
		builder.append(", responseCode=");
		builder.append(responseCode);
		builder.append(", responseMessage=");
		builder.append(responseMessage);
		builder.append(", actionAlias=");
		builder.append(actionAlias);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	

}
