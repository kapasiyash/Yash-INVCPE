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


@Entity
@Cache (usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name="TBLMWAREHOUSE"
)
@NamedQueries({ 
	@NamedQuery(name = "WarehouseData.findAllCentralTypeWH",query ="select o from WarehouseData o where o.warehouseTypeData.alias = 'CENTRAL' and  o.systemgenerated = 'N'"),
	@NamedQuery(name = "WarehouseData.findChildWH",query ="select o from WarehouseData o where o.parentWarehouse.warehouseId = :parentWarehouseId and  o.systemgenerated = 'N'")
})
public class WarehouseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long warehouseId; 
	private String name;
	private String warehouseCode;
	private String alias; 
	private String description; 
	
	private Timestamp createdate;
    private Timestamp  updatedate;
    private String createdby;
    private String updatedby;
    
    private String systemgenerated;
    private String editable;
    
    private String location;
    private WarehouseData parentWarehouse;
    private String reason;
    private WarehouseTypeData warehouseTypeData;
    
    private Set<InventoryData> inventoryDatas = new HashSet<InventoryData>(0);
    
    
    private String owner;
    private String contactNo;
    private String emailId;
    
    @SequenceGenerator(name="generator", sequenceName="TBLMWAREHOUSE_SEQ", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name="WAREHOUSEID")
	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Column(name="NAME",unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	@Column(name="WAREHOUSECODE",unique=true)
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	@Column(name="ALIAS",unique=true)
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

	@Column(name="EDITABLE")
	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	@Column(name="LOCATION")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENTWAREHOUSEID")
	public WarehouseData getParentWarehouse() {
		return parentWarehouse;
	}
	
	public void setParentWarehouse(WarehouseData parentWarehouse) {
		this.parentWarehouse = parentWarehouse;
	}
	
	@Column(name="REASON")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	@Column(name="EMAILID")
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="WAREHOUSETYPEID")
	public WarehouseTypeData getWarehouseTypeData() {
		return warehouseTypeData;
	}
	
	public void setWarehouseTypeData(WarehouseTypeData warehouseTypeData) {
		this.warehouseTypeData = warehouseTypeData;
	}
	
	@Column(name="OWNER")
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(name="CONTACTNO")
	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}



	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, mappedBy="warehousedata")	
	public Set<InventoryData> getInventoryDatas() {
		return inventoryDatas;
	}

	public void setInventoryDatas(Set<InventoryData> inventoryDatas) {
		this.inventoryDatas = inventoryDatas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WarehouseData [warehouseId=");
		builder.append(warehouseId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", alias=");
		builder.append(alias);
		builder.append(", description=");
		builder.append(description);
		builder.append(", createdate=");
		builder.append(createdate);
		builder.append(", updatedate=");
		builder.append(updatedate);
		builder.append(", createdby=");
		builder.append(createdby);
		builder.append(", updatedby=");
		builder.append(updatedby);
		builder.append(", systemgenerated=");
		builder.append(systemgenerated);
		builder.append(", editable=");
		builder.append(editable);
		builder.append(", location=");
		builder.append(location);
		builder.append(", parentWarehouse=");
		builder.append(parentWarehouse);
		builder.append(", reason=");
		builder.append(reason);
		builder.append(", warehouseTypeData=");
		builder.append(warehouseTypeData);
		builder.append(", owner=");
		builder.append(owner);
		builder.append(", contactNo=");
		builder.append(contactNo);
		builder.append(", emailId=");
		builder.append(emailId);
		builder.append("]");
		return builder.toString();
	}


    
    
    
	
}
