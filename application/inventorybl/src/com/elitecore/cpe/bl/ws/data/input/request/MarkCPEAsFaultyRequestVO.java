package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_MarkCPEAsFaultyWithOwnerChangRequest", propOrder = {
    "warehouseCode",
    "faultyInventoryList"
})
public class MarkCPEAsFaultyRequestVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String warehouseCode;
	@XmlElement(name = "NICE_FaultyInventoryList", required = true)
	private FaultyInventoryList faultyInventoryList;
	
	
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public FaultyInventoryList getFaultyInventoryList() {
		return faultyInventoryList;
	}
	public void setFaultyInventoryList(FaultyInventoryList faultyInventoryList) {
		this.faultyInventoryList = faultyInventoryList;
	}
	@Override
	public String toString() {
		return "MarkCPEAsFaultyRequestVO [warehouseCode=" + warehouseCode
				+ ", faultyInventoryList=" + faultyInventoryList + "]";
	}
	
	
	
}
