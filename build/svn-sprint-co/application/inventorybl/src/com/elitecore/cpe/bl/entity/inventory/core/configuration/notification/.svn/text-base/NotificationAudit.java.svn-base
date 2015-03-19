package com.elitecore.cpe.bl.entity.inventory.core.configuration.notification;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TBLTNOTIFICATIONAUDIT"
)
public class NotificationAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private Long notificationId;
	private String notificationType;
	private String to;
	private String cc;
	private String bcc;
	private String mobileNum;
	private String subject;
	private byte[] content;
	private Timestamp createDate;
	private Long documentCategoryId;
	private String deliveryStatus;
	private String destincationSystem;
	private String responseCode;
	private String responseMessage;
	
	
	@SequenceGenerator(name="generator", sequenceName="SEQ_NOTIFICATIONAUDIT")
    @Id @GeneratedValue(strategy=SEQUENCE, generator="generator")
   @Column(name="NOTIFICATIONAUDITID")
	public Long getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}
	
	
	@Column(name="NOTIFICATIONTYPE")
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	
	
	@Column(name="TOEMAIL")
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	
	@Column(name="CCEMAIL")
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	
	
	@Column(name="BCCEMAIL")
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	
	
	@Column(name="MOBNUM")
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	
	
	
	@Column(name="SUBJECT")
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
	@Column(name="CONTENT")
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	
	@Column(name="CREATEDATE")
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
	@Column(name="DOCUMENTCATEGORYID")
	public Long getDocumentCategoryId() {
		return documentCategoryId;
	}
	public void setDocumentCategoryId(Long documentCategoryId) {
		this.documentCategoryId = documentCategoryId;
	}
	
	
	@Column(name="DELIVERYSTATUS")
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	
	
	@Column(name="DELIVERYSYSTEM")
	public String getDestincationSystem() {
		return destincationSystem;
	}
	public void setDestincationSystem(String destincationSystem) {
		this.destincationSystem = destincationSystem;
	}
	
	
	@Column(name="RESPONSECODE")
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	
	@Column(name="RESPONSEMESSAGE")
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	
	@Override
	public String toString() {
		return "NotificationAudit [notificationId=" + notificationId
				+ ", notificationType=" + notificationType + ", to=" + to
				+ ", cc=" + cc + ", bcc=" + bcc + ", mobileNum=" + mobileNum
				+ ", subject=" + subject + ", content="
				+ Arrays.toString(content) + ", createDate=" + createDate
				+ ", documentCategoryId=" + documentCategoryId
				+ ", deliveryStatus=" + deliveryStatus
				+ ", destincationSystem=" + destincationSystem
				+ ", responseCode=" + responseCode + ", responseMessage="
				+ responseMessage + "]";
	}
	
	
	
	
	

}
