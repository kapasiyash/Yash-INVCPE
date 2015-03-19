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
@Table(name="TBLMATTRIBUTE"
)
public class AttributeData implements Serializable{

	private Long attributeId;
	
	private String name; 
	private String usedBy;
	
	private Timestamp createdate;
    private Timestamp  updatedate;
    private String createdby;
    private String updatedby;
    
    private String systemgenerated;
    
    private char mandatory;
    private String dataType;
    private String dataValue;
    private String reason;
    private char unique;
    
    
    @SequenceGenerator(name="generator", sequenceName="TBLMATTRIBUTE_SEQ", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name="ATTRIBUTEID")
	public Long getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}

	 @Column(name="NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	 @Column(name="ATTRIBUTEREL")
	public String getUsedBy() {
		return usedBy;
	}

	public void setUsedBy(String usedBy) {
		this.usedBy = usedBy;
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

	@Column(name="MANDATORY")
	public char getMandatory() {
		return mandatory;
	}

	public void setMandatory(char mandatory) {
		this.mandatory = mandatory;
	}

	@Column(name="DATATYPE")
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name="SOURCEVALUE")
	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	 @Column(name="REASON")
		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}
	
	@Column(name="ISUNIQUE")
	public char getUnique() {
			return unique;
		}

		public void setUnique(char unique) {
			this.unique = unique;
		}

	@Override
	public String toString() {
		return "AttributeData [attributeId=" + attributeId + ", name=" + name
				+ ", usedBy=" + usedBy + ", createdate=" + createdate
				+ ", updatedate=" + updatedate + ", createdby=" + createdby
				+ ", updatedby=" + updatedby + ", systemgenerated="
				+ systemgenerated + ", mandatory=" + mandatory + ", dataType="
				+ dataType + ", dataValue=" + dataValue + ", reason=" + reason
				+ ", unique=" + unique + "]";
	}
	
    
    
}
