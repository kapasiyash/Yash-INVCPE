package com.elitecore.cpe.bl.entity.inventory.inventorymgt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "TBLSINVSUBSTATUSTRANSITION")
public class InventorySubStatusTransition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long subStatusTransitionId;
	private Long statusId;
	private Long allowedSubStatusId;
	
	
	private InventoryStatusData statusData;
	
	private InventorySubStatusData allowedStatusData;


	 @SequenceGenerator(name="generator", sequenceName="TBLSINVSUBSTATUSTRANSITION_SEQ", allocationSize=1)
	    @Id
	    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
	    @Column(name="SUBSTATUSTRANSITIONID")
	 public Long getSubStatusTransitionId() {
		 return subStatusTransitionId;
	 }
	 
	 public void setSubStatusTransitionId(Long subStatusTransitionId) {
		 this.subStatusTransitionId = subStatusTransitionId;
	 }
	


 	@Column(name="STATUSID")
	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}


	@Column(name="ALLOWEDSUBSTATUSID")
	public Long getAllowedSubStatusId() {
		return allowedSubStatusId;
	}

	public void setAllowedSubStatusId(Long allowedSubStatusId) {
		this.allowedSubStatusId = allowedSubStatusId;
	}
	
	


	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STATUSID",insertable=false,updatable=false)
	public InventoryStatusData getStatusData() {
		return statusData;
	}
	

	public void setStatusData(InventoryStatusData statusData) {
		this.statusData = statusData;
	}



	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ALLOWEDSUBSTATUSID",insertable=false,updatable=false)
	public InventorySubStatusData getAllowedStatusData() {
		return allowedStatusData;
	}

	public void setAllowedStatusData(InventorySubStatusData allowedStatusData) {
		this.allowedStatusData = allowedStatusData;
	}
	
	

}
