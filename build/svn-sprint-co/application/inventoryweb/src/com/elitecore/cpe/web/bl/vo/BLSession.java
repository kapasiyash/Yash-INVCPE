package com.elitecore.cpe.web.bl.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author elitecore
 *
 */
public class BLSession implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long userId;	
	private String username;
	private String userRole;
	private String password;	
	private Date sessionCreateTime;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getSessionCreateTime() {
		return sessionCreateTime;
	}
	public void setSessionCreateTime(Date sessionCreateTime) {
		this.sessionCreateTime = sessionCreateTime;
	}
	
}
