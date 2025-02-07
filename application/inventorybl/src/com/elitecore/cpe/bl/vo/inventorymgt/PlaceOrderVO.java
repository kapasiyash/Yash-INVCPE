package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;
import java.sql.Timestamp;

public class PlaceOrderVO implements Serializable,Comparable<PlaceOrderVO>{
	private Long towarehouseId;
	private Long fromwarehouseId;
	
	private Long quantity;
	private String remark;
	private Long resourceTypeId;
	private Long resourceSubTypeId;
	private Long itemId;
	private String createdby;
	private String updatedby;
	private Timestamp createDate;
	private Timestamp updatedDate;
	private String orderNo;
	private String fromwarehouse;
	private String towarehouse;
	private String resourceType;
	private String resourceSubtype;
	private String resourceName;
	private String status;
	private Timestamp fromDate;
	private Timestamp toDate;
	//private String orderStatus;
	private String transferOrderNo;
	private Timestamp acceptRejectDate;
	private String transferRemark;
	private Long acceptquantity;
	
	private Long orderType;
	
	private Long orderId;
	private String emailId;
	private String parentEmailId;
	
	
	
	
		public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getParentEmailId() {
		return parentEmailId;
	}
	public void setParentEmailId(String parentEmailId) {
		this.parentEmailId = parentEmailId;
	}
		public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
		public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
		public Long getOrderType() {
		return orderType;
	}
	public void setOrderType(Long orderType) {
		this.orderType = orderType;
	}
		public Long getAcceptquantity() {
		return acceptquantity;
	}
	public void setAcceptquantity(Long acceptquantity) {
		this.acceptquantity = acceptquantity;
	}
		public Long getTowarehouseId() {
			return towarehouseId;
		}
		public void setTowarehouseId(Long towarehouseId) {
			this.towarehouseId = towarehouseId;
		}
		public Long getFromwarehouseId() {
			return fromwarehouseId;
		}
		public void setFromwarehouseId(Long fromwarehouseId) {
			this.fromwarehouseId = fromwarehouseId;
		}
		public Long getQuantity() {
			return quantity;
		}
		public void setQuantity(Long quantity) {
			this.quantity = quantity;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public Long getResourceTypeId() {
			return resourceTypeId;
		}
		public void setResourceTypeId(Long resourceTypeId) {
			this.resourceTypeId = resourceTypeId;
		}
		public Long getResourceSubTypeId() {
			return resourceSubTypeId;
		}
		public void setResourceSubTypeId(Long resourceSubTypeId) {
			this.resourceSubTypeId = resourceSubTypeId;
		}
		public String getCreatedby() {
			return createdby;
		}
		public void setCreatedby(String createdby) {
			this.createdby = createdby;
		}
		public String getUpdatedby() {
			return updatedby;
		}
		public void setUpdatedby(String updatedby) {
			this.updatedby = updatedby;
		}
		public Timestamp getCreateDate() {
			return createDate;
		}
		public void setCreateDate(Timestamp createDate) {
			this.createDate = createDate;
		}
		public Timestamp getUpdatedDate() {
			return updatedDate;
		}
		public void setUpdatedDate(Timestamp updatedDate) {
			this.updatedDate = updatedDate;
		}
		
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		public String getFromwarehouse() {
			return fromwarehouse;
		}
		public void setFromwarehouse(String fromwarehouse) {
			this.fromwarehouse = fromwarehouse;
		}
		public String getTowarehouse() {
			return towarehouse;
		}
		public void setTowarehouse(String towarehouse) {
			this.towarehouse = towarehouse;
		}
		public String getResourceType() {
			return resourceType;
		}
		public void setResourceType(String resourceType) {
			this.resourceType = resourceType;
		}
		public String getResourceSubtype() {
			return resourceSubtype;
		}
		public void setResourceSubtype(String resourceSubtype) {
			this.resourceSubtype = resourceSubtype;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
		
		public Timestamp getFromDate() {
			return fromDate;
		}
		public void setFromDate(Timestamp fromDate) {
			this.fromDate = fromDate;
		}
		public Timestamp getToDate() {
			return toDate;
		}
		public void setToDate(Timestamp toDate) {
			this.toDate = toDate;
		}
		
		
		
		
		
		
		
		public String getTransferOrderNo() {
			return transferOrderNo;
		}
		public void setTransferOrderNo(String transferOrderNo) {
			this.transferOrderNo = transferOrderNo;
		}
		public Timestamp getAcceptRejectDate() {
			return acceptRejectDate;
		}
		public void setAcceptRejectDate(Timestamp acceptRejectDate) {
			this.acceptRejectDate = acceptRejectDate;
		}
		public String getTransferRemark() {
			return transferRemark;
		}
		public void setTransferRemark(String transferRemark) {
			this.transferRemark = transferRemark;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("PlaceOrderVO [towarehouseId=");
			builder.append(towarehouseId);
			builder.append(", fromwarehouseId=");
			builder.append(fromwarehouseId);
			builder.append(", quantity=");
			builder.append(quantity);
			builder.append(", remark=");
			builder.append(remark);
			builder.append(", resourceTypeId=");
			builder.append(resourceTypeId);
			builder.append(", resourceSubTypeId=");
			builder.append(resourceSubTypeId);
			builder.append(", createdby=");
			builder.append(createdby);
			builder.append(", updatedby=");
			builder.append(updatedby);
			builder.append(", createDate=");
			builder.append(createDate);
			builder.append(", updatedDate=");
			builder.append(updatedDate);
			builder.append(", orderNo=");
			builder.append(orderNo);
			builder.append(", fromwarehouse=");
			builder.append(fromwarehouse);
			builder.append(", towarehouse=");
			builder.append(towarehouse);
			builder.append(", resourceType=");
			builder.append(resourceType);
			builder.append(", resourceSubtype=");
			builder.append(resourceSubtype);
			builder.append(", status=");
			builder.append(status);
			builder.append(", fromDate=");
			builder.append(fromDate);
			builder.append(", toDate=");
			builder.append(toDate);
			builder.append(", transferOrderNo=");
			builder.append(transferOrderNo);
			builder.append(", acceptRejectDate=");
			builder.append(acceptRejectDate);
			builder.append(", transferRemark=");
			builder.append(transferRemark);
			builder.append(", acceptquantity=");
			builder.append(acceptquantity);
			builder.append("]");
			return builder.toString();
		}
		
		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return super.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}
		
		@Override
		public int compareTo(PlaceOrderVO arg0) {
			
			if(arg0!=null) {
				return arg0.getOrderNo().compareTo(this.orderNo);
			}
			
			return 0;
		}
		

}
