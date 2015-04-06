package com.elitecore.cpe.bl.ws.data.input.response;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_ChangeInventoryStatusResponse", propOrder = {
    "responseCode",
    "responseMessage",
    "inventoryvos"
})
public class ChangeInventoryStatusResponseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String responseCode;
	private String responseMessage;
	
	private List<InventoryStatusVO> inventoryvos;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public List<InventoryStatusVO> getInventoryvos() {
		return inventoryvos;
	}

	public void setInventoryvos(List<InventoryStatusVO> inventoryvos) {
		this.inventoryvos = inventoryvos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChangeInventoryStatusResponseData [responseCode=");
		builder.append(responseCode);
		builder.append(", responseMessage=");
		builder.append(responseMessage);
		builder.append(", inventoryvos=");
		builder.append(inventoryvos);
		builder.append("]");
		return builder.toString();
	}
	
	
}
