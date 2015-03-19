package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;
import java.util.List;

public class PlaceOrderNotificationEmailVO implements Serializable {

private static final long serialVersionUID = 1L;
	
private String orderNumber;
	private String fromwareHouseName;
	private String towareHouseName;
	private String resourceTypeName;
	private String resourceSubTypeName;
	private Long quantity;
	private Long acceptQuantity;
	
	private List<String> emailId;
	

	public String getFromwareHouseName() {
		return fromwareHouseName;
	}

	public void setFromwareHouseName(String fromwareHouseName) {
		this.fromwareHouseName = fromwareHouseName;
	}

	public String getTowareHouseName() {
		return towareHouseName;
	}

	public void setTowareHouseName(String towareHouseName) {
		this.towareHouseName = towareHouseName;
	}

	public String getResourceTypeName() {
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}

	public String getResourceSubTypeName() {
		return resourceSubTypeName;
	}

	public void setResourceSubTypeName(String resourceSubTypeName) {
		this.resourceSubTypeName = resourceSubTypeName;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public List<String> getEmailId() {
		return emailId;
	}

	public void setEmailId(List<String> emailId) {
		this.emailId = emailId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	
	
	public Long getAcceptQuantity() {
		return acceptQuantity;
	}

	public void setAcceptQuantity(Long acceptQuantity) {
		this.acceptQuantity = acceptQuantity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlaceOrderNotificationEmailVO [orderNumber=");
		builder.append(orderNumber);
		builder.append(", fromwareHouseName=");
		builder.append(fromwareHouseName);
		builder.append(", towareHouseName=");
		builder.append(towareHouseName);
		builder.append(", resourceTypeName=");
		builder.append(resourceTypeName);
		builder.append(", resourceSubTypeName=");
		builder.append(resourceSubTypeName);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", acceptQuantity=");
		builder.append(acceptQuantity);
		builder.append(", emailId=");
		builder.append(emailId);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
