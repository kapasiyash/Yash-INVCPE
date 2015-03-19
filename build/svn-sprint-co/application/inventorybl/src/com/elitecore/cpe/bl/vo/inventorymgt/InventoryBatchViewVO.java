/**
 * 
 */
package com.elitecore.cpe.bl.vo.inventorymgt;

import java.io.Serializable;

/**
 * @author Joel.Macwan
 *
 */
public class InventoryBatchViewVO implements Serializable{
	
	Long validcount;
	Long invalidcount;
	String batchNumber;
	
	
	
	public String getBatchNumber() {
		return batchNumber;
	}
	
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	
	
	public Long getValidcount() {
		return validcount;
	}

	public void setValidcount(Long validcount) {
		this.validcount = validcount;
	}

	public Long getInvalidcount() {
		return invalidcount;
	}

	public void setInvalidcount(Long invalidcount) {
		this.invalidcount = invalidcount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryBatchViewVO [validcount=");
		builder.append(validcount);
		builder.append(", invalidcount=");
		builder.append(invalidcount);
		builder.append(", batchNumber=");
		builder.append(batchNumber);
		builder.append("]");
		return builder.toString();
	}

	
}
