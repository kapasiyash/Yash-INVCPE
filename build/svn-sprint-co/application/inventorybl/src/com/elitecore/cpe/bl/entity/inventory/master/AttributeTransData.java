package com.elitecore.cpe.bl.entity.inventory.master;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache (usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name="TBLTATTRIBUTETRANS"
)
public class AttributeTransData implements Serializable{

	private int attributeTransId;
	
	private Long referenceId;
	private AttributeData attributeData;
	private Long attributeId;
	private String value;
	private String tableRefName;
	
  @SequenceGenerator(name="generator", sequenceName="TBLTATTRIBUTETRANS_SEQ", allocationSize=1)
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
  @Column(name="ID")
	public int getAttributeTransId() {
		return attributeTransId;
	}
	public void setAttributeTransId(int attributeTransId) {
		this.attributeTransId = attributeTransId;
	}
	
	@Column(name="REFERENCEID")
	public Long getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}
	
	 @Column(name="ATTRIBUTEID")
	public Long getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ATTRIBUTEID",insertable=false,updatable=false)
	public AttributeData getAttributeData() {
		return attributeData;
	}
	public void setAttributeData(AttributeData attributeData) {
		this.attributeData = attributeData;
	}
	
	@Column(name="VALUE")
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(name="TABLEREFNAME")
	public String getTableRefName() {
		return tableRefName;
	}
	public void setTableRefName(String tableRefName) {
		this.tableRefName = tableRefName;
	}
	
	@Override
	public String toString() {
		return "AttributeTransData [attributeTransId=" + attributeTransId
				+ ", referenceId=" + referenceId + ", attributeData="
				+ attributeData + ", value=" + value + ", tableRefName="
				+ tableRefName + "]";
	}
	
	
	
}
