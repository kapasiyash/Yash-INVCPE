/**
 * 
 */
package com.elitecore.cpe.bl.entity.inventory.master;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "TBLSRESOURCECATEGORY")
public class ResourceCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long resourceCategoryId;
	private String systemgenerated;
	private String name;
	private String alias;
	private String description;

	
	@SequenceGenerator(name = "generator", sequenceName = "TBLSRESOURCECATEGORY_SEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "RESOURCECATEGORYID")
	
	
	/**
	 * @return the resourceCategoryId
	 */
	public Long getResourceCategoryId() {
		return resourceCategoryId;
	}

	/**
	 * @param resourceTypeId the resourceCategoryId to set
	 */
	public void setResourceCategoryId(Long resourceCategoryId) {
		this.resourceCategoryId = resourceCategoryId;
	}

	
	/**
	 * @return the systemgenerated
	 */
	@Column(name = "SYSTEMGENERATED")
	public String getSystemgenerated() {
		return systemgenerated;
	}

	
	/**
	 * @param systemgenerated
	 *            the systemgenerated to set
	 */
	public void setSystemgenerated(String systemgenerated) {
		this.systemgenerated = systemgenerated;
	}

	/**
	 * @return the name
	 */
	@Column(name = "NAME", unique = true)
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the alias
	 */
	@Column(name = "ALIAS")
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the description
	 */
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResourceTypeData [resourceCategoryId=" + resourceCategoryId
				+ ", systemgenerated=" + systemgenerated + ", name=" + name
				+ ", alias=" + alias + ", description=" + description + "]";
	}
}
