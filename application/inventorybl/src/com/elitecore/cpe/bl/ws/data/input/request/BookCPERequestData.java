package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.elitecore.cpe.bl.ws.data.input.vo.ReserveAllocateRequestVO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICE_BookCPEResourceRequest", propOrder = {
    "orderLineItemID",
    "operationType",
    "isResourceRecoverable",
    "bookInventoryList"
})
public class BookCPERequestData implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private String orderLineItemID;
	@XmlElement(required=true)
	private Integer operationType;
	private String isResourceRecoverable;
	@XmlElement(name = "NICE_BookInventoryList")
	private BookInventoryList bookInventoryList;
	
	
	public BookInventoryList getBookInventoryList() {
		return bookInventoryList;
	}
	public void setBookInventoryList(BookInventoryList bookInventoryList) {
		this.bookInventoryList = bookInventoryList;
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
		return "BookCPERequestData [orderLineItemID=" + orderLineItemID
				+ ", operationType=" + operationType
				+ ", isResourceRecoverable=" + isResourceRecoverable
				+ ", bookInventoryList=" + bookInventoryList + "]";
	}
	
	
	
	
}
