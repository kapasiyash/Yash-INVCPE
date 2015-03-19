package com.elitecore.cpe.bl.entity.inventory.system.audit;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemAction;

@Entity
@Table(name="TBLISYSTEMAUDIT")
@NamedQueries({ 
	@NamedQuery(name = "SystemAudit.searchSystemAuditByDate",query ="select o from SystemAudit o where o.auditdate between :auditFromDate and :auditToDate"),
	@NamedQuery(name = "SystemAudit.searchSystemAuditById",query ="select o from SystemAudit o where o.systemauditid = :systemauditid"),
	@NamedQuery(name = "SystemAudit.searchAllAuditData",query ="select o from SystemAudit o")
})
@SequenceGenerator(name = "TBLISYSTEMAUDIT_SEQ", sequenceName = "TBLISYSTEMAUDIT_SEQ", allocationSize = 1)
public class SystemAudit implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long systemauditid;
	private String systemAuditTypeId;
//     private User user;
//     private BSSUser bssUser;
     private String userId;
     private Long systemActionId;
     private SystemAction systemAction;
     private Timestamp auditdate;
     private String reason;
     private String remarks;
     private String ipaddress;
     private Set<AuditEntry> auditEntries = new HashSet<AuditEntry>(0);
     
     public SystemAudit() {
     }

 	
     public SystemAudit(Long systemauditid,  SystemAction systemAction, Timestamp auditdate, String reason) {
         this.systemauditid = systemauditid;
//         this.user = user;
         this.systemAction = systemAction;
         this.auditdate = auditdate;
         this.reason = reason;
     }
     public SystemAudit(Long systemauditid,  SystemAction systemAction, Timestamp auditdate, String reason, String remarks, String ipaddress) {
        this.systemauditid = systemauditid;
//        this.user = user;
        this.systemAction = systemAction;
        this.auditdate = auditdate;
        this.reason = reason;
        this.remarks = remarks;
        this.ipaddress = ipaddress;
     }
    
      @Id 
     @Column(name="SYSTEMAUDITID")
      @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLISYSTEMAUDIT_SEQ")
     public Long getSystemauditid() {
         return this.systemauditid;
     }
     
     public void setSystemauditid(Long systemauditid) {
         this.systemauditid = systemauditid;
     }
     
     @Column(name="USERID")
     public String getUserId() {
 		return userId;
 	}

 	public void setUserId(String userId) {
 		this.userId = userId;
 	}
     
   /*  @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="USERID", insertable = false, updatable = false)
     public User getUser() {
         return this.user;
     }
     
 	public void setUser(User user) {
         this.user = user;
     }*/

 	
 	
 	/*@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="USERID", insertable = false, updatable = false)
 	public BSSUser getBssUser() {
		return bssUser;
	}


	public void setBssUser(BSSUser bssUser) {
		this.bssUser = bssUser;
	}*/


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
         return this.systemAction;
     }
     
 	public void setSystemAction(SystemAction systemAction) {
         this.systemAction = systemAction;
     }

     @Column(name="AUDITDATE")
     public Timestamp getAuditdate() {
         return this.auditdate;
     }
     
     public void setAuditdate(Timestamp auditdate) {
         this.auditdate = auditdate;
     }

     
     @Column(name="REASON")
     public String getReason() {
         return this.reason;
     }
     
     public void setReason(String reason) {
         this.reason = reason;
     }

     
     @Column(name="REMARKS")
     public String getRemarks() {
         return this.remarks;
     }
     
     public void setRemarks(String remarks) {
         this.remarks = remarks;
     }

     
     @Column(name="IPADDRESS")
     public String getIpaddress() {
         return this.ipaddress;
     }
     
     public void setIpaddress(String ipaddress) {
         this.ipaddress = ipaddress;
     }


 	@Column(name="AUDITTYPEID")
 	public String getSystemAuditTypeId() {
 		return systemAuditTypeId;
 	}


 	public void setSystemAuditTypeId(String systemAuditTypeId) {
 		this.systemAuditTypeId = systemAuditTypeId;
 	}
 	
 	 @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="systemAudit")
  	public Set<AuditEntry> getAuditEntries() {
  		return auditEntries;
  	}


  	public void setAuditEntries(Set<AuditEntry> auditEntries) {
  		this.auditEntries = auditEntries;
  	}

}
