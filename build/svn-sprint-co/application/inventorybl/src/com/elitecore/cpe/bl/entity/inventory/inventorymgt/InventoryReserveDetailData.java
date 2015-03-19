package com.elitecore.cpe.bl.entity.inventory.inventorymgt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TBLTINVENTORYRESERVEDETAIL")
public class InventoryReserveDetailData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long Id;
	private Long reservationId;
	private InventoryReserveData inventoryReserveData;
	private String inventoryNo;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "TBLTINVENTORYRESERVEDETAIL_SEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID")
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	@Column(name = "RESERVATIONID")
	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	@ManyToOne
	@JoinColumn(name = "RESERVATIONID",insertable=false,updatable=false)
	public InventoryReserveData getInventoryReserveData() {
		return inventoryReserveData;
	}

	public void setInventoryReserveData(InventoryReserveData inventoryReserveData) {
		this.inventoryReserveData = inventoryReserveData;
	}


	@Column(name = "INVENTORYNO")
	public String getInventoryNo() {
		return inventoryNo;
	}

	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryReserveDetailData [Id=");
		builder.append(Id);
		builder.append(", reservationId=");
		builder.append(reservationId);
		builder.append(", inventoryReserveData=");
		builder.append(inventoryReserveData);
		builder.append(", inventoryNo=");
		builder.append(inventoryNo);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
