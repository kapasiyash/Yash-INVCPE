package com.elitecore.cpe.bl.entity.inventory.system.audit;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TBLMDATATAGS")
public class DataTagsData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private int tagId;
	private String tagName; 
	private String param1;
	private String param2;
	
	@Id
	@Column(name = "TAGID")
	public int getTagId() {
		return tagId;
	}
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	
	@Column(name = "TAGNAME")
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	@Column(name = "PARAM1")
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	
	@Column(name = "PARAM2")
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	
	@Override
	public String toString() {
		return "DataTagsData [tagId=" + tagId + ", tagName=" + tagName
				+ ", param1=" + param1 + ", param2=" + param2 + "]";
	}
	
	
	
}
