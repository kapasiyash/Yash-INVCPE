package com.elitecore.cpe.bl.entity.inventory.inventorymgt;

import java.io.Serializable;
import java.sql.Timestamp;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceSubTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.entity.inventory.transfer.InventoryTransferOrderStatus;



@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TBLMORDER")
@NamedQueries({ 
	@NamedQuery(name = "OrderData.searchOrderDataByOrderNo",query ="select o from OrderData o where o.orderNo=:orderNo"),
	@NamedQuery(name = "OrderData.searchAllOrderData",query ="select o from OrderData o ")
})
public class OrderData implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long orderId;
	private String orderNo;
	private Timestamp createdate;
	private Timestamp updatedate;
	private String createdby;
	private String updatedby;	
	private Long fromWarehouseId;
	private Long toWarehouseId;
	private Long resourceTypeId;
	private Long resourceSubTypeId;
	private Long itemId;
	
	private Long quantity;
	private String orderStatusId;
	private String remarks;
	private String transferOrderNo;
	private Long acceptQuantity;
	
	private Long orderType;
	
	private String transferRemarks;
	private Timestamp acceptRejectDate;
	private InventoryTransferOrderStatus OrderStatus;
	private WarehouseData fromWarehouseData;
	private WarehouseData toWarehouseData;
	private ResourceTypeData resourceType;
	private ResourceSubTypeData resourceSubTypeData;
	private ItemData itemData;
	
	
	@SequenceGenerator(name="generator", sequenceName="TBLMORDER_SEQ", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name="PLACEORDERID")
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	 @Column(name="PLACEORDERNO")
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	
	 @Column(name="RESOURCETYPEID")
	public Long getResourceTypeId() {
		return resourceTypeId;
	}
	public void setResourceTypeId(Long resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}
	
	 @Column(name="RESOURCESUBTYPEID")
	public Long getResourceSubTypeId() {
		return resourceSubTypeId;
	}
	public void setResourceSubTypeId(Long resourceSubTypeId) {
		this.resourceSubTypeId = resourceSubTypeId;
	}
	
	
	@Column(name = "RESOURCEID")
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RESOURCEID",insertable=false,updatable=false)
	public ItemData getItemData() {
		return itemData;
	}

	public void setItemData(ItemData itemData) {
		this.itemData = itemData;
	}
	
	 @Column(name="QUANTITY")
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	 @Column(name="ORDERSTATUSID")
	public String getOrderStatusId() {
		return orderStatusId;
	}
	public void setOrderStatusId(String orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	
	 @Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	

	
	
	 @Column(name="ORDERTYPE")
	public Long getOrderType() {
		return orderType;
	}
	public void setOrderType(Long orderType) {
		this.orderType = orderType;
	}
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ORDERSTATUSID",insertable=false,updatable=false)
	public InventoryTransferOrderStatus getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(InventoryTransferOrderStatus orderStatus) {
		OrderStatus = orderStatus;
	}
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="FROMWAREHOUSEID",insertable=false,updatable=false)
	public WarehouseData getFromWarehouseData() {
		return fromWarehouseData;
	}
	public void setFromWarehouseData(WarehouseData fromWarehouseData) {
		this.fromWarehouseData = fromWarehouseData;
	}
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="TOWAREHOUSEID",insertable=false,updatable=false)
	public WarehouseData getToWarehouseData() {
		return toWarehouseData;
	}
	public void setToWarehouseData(WarehouseData toWarehouseData) {
		this.toWarehouseData = toWarehouseData;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "RESOURCETYPEID",insertable=false,updatable=false)
	public ResourceTypeData getResourceType() {
		return resourceType;
	}

	/**
	 * @param resoureceType
	 *            the resoureceType to set
	 */
	public void setResourceType(ResourceTypeData resoureceType) {
		this.resourceType = resoureceType;
	}
	

	

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RESOURCESUBTYPEID",insertable=false,updatable=false)
	public ResourceSubTypeData getResourceSubTypeData() {
		return resourceSubTypeData;
	}

	public void setResourceSubTypeData(ResourceSubTypeData resourceSubTypeData) {
		this.resourceSubTypeData = resourceSubTypeData;
	}

	
	
	
	
	
	public String getTransferOrderNo() {
		return transferOrderNo;
	}
	public void setTransferOrderNo(String transferOrderNo) {
		this.transferOrderNo = transferOrderNo;
	}
	public Long getAcceptQuantity() {
		return acceptQuantity;
	}
	public void setAcceptQuantity(Long acceptQuantity) {
		this.acceptQuantity = acceptQuantity;
	}
	public String getTransferRemarks() {
		return transferRemarks;
	}
	public void setTransferRemarks(String transferRemarks) {
		this.transferRemarks = transferRemarks;
	}
	public Timestamp getAcceptRejectDate() {
		return acceptRejectDate;
	}
	public void setAcceptRejectDate(Timestamp acceptRejectDate) {
		this.acceptRejectDate = acceptRejectDate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderData [orderId=");
		builder.append(orderId);
		builder.append(", orderNo=");
		builder.append(orderNo);
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
		builder.append(", resourceTypeId=");
		builder.append(resourceTypeId);
		builder.append(", resourceSubTypeId=");
		builder.append(resourceSubTypeId);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", orderStatusId=");
		builder.append(orderStatusId);
		builder.append(", remarks=");
		builder.append(remarks);
		builder.append(", transferOrderNo=");
		builder.append(transferOrderNo);
		builder.append(", acceptQuantity=");
		builder.append(acceptQuantity);
		builder.append(", transferRemarks=");
		builder.append(transferRemarks);
		builder.append(", acceptRejectDate=");
		builder.append(acceptRejectDate);
		builder.append(", resourceType=");
		builder.append(resourceType);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
