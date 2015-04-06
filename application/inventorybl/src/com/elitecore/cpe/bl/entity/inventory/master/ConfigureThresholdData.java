/**
 * 
 */
package com.elitecore.cpe.bl.entity.inventory.master;

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

/**
 * @author Joel.Macwan
 *
 */
@Entity
@Cache (usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name="TBLMWAREHOUSEALERT"
)
@NamedQueries({ 
	@NamedQuery(name = "ConfigureThresholdData.findByThreeId",query ="select o from ConfigureThresholdData o where o.warehousedata.warehouseId = :warehouseId and o.resourceTypedata.resourceTypeId=:resourceTypeId and o.resourceSubTypeData.resourceSubTypeId=:resourceSubTypeId and  o.systemgenerated = 'N'"),
	@NamedQuery(name = "ConfigureThresholdData.findByTwoId",query ="select o from ConfigureThresholdData o where o.warehousedata.warehouseId = :warehouseId and o.resourceTypedata.resourceTypeId=:resourceTypeId and o.resourceSubTypeData.resourceSubTypeId=null and  o.systemgenerated = 'N'"),
	@NamedQuery(name = "ConfigureThresholdData.findConfigureThresholdDataByThresholdID",query ="select o from ConfigureThresholdData o where o.thresholdID=:thresholdID")
})
public class ConfigureThresholdData implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long thresholdID;
	private Long warehouseId; 
	private Long resourceTypeId;
	private Long resourceSubTypeId;
	private Long itemId;
	
	private WarehouseData warehousedata;
	private ResourceTypeData resourceTypedata;
	private ResourceSubTypeData resourceSubTypeData;
	private ItemData itemData;
	
	private Character automaticOrder;
	private Long thresholdValue;
	private Timestamp createdate;
	private Timestamp updatedate;
	private String createdby;
	private String updatedby;
	private String thresholdType;
	private String systemgenerated;
	
	private String email;
	private String mobile;
	private Long quantity;
	
	@SequenceGenerator(name = "generator", sequenceName = "TBLMWAREHOUSEALERT_SEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "WAREHOUSTHRESHOLDID")
	
	public Long getThresholdID() {
		return thresholdID;
	}
	
	public void setThresholdID(Long thresholdID) {
		this.thresholdID = thresholdID;
	}
	
//	@ManyToOne(cascade=CascadeType.ALL)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "WAREHOUSEID",insertable=false,updatable=false)
	public WarehouseData getWarehousedata() {
		return warehousedata;
	}
	
	public void setWarehousedata(WarehouseData warehousedata) {
		this.warehousedata = warehousedata;
	}
	
	
	@Column(name = "WAREHOUSEID")
	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	
	
	@Column(name = "AUTOMATICORDER")
	public Character getAutomaticOrder() {
		return automaticOrder;
	}

	public void setAutomaticOrder(Character automaticOrder) {
		this.automaticOrder = automaticOrder;
	}

	@Column(name = "THRESHOLDVALUE")
	public Long getThresholdValue() {
		return thresholdValue;
	}
	
	
	public void setThresholdValue(Long thresholdValue) {
		this.thresholdValue = thresholdValue;
	}
	
	
	
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
	@Column(name = "QUANTITY")
	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
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
	
	@Column(name = "SYSTEMGENERATED")
	public String getSystemgenerated() {
		return systemgenerated;
	}
	
	public void setSystemgenerated(String systemgenerated) {
		this.systemgenerated = systemgenerated;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	/**
	 * @return the resourceTypedata
	 */
/*	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "RESOURCETYPEID",insertable=false,updatable=false)
*/	 @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="RESOURCETYPEID",insertable=false,updatable=false)
	public ResourceTypeData getResourceTypedata() {
		return resourceTypedata;
	}

	/**
	 * @param resourceTypedata the resourceTypedata to set
	 */
	public void setResourceTypedata(ResourceTypeData resourceTypedata) {
		this.resourceTypedata = resourceTypedata;
	}

	
	@Column(name = "RESOURCETYPEID")
	public Long getResourceTypeId() {
		return resourceTypeId;
	}

	public void setResourceTypeId(Long resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
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

	/**
	 * @return the resourceSubTypeData
	 */
/*	@ManyToOne(cascade=CascadeType.ALL)*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "RESOURCESUBTYPEID",insertable=false,updatable=false)
	public ResourceSubTypeData getResourceSubTypeData() {
		return resourceSubTypeData;
	}

	/**
	 * @param resourceSubTypeData the resourceSubTypeData to set
	 */
	public void setResourceSubTypeData(ResourceSubTypeData resourceSubTypeData) {
		this.resourceSubTypeData = resourceSubTypeData;
	}
	
	
	
	@Column(name = "RESOURCESUBTYPEID")
	public Long getResourceSubTypeId() {
		return resourceSubTypeId;
	}

	public void setResourceSubTypeId(Long resourceSubTypeId) {
		this.resourceSubTypeId = resourceSubTypeId;
	}

	/**
	 * @return the thresholdType
	 */
	@Column(name = "THRESHOLDTYPE")
	public String getThresholdType() {
		return thresholdType;
	}

	/**
	 * @param thresholdType the thresholdType to set
	 */
	public void setThresholdType(String thresholdType) {
		this.thresholdType = thresholdType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConfigureThresholdData [thresholdID=" + thresholdID
				+ ", warehousedata=" + warehousedata + ", resourceTypedata="
				+ resourceTypedata + ", resourceSubTypeData="
				+ resourceSubTypeData + ", thresholdValue=" + thresholdValue
				+ ", createdate=" + createdate + ", updatedate=" + updatedate
				+ ", createdby=" + createdby + ", updatedby=" + updatedby
				+ ", thresholdType=" + thresholdType + ", systemgenerated="
				+ systemgenerated + "]";
	}
	
	
	
}
