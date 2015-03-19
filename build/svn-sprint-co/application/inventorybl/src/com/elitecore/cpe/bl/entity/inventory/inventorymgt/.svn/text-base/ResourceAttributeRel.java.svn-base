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

import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;

@Entity
@Table(name="TBLMRESOURCEATTRIBUTEREL"
)
public class ResourceAttributeRel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long resourceAttributeRelId;
	private ItemData itemData;
	private AttributeData attribute;
	
	public ResourceAttributeRel() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public ResourceAttributeRel(ItemData itemData, AttributeData attribute) {
		super();
		this.itemData = itemData;
		this.attribute = attribute;
	}




	@SequenceGenerator(name="generator", sequenceName="TBLMRESOURCEATTRIBUTEREL_SEQ", allocationSize=1)
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
	@Column(name="RESOURCEATTRRELID")
	public Long getResourceAttributeRelId() {
		return resourceAttributeRelId;
	}
	public void setResourceAttributeRelId(Long resourceAttributeRelId) {
		this.resourceAttributeRelId = resourceAttributeRelId;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RESOURCEID")
	public ItemData getItemData() {
		return itemData;
	}
	public void setItemData(ItemData itemData) {
		this.itemData = itemData;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ATTRIBUTEID")
	public AttributeData getAttribute() {
		return attribute;
	}


	public void setAttribute(AttributeData attribute) {
		this.attribute = attribute;
	}
	
	
	
	

}
