package com.elitecore.cpe.bl.entity.inventory.bss.acl;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.elitecore.cpe.bl.entity.inventory.bss.user.BSSUser;
import com.elitecore.cpe.bl.entity.inventory.system.internal.SystemAction;


@Entity
@Table(name="TBLMGROUP")
public class BSSACLGroup implements java.io.Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupid;
    private BSSUser userByCreatedbystaffid;
    private BSSUser userByLastmodifiedbystaffid;
    private String name;
    private String groupemailid;
    private String description;
    private Character systemgenerated;
    private Timestamp statuschangedate;
    private String commonstatusid;
    private Timestamp createdate;
    private Timestamp lastmodifieddate;
    private String attributeaccessgroupid;
	private Set<SystemAction> permittedActions;
//    private Set<BSSACLActionRel> permittedActions = new HashSet<BSSACLActionRel>(0); 

   public BSSACLGroup() {
   }

	
   public BSSACLGroup(String groupid) {
       this.groupid = groupid;
   }
   public BSSACLGroup(String groupid, BSSUser userByCreatedbystaffid, BSSUser userByLastmodifiedbystaffid, String name, String groupemailid, String description, Character systemgenerated, Timestamp statuschangedate, String commonstatusid, Timestamp createdate, Timestamp lastmodifieddate, String attributeaccessgroupid) {
      this.groupid = groupid;
      this.userByCreatedbystaffid = userByCreatedbystaffid;
      this.userByLastmodifiedbystaffid = userByLastmodifiedbystaffid;
      this.name = name;
      this.groupemailid = groupemailid;
      this.description = description;
      this.systemgenerated = systemgenerated;
      this.statuschangedate = statuschangedate;
      this.commonstatusid = commonstatusid;
      this.createdate = createdate;
      this.lastmodifieddate = lastmodifieddate;
      this.attributeaccessgroupid = attributeaccessgroupid;
   }
  
   @Id 
   @Column(name="GROUPID", nullable=false, length=5)
   public String getGroupid() {
       return this.groupid;
   }
   
   public void setGroupid(String groupid) {
       this.groupid = groupid;
   }

   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="CREATEDBYSTAFFID")
   public BSSUser getUserByCreatedbystaffid() {
       return this.userByCreatedbystaffid;
   }
   
   public void setUserByCreatedbystaffid(BSSUser userByCreatedbystaffid) {
       this.userByCreatedbystaffid = userByCreatedbystaffid;
   }

   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="LASTMODIFIEDBYSTAFFID")
   public BSSUser getUserByLastmodifiedbystaffid() {
       return this.userByLastmodifiedbystaffid;
   }
   
   public void setUserByLastmodifiedbystaffid(BSSUser userByLastmodifiedbystaffid) {
       this.userByLastmodifiedbystaffid = userByLastmodifiedbystaffid;
   }

   
   @Column(name="NAME", unique=true, length=50)
   public String getName() {
       return this.name;
   }
   
   public void setName(String name) {
       this.name = name;
   }

   
   @Column(name="GROUPEMAILID", length=60)
   public String getGroupemailid() {
       return this.groupemailid;
   }
   
   public void setGroupemailid(String groupemailid) {
       this.groupemailid = groupemailid;
   }

   
   @Column(name="DESCRIPTION")
   public String getDescription() {
       return this.description;
   }
   
   public void setDescription(String description) {
       this.description = description;
   }

   
   @Column(name="SYSTEMGENERATED", length=1)
   public Character getSystemgenerated() {
       return this.systemgenerated;
   }
   
   public void setSystemgenerated(Character systemgenerated) {
       this.systemgenerated = systemgenerated;
   }

   @Column(name="STATUSCHANGEDATE", length=7)
   public Timestamp getStatuschangedate() {
       return this.statuschangedate;
   }
   
   public void setStatuschangedate(Timestamp statuschangedate) {
       this.statuschangedate = statuschangedate;
   }

   
   @Column(name="COMMONSTATUSID", length=5)
   public String getCommonstatusid() {
       return this.commonstatusid;
   }
   
   public void setCommonstatusid(String commonstatusid) {
       this.commonstatusid = commonstatusid;
   }

   @Column(name="CREATEDATE", length=7)
   public Timestamp getCreatedate() {
       return this.createdate;
   }
   
   public void setCreatedate(Timestamp createdate) {
       this.createdate = createdate;
   }

   @Column(name="LASTMODIFIEDDATE", length=7)
   public Timestamp getLastmodifieddate() {
       return this.lastmodifieddate;
   }
   
   public void setLastmodifieddate(Timestamp lastmodifieddate) {
       this.lastmodifieddate = lastmodifieddate;
   }

   
   @Column(name="ATTRIBUTEACCESSGROUPID")
   public String getAttributeaccessgroupid() {
       return this.attributeaccessgroupid;
   }
   
   public void setAttributeaccessgroupid(String attributeaccessgroupid) {
       this.attributeaccessgroupid = attributeaccessgroupid;
   }
   /*
   	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
   	@JoinColumn(name = "GROUPID", referencedColumnName="ACLGROUPID" ,insertable = false, updatable = false)
	public Set<BSSACLActionRel> getPermittedActions() {
		return permittedActions;
	}
	public void setPermittedActions(Set<BSSACLActionRel> permittedActions) {
		this.permittedActions = permittedActions;
	}
*/
	
    @ManyToMany(
      		 targetEntity=SystemAction.class,
      		 cascade={CascadeType.PERSIST, CascadeType.MERGE},
      		 fetch = FetchType.LAZY
      		 )
      		 @JoinTable(
      		 name="VW_ACL_ACTION_REL",
      		 joinColumns = @JoinColumn( name="ACLGROUPID",referencedColumnName="GROUPID"),
      		 inverseJoinColumns = @JoinColumn( name="ACTIONALIAS", referencedColumnName="ACTIONALIAS")
      		 )
   	public Set<SystemAction> getPermittedActions() {
   		return permittedActions;
   	}


   	public void setPermittedActions(Set<SystemAction> permittedActions) {
   		this.permittedActions = permittedActions;
   	}
   	

}

