package com.elitecore.cpe.bl.entity.inventory.inventorymgt;

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
@Table(name="TBLMBATCH")
public class BatchData implements Serializable{

	private Long batchId;
	private String batchNo;
	private String desc;
    private char systemgenerated;

    private Timestamp createdate;
    private Timestamp updatedate;
    private String createdby;
    private String updatedby;
    
    private Long totalValidInventory;
    private Long totalInvalidInventory;

    @SequenceGenerator(name="generator", sequenceName="TBLMBATCH_SEQ", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name="BATCHID")
	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	@Column(name="BATCHNUMBER")
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name="DESCRIPTION")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(name="SYSTEMGENERATED")
	public char getSystemgenerated() {
		return systemgenerated;
	}

	public void setSystemgenerated(char systemgenerated) {
		this.systemgenerated = systemgenerated;
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

	@Column(name="VALIDCOUNT")
	public Long getTotalValidInventory() {
		return totalValidInventory;
	}

	public void setTotalValidInventory(Long totalValidInventory) {
		this.totalValidInventory = totalValidInventory;
	}

	@Column(name="INVALIDCOUNT")
	public Long getTotalInvalidInventory() {
		return totalInvalidInventory;
	}

	public void setTotalInvalidInventory(Long totalInvalidInventory) {
		this.totalInvalidInventory = totalInvalidInventory;
	}
    
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BatchData [batchId=");
		builder.append(batchId);
		builder.append(", batchNo=");
		builder.append(batchNo);
		builder.append(", desc=");
		builder.append(desc);
		builder.append(", systemgenerated=");
		builder.append(systemgenerated);
		builder.append(", createdate=");
		builder.append(createdate);
		builder.append(", updatedate=");
		builder.append(updatedate);
		builder.append(", createdby=");
		builder.append(createdby);
		builder.append(", updatedby=");
		builder.append(updatedby);
		builder.append(", totalValidInventory=");
		builder.append(totalValidInventory);
		builder.append(", totalInvalidInventory=");
		builder.append(totalInvalidInventory);
		builder.append("]");
		return builder.toString();
	}

    
}
