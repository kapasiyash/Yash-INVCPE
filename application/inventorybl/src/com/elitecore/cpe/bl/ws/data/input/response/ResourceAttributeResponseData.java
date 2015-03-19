package com.elitecore.cpe.bl.ws.data.input.response;

import java.io.Serializable;
import java.util.List;

import com.elitecore.cpe.bl.ws.data.input.vo.AttributeVO;


public class ResourceAttributeResponseData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<AttributeVO> listAttributes;
	private Long responseCode;
	private String responseMessage;
	
	
	public List<AttributeVO> getListAttributes() {
		return listAttributes;
	}

	public void setListAttributes(List<AttributeVO> listAttributes) {
		this.listAttributes = listAttributes;
	}

	public Long getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Long responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	@Override
	public String toString() {
		return "ResourceAttributeResponseData [listAttributes="
				+ listAttributes + ", responseCode=" + responseCode
				+ ", responseMessage=" + responseMessage + "]";
	}

	
	
	
}
