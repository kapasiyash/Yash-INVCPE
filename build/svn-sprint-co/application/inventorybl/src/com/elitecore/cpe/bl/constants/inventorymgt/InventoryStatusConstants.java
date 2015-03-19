package com.elitecore.cpe.bl.constants.inventorymgt;

public class InventoryStatusConstants {

	public static final int NEW 	  = 1;
	public static final int AVAILABLE = 2;
	public static final int RESERVED  = 3;
	public static final int ALLOCATED    = 4;
	public static final int DELIVERED = 5;
	public static final int FAULTY 	  = 6;
	public static final int RELEASED  = 7;
	public static final int REPAIRED  = 8;
	public static final int SCRAPPED   = 9;
	public static final int VOID      = 10;
	public static final int FAILED    = -1;
	public static final int RETIRED      = 11;
	public static final int ACCEPTED_SUBSTATUS      = 2;
	
	
	public static final String NEW_STATUS 	  = "New";
	public static final String AVAILABLE_STATUS = "Available";
	public static final String RESERVED_STATUS  = "Reserved";
	public static final String ALLOCATED_STATUS    = "Allocated";
	public static final String DELIVERED_STATUS = "Delivered";
	public static final String FAULTY_STATUS 	  = "Faulty";
	public static final String RELEASED_STATUS  = "Recovered";
	public static final String REPAIRED_STATUS  = "Repaired";
	public static final String SCRAPPED_STATUS   = "Scrapped";
	public static final String VOID_STATUS      = "Void";
	public static final String FAILED_STATUS    = "Failed";
	public static final String RETIRED_STATUS    = "Retired";

	
	public static final String FAULTY_STATUS_ALIAS 	  = "FAULTY";
	public static final String AVAILABLE_STATUS_ALIAS 	  = "AVAILABLE";
	
	public static final String TRANSFERINVENTORY_STATUS_WAITING = "WAITING";
	public static final String TRANSFERINVENTORY_STATUS_ACCEPTED = "ACCEPTED";
	public static final String TRANSFERINVENTORY_STATUS_REJECTED= "REJECTED";
	
	public static final String SUBSTATUS_IN_TRANSFER = "IN-TRANSFER";
	public static final String SUBSTATUS_ACCEPTED = "ACCEPTED";
	
	public static final String ORDER_CANCELLED = "CANCELLED";
	public static final String ORDER_IN_PROGRESS = "IN_PROGRESS";
	public static final String ORDER_ACCEPTED = "ACCEPTED";
	public static final String ORDER_REJECTED = "REJECTED";
	public static final String ORDER_PARTIALLY_ACCEPTED = "PARTIALLY_ACCEPTED";
	public static final String ORDER_COMPLETED = "COMPLETED";
	
/*	public static final String NEW_SUBSTATUS 	  = "New";
	public static final String REFURBISHED_SUBSTATUS 	  = "Refurbished";
	public static final String STANDBY_SUBSTATUS 	  = "Standby";
	public static final String ACCEPTED_SUBSTATUS 	  = "Accepted";
*/	
	public static final String NEW_SUBSTATUS_ALIAS = "NEW";
	public static final String REFURBISHED_SUBSTATUS_ALIAS 	  = "REFURNISHED";
	public static final String STANDBY_SUBSTATUS_ALIAS 	  = "STAND_BY";
	public static final String ACCEPTED_SUBSTATUS_ALIAS 	  = "ACCEPTED";
	public static final String RETURNED_SUBSTATUS_ALIAS 	  = "RETURNED";
	

	
}

