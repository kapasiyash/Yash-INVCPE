package com.elitecore.cpe.bl.vo.master;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.elitecore.cpe.bl.vo.inventorymgt.ResourceAttributeVO;

public class ItemVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long itemId; 
	private String name; 
	private String alias;
	private String modelnumber;	
	private String description; 
	
	private Timestamp createdate;
    private Timestamp  updatedate;
    private String createdby;
    private String updatedby;
    private String systemgenerated;
    private String editable;
    private String vendor;
    private String referenceID;
    private String resourceNumber;
    private ResourceTypeVO resourceTypeVO;
    private ResourceCategoryVO resourceCategoryVO;
    private List<ResourceAttributeVO> attributeVO;
    
    private Long inventoryGeneration;
    private boolean isAllowedPrefixChange;
    
    private String prefix;
    private String reason;
    
    
    
	public boolean isAllowedPrefixChange() {
		return isAllowedPrefixChange;
	}
	public void setAllowedPrefixChange(boolean isAllowedPrefixChange) {
		this.isAllowedPrefixChange = isAllowedPrefixChange;
	}
	public Long getInventoryGeneration() {
		return inventoryGeneration;
	}
	public void setInventoryGeneration(Long inventoryGeneration) {
		this.inventoryGeneration = inventoryGeneration;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getModelnumber() {
		return modelnumber;
	}
	public void setModelnumber(String modelnumber) {
		this.modelnumber = modelnumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}
	public Timestamp getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public String getSystemgenerated() {
		return systemgenerated;
	}
	public void setSystemgenerated(String systemgenerated) {
		this.systemgenerated = systemgenerated;
	}
	public String getEditable() {
		return editable;
	}
	public void setEditable(String editable) {
		this.editable = editable;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getReferenceID() {
		return referenceID;
	}
	public void setReferenceID(String referenceID) {
		this.referenceID = referenceID;
	}
	
	
	public String getResourceNumber() {
		return resourceNumber;
	}
	public void setResourceNumber(String externalReferenceID) {
		this.resourceNumber = externalReferenceID;
	}
	public ResourceCategoryVO getResourceCategoryVO() {
		return resourceCategoryVO;
	}
	public void setResourceCategoryVO(ResourceCategoryVO resourceCategoryVO) {
		this.resourceCategoryVO = resourceCategoryVO;
	}
	/**
	 * @return the resourceTypeVO
	 */
	public ResourceTypeVO getResourceTypeVO() {
		return resourceTypeVO;
	}
	/**
	 * @param resourceTypeVO the resourceTypeVO to set
	 */
	public void setResourceTypeVO(ResourceTypeVO resourceTypeVO) {
		this.resourceTypeVO = resourceTypeVO;
	}
	
	/**
	 * @return the prefix
	 */
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
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	public List<ResourceAttributeVO> getAttributeVO() {
		return attributeVO;
	}
	public void setAttributeVO(List<ResourceAttributeVO> attributeVO) {
		this.attributeVO = attributeVO;
	}
	@Override
	public String toString() {
		return "ItemVO [itemId=" + itemId + ", name=" + name + ", alias="
				+ alias + ", modelnumber=" + modelnumber + ", description="
				+ description + ", createdate=" + createdate + ", updatedate="
				+ updatedate + ", createdby=" + createdby + ", updatedby="
				+ updatedby + ", systemgenerated=" + systemgenerated
				+ ", editable=" + editable + ", vendor=" + vendor
				+ ", referenceID=" + referenceID + ", resourceTypeVO="
				+ resourceTypeVO + ", prefix=" + prefix + ", reason=" + reason
				+ "]";
	}


}
