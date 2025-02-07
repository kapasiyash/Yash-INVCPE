package com.elitecore.cpe.bl.vo.order;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrderDetailVo implements  Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String orderNo;
	private Long orderId;
	private String orderType;
	private String status;
	private Long agentRunDetailId;
	private Long fromWarehouseId;
	private Long toWarehouseId;
	private Timestamp agentRunDate;
	private Timestamp emailSendDate;
	private Timestamp smsSendDate;
	
	private Long agenetRunQueueId;

	
	
	
	public Long getAgenetRunQueueId() {
		return agenetRunQueueId;
	}

	public void setAgenetRunQueueId(Long agenetRunQueueId) {
		this.agenetRunQueueId = agenetRunQueueId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Long getFromWarehouseId() {
		return fromWarehouseId;
	}

	public void setFromWarehouseId(Long fromWarehouseId) {
		this.fromWarehouseId = fromWarehouseId;
	}
	public Long getAgentRunDetailId() {
		return agentRunDetailId;
	}

	public void setAgentRunDetailId(Long agentRunDetailId) {
		this.agentRunDetailId = agentRunDetailId;
	}
	public Long getToWarehouseId() {
		return toWarehouseId;
	}

	public void setToWarehouseId(Long toWarehouseId) {
		this.toWarehouseId = toWarehouseId;
	}
	public Timestamp getAgentRunDate() {
		return agentRunDate;
	}

	public void setAgentRunDate(Timestamp agentRunDate) {
		this.agentRunDate = agentRunDate;
	}

	public Timestamp getEmailSendDate() {
		return emailSendDate;
	}

	public void setEmailSendDate(Timestamp emailSendDate) {
		this.emailSendDate = emailSendDate;
	}
	public Timestamp getSmsSendDate() {
		return smsSendDate;
	}

	public void setSmsSendDate(Timestamp smsSendDate) {
		this.smsSendDate = smsSendDate;
	}

	@Override
	public String toString() {
		return "OrderDetailVo [orderNo=" + orderNo + ", orderId=" + orderId
				+ ", orderType=" + orderType + ", status=" + status
				+ ", agentRunDetailId=" + agentRunDetailId
				+ ", fromWarehouseId=" + fromWarehouseId + ", toWarehouseId="
				+ toWarehouseId + ", agentRunDate=" + agentRunDate
				+ ", emailSendDate=" + emailSendDate + ", smsSendDate="
				+ smsSendDate + "]";
	}


	
}