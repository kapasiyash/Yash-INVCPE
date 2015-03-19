package com.elitecore.cpe.bl.vo.configuration.notification;

import java.io.Serializable;

public class NotificationCategoryVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long Id;
	private String name;
	private boolean isEmail;
	private boolean isSms;
	
	
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isEmail() {
		return isEmail;
	}
	public void setEmail(boolean isEmail) {
		this.isEmail = isEmail;
	}
	public boolean isSms() {
		return isSms;
	}
	public void setSms(boolean isSms) {
		this.isSms = isSms;
	}
	@Override
	public String toString() {
		return "NotificationCategoryVO [Id=" + Id + ", name=" + name
				+ ", isEmail=" + isEmail + ", isSms=" + isSms + "]";
	}
	
	
	
	

}
