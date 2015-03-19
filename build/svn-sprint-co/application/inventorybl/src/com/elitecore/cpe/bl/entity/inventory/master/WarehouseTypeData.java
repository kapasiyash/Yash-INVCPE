package com.elitecore.cpe.bl.entity.inventory.master;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache (usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name="TBLSWAREHOUSETYPE"
)
public class WarehouseTypeData implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private Long warehouseTypeId; 
	private String name; 
	private String alias; 
	private String description; 
	
	private Timestamp createdate;
    private Timestamp  updatedate;
    private String createdby;
    private String updatedby;
    
    private String systemgenerated;
    private String reason;
    
    @SequenceGenerator(name="generator", sequenceName="TBLSWAREHOUSETYPE_SEQ", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name="WAREHOUSETYPEID")
	public Long getWarehouseTypeId() {
		return warehouseTypeId;
	}
	public void setWarehouseTypeId(Long warehouseTypeId) {
		this.warehouseTypeId = warehouseTypeId;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="ALIAS")
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	@Column(name="SYSTEMGENERATED")
	public String getSystemgenerated() {
		return systemgenerated;
	}
	public void setSystemgenerated(String systemgenerated) {
		this.systemgenerated = systemgenerated;
	}
	
	@Column(name="REASON")
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
	@Override
	public String toString() {
		return "WarehouseTypeData [warehouseTypeId=" + warehouseTypeId
				+ ", name=" + name + ", alias=" + alias + ", description="
				+ description + ", createdate=" + createdate + ", updatedate="
				+ updatedate + ", createdby=" + createdby + ", updatedby="
				+ updatedby + ", systemgenerated=" + systemgenerated
				+ ", reason=" + reason + "]";
	}
	
	
}
