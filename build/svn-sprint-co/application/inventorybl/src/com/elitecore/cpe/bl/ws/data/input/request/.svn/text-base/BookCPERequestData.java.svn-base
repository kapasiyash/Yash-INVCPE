package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.elitecore.cpe.bl.ws.data.input.vo.ReserveAllocateRequestVO;

public class BookCPERequestData implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private String orderLineItemID;
	private Integer operationType;
	private String isResourceRecoverable;
/*	private String resourceId;
	private Integer noOfResource;
  	private String warehouseName;
	private String warehouseCode;
*/	private List<ReserveAllocateRequestVO> ReserveAllocateRequestVO;
	
	public List<ReserveAllocateRequestVO> getReserveAllocateRequestVO() {
		return ReserveAllocateRequestVO;
	}
	public void setReserveAllocateRequestVO(
			List<ReserveAllocateRequestVO> reserveAllocateRequestVO) {
		ReserveAllocateRequestVO = reserveAllocateRequestVO;
	}
	public String getIsResourceRecoverable() {
		return isResourceRecoverable;
	}
	public void setIsResourceRecoverable(String isResourceRecoverable) {
		this.isResourceRecoverable = isResourceRecoverable;
	}
	public String getOrderLineItemID() {
		return orderLineItemID;
	}
	public void setOrderLineItemID(String orderLineItemID) {
		this.orderLineItemID = orderLineItemID;
	}
	
	@XmlElement(required=true)
	public Integer getOperationType() {
		return operationType;
	}
	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}
/*	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getNoOfResource() {
		return noOfResource;
	}
	public void setNoOfResource(Integer noOfResource) {
		this.noOfResource = noOfResource;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	
	
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
		*/
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BookCPERequestData [");
		if(orderLineItemID!=null){
		builder.append("orderLineItemID=").append(orderLineItemID);
		}
		if(operationType!=null){
		builder.append(", operationType=").append(operationType);
		}
		if(ReserveAllocateRequestVO!=null){
		builder.append(", ReserveAllocateRequestVO=").append(ReserveAllocateRequestVO);
		}
		builder.append("]");
		return builder.toString();
	}
	
	
}
