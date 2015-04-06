package com.elitecore.cpe.bl.entity.inventory.master;

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

import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.ResourceAttributeRel;

@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TBLMRESOURCE")
@NamedQueries({ 
	@NamedQuery(name = "ItemData.findItem",query ="select o from ItemData o where o.name = :name and o.resourceType.name=:resourceType and o.resourceSubTypeData.name=:resourceSubTypeData and  o.systemgenerated = 'N'"),
	@NamedQuery(name = "ItemData.checkUnique",query ="select o from ItemData o where o.resourceTypeId = :resourceTypeId and o.resourceSubTypeId =:resourceSubTypeId and o.modelnumber =:modelnumber and  o.vendor = :vendor"),
	@NamedQuery(name = "ItemData.checkUniqueIgnoringResource",query ="select o from ItemData o where o.resourceTypeId = :resourceTypeId and o.resourceSubTypeId =:resourceSubTypeId and o.modelnumber =:modelnumber and  o.vendor = :vendor and o.itemId not in :itemId")
})
public class ItemData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long itemId;
	private String name;
	private String alias;
	private String modelnumber;
	private String description;

	private Timestamp createdate;
	private Timestamp updatedate;
	private String createdby;
	private String updatedby;
	private String systemgenerated;
	private String editable;
	private String vendor;
	private String referenceID;
	private String resourceNumber;
	
	private Long resourceTypeId;
	private Long resourceSubTypeId;
	private Long resourceCategoryId;
	
	// private String
	private ResourceTypeData resourceType;
	private ResourceCategory resourceCategory;
	private ResourceSubTypeData resourceSubTypeData;
	private String prefix;
	private String reason;
	
	private Set<InventoryData> inventoryDatas = new HashSet<InventoryData>(0);
	
	private Set<ResourceAttributeRel> resourceAttributeRels = new HashSet<ResourceAttributeRel>(0);
	

	@SequenceGenerator(name = "generator", sequenceName = "TBLMRESOURCE_SEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "RESOURCEID")

	/**
	 * @return the itemId
	 */
	public Long getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	@Column(name = "NAME", unique = true)
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ALIAS")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Column(name = "MODEL")
	public String getModelnumber() {
		return modelnumber;
	}

	public void setModelnumber(String modelnumber) {
		this.modelnumber = modelnumber;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Column(name = "EDITABLE")
	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	@Column(name = "VENDOR")
	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	@Column(name = "REFERENCEID", unique = true)
	public String getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(String referenceID) {
		this.referenceID = referenceID;
	}



	@Column(name = "RESOURCENUMBER", unique = true)
	public String getResourceNumber() {
		return resourceNumber;
	}

	public void setResourceNumber(String externalReferenceId) {
		this.resourceNumber = externalReferenceId;
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
	
	@Column(name = "RESOURCECATEGORYID")
	public Long getResourceCategoryId() {
		return resourceCategoryId;
	}

	public void setResourceCategoryId(Long resourceCategoryId) {
		this.resourceCategoryId = resourceCategoryId;
	}

	
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, mappedBy="itemData")
	public Set<ResourceAttributeRel> getResourceAttributeRels() {
		return resourceAttributeRels;
	}

	public void setResourceAttributeRels(
			Set<ResourceAttributeRel> resourceAttributeRels) {
		this.resourceAttributeRels = resourceAttributeRels;
	}

	/**
	 * @return the resoureceType
	 */
	@ManyToOne
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
    @JoinColumn(name="RESOURCECATEGORYID",insertable=false,updatable=false)
	public ResourceCategory getResourceCategory() {
		return resourceCategory;
	}

	public void setResourceCategory(ResourceCategory resourceCategory) {
		this.resourceCategory = resourceCategory;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RESOURCESUBTYPEID",insertable=false,updatable=false)
	public ResourceSubTypeData getResourceSubTypeData() {
		return resourceSubTypeData;
	}

	public void setResourceSubTypeData(ResourceSubTypeData resourceSubTypeData) {
		this.resourceSubTypeData = resourceSubTypeData;
	}

	/**
	 * @return the prefix
	 */
	@Column(name = "PREFIX", unique = true)
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	@Column(name="REASON")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, mappedBy="itemData")	
	public Set<InventoryData> getInventoryDatas() {
		return inventoryDatas;
	}

	public void setInventoryDatas(Set<InventoryData> inventoryDatas) {
		this.inventoryDatas = inventoryDatas;
	}
	
	
	@Override
	public String toString() {
		return "ItemData [itemId=" + itemId + ", name=" + name + ", alias="
				+ alias + ", modelnumber=" + modelnumber + ", description="
				+ description + ", createdate=" + createdate + ", updatedate="
				+ updatedate + ", createdby=" + createdby + ", updatedby="
				+ updatedby + ", systemgenerated=" + systemgenerated
				+ ", editable=" + editable + ", vendor=" + vendor
				+ ", referenceID=" + referenceID + ", resoureceType="
				+ resourceType + ", prefix=" + prefix + ", reason=" + reason
				+ "]";
	}

}
