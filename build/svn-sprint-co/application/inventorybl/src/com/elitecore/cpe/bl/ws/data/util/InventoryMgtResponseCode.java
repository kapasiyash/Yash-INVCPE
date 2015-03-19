package com.elitecore.cpe.bl.ws.data.util;

import java.io.Serializable;
import java.lang.reflect.Field;

public class InventoryMgtResponseCode implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	public static final long SUCCESS_RESPONSE_CODE = 0;
	public static final long FAIL_RESPONSE_CODE    = -1;
	
	
	public static final long WAREHOUSE_NOT_FOUND			= getEjbResponseCode(-99000);
	public static final String WAREHOUSE_NOT_FOUND_MESSAGE  =  "Warehouse name should not be blank for operation type Reserve";
	
	public static final long RESOURCETYPE_NOT_FOUND			= getEjbResponseCode(-99001);
	public static final String RESOURCETYPE_NOT_FOUND_MESSAGE =  "Resource Type should not be as blank";
	
	public static final long INVALIAD_WAREHOUSE			= getEjbResponseCode(-99002);
	public static final String INVALIAD_WAREHOUSE_MESSAGE  =  "Invalid Warehouse Name";
	
	public static final long RESOURCE_NOT_FOUND			= getEjbResponseCode(-99003);
	public static final String RESOURCE_NOT_FOUND_MESSAGE = "ResourceId should not be blank";
	
	public static final long INVENTORY_DETAILS_REQUEST_NOT_FOUND = getEjbResponseCode(-99004);
	public static final String INVENTORY_DETAILS_REQUEST_NOT_FOUND_MESSAGE = "Inventory Request Data should not be blank";

	public static final long ATTRIBUTESVO_VALIDATION_ERROR = getEjbResponseCode(-99005);
	public static final String ATTRIBUTESVO_VALIDATION_ERROR_MESSAGE = "Attribute VO details not proper";

	public static final long INVENTORY_DETAILS_ERROR_VALIDATION = getEjbResponseCode(-99006);
	public static final String INVENTORY_DETAILS_ERROR_VALIDATION_MESSAGE = "Inventory request validation Error";
	
	public static final long RESOURCEID_NOT_FOUND = getEjbResponseCode(-99007);
	public static final String RESOURCEID_NOT_FOUND_MESSAGE = "ResourceId should not be as blank";
	

	public static final long NOOFRESOURCE_NOT_FOUND = getEjbResponseCode(-99008);
	public static final String NOOFRESOURCE_NOT_FOUND_MESSAGE = "No Of Resource should not be blank for operation type Reserve";
	
	public static final long OPERATIONTYPE_NOT_FOUND = getEjbResponseCode(-99009);
	public static final String OPERATIONTYPE_NOT_FOUND_MESSAGE = "Operation Type should not be as blank Possible value is Reserve or Allocate";
	
	public static final long INVENTORYNO_NOT_FOUND = getEjbResponseCode(-99010);
	public static final String INVENTORYNO_NOT_FOUND_MESSAGE = "Invalid Inventory Number or Inventory Number list should not be blank.";
	
	public static final long ORDERLINEITEM_NOT_FOUND = getEjbResponseCode(-99011);
	public static final String ORDERLINEITEM_NOT_FOUND_MESSAGE = "Order Line Item ID should not be blank ";
	
	public static final long INVENTORY_NUMBER_OR_SERIALNUMBER_NOT_FOUND = getEjbResponseCode(-99012);
	public static final String INVENTORY_NUMBER_OR_SERIALNUMBER_NOT_FOUND_MESSAGE = "Inventory Number or SerialNumber can not be empty or blank";

	public static final long INVENTORY_OLDSTATUS_NOT_FOUND = getEjbResponseCode(-99013);
	public static final String INVENTORY_OLDSTATUS_NOT_FOUND_MESSAGE = "Inventory Old Status can not be empty or blank";
	
	public static final long RESERVE_MORETHAN_AVAILABLE = getEjbResponseCode(-99014);
	public static final String RESERVE_MORETHAN_AVAILABLE_MESSAGE = "Required quantity is not available for reserve, Please try again with different quantity. Current available quantity is :";

	public static final long INVENTORY_RESERVE_NOT_FOUND = getEjbResponseCode(-99015);
	public static final String INVENTORY_RESERVE_NOT_FOUND_MESSAGE = "Some of Inventory not found in reserved Status. Please try again later";
	
	public static final long INVALIAD_RESOURCE			= getEjbResponseCode(-99016);
	public static final String INVALIAD_RESOURCE_MESSAGE  =  "Invalid Resource ID";
	
	public static final long INVENTORY_NEWSTATUS_NOT_FOUND = getEjbResponseCode(-99017);
	public static final String INVENTORY_NEWSTATUS_NOT_FOUND_MESSAGE = "Inventory New Status can not be blank or empty.";
	
	public static final long REMARKS_NOT_FOUND = getEjbResponseCode(-99018);
	public static final String REMARKS_NOT_FOUND_MESSAGE = "Remarks should not be blank or empty";
	
	public static final long STATUS_TRANSITION_NOTALLOWED = getEjbResponseCode(-99019);
	public static final String STATUS_TRANSITION_NOTALLOWED_MESSAGE = "Status Change not allowed";
	
	public static final long INVALID_OPERATIONTYPE = getEjbResponseCode(-99020);
	public static final String INVALID_OPERATIONTYPE_MESSAGE = "Invalid Operation Type, Possible value is Reserve or Allocate";

	public static final long INVALID_NOOFRESOURCE = getEjbResponseCode(-99021);
	public static final String INVALID_NOOFRESOURCE_MESSAGE = "Invalid noOfResource, Please provide numeric values for noOfResource";
	
	public static final long INVALID_TRANSFERSTATUS = getEjbResponseCode(-99030);
	public static final String INVALID_TRANSFERSTATUS_MESSAGE = "Inventory TransferStatus is in progress, so can't change the Status";
	
	public static final long INVALID_INVENTORY_NO = getEjbResponseCode(-99022);
	public static final String INVALID_INVENTORY_NO_MESSAGE = "Invalid Inventory Number";
	
	public static final long INVALID_SERIALNUMBER = getEjbResponseCode(-99023);
	public static final String INVALID_SERIALNUMBER_MESSAGE = "SerialNumber not valid or more than one Inventories attached with given SerialNumber";
	
	
	public static final long INVENTORYNO_OR_SERIALNUMBER_NOT_FOUND = getEjbResponseCode(-99024);
	public static final String INVENTORYNO_OR_SERIALNUMBER_NOT_FOUND_MESSAGE = "Inventory Number or SerialNumber should not be blank for operation type Allocate";
	
	public static final long INVALID_TRANSFERSTATUS_ALLOCATE = getEjbResponseCode(-99025);
	public static final String INVALID_TRANSFERSTATUS_ALLOCATE_MESSAGE = "Invalid Status Transition, can not change status of resource to Allocate.";
	
	public static final long ALLOCATE_INVENTORIES_FAILED = getEjbResponseCode(-99033);
	public static final String ALLOCATE_INVENTORIES_FAILED_MESSAGE = "Not able to allocate all inventories , please try later. Failed Inventories : ";
	
	public static final long NO_RESOURCE_FOUND = getEjbResponseCode(-99026);
	public static final String NO_RESOURCE_FOUND_MESSAGE="No Resources Found";
	
	public static final long PARTIAL_CHANGE_INVENTORIES_FAILED = getEjbResponseCode(-99031);
	public static final String PARTIAL_CHANGE_INVENTORIES_FAILED_MESSAGE = "Change Status for some inventories Success, find details in response object";
	
	public static final long CHANGE_INVENTORIES_FAILED = getEjbResponseCode(-99032);
	public static final String CHANGE_INVENTORIES_FAILED_MESSAGE = "Not able to change status , find details in response object. ";
	
	public static final long INVALIAD_WAREHOUSECODE			= getEjbResponseCode(-99027);
	public static final String INVALIAD_WAREHOUSECODE_MESSAGE  =  "Invalid Warehouse Code";
	
	public static final long RESOURCE_NOT_FOUND_WITHWAREHOUSECODE = getEjbResponseCode(-99028);
	public static final String RESOURCE_NOT_FOUND_WITHWAREHOUSECODE_MESSAGE = "Resousrce is not availbale in specified warehouse : ";
	
	public static final long WAREHOUSECODE_NOT_FOUND = getEjbResponseCode(-99029);
	public static final String WAREHOUSECODE_NOT_FOUND_MESSAGE="Warehouse code should not be blank for operation type Reserve";

	public static final long ISRESOURCERECOVERABLE_NOT_FOUND = getEjbResponseCode(-99034);
	public static final String ISRESOURCERECOVERABLE_NOT_FOUND_MESSAGE="isResourceRecoverable should not be blank for operation type Allocate";
	
	public static final long ISRESOURCERECOVERABLE_INVALID = getEjbResponseCode(-99035);
	public static final String ISRESOURCERECOVERABLE_INVALID_MESSAGE="Invalid value for isResourceRecoverable field, possible value is Yes or No.";
	
	
	public static final long STATUSCHANGE_FOR_CENTRAL = getEjbResponseCode(-99036);
	public static final String STATUSCHANGE_FOR_CENTRAL_MESSAGE="Central Warehouse Inventory can not be changed to Reserved,Allocated,Delivered or Recovered";

	//Added By rinkal-start
	public static final long INVENTORY_NUMBER_NOT_FOUND = getEjbResponseCode(-99045);
	public static final String INVENTORY_NUMBER_NOT_FOUND_MESSAGE = "Inventory Number can not be empty or blank.";
	
	public static final long INVENTORY_NUMBER_RESERVE_FAILED = getEjbResponseCode(-99037);
	public static final String INVENTORY_NUMBER_RESERVE_FAILED_MESSAGE = "Some of Inventory not found in Available Status. Please try again later.";

	public static final long INVENTORY_NUMBER_RELEASE_FAILED = getEjbResponseCode(-99043);
	public static final String INVENTORY_NUMBER_RELEASE_FAILED_MESSAGE = "Not able to Release all CPE Resource,Please try again later.";

	public static final long STATUS_TRANSITION_NOTALLOWED_FOR_INVENTORY_INTRANSFER = getEjbResponseCode(-99039);
	public static final String STATUS_TRANSITION_NOTALLOWED_FOR_INVENTORY_INTRANSFER_MESSAGE = "Inventory is in transfer status.";

	public static final long INVENTORY_INVALID_OLD_STATUS = getEjbResponseCode(-99040);
	public static final String INVENTORY_INVALID_OLD_STATUS_MESSAGE = "Inventory old status is invalid. Possible value is between 1 to 11";
	
	public static final long INVENTORY_INVALID_NEW_STATUS = getEjbResponseCode(-99041);
	public static final String INVENTORY_INVALID_NEW_STATUS_MESSAGE = "Inventory new status is invalid.Possible value is between 2 to 11";

	public static final long STATUS_TRANSITION_NOTALLOWED_RELEASECPE = getEjbResponseCode(-99042);
	public static final String STATUS_TRANSITION_NOTALLOWED_RELEASECPE_MESSAGE = "Inventory is not in Reserved,Allocated,Delivered status.";

	public static final long INVALID_OPERATIONTYPE_RELEASE = getEjbResponseCode(-99044);
	public static final String INVALID_OPERATIONTYPE_RELEASE_MESSAGE = "Invalid Operation Type, Possible value is Release";

	public static final long WAREHOUSE_CODE_AND_NAME_MISMATCH = getEjbResponseCode(-99038);
	public static final String WAREHOUSE_CODE_AND_NAME_MISMATCH_MESSAGE = "Warehhosue Name and Warehouse Code is mismatch. Please, provide proper value and try again.";

	public static final long MARK_INVENTORY_AS_FAULTY_FAILED = getEjbResponseCode(-99046);
	public static final String MARK_INVENTORY_AS_FAULTY_FAILED_MESSAGE = "Not able to mark all CPEs As Faulty,Please try again later.";

	public static final long WAREHOUSECODE_NOTFOUND = getEjbResponseCode(-99047);
	public static final String WAREHOUSECODE_NOTFOUND_MESSAGE="Warehouse code should not be blank or empty";

	//Added By rinkal-end

	public static String responseCodeToMessage(long lResponseCode)
	{
		try {
			
			Field[] fieldsArr =  Class.forName("com.elitecore.cpe.bl.ws.data.util.InventoryMgtResponseCode").getDeclaredFields();
			for(Field field : fieldsArr){
				 if(field.getType().toString().equalsIgnoreCase("long") ){
					 long code = (Long)field.get(null);
					 if(code == lResponseCode){
						 Field newF = Class.forName("com.elitecore.cpe.bl.ws.data.util.InventoryMgtResponseCode").getField(field.getName()+"_MESSAGE");
						 return newF.get(null).toString();
					 }
				 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(responceCodeToE2ECode(-1));
	}
	
	public static String responceCodeToE2ECode(long lResponseCode) {
		
		try {
			if(lResponseCode!=-1) {
				String code = String.valueOf(lResponseCode);
				return "err-211"+code.substring(code.length()-3, code.length());
			} else {
				return "err-111999";
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static long getEjbResponseCode(long code) 
	{
    	if(code<=0)
            return (-101100000)+code;
		else
			return 101100000 + code;
    }
	
}
