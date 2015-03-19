/**
 * 
 */
package com.elitecore.cpe.bl.vo.master;

import java.io.Serializable;

/**
 * @author Joel.Macwan
 *
 */
public class ThresholdStatusVO implements Serializable{

	
	private String warehouse; 
	private StringBuffer remarks; 
	private String warehouseid;
	private String resource; 
	private String resourceid;
	private String thresholdValue;
	private String avialablestock;
	
	private String email;
	
	
	 
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the warehouse
	 */
	public String getWarehouse() {
		return warehouse;
	}
	/**
	 * @param warehouse the warehouse to set
	 */
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	/**
	 * @return the warehouseid
	 */
	public String getWarehouseid() {
		return warehouseid;
	}
	/**
	 * @param warehouseid the warehouseid to set
	 */
	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}
	/**
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * @param resource the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	/**
	 * @return the resourceid
	 */
	public String getResourceid() {
		return resourceid;
	}
	/**
	 * @param resourceid the resourceid to set
	 */
	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}
	/**
	 * @return the thresholdValue
	 */
	public String getThresholdValue() {
		return thresholdValue;
	}
	/**
	 * @param thresholdValue the thresholdValue to set
	 */
	public void setThresholdValue(String thresholdValue) {
		this.thresholdValue = thresholdValue;
	}
	/**
	 * @return the avialablestock
	 */
	public String getAvialablestock() {
		return avialablestock;
	}
	/**
	 * @param avialablestock the avialablestock to set
	 */
	public void setAvialablestock(String avialablestock) {
		this.avialablestock = avialablestock;
	}
	
	/**
	 * @return the remarks
	 */
	public StringBuffer getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(StringBuffer remarks) {
		this.remarks = remarks;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ThresholdStatusVO [warehouse=" + warehouse + ", remarks="
				+ remarks + ", warehouseid=" + warehouseid + ", resource="
				+ resource + ", resourceid=" + resourceid + ", thresholdValue="
				+ thresholdValue + ", avialablestock=" + avialablestock + "]";
	}
}
