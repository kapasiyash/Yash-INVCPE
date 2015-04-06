package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_ReleaseCPEResourceRequest", propOrder = {
    "operationType",
    "releaseInventoryList"
})
public class ReleaseCPERequestVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Integer operationType;
	
	@XmlElement(name = "NICE_ReleaseInventoryList", required = true)
	private ReleaseInventoryList releaseInventoryList;
	 
	
	public Integer getOperationType() {
		return operationType;
	}
	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}
	public ReleaseInventoryList getReleaseInventoryList() {
		return releaseInventoryList;
	}
	public void setReleaseInventoryList(ReleaseInventoryList releaseInventoryList) {
		this.releaseInventoryList = releaseInventoryList;
	}
	
	
	@Override
	public String toString() {
		return "ReleaseCPERequestVO [operationType=" + operationType
				+ ", releaseInventoryList=" + releaseInventoryList + "]";
	}
	
	
	
	
	
	
}
