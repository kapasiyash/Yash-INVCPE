package com.elitecore.cpe.bl.ws.data.input.response;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_MarkCPEAsFaultyWithOwnerChangResponse", propOrder = {
    "responseCode",
    "responseMessage",
    "responseVo"
})
public class CPEResponseVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String responseCode;
	private String responseMessage;
	private List<InventoryStatusResponseVO> responseVo;
	
	
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
	public List<InventoryStatusResponseVO> getResponseVo() {
		return responseVo;
	}
	public void setResponseVo(List<InventoryStatusResponseVO> responseVo) {
		this.responseVo = responseVo;
	}
	@Override
	public String toString() {
		return "CPEResponseVO [responseCode=" + responseCode
				+ ", responseMessage=" + responseMessage + ", responseVo="
				+ responseVo + "]";
	}

}
