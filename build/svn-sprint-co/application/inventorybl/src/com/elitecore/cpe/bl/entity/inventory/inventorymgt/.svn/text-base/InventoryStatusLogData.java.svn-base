package com.elitecore.cpe.bl.entity.inventory.inventorymgt;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache (usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name="TBLTINVENTORYSTUATUSLOG"
)
public class InventoryStatusLogData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long inventoryStatusLogId;
	
	private int oldStatusId;
	private int newStatusId;
	private String oldStatusName;
	private String newStatusName;
	
	private Timestamp statusChangedDate;
	private String staffId;
	private Long inventoryId;
	private String remark;
	
	private InventoryData inventoryData;
	
	@SequenceGenerator(name="generator", sequenceName="TBLTINVENTORYSTUATUSLOG_SEQ", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
    @Column(name="INVENTORYSTATUSLOGID")
	public Long getInventoryStatusLogId() {
		return inventoryStatusLogId;
	}
	public void setInventoryStatusLogId(Long inventoryStatusLogId) {
		this.inventoryStatusLogId = inventoryStatusLogId;
	}
	
	@Column(name="OLDSTATUSID")
	public int getOldStatusId() {
		return oldStatusId;
	}
	public void setOldStatusId(int oldStatusId) {
		this.oldStatusId = oldStatusId;
	}
	
	@Column(name="NEWSTATUSID")
	public int getNewStatusId() {
		return newStatusId;
	}
	public void setNewStatusId(int newStatusId) {
		this.newStatusId = newStatusId;
	}
	
	@Column(name="OLDSTATUSNAME")
	public String getOldStatusName() {
		return oldStatusName;
	}
	public void setOldStatusName(String oldStatusName) {
		this.oldStatusName = oldStatusName;
	}
	
	@Column(name="NEWSTATUSNAME")
	public String getNewStatusName() {
		return newStatusName;
	}
	public void setNewStatusName(String newStatusName) {
		this.newStatusName = newStatusName;
	}
	
	@Column(name="STATUSCHANGEDATE")
	public Timestamp getStatusChangedDate() {
		return statusChangedDate;
	}
	public void setStatusChangedDate(Timestamp statusChangedDate) {
		this.statusChangedDate = statusChangedDate;
	}
	
	
	@Column(name="CHANGEBYSTAFFID")
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	@Column(name="INVENTORYID")
	public Long getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="INVENTORYID",insertable=false,updatable=false)
	public InventoryData getInventoryData() {
		return inventoryData;
	}
	public void setInventoryData(InventoryData inventoryData) {
		this.inventoryData = inventoryData;
	}
	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	@Override
	public String toString() {
		return "InventoryStatusLogData [inventoryStatusLogId="
				+ inventoryStatusLogId + ", oldStatusId=" + oldStatusId
				+ ", newStatusId=" + newStatusId + ", oldStatusName="
				+ oldStatusName + ", newStatusName=" + newStatusName
				+ ", statusChangedDate=" + statusChangedDate + ", staffId="
				+ staffId + ", inventoryId=" + inventoryId + ", remark="
				+ remark + "]";
	}
	
	
	
	
	
}
