package com.elitecore.cpe.bl.data.common;

import java.io.Serializable;

/**
 * @author yash.kapasi
 *
 */
public class ComboBoxData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	
	public ComboBoxData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ComboBoxData(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
