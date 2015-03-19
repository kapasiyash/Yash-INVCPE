package com.elitecore.cpe.bl.vo.inventorymgt;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

public class InventoryUploadVO implements Serializable{

	private byte[] uploadbyteData;
	private String staffId;
	private Long validEntry;
	private Long invalidEntry;
	private String batchNo;
	private Map<Long, Integer> invalidEntryFromvalidMap;
	
	public byte[] getUploadbyteData() {
		return uploadbyteData;
	}

	public void setUploadbyteData(byte[] uploadbyteData) {
		this.uploadbyteData = uploadbyteData;
	}
	
	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public Long getValidEntry() {
		return validEntry;
	}

	public void setValidEntry(Long validEntry) {
		this.validEntry = validEntry;
	}

	public Long getInvalidEntry() {
		return invalidEntry;
	}

	public void setInvalidEntry(Long invalidEntry) {
		this.invalidEntry = invalidEntry;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Map<Long, Integer> getInvalidEntryFromvalidMap() {
		return invalidEntryFromvalidMap;
	}

	public void setInvalidEntryFromvalidMap(
			Map<Long, Integer> invalidEntryFromvalidMap) {
		this.invalidEntryFromvalidMap = invalidEntryFromvalidMap;
	}

	@Override
	public String toString() {
		return "InventoryUploadVO [uploadbyteData="
				+ Arrays.toString(uploadbyteData) + ", staffId=" + staffId
				+ ", validEntry=" + validEntry + ", invalidEntry="
				+ invalidEntry + ", batchNo=" + batchNo
				+ ", invalidEntryFromvalidMap=" + invalidEntryFromvalidMap
				+ "]";
	}

	
	
	
}
