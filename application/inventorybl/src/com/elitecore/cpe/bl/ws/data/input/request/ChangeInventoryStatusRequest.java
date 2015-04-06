package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_ChangeInventoryStatusRequest", propOrder = {
    "changeInventoryRequestList"
})
public class ChangeInventoryStatusRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "NICE_ChangeInventoryStatusList", required = true)
	private ChangeInventoryRequestList changeInventoryRequestList;

	public ChangeInventoryRequestList getChangeInventoryRequestList() {
		return changeInventoryRequestList;
	}

	public void setChangeInventoryRequestList(
			ChangeInventoryRequestList changeInventoryRequestList) {
		this.changeInventoryRequestList = changeInventoryRequestList;
	}

	@Override
	public String toString() {
		return "ChangeInventoryStatusRequest [changeInventoryRequestList="
				+ changeInventoryRequestList + "]";
	}
	
	
	

}
