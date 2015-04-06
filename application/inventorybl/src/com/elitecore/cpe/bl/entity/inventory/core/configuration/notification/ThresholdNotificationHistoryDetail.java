package com.elitecore.cpe.bl.entity.inventory.core.configuration.notification;

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

import com.elitecore.cpe.bl.entity.inventory.inventorymgt.OrderData;
import com.elitecore.cpe.bl.entity.inventory.master.ConfigureThresholdData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceSubTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceTypeData;

@Entity
@Cache (usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name="TBLTTHRESHOLDNOTIHISTORY"
)

@NamedQueries({
	@NamedQuery(name = "ThresholdNotificationHistoryDetail.findById", query = "select o from ThresholdNotificationHistoryDetail o where o.notificationHistoryId = :notificationHistoryId")
})
public class ThresholdNotificationHistoryDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private Long notificationHistoryId;
	private Long warehouseThresholdId;
	private Long resourceTypeId;
	private Long resourceSubTypeId;
	private Long itemId;
	private Character notificationSent;
	private Long notificationAuditId;
	private Character placeOrderGenerated;
	private String placeOrderNo;
	private String createdBy;
	private Timestamp createDate;
	
	private ConfigureThresholdData configureThresholdData;
	private ResourceTypeData resourceTypeData;
	private ResourceSubTypeData resourceSubTypeData;
	private ItemData itemData;
	private NotificationAudit notificationAudit;
	
	
	
	
	@SequenceGenerator(name = "generator", sequenceName = "TBLTTHRESHOLDNOTIHISTORY_SEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "NOTIFICATIONHISTORYID")
	public Long getNotificationHistoryId() {
		return notificationHistoryId;
	}
	public void setNotificationHistoryId(Long notificationHistoryId) {
		this.notificationHistoryId = notificationHistoryId;
	}
	
	@Column(name = "WAREHOUSTHRESHOLDID")
	public Long getWarehouseThresholdId() {
		return warehouseThresholdId;
	}
	public void setWarehouseThresholdId(Long warehouseThresholdId) {
		this.warehouseThresholdId = warehouseThresholdId;
	}
	
	@Column(name = "RESOURCETYPEID")
	public Long getResourceTypeId() {
		return resourceTypeId;
	}
	public void setResourceTypeId(Long resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}
	
	
	@Column(name = "RESOURCESUBTYPEID")
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
	
	
	@Column(name = "NOTIFICATIONSENT")
	public Character getNotificationSent() {
		return notificationSent;
	}
	public void setNotificationSent(Character notificationSent) {
		this.notificationSent = notificationSent;
	}
	
	
	@Column(name = "NOTIFICATIONAUDITID")
	public Long getNotificationAuditId() {
		return notificationAuditId;
	}
	public void setNotificationAuditId(Long notificationAuditId) {
		this.notificationAuditId = notificationAuditId;
	}
	
	
	@Column(name = "PLACEORDERGENERATED")
	public Character getPlaceOrderGenerated() {
		return placeOrderGenerated;
	}
	public void setPlaceOrderGenerated(Character placeOrderGenerated) {
		this.placeOrderGenerated = placeOrderGenerated;
	}
	
	
	@Column(name = "PLACEORDERNO")
	public String getPlaceOrderNo() {
		return placeOrderNo;
	}
	public void setPlaceOrderNo(String placeOrderNo) {
		this.placeOrderNo = placeOrderNo;
	}
	
	
	@Column(name = "CREATEDBYSTAFFID")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	@Column(name = "CREATEDATE")
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="WAREHOUSTHRESHOLDID",insertable=false,updatable=false)
	public ConfigureThresholdData getConfigureThresholdData() {
		return configureThresholdData;
	}
	public void setConfigureThresholdData(
			ConfigureThresholdData configureThresholdData) {
		this.configureThresholdData = configureThresholdData;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RESOURCETYPEID",insertable=false,updatable=false)
	public ResourceTypeData getResourceTypeData() {
		return resourceTypeData;
	}
	public void setResourceTypeData(ResourceTypeData resourceTypeData) {
		this.resourceTypeData = resourceTypeData;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RESOURCESUBTYPEID",insertable=false,updatable=false)
	public ResourceSubTypeData getResourceSubTypeData() {
		return resourceSubTypeData;
	}
	public void setResourceSubTypeData(ResourceSubTypeData resourceSubTypeData) {
		this.resourceSubTypeData = resourceSubTypeData;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RESOURCEID",insertable=false,updatable=false)
	public ItemData getItemData() {
		return itemData;
	}
	public void setItemData(ItemData itemData) {
		this.itemData = itemData;
	}
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="NOTIFICATIONAUDITID",insertable=false,updatable=false)
	public NotificationAudit getNotificationAudit() {
		return notificationAudit;
	}
	public void setNotificationAudit(NotificationAudit notificationAudit) {
		this.notificationAudit = notificationAudit;
	}
	
	
	@Override
	public String toString() {
		return "ThresholdNotificationHistoryDetail [notificationHistoryId="
				+ notificationHistoryId + ", warehouseThresholdId="
				+ warehouseThresholdId + ", resourceTypeId=" + resourceTypeId
				+ ", resourceSubTypeId=" + resourceSubTypeId + ", itemId="
				+ itemId + ", notificationSent=" + notificationSent
				+ ", notificationAuditId=" + notificationAuditId
				+ ", placeOrderGenerated=" + placeOrderGenerated
				+ ", placeOrderNo=" + placeOrderNo + ", createdBy=" + createdBy
				+ ", createDate=" + createDate + "]";
	}
	
	
	
	
	

}
