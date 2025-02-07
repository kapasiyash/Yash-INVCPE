package com.elitecore.cpe.bl.entity.inventory.bss.user;

import java.sql.Timestamp;
import java.util.HashSet;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.elitecore.cpe.bl.entity.inventory.bss.acl.BSSACLGroup;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "BSSUser.findByUsername", query = "select o from BSSUser o  where o.username = :username"),
	@NamedQuery(name = "BSSUser.findByUserId", query = "select o from BSSUser o  where o.userId = :userId"),
	@NamedQuery(name = "BSSUser.findAll", query = "select o from BSSUser o where o.systemgenerated='N' and o.userId not in ('STFAGN1','STFCRM1','STFCRM2','STFWS1')")
})

@Table(name = "TBLMSTAFF")
public class BSSUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private BSSUser userByCreatedbystaffid;
	private BSSUser userByLastmodifiedbystaffid;
	private String name;
	private String username;
	private Timestamp createdate;
	private String password;
	private Timestamp birthdate;
	private Timestamp lastmodifieddate;
	private String encryptionType;
	private String emailaddress;
	private String address1;
	private String address2;
	private String zip;
	private String cityid;
	private String stateid;
	private String countryid;
	private String phone;
	private Timestamp lastlogintime;
	private String mobile;
	private Timestamp statuschangedate;
	private String commonstatusid;
	private String systemuserid;
	private Character systemgenerated;
	private String employeecode;
	private String externalreferenceid;
	private Set<BSSACLGroup> aclGroups = new HashSet<BSSACLGroup>(0);
	/*
	 * private Set<ACLGroup> ACLGroupsForCreatedbystaffid = new
	 * HashSet<ACLGroup>(0);
	 */
	private Set<BSSUser> usersForLastmodifiedbystaffid = new HashSet<BSSUser>(0);

	/*
	 * private Set<ACLGroup> ACLGroupsForLastmodifiedbystaffid = new
	 * HashSet<ACLGroup>(0);
	 */

	public BSSUser() {
	}

	public BSSUser(String staffid) {
		this.userId = staffid;
	}

	@Id
	@Column(name = "STAFFID", nullable = false, length = 7)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATEDBYSTAFFID")
	public BSSUser getUserByCreatedbystaffid() {
		return this.userByCreatedbystaffid;
	}

	public void setUserByCreatedbystaffid(BSSUser userByCreatedbystaffid) {
		this.userByCreatedbystaffid = userByCreatedbystaffid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LASTMODIFIEDBYSTAFFID")
	public BSSUser getUserByLastmodifiedbystaffid() {
		return this.userByLastmodifiedbystaffid;
	}

	public void setUserByLastmodifiedbystaffid(BSSUser userByLastmodifiedbystaffid) {
		this.userByLastmodifiedbystaffid = userByLastmodifiedbystaffid;
	}

	@Column(name = "NAME", length = 60)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "USERNAME", length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "CREATEDATE", length = 7)
	public Timestamp getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	@Column(name = "PASSWORD", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "BIRTHDATE", length = 7)
	public Timestamp getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Timestamp birthdate) {
		this.birthdate = birthdate;
	}

	@Column(name = "LASTMODIFIEDDATE", length = 7)
	public Timestamp getLastmodifieddate() {
		return this.lastmodifieddate;
	}

	public void setLastmodifieddate(Timestamp lastmodifieddate) {
		this.lastmodifieddate = lastmodifieddate;
	}

	@Column(name = "ENCRYPTIONTYPEID")
	public String getEncryptionType() {
		return this.encryptionType;
	}

	public void setEncryptionType(String encryptionType) {
		this.encryptionType = encryptionType;
	}

	@Column(name = "EMAILADDRESS")
	public String getEmailaddress() {
		return this.emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	@Column(name = "ADDRESS1", length = 60)
	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name = "ADDRESS2", length = 60)
	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(name = "ZIP", length = 30)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "CITYID", length = 7)
	public String getCityid() {
		return this.cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	@Column(name = "STATEID", length = 7)
	public String getStateid() {
		return this.stateid;
	}

	public void setStateid(String stateid) {
		this.stateid = stateid;
	}

	@Column(name = "COUNTRYID", length = 7)
	public String getCountryid() {
		return this.countryid;
	}

	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "LASTLOGINTIME", length = 7)
	public Timestamp getLastlogintime() {
		return this.lastlogintime;
	}

	public void setLastlogintime(Timestamp lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	@Column(name = "MOBILE", length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "STATUSCHANGEDATE", length = 7)
	public Timestamp getStatuschangedate() {
		return this.statuschangedate;
	}

	public void setStatuschangedate(Timestamp statuschangedate) {
		this.statuschangedate = statuschangedate;
	}

	@Column(name = "COMMONSTATUSID", length = 5)
	public String getCommonstatusid() {
		return this.commonstatusid;
	}

	public void setCommonstatusid(String commonstatusid) {
		this.commonstatusid = commonstatusid;
	}

	@Column(name = "SYSTEMUSERID", unique = true, length = 12)
	public String getSystemuserid() {
		return this.systemuserid;
	}

	public void setSystemuserid(String systemuserid) {
		this.systemuserid = systemuserid;
	}

	@Column(name = "SYSTEMGENERATED", length = 1)
	public Character getSystemgenerated() {
		return this.systemgenerated;
	}

	public void setSystemgenerated(Character systemgenerated) {
		this.systemgenerated = systemgenerated;
	}


	@Column(name = "EMPLOYEECODE", length = 60)
	public String getEmployeecode() {
		return this.employeecode;
	}

	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}

	@Column(name = "EXTERNALREFERENCEID", length = 50)
	public String getExternalreferenceid() {
		return this.externalreferenceid;
	}

	public void setExternalreferenceid(String externalreferenceid) {
		this.externalreferenceid = externalreferenceid;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userByLastmodifiedbystaffid")
	public Set<BSSUser> getUsersForLastmodifiedbystaffid() {
		return this.usersForLastmodifiedbystaffid;
	}

	public void setUsersForLastmodifiedbystaffid(
			Set<BSSUser> usersForLastmodifiedbystaffid) {
		this.usersForLastmodifiedbystaffid = usersForLastmodifiedbystaffid;
	}

	
	
	@ManyToMany(
     		 targetEntity=BSSACLGroup.class,
     		 cascade={CascadeType.PERSIST, CascadeType.MERGE},
     		 fetch = FetchType.LAZY
     		 )
     		 @JoinTable(
     		 name="VW_USER_ACL_REL",
     		 joinColumns = @JoinColumn( name="USERID"),
     		 inverseJoinColumns = @JoinColumn( name="ACLGROUPID")
     		 )
	public Set<BSSACLGroup> getAclGroups() {
		return aclGroups;
	}

	public void setAclGroups(Set<BSSACLGroup> aclGroups) {
		this.aclGroups = aclGroups;
	}

	/*
	 * @OneToMany(fetch=FetchType.LAZY, mappedBy="userByLastmodifiedbystaffid")
	 * public Set<ACLGroup> getACLGroupsForLastmodifiedbystaffid() { return
	 * this.ACLGroupsForLastmodifiedbystaffid; }
	 * 
	 * public void setACLGroupsForLastmodifiedbystaffid(Set<ACLGroup>
	 * ACLGroupsForLastmodifiedbystaffid) {
	 * this.ACLGroupsForLastmodifiedbystaffid =
	 * ACLGroupsForLastmodifiedbystaffid; }
	 * 
	 */

	
}
