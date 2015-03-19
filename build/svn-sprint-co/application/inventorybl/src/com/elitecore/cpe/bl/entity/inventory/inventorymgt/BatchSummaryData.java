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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.elitecore.cpe.bl.entity.inventory.master.ItemData;


@Entity
@Cache (usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name="TBLMBATCHSUMMARY")
public class BatchSummaryData implements Serializable{

	private Long id;
	private BatchData batchData;
	private ItemData resource;
	private String numberFrom;
	private String numberTo;
	private Long totalInvetoryByItem;
	private Long resourceId;
	private Long batchId;
	
	@SequenceGenerator(name="generator", sequenceName="TBLMBATCHSUMMARY_SEQ", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name="ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="BATCHID",insertable=false,updatable=false)
	public BatchData getBatchData() {
		return batchData;
	}
	public void setBatchData(BatchData batchData) {
		this.batchData = batchData;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RESOURCEID" ,insertable=false,updatable=false)
	public ItemData getResource() {
		return resource;
	}
	public void setResource(ItemData resource) {
		this.resource = resource;
	}
	
	@Column(name="NUMBERFROM")
	public String getNumberFrom() {
		return numberFrom;
	}
	public void setNumberFrom(String numberFrom) {
		this.numberFrom = numberFrom;
	}
	
	@Column(name="NUMBERTO")
	public String getNumberTo() {
		return numberTo;
	}
	public void setNumberTo(String numberTo) {
		this.numberTo = numberTo;
	}
	
	@Column(name="NUMBERCOUNT")
	public Long getTotalInvetoryByItem() {
		return totalInvetoryByItem;
	}
	public void setTotalInvetoryByItem(Long totalInvetoryByItem) {
		this.totalInvetoryByItem = totalInvetoryByItem;
	}
	
	@Column(name="RESOURCEID")
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	
	
	/**
	 * 
	 */
	@Column(name = "BATCHID")
	public Long getBatchId() {
		return batchId;
	}
	/**
	 * 
	 */
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	/* 
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BatchSummaryData [id=");
		builder.append(id);
		builder.append(", batchData=");
		builder.append(batchData);
		builder.append(", resource=");
		builder.append(resource);
		builder.append(", numberFrom=");
		builder.append(numberFrom);
		builder.append(", numberTo=");
		builder.append(numberTo);
		builder.append(", totalInvetoryByItem=");
		builder.append(totalInvetoryByItem);
		builder.append(", resourceId=");
		builder.append(resourceId);
		builder.append(", batchId=");
		builder.append(batchId);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
