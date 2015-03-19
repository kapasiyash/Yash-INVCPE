package com.elitecore.cpe.bl.constants.notification;

import java.io.Serializable;

public class NotificationConstants implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String CPE_RESOURCE_NUMBER= "${CPE_RESOURCE_NUMBER}";
	public static final String CPE_RESOURCE_NAME= "${CPE_RESOURCE_NAME}";
	public static final String CPE_MODEL_NUMBER= "${CPE_MODEL_NUMBER}";
	public static final String CPE_VENDOR= "${CPE_VENDOR}";
	public static final String CPE_DESCRIPTION= "${CPE_DESCRIPTION}";
	public static final String CPE_REFERENCEID= "${CPE_REFERENCEID}";
	public static final String CPE_RESOURCE_TYPENAME= "${CPE_RESOURCE_TYPENAME}";
	public static final String CPE_RESOURCE_SUBTYPENAME= "${CPE_RESOURCE_SUBTYPENAME}";
	public static final String CPE_WAREHOUSE_NAME= "${CPE_WAREHOUSE_NAME}";
	public static final String CPE_THRESHOLD_LIMIT= "${CPE_THRESHOLD_LIMIT}";
	public static final String CPE_ORDER_NUMBER= "${CPE_ORDER_NUMBER}";
	public static final String CPE_FROM_WAREHOUSE= "${CPE_FROM_WAREHOUSE}";
	public static final String CPE_TO_WAREHOUSE= "${CPE_TO_WAREHOUSE}";
	public static final String CPE_QUANTITY= "${CPE_QUANTITY}";
	public static final String CPE_ACCEPTED_QUANTITY= "${CPE_ACCEPTED_QUANTITY}";
	public static final String CPE_AVAILABLE_QUANTITY = "${CPE_AVAILABLE_QUANTITY}";
	public static final String CPE_REMARK= "${CPE_REMARK}";
	
	
	
	public static final String CREATE_RESOURCE="CREATE_RESOURCE";
	public static final String RESOURCE_THRESHOLD="RESOURCE_THRESHOLD";
	public static final String PLACE_ORDER="PLACE_ORDER";
	public static final String ACCEPT_REJECT_PLACE_ORDER="ACCEPT-REJECT_PLACE_ORDER";
	public static final String TRANSFER_ORDER = "TRANSFER_ORDER";
	public static final String ACCEPT_REJECT_TRANSFER_ORDER = "ACCEPT-REJECT_TRANSFER_ORDER";
	public static final String TRANSFER_ORDER_PLACEORDER = "TRANSFER_ORDER_PLACEORDER";
	
}
