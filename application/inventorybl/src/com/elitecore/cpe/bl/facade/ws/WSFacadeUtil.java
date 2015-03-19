package com.elitecore.cpe.bl.facade.ws;

import com.elitecore.cpe.bl.constants.inventorymgt.InventoryStatusConstants;
import com.elitecore.cpe.bl.facade.BaseDataConversionUtils;

public class WSFacadeUtil  extends BaseDataConversionUtils {
	
	
	public static final Integer NEW_STATUS_CODE = 1;
	public static final Integer AVAILABLE_STATUS_CODE = 2;
	public static final Integer RESERVED_STATUS_CODE = 3;
	public static final Integer IN_USE_STATUS_CODE = 4;
	public static final Integer DELIVERED_STATUS_CODE = 5;
	public static final Integer FAULTY_STATUS_CODE = 6;
	public static final Integer RELEASED_STATUS_CODE = 7;
	public static final Integer REPAIRED_STATUS_CODE = 8;
	public static final Integer SCRAPPED_STATUS_CODE = 9;
	public static final Integer VOID_STATUS_CODE = 10;
	
	public static Integer getStatusFromWSStatus(String status) {
		Integer statusCode = null;
		
		if(status!=null && !status.isEmpty()) {
			if(status.equals(InventoryStatusConstants.NEW_STATUS)) {
				statusCode = 1;
			} else if(status.equals(InventoryStatusConstants.AVAILABLE_STATUS)) {
				statusCode = 2;
			}  else if(status.equals(InventoryStatusConstants.RESERVED_STATUS)) {
				statusCode = 3;
			}  else if(status.equals(InventoryStatusConstants.ALLOCATED_STATUS)) {
				statusCode = 4;
			}  else if(status.equals(InventoryStatusConstants.DELIVERED_STATUS)) {
				statusCode = 5;
			}  else if(status.equals(InventoryStatusConstants.FAULTY_STATUS)) {
				statusCode = 6;
			}  else if(status.equals(InventoryStatusConstants.RELEASED_STATUS)) {
				statusCode = 7;
			}  else if(status.equals(InventoryStatusConstants.REPAIRED_STATUS)) {
				statusCode = 8;
			}  else if(status.equals(InventoryStatusConstants.SCRAPPED_STATUS)) {
				statusCode = 9;
			}  else if(status.equals(InventoryStatusConstants.VOID_STATUS)) {
				statusCode = 10;
			} 
		}
		
		
		return statusCode;
	}
	
	
	
	public static String getStatusFromWSCode(Integer statusCode) {
		String status = "";
		
		if(statusCode!=null) {
			
			if(statusCode.equals(1)) {
				status = InventoryStatusConstants.NEW_STATUS;
			} else if(statusCode.equals(2)) {
				status = InventoryStatusConstants.AVAILABLE_STATUS;
			}  else if(statusCode.equals(3)) {
				status = InventoryStatusConstants.RESERVED_STATUS;
			}  else if(statusCode.equals(4)) {
				status = InventoryStatusConstants.ALLOCATED_STATUS;
			}  else if(statusCode.equals(5)) {
				status = InventoryStatusConstants.DELIVERED_STATUS;
			}  else if(statusCode.equals(6)) {
				status = InventoryStatusConstants.FAULTY_STATUS;
			}  else if(statusCode.equals(7)) {
				status = InventoryStatusConstants.RELEASED_STATUS;
			}  else if(statusCode.equals(8)) {
				status = InventoryStatusConstants.REPAIRED_STATUS;
			}  else if(statusCode.equals(9)) {
				status = InventoryStatusConstants.SCRAPPED_STATUS;
			}  else if(statusCode.equals(10)) {
				status = InventoryStatusConstants.VOID_STATUS;
			} 
			
		}
		
		
		return status;
	}

	
	
	
}
