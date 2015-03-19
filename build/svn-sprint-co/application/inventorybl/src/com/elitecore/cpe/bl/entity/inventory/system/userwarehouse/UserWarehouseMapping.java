package com.elitecore.cpe.bl.entity.inventory.system.userwarehouse;

import java.io.Serializable;

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

import com.elitecore.cpe.bl.entity.inventory.bss.user.BSSUser;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;


@Entity
@NamedQueries({ 
	@NamedQuery(name = "UserWarehouseMapping.findByUserId", query = "select o from UserWarehouseMapping o  where o.userId = :userId")
})

@Table(name = "TBLMUSERWAREHOUSEMAPPING")
public class UserWarehouseMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long userWarehouseId;
	private String userId;
	private Long warehouseId;
	
	private BSSUser bssUser;
	private WarehouseData warehouseData;
	
	
	@SequenceGenerator(name="generator", sequenceName="SEQ_TBLMUSERWAREHOUSEMAPPING", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generator")
	@Column(name = "USERWAREHOUSEID", nullable = false)
	public Long getUserWarehouseId() {
		return userWarehouseId;
	}
	public void setUserWarehouseId(Long userWarehouseId) {
		this.userWarehouseId = userWarehouseId;
	}
	
	
	@Column(name = "USERID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	@Column(name = "WAREHOUSEID")
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	
	 @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="USERID" ,insertable=false,updatable=false)
	public BSSUser getBssUser() {
		return bssUser;
	}
	public void setBssUser(BSSUser bssUser) {
		this.bssUser = bssUser;
	}
	
	
	 @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="WAREHOUSEID" ,insertable=false,updatable=false)
	public WarehouseData getWarehouseData() {
		return warehouseData;
	}
	public void setWarehouseData(WarehouseData warehouseData) {
		this.warehouseData = warehouseData;
	}
	@Override
	public String toString() {
		return "UserWarehouseMapping [userWarehouseId=" + userWarehouseId
				+ ", userId=" + userId + ", warehouseId=" + warehouseId + "]";
	}
	
	
	
	

}
