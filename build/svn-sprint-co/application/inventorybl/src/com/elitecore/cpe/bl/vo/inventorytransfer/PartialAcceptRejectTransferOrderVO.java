package com.elitecore.cpe.bl.vo.inventorytransfer;

import java.io.Serializable;
import java.util.List;

public class PartialAcceptRejectTransferOrderVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String orderNo;
	private String orderStatus;
	private String fromWarehouseName;
	private String toWarehouseName;
	private String remark;
	private Long acceptQuantity;

	private List<InventoryVO> inventoryVOs;
	
	
	
	
	
	public Long getAcceptQuantity() {
		return acceptQuantity;
	}





	public void setAcceptQuantity(Long acceptQuantity) {
		this.acceptQuantity = acceptQuantity;
	}





	public String getOrderNo() {
		return orderNo;
	}





	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}





	public String getOrderStatus() {
		return orderStatus;
	}





	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}





	public String getFromWarehouseName() {
		return fromWarehouseName;
	}





	public void setFromWarehouseName(String fromWarehouseName) {
		this.fromWarehouseName = fromWarehouseName;
	}





	public String getToWarehouseName() {
		return toWarehouseName;
	}





	public void setToWarehouseName(String toWarehouseName) {
		this.toWarehouseName = toWarehouseName;
	}





	public String getRemark() {
		return remark;
	}





	public void setRemark(String remark) {
		this.remark = remark;
	}





	public List<InventoryVO> getInventoryVOs() {
		return inventoryVOs;
	}





	public void setInventoryVOs(List<InventoryVO> inventoryVOs) {
		this.inventoryVOs = inventoryVOs;
	}





	public static class InventoryVO implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private Long responseCode;
		private String responseMessage;
		private String batchNumber;
		private String inventoryStatus;
		private String warehouseName;
		private String resourceType;
		private String resourceSubtype;
		
		private String inventoryNo;
		private String transferStatus;
		private String remarks;
		
		
		
		public String getInventoryStatus() {
			return inventoryStatus;
		}
		public void setInventoryStatus(String inventoryStatus) {
			this.inventoryStatus = inventoryStatus;
		}
		public String getWarehouseName() {
			return warehouseName;
		}
		public void setWarehouseName(String warehouseName) {
			this.warehouseName = warehouseName;
		}
		public String getResourceType() {
			return resourceType;
		}
		public void setResourceType(String resourceType) {
			this.resourceType = resourceType;
		}
		public String getResourceSubtype() {
			return resourceSubtype;
		}
		public void setResourceSubtype(String resourceSubtype) {
			this.resourceSubtype = resourceSubtype;
		}
		public String getBatchNumber() {
			return batchNumber;
		}
		public void setBatchNumber(String batchNumber) {
			this.batchNumber = batchNumber;
		}
		public Long getResponseCode() {
			return responseCode;
		}
		public void setResponseCode(Long responseCode) {
			this.responseCode = responseCode;
		}
		public String getResponseMessage() {
			return responseMessage;
		}
		public void setResponseMessage(String responseMessage) {
			this.responseMessage = responseMessage;
		}
		public String getInventoryNo() {
			return inventoryNo;
		}
		public void setInventoryNo(String inventoryNo) {
			this.inventoryNo = inventoryNo;
		}
		public String getTransferStatus() {
			return transferStatus;
		}
		public void setTransferStatus(String transferStatus) {
			this.transferStatus = transferStatus;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		
		
	}
	
	
}
