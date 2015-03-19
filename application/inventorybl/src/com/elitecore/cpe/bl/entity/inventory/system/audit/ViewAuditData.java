package com.elitecore.cpe.bl.entity.inventory.system.audit;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemAction;


@Entity
@Table(name="VIEWAUDITDATA")
@NamedQueries({ 
	@NamedQuery(name = "ViewAuditData.searchSystemAuditByDate",query ="select o from ViewAuditData o where o.auditdate between :auditFromDate and :auditToDate"),
	@NamedQuery(name = "ViewAuditData.searchSystemAuditById",query ="select o from ViewAuditData o where o.systemauditid = :systemauditid"),
	@NamedQuery(name = "ViewAuditData.searchAllAuditData",query ="select o from ViewAuditData o")
})
public class ViewAuditData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	 private Long systemauditid;
     private Long systemActionId;
     private SystemAction systemAction;
     private Timestamp auditdate;
     private String reason;
     private String remarks;
     private String userName;
     private String actionAlias;
     private String ipaddress;
     private String auditType;
     private SystemAudit systemAudit;
     
     @Id 
     @Column(name="SYSTEMAUDITID")
    public Long getSystemauditid() {
		return systemauditid;
	}
	public void setSystemauditid(Long systemauditid) {
		this.systemauditid = systemauditid;
	}
	
	
	@Column(name="ACTIONID")
	public Long getSystemActionId() {
		return systemActionId;
	}
	public void setSystemActionId(Long systemActionId) {
		this.systemActionId = systemActionId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ACTIONID", insertable = false, updatable = false)
	public SystemAction getSystemAction() {
		return systemAction;
	}
	public void setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
	}
	
	
	@Column(name="AUDITDATE")
	public Timestamp getAuditdate() {
		return auditdate;
	}
	public void setAuditdate(Timestamp auditdate) {
		this.auditdate = auditdate;
	}
	
	
	@Column(name="REASON")
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Column(name="USERNAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	@Column(name="ACTIONALIAS")
	public String getActionAlias() {
		return actionAlias;
	}
	public void setActionAlias(String actionAlias) {
		this.actionAlias = actionAlias;
	}
	
	@Column(name="IPADDRESS")
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	
	
	@Column(name="AUDITTYPE")
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SYSTEMAUDITID", insertable=false,updatable=false)
	public SystemAudit getSystemAudit() {
		return systemAudit;
	}
	public void setSystemAudit(SystemAudit systemAudit) {
		this.systemAudit = systemAudit;
	}
     
     
}
