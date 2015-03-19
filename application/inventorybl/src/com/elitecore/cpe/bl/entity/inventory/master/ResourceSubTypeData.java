/**
 * 
 */
package com.elitecore.cpe.bl.entity.inventory.master;

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

/**
 * @author Joel.Macwan
 * 
 */

@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TBLSRESOURCESUBTYPE")
@NamedQueries({ 
	@NamedQuery(name = "ResourceSubTypeData.findByResourceTypeId",query ="select o from ResourceSubTypeData o where o.resourceType.resourceTypeId = :resourceTypeId and  o.systemgenerated = 'N'"),
	@NamedQuery(name = "ResourceSubTypeData.findById",query ="select o from ResourceSubTypeData o where o.resourceSubTypeId = :resourceSubTypeId and  o.systemgenerated = 'N'")
})
public class ResourceSubTypeData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long resourceSubTypeId;
//	private Long resourceTypeId;
	private String systemgenerated;
	private String name;
	private String alias;
	private String description;

	private Timestamp createdate;
	private Timestamp lastmodifieddate;
	private String createdby;
	private String lastmodifiedby;
	
	private ResourceTypeData resourceType;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "TBLSRESOURCESUBTYPE_SEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "RESOURCESUBTYPEID")	
	public Long getResourceSubTypeId() {
		return resourceSubTypeId;
	}
	
	public void setResourceSubTypeId(Long resourceSubTypeId) {
		this.resourceSubTypeId = resourceSubTypeId;
	}
	
	
	/*@Column(name = "RESOURCETYPEID")
	public Long getResourceTypeId() {
		return resourceTypeId;
	}

	*//**
	 * @param resourceTypeId the resourceTypeId to set
	 *//*
	public void setResourceTypeId(Long resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}*/

	
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
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RESOURCETYPEID")
	public ResourceTypeData getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceTypeData resourceType) {
		this.resourceType = resourceType;
	}
	
	@Column(name = "CREATEDATE")
	public Timestamp getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	@Column(name = "LASTMODIFIEDDATE")
	public Timestamp getLastmodifieddate() {
		return lastmodifieddate;
	}

	public void setLastmodifieddate(Timestamp lastmodifieddate) {
		this.lastmodifieddate = lastmodifieddate;
	}

	@Column(name = "CREATEDBY")
	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	@Column(name = "LASTMODIFIEDBY")
	public String getLastmodifiedby() {
		return lastmodifiedby;
	}

	public void setLastmodifiedby(String lastmodifiedby) {
		this.lastmodifiedby = lastmodifiedby;
	}
	

	@Override
	public String toString() {
		return "ResourceSubTypeData [resourceSubTypeId=" + resourceSubTypeId
				+ ", systemgenerated=" + systemgenerated + ", name=" + name
				+ ", alias=" + alias + ", description=" + description + "]";
	}

	
	
}
