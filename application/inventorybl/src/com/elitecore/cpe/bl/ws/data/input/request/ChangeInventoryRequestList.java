package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.elitecore.cpe.bl.ws.data.input.vo.InventoryRequestVO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_ChangeInventoryStatusList", propOrder = {
    "requestData"
})
public class ChangeInventoryRequestList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @XmlElement(name = "NICE_ChangeInventoryStatus", required = true)
	private List<InventoryRequestVO> requestData;

	public List<InventoryRequestVO> getRequestData() {
		return requestData;
	}

	public void setRequestData(List<InventoryRequestVO> requestData) {
		this.requestData = requestData;
	}

	@Override
	public String toString() {
		return "ChangeInventoryRequestList [requestData=" + requestData + "]";
	}
	
	
	

}
