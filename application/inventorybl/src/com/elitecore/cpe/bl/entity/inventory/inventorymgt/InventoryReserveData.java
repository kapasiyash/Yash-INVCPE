package com.elitecore.cpe.bl.entity.inventory.inventorymgt;

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
@Table(name = "TBLTINVENTORYRESERVE")
public class InventoryReserveData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long reservationId;
	private String reservationNo;
	private String orderLineItemId;
	private Long totalResource;
	
	private Timestamp createdate;
	private Timestamp updatedate;
	private String createdby;
	private String updatedby;
	
	@SequenceGenerator(name = "generator", sequenceName = "TBLTINVENTORYRESERVE_SEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "RESERVATIONID")
	public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	
	@Column(name = "RESERVATIONNO")
	public String getReservationNo() {
		return reservationNo;
	}
	public void setReservationNo(String reservationNo) {
		this.reservationNo = reservationNo;
	}
	
	@Column(name = "ORDERLINEITEMID")
	public String getOrderLineItemId() {
		return orderLineItemId;
	}
	public void setOrderLineItemId(String orderLineItemId) {
		this.orderLineItemId = orderLineItemId;
	}
	
	@Column(name = "TOTALRESOURCE")
	public Long getTotalResource() {
		return totalResource;
	}
	public void setTotalResource(Long totalResource) {
		this.totalResource = totalResource;
	}
	
	@Column(name = "CREATEDATE")
	public Timestamp getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	@Column(name = "LASTMODIFIEDDATE")
	public Timestamp getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}

	@Column(name = "CREATEDBYSTAFFID")
	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	@Column(name = "LASTMODIFIEDDATEBYSTAFFID")
	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryReserveData [reservationId=");
		builder.append(reservationId);
		builder.append(", reservationNo=");
		builder.append(reservationNo);
		builder.append(", orderLineItemId=");
		builder.append(orderLineItemId);
		builder.append(", totalResource=");
		builder.append(totalResource);
		builder.append(", createdate=");
		builder.append(createdate);
		builder.append(", updatedate=");
		builder.append(updatedate);
		builder.append(", createdby=");
		builder.append(createdby);
		builder.append(", updatedby=");
		builder.append(updatedby);
		builder.append("]");
		return builder.toString();
	}


	
	
	
}
