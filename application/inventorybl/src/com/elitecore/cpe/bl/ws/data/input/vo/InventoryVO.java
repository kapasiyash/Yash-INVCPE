package com.elitecore.cpe.bl.ws.data.input.vo;

import java.io.Serializable;
import java.util.List;

public class InventoryVO implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String inventoryNumber;
	private String inventoryStaus;
	private String batchNumber;
	
	private List<InventoryAttributeVO> attributeVOs;

	public String getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(String inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public String getInventoryStaus() {
		return inventoryStaus;
	}

	public void setInventoryStaus(String inventoryStaus) {
		this.inventoryStaus = inventoryStaus;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	

	public List<InventoryAttributeVO> getAttributeVOs() {
		return attributeVOs;
	}

	public void setAttributeVOs(List<InventoryAttributeVO> attributeVOs) {
		this.attributeVOs = attributeVOs;
	}

	/* 
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryVO [" );
		if(inventoryNumber!=null){
		builder.append("inventoryNumber=").append(inventoryNumber);
		}
		if(inventoryStaus!=null){
		builder.append(", inventoryStaus=").append(inventoryStaus);
		}
		if(batchNumber!=null){
			
		builder.append(", batchNumber=").append(batchNumber);
		}
		
		if(attributeVOs!=null){
		builder.append(", attributeVOs=").append(attributeVOs);
		}
		builder.append("]");
		return builder.toString();
	}
	
	
	
	

}
