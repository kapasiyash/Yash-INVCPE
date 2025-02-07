/**
 * 
 */
package com.elitecore.cpe.bl.entity.inventory.inventorymgt;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Joel.Macwan
 *
 */

@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TBLTTRANSFERORDERDETAIL")
@NamedQueries({ 
	@NamedQuery(name = "TransferOrderDetailData.isAvailable",query ="select o from TransferOrderDetailData o where o.transferOrderData.transferOrderNo = :transferOrderNo and  o.inventoryNo = :inventoryNo and o.transferStatus is null")
	
})
public class TransferOrderDetailData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long transferOrderDetailId;
	private Long transferOrderId;
	private String inventoryNo;
	private String batchNo;
	private String transferStatus;
	private String remark;
	
	private Long previousStatus;
	private Long previousSubStatus;
	
	private TransferOrderData transferOrderData;
	
	private InventoryData inventoryData;
	
	/**
	 * 
	 */
	 @SequenceGenerator(name="generator", sequenceName="TBLTTRANSFERORDERDETAIL_SEQ", allocationSize=1)
	    @Id
	    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
	    @Column(name="ID")
	public Long getTransferOrderDetailId() {
		return transferOrderDetailId;
	}
	/**
	 * 
	 */
	public void setTransferOrderDetailId(Long transferOrderDetailId) {
		this.transferOrderDetailId = transferOrderDetailId;
	}
	/**
	 * 
	 */
	 @Column(name="TRANSFERORDERID")
	public Long getTransferOrderId() {
		return transferOrderId;
	}
	/**
	 * 
	 */
	public void setTransferOrderId(Long transferOrderId) {
		this.transferOrderId = transferOrderId;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TRANSFERORDERID" ,insertable=false,updatable=false)
	public TransferOrderData getTransferOrderData() {
		return transferOrderData;
	}
	public void setTransferOrderData(TransferOrderData transferOrderData) {
		this.transferOrderData = transferOrderData;
	}
	/**
	 * 
	 */
	@Column(name="INVENTORYNO")
	public String getInventoryNo() {
		return inventoryNo;
	}
	/**
	 * 
	 */
	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}
	
	
	
	/**
	 * This method is for read Only Purpose to get InventoryData with non-Primary Key 
	 * @return
	 */
//	@Formula("(select o from tblminventory o where o.inventoryno=inventoryNo)")
//	@OneToOne(cascade=CascadeType.ALL)
//	@PrimaryKeyJoinColumn(name="INVENTORYNO")
//	public InventoryData getInventoryData() {
//		return inventoryData;
//	}
//	public void setInventoryData(InventoryData inventoryData) {
//		this.inventoryData = inventoryData;
//	}
	/**
	 * 
	 */
	@Column(name="BATCHNO")
	public String getBatchNo() {
		return batchNo;
	}
	/**
	 * 
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * 
	 */
	@Column(name="TRANSFERSTATUS")
	public String getTransferStatus() {
		return transferStatus;
	}
	/**
	 * 
	 */
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}
	/**
	 * 
	 */
	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}
	/**
	 * 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	@Column(name="PREVIOUSSTATUS")
	public Long getPreviousStatus() {
		return previousStatus;
	}
	public void setPreviousStatus(Long previousStatus) {
		this.previousStatus = previousStatus;
	}
	
	
	@Column(name="PREVIOUSSUBSTATUS")
	public Long getPreviousSubStatus() {
		return previousSubStatus;
	}
	public void setPreviousSubStatus(Long previousSubStatus) {
		this.previousSubStatus = previousSubStatus;
	}
	/* 
	 * 
	 */
	@Override
	public String toString() {
		return "TransferOrderDetailData [transferOrderDetailId="
				+ transferOrderDetailId + ", transferOrderId="
				+ transferOrderId + ", inventoryNo=" + inventoryNo
				+ ", batchNo=" + batchNo + ", transferStatus=" + transferStatus
				+ ", remark=" + remark + "]";
	}
	
	
}
