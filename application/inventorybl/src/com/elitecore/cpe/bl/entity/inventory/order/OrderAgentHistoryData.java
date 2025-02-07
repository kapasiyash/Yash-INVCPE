package com.elitecore.cpe.bl.entity.inventory.order;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


	@Entity
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	@Table(name = "TBLMORDERAGENTHISTORY")
public class OrderAgentHistoryData implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		private Long orderAgentHistoryId;
		private Long orderId;
		private String orderNo;
		private String orderType;
		private Long fromWarehouseId;
		private Long toWarehouseId;
		private Long agentrundetailId;
		private Timestamp agentrundate;
		private String status;
		private Timestamp emailSendDate;
		private Timestamp smsSendDate;

		
		@SequenceGenerator(name="generator", sequenceName="TBLMAGENTHISTORY_SEQ", allocationSize=1)
	    @Id
	    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
		@Column(name="ORDERAGENTHISTORYID")
		public Long getOrderAgentHistoryId() {
			return orderAgentHistoryId;
		}
		public void setOrderAgentHistoryId(Long orderAgentHistoryId) {
			this.orderAgentHistoryId = orderAgentHistoryId;
		}
		
	    @Column(name="ORDERID")
	    public Long getOrderId() {
			return orderId;
		}
		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
		
		
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		
		
		@Column(name="ORDERTYPE")
		public String getOrderType() {
			return orderType;
		}
		public void setOrderType(String orderType) {
			this.orderType = orderType;
		}
		@Column(name="FROMWAREHOUSEID")
			public Long getFromWarehouseId() {
			return fromWarehouseId;
		}
			public void setFromWarehouseId(Long fromWarehouseId) {
			this.fromWarehouseId = fromWarehouseId;
		}
			
		@Column(name="TOWAREHOUSEID")
		public Long getToWarehouseId() {
			return toWarehouseId;
		}
		public void setToWarehouseId(Long toWarehouseId) {
			this.toWarehouseId = toWarehouseId;
		}
		
		@Column(name="AGENTRUNDETAILID")
		public Long getAgentrundetailId() {
			return agentrundetailId;
		}
		public void setAgentrundetailId(Long agentrundetailId) {
			this.agentrundetailId = agentrundetailId;
		}
		
		
		
		@Column(name="AGENTRUNDATE")
	    public Timestamp getAgentrundate() {
			return agentrundate;
		}
		public void setAgentrundate(Timestamp agentrundate) {
			this.agentrundate = agentrundate;
		}
		
		
		@Column(name="EMAILSENDDATE")
		public Timestamp getEmailSendDate() {
			return emailSendDate;
		}
		public void setEmailSendDate(Timestamp emailSendDate) {
			this.emailSendDate = emailSendDate;
		}
		
		 @Column(name="SMSSENDDATE")
		public Timestamp getSmsSendDate() {
			return smsSendDate;
		}
		public void setSmsSendDate(Timestamp smsSendDate) {
			this.smsSendDate = smsSendDate;
		}
		
		@Column(name="STATUS")
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		@Override
		public String toString() {
			return "OrderAgentHistoryData [orderAgentHistoryId="
					+ orderAgentHistoryId + ", orderId=" + orderId
					+ ", orderNo=" + orderNo + ", orderType=" + orderType
					+ ", fromWarehouseId=" + fromWarehouseId
					+ ", toWarehouseId=" + toWarehouseId
					+ ", agentrundetailId=" + agentrundetailId
					+ ", agentrundate=" + agentrundate + ", status=" + status
					+ ", emailSendDate=" + emailSendDate + ", smsSendDate="
					+ smsSendDate + "]";
		}
		
		
		
		
		
		
}