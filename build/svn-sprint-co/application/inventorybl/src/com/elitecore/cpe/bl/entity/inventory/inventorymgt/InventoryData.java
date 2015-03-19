package com.elitecore.cpe.bl.entity.inventory.inventorymgt;

import java.io.Serializable;
import java.sql.Timestamp;

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

import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;

@Entity
@Cache (usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name="TBLMINVENTORY"
)
@NamedQueries({ 
	@NamedQuery(name = "InventoryData.isAvailable",query ="select o from InventoryData o where o.warehousedata.warehouseId = :warehouseId and  o.itemData.itemId = :itemId and o.inventoryStatusId=2 ")
	
})
public class InventoryData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long inventoryId;
	
	private Long batchId;
	private int inventoryStatusId;
	private WarehouseData warehousedata;
	private ItemData itemData;
	
	private String distributorId;
	
	private Timestamp createdate;
    private Timestamp updatedate;
    private String createdby;
    private String updatedby;
    
    private String errordesc;

    private String partnerRefId;
    private String partnerType;
    private String channelType;
    private String customerRefId;
    private String orderId;
    private String inventoryNo;
    private String externalRefId;
    private char systemgenerated;
   // private String accepted;
  //  private String refurbished;
  //  private String standBy;
  //  private String newed;
    
    private Long warehouseId;
    private Long itemId;
    private Timestamp  reserveddate;
    private String  transferInventoryStatus;
    private String externalBatchNumber;
    
    private String gurrantyWarrantyMode;
    private Timestamp warrantyDate;
    private String warrantyType;
    
    private InventoryStatusData statusData;
    private BatchData batchData;
    
//--Added By Rinkal-start
    private InventorySubStatusData  subStatusData;
	private Long inventorySubStatusId;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SUBSTATUSID",insertable=false,updatable=false)
    public InventorySubStatusData getSubStatusData() {
		return subStatusData;
	}

	public void setSubStatusData(InventorySubStatusData subStatusData) {
		this.subStatusData = subStatusData;
	}
	
	 @Column(name="SUBSTATUSID")
    public Long getInventorySubStatusId() {
		return inventorySubStatusId;
	}

	public void setInventorySubStatusId(Long inventorySubStatusId) {
		this.inventorySubStatusId = inventorySubStatusId;
	}
//--Added By Rinkal-End
	@SequenceGenerator(name="generator", sequenceName="TBLMINVENTORY_SEQ", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name="INVENTORYID")
	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	 @Column(name="BATCHID")
	public Long getBatchId() {
		return batchId;
	}

	 
	 @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="BATCHID" ,insertable=false,updatable=false)
	public BatchData getBatchData() {
		return batchData;
	}

	public void setBatchData(BatchData batchData) {
		this.batchData = batchData;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	 @Column(name="STATUSID")
	public int getInventoryStatusId() {
		return inventoryStatusId;
	}

	
	public void setInventoryStatusId(int inventoryStatusId) {
		this.inventoryStatusId = inventoryStatusId;
	}

	
	 @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="WAREHOUSEID" ,insertable=false,updatable=false)
	public WarehouseData getWarehousedata() {
		return warehousedata;
	}

	public void setWarehousedata(WarehouseData warehousedata) {
		this.warehousedata = warehousedata;
	}

	 @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="RESOURCEID",insertable=false,updatable=false)
	 public ItemData getItemData() {
		 return itemData;
	 }
	 
	 public void setItemData(ItemData itemData) {
		 this.itemData = itemData;
	 }
	

	 @Column(name="DISTRIBUTORID")
	public String getDistributorId() {
		return distributorId;
	}


	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	 @Column(name="CREATEDATE")
	public Timestamp getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	 @Column(name="LASTMODIFIEDDATE")
	public Timestamp getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}

	 @Column(name="CREATEDBYSTAFFID")
	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	 @Column(name="LASTMODIFIEDDATEBYSTAFFID")
	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	 @Column(name="ERRORDESC")
	public String getErrordesc() {
		return errordesc;
	}

	public void setErrordesc(String errordesc) {
		this.errordesc = errordesc;
	}

	 @Column(name="PARTNERREFID")
	public String getPartnerRefId() {
		return partnerRefId;
	}

	public void setPartnerRefId(String partnerRefId) {
		this.partnerRefId = partnerRefId;
	}

	
	@Column(name="PARTNERTYPE")
	public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	@Column(name="CHANNELTYPE")
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	@Column(name="CUTROMERREFID")
	public String getCustomerRefId() {
		return customerRefId;
	}

	public void setCustomerRefId(String customerRefId) {
		this.customerRefId = customerRefId;
	}

	@Column(name="ORDERID")
	public String getOrderNo() {
		return orderId;
	}

	public void setOrderNo(String orderId) {
		this.orderId = orderId;
	}

	@Column(name="INVENTORYNO")
	public String getInventoryNo() {
		return inventoryNo;
	}

	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}

	@Column(name="SYSTEMGENERATED")
	public char getSystemgenerated() {
		return systemgenerated;
	}

	public void setSystemgenerated(char systemgenerated) {
		this.systemgenerated = systemgenerated;
	}

	@Column(name="EXTERNALREFERENCEID")
	public String getExternalRefId() {
		return externalRefId;
	}

	public void setExternalRefId(String externalRefId) {
		this.externalRefId = externalRefId;
	}
	
/*	@Column(name="ACCEPTED")
	public String getAccepted() {
		return accepted;
	}

	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}

	@Column(name="REFURBISHED")
	public String getRefurbished() {
		return refurbished;
	}

	public void setRefurbished(String refurbished) {
		this.refurbished = refurbished;
	}

	@Column(name="STANDBY")
	public String getStandBy() {
		return standBy;
	}

	public void setStandBy(String standBy) {
		this.standBy = standBy;
	}

	@Column(name="NEW")
	public String getNewed() {
		return newed;
	}

	public void setNewed(String newed) {
		this.newed = newed;
	}*/
	
//
//	@Column(name="WAREHOUSEID")
//	public Long getWarehouseId() {
//		return warehouseId;
//	}
//
//	/**
//	 * @param warehouseId the warehouseId to set
//	 */
//	public void setWarehouseId(Long warehouseId) {
//		this.warehouseId = warehouseId;
//	}
	
	@Column(name="RESERVEDDATE")
	public Timestamp getReserveddate() {
		return reserveddate;
	}

	/**
	 * @param reserveddate the reserveddate to set
	 */
	public void setReserveddate(Timestamp reserveddate) {
		this.reserveddate = reserveddate;
	}

	@Column(name="TRANSFERINVENTORYSTATUS")
	public String getTransferInventoryStatus() {
		return transferInventoryStatus;
	}

	/**
	 * @param transferInventoryStatus the transferInventoryStatus to set
	 */
	public void setTransferInventoryStatus(String transferInventoryStatus) {
		this.transferInventoryStatus = transferInventoryStatus;
	}

	@Column(name = "WAREHOUSEID")
	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Column(name = "RESOURCEID")
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STATUSID",insertable=false,updatable=false)
	public InventoryStatusData getStatusData() {
		return statusData;
	}

	public void setStatusData(InventoryStatusData statusData) {
		this.statusData = statusData;
	}

	
	
	
	@Column(name = "EXTERNALBATCHNUMBER")
	public String getExternalBatchNumber() {
		return externalBatchNumber;
	}

	public void setExternalBatchNumber(String externalBatchNumber) {
		this.externalBatchNumber = externalBatchNumber;
	}

	
	
	
	
	@Column(name = "GURANTEEWARRANTYMODE")
	public String getGurrantyWarrantyMode() {
		return gurrantyWarrantyMode;
	}

	public void setGurrantyWarrantyMode(String gurrantyWarrantyMode) {
		this.gurrantyWarrantyMode = gurrantyWarrantyMode;
	}

	@Column(name = "WARRANTYDATE")
	public Timestamp getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(Timestamp warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	@Column(name = "WARRANTYTYPE")
	public String getWarrantyType() {
		return warrantyType;
	}

	public void setWarrantyType(String warrantyType) {
		this.warrantyType = warrantyType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryData [inventoryId=");
		builder.append(inventoryId);
		builder.append(", batchId=");
		builder.append(batchId);
		builder.append(", inventoryStatusId=");
		builder.append(inventoryStatusId);
		builder.append(", warehousedata=");
		builder.append(warehousedata);
		builder.append(", itemData=");
		builder.append(itemData);
		builder.append(", distributorId=");
		builder.append(distributorId);
		builder.append(", createdate=");
		builder.append(createdate);
		builder.append(", updatedate=");
		builder.append(updatedate);
		builder.append(", createdby=");
		builder.append(createdby);
		builder.append(", updatedby=");
		builder.append(updatedby);
		builder.append(", errordesc=");
		builder.append(errordesc);
		builder.append(", partnerRefId=");
		builder.append(partnerRefId);
		builder.append(", partnerType=");
		builder.append(partnerType);
		builder.append(", channelType=");
		builder.append(channelType);
		builder.append(", customerRefId=");
		builder.append(customerRefId);
		builder.append(", orderId=");
		builder.append(orderId);
		builder.append(", inventoryNo=");
		builder.append(inventoryNo);
		builder.append(", externalRefId=");
		builder.append(externalRefId);
		builder.append(", systemgenerated=");
		builder.append(systemgenerated);
/*		builder.append(", accepted=");
		builder.append(accepted);
		builder.append(", refurbished=");
		builder.append(refurbished);
		builder.append(", standBy=");
		builder.append(standBy);
		builder.append(", newed=");
		builder.append(newed);
*/		builder.append(", warehouseId=");
		builder.append(warehouseId);
		builder.append(", itemId=");
		builder.append(itemId);
		builder.append(", reserveddate=");
		builder.append(reserveddate);
		builder.append(", transferInventoryStatus=");
		builder.append(transferInventoryStatus);
		builder.append(", statusData=");
		builder.append(statusData);
		builder.append("]");
		return builder.toString();
	}
	
    
    
}
