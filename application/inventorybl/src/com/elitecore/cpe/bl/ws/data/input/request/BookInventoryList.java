package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.elitecore.cpe.bl.ws.data.input.vo.ReserveAllocateRequestVO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_BookInventoryList", propOrder = {
    "ReserveAllocateRequestVO"
})
public class BookInventoryList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "NICE_BookInventory", required = true)
	private List<ReserveAllocateRequestVO> ReserveAllocateRequestVO;

	public List<ReserveAllocateRequestVO> getReserveAllocateRequestVO() {
		return ReserveAllocateRequestVO;
	}

	public void setReserveAllocateRequestVO(
			List<ReserveAllocateRequestVO> reserveAllocateRequestVO) {
		ReserveAllocateRequestVO = reserveAllocateRequestVO;
	}

	@Override
	public String toString() {
		return "BookInventoryList [ReserveAllocateRequestVO="
				+ ReserveAllocateRequestVO + "]";
	}

	
	
	
}
