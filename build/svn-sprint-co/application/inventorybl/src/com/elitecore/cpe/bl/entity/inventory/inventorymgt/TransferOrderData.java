/**
 * 
 */
package com.elitecore.cpe.bl.entity.inventory.inventorymgt;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.entity.inventory.transfer.InventoryTransferOrderStatus;

/**
 * @author Joel.Macwan
 *
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TBLMTRANSFERORDER")
@NamedQueries({ 
	@NamedQuery(name = "TransferOrderData.searchTransferOrderDataByOrderNo",query ="select o from TransferOrderData o where o.transferOrderNo=:transferOrderNo")
})
public class TransferOrderData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long transferOrderId;
	private String transferOrderNo;
	private Timestamp createdate;
	private Timestamp updatedate;
	private String createdby;
	private String updatedby;	
	private Long fromWarehouseId;
	private Long toWarehouseId;
	
	private String inventoryOrderStatusId;
	private String remarks;
	
	private InventoryTransferOrderStatus transferOrderStatus;
	private WarehouseData fromWarehouseData;
	private WarehouseData toWarehouseData;
	
	private Set<TransferOrderDetailData> transferOrderDetailDatas = new HashSet<TransferOrderDetailData>(0);
	
	/**
	 * 
	 */
	 @SequenceGenerator(name="generator", sequenceName="TBLMTRANSFERORDER_SEQ", allocationSize=1)
	    @Id
	    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
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
	/**
	 * 
	 */
	 @Column(name="TRANSFERORDERNO")
	public String getTransferOrderNo() {
		return transferOrderNo;
	}
	/**
	 * 
	 */
	public void setTransferOrderNo(String transferOrderNo) {
		this.transferOrderNo = transferOrderNo;
	}
	/**
	 * 
	 */
	 @Column(name="CREATEDATE")
	public Timestamp getCreatedate() {
		return createdate;
	}
	/**
	 * 
	 */
	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}
	/**
	 * 
	 */
	 @Column(name="LASTMODIFIEDDATE")
	public Timestamp getUpdatedate() {
		return updatedate;
	}
	/**
	 * 
	 */
	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}
	/**
	 * 
	 */
	 @Column(name="CREATEDBYSTAFFID")
	public String getCreatedby() {
		return createdby;
	}
	/**
	 * 
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	/**
	 * 
	 */
	 @Column(name="LASTMODIFIEDDATEBYSTAFFID")
	public String getUpdatedby() {
		return updatedby;
	}
	/**
	 * 
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	/**
	 * 
	 */
	 @Column(name="FROMWAREHOUSEID")
	public Long getFromWarehouseId() {
		return fromWarehouseId;
	}
	/**
	 * 
	 */
	public void setFromWarehouseId(Long fromWarehouseId) {
		this.fromWarehouseId = fromWarehouseId;
	}
	/**
	 * 
	 */
	 @Column(name="TOWAREHOUSEID")
	public Long getToWarehouseId() {
		return toWarehouseId;
	}
	/**
	 * 
	 */
	public void setToWarehouseId(Long toWarehouseId) {
		this.toWarehouseId = toWarehouseId;
	}
	
	
	
	@Column(name="INVENTORYORDERSTATUSID")
	public String getInventoryOrderStatusId() {
		return inventoryOrderStatusId;
	}
	public void setInventoryOrderStatusId(String inventoryOrderStatusId) {
		this.inventoryOrderStatusId = inventoryOrderStatusId;
	}
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="INVENTORYORDERSTATUSID",insertable=false,updatable=false)
	public InventoryTransferOrderStatus getTransferOrderStatus() {
		return transferOrderStatus;
	}
	public void setTransferOrderStatus(
			InventoryTransferOrderStatus transferOrderStatus) {
		this.transferOrderStatus = transferOrderStatus;
	}
	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/* 
	 * 
	 */
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="FROMWAREHOUSEID",insertable=false,updatable=false)
	public WarehouseData getFromWarehouseData() {
		return fromWarehouseData;
	}
	public void setFromWarehouseData(WarehouseData fromWarehouseData) {
		this.fromWarehouseData = fromWarehouseData;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TOWAREHOUSEID",insertable=false,updatable=false)
	public WarehouseData getToWarehouseData() {
		return toWarehouseData;
	}
	public void setToWarehouseData(WarehouseData toWarehouseData) {
		this.toWarehouseData = toWarehouseData;
	}
	
	
	
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, mappedBy="transferOrderData")
	public Set<TransferOrderDetailData> getTransferOrderDetailDatas() {
		return transferOrderDetailDatas;
	}
	public void setTransferOrderDetailDatas(
			Set<TransferOrderDetailData> transferOrderDetailDatas) {
		this.transferOrderDetailDatas = transferOrderDetailDatas;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransferOrderData [transferOrderId=");
		builder.append(transferOrderId);
		builder.append(", transferOrderNo=");
		builder.append(transferOrderNo);
		builder.append(", createdate=");
		builder.append(createdate);
		builder.append(", updatedate=");
		builder.append(updatedate);
		builder.append(", createdby=");
		builder.append(createdby);
		builder.append(", updatedby=");
		builder.append(updatedby);
		builder.append(", fromWarehouseId=");
		builder.append(fromWarehouseId);
		builder.append(", toWarehouseId=");
		builder.append(toWarehouseId);
		builder.append(", fromWarehouseData=");
		builder.append(fromWarehouseData);
		builder.append(", toWarehouseData=");
		builder.append(toWarehouseData);
		builder.append("]");
		return builder.toString();
	}
	
}
