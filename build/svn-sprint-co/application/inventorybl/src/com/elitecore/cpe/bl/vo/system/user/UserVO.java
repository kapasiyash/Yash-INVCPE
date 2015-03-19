package com.elitecore.cpe.bl.vo.system.user;

import java.util.HashSet;
import java.util.Set;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;

public class UserVO {

	
	private String userId;
	private String username;
	private String password;
	private String encryptionType;
	private String encryptionTypeAlias;
	private String name;
	private String ipAddress;
	private boolean isActive;
	private Set<SystemActionData> permittedActions = new HashSet<SystemActionData>();
	public UserVO() {
		
	}
	
	
	
	public UserVO(String userId, String username, String password,
			String encryptionType, String encryptionTypeAlias, String name,
			String ipAddress, boolean isActive) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.encryptionType = encryptionType;
		this.encryptionTypeAlias = encryptionTypeAlias;
		this.name = name;
		this.ipAddress = ipAddress;
		this.isActive = isActive;
	}



	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEncryptionType() {
		return encryptionType;
	}
	public void setEncryptionType(String encryptionType) {
		this.encryptionType = encryptionType;
	}
	public String getEncryptionTypeAlias() {
		return encryptionTypeAlias;
	}
	public void setEncryptionTypeAlias(String encryptionTypeAlias) {
		this.encryptionTypeAlias = encryptionTypeAlias;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public void addPermittedActions(SystemActionData actionAlias){
		this.permittedActions.add(actionAlias);
		}
	public Set<SystemActionData> getPermittedActions(){
		return this.permittedActions;
	}
	public void setPermittedActions(Set<SystemActionData> permittedActions){
		this.permittedActions=permittedActions;
	}
}
