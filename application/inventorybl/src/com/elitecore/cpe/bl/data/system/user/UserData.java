package com.elitecore.cpe.bl.data.system.user;

import java.util.List;

/**
 * @author yash.kapasi
 */
public class UserData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;
	private String externalRefId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String userName;
	private String password;
	private String emailId;
	private String userTypeId;
	private String createdBy;
	private Long mobile;
	private Long phone;
	private List<Long> userGroupIds;
	private Long aclGroupId;

	public UserData() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getExternalRefId() {
		return externalRefId;
	}

	public void setExternalRefId(String externalRefId) {
		this.externalRefId = externalRefId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<Long> getUserGroupIds() {
		return userGroupIds;
	}

	public void setUserGroupIds(List<Long> userGroupIds) {
		this.userGroupIds = userGroupIds;
	}

	public Long getAclGroupId() {
		return aclGroupId;
	}

	public void setAclGroupId(Long aclGroupId) {
		this.aclGroupId = aclGroupId;
	}

	/*public User getUserEntity() {
		User user = new User();
		user.setFirstName(getFirstName());
		if (getMiddleName() != null)
			user.setMiddleName(getMiddleName());
		if (getLastName() != null)
			user.setLastName(getLastName());
		user.setUserName(getUserName());
		user.setPassword(getPassword());
		if (getEmailId() != null)
			user.setEmailId(getEmailId());
		if (getExternalRefId() != null)
			user.setExternalRefId(getExternalRefId());
		user.setEmailId(getEmailId());
		user.setUserTypeId(getUserTypeId());
		if (getMobile() != null) {
			user.setMobile(getMobile().toString());
		}
		if (getPhone() != null) {
			user.setPhone(getPhone().toString());
		}
		user.setCreatedBy(getCreatedBy());
		user.setLastModifyBy(getCreatedBy());
		return user;
	}*/

}
