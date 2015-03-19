package com.elitecore.cpe.bl.entity.inventory.system.audit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import oracle.sql.CLOB;

import org.hibernate.type.descriptor.CharacterStream;

@Entity
@Table(name = "TBLTWSAUDIT")
@NamedQueries({ 
	@NamedQuery(name = "WebServiceAuditEntry.searchWsAuditById",query ="select o from WebServiceAuditEntry o where o.wsAuditId = :wsAuditId")
})
@SequenceGenerator( name="TBLTWSAUDIT_SEQ", sequenceName="TBLTWSAUDIT_SEQ",allocationSize=1)
public class WebServiceAuditEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="TBLTWSAUDIT_SEQ")
	@Column(name = "WSAUDITID")
	private Long wsAuditId;
	
	@Column(name = "EVENTTYPEID")
	private Long entityTypeId;
	
	@Column(name = "EVENTNAME")
	private String eventName;
	
	@Column(name = "INPUTPARAM",columnDefinition="CLOB" )
	@Lob
	private String inputParam;
	
	
	@Column(name = "OUTPARAM",columnDefinition="CLOB" )
	@Lob
	private String outputParam;
	
	@Column(name = "EVENTPROCESSSTATUS")
	private String eventProcessStatus;
	
	@Column(name = "RESPONSECODE")
	private String responseCode;
	
	@Column(name = "RESPONSEMESSAGE")
	private String responseMessage;
	
	@Column(name = "PROCESSDATE")
	private Date processDate;

	public Long getWsAuditId() {
		return wsAuditId;
	}

	public void setWsAuditId(Long wsAuditId) {
		this.wsAuditId = wsAuditId;
	}

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

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	@Override
	public String toString() {
		return "WebServiceAuditEntry [wsAuditId=" + wsAuditId
				+ ", entityTypeId=" + entityTypeId + ", eventName=" + eventName
				+ ", inputParam=" + inputParam + ", outputParam=" + outputParam
				+ ", eventProcessStatus=" + eventProcessStatus
				+ ", responseCode=" + responseCode + ", responseMessage="
				+ responseMessage + ", processDate=" + processDate + "]";
	}
	
	
	
	
}
