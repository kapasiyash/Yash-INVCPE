package com.elitecore.cpe.bl.vo.system.user;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * @author Yash.Kapasi
 *
 */
public class SearchUserVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userName;
	private String firstName;
	private String userType;
	private String email;
	private Timestamp createDate;
	private Timestamp lastModiDate;
	private String createdBy;
	private String lastModiBy;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getLastModiDate() {
		return lastModiDate;
	}

	public void setLastModiDate(Timestamp lastModiDate) {
		this.lastModiDate = lastModiDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModiBy() {
		return lastModiBy;
	}

	public void setLastModiBy(String lastModiBy) {
		this.lastModiBy = lastModiBy;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
