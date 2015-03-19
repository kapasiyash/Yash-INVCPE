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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TBLSINVSTATUSTRANSITION")
@NamedQueries({	@NamedQuery(name = "InventoryStatusTransition.findStatusById",query ="select o from InventoryStatusTransition o where o.statusId = :statusId")
})
public class InventoryStatusTransition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long statusTransitionId;
	private Long statusId;
	private Long allowedStatusId;
	
	
	private InventoryStatusData statusData;
	
	private InventoryStatusData allowedStatusData;


	 @SequenceGenerator(name="generator", sequenceName="TBLSINVSTATUSTRANSITION_SEQ", allocationSize=1)
	    @Id
	    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
	    @Column(name="STATUSTRANSITIONID")
	public Long getStatusTransitionId() {
		return statusTransitionId;
	}

	public void setStatusTransitionId(Long statusTransitionId) {
		this.statusTransitionId = statusTransitionId;
	}


 	@Column(name="STATUSID")
	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}


	@Column(name="ALLOWEDSTATUSID")
	public Long getAllowedStatusId() {
		return allowedStatusId;
	}

	public void setAllowedStatusId(Long allowedStatusId) {
		this.allowedStatusId = allowedStatusId;
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
    @JoinColumn(name="ALLOWEDSTATUSID",insertable=false,updatable=false)
	public InventoryStatusData getAllowedStatusData() {
		return allowedStatusData;
	}

	public void setAllowedStatusData(InventoryStatusData allowedStatusData) {
		this.allowedStatusData = allowedStatusData;
	}

	@Override
	public String toString() {
		return "InventoryStatusTransition [statusTransitionId="
				+ statusTransitionId + ", statusId=" + statusId
				+ ", allowedStatusId=" + allowedStatusId + "]";
	}
	
	
	

}
