package com.elitecore.cpe.bl.constants.system.audit;

public class AuditConstants {

	
	//Audit Constants
	public static final  String DEFAULT_AUDIT_TYPE = "AT000";
	public static final  String CREATE_AUDIT_TYPE = "AT001";
	public static final  String UPDATE_AUDIT_TYPE = "AT002";
	public static final  String DELETE_AUDIT_TYPE = "AT003";
	public static final  String LOGIN_AUDIT_TYPE = "AT004";
	
	public static final  String AT000 = "DEFAULT_AUDIT_TYPE";
	public static final  String AT001 = "CREATE_AUDIT_TYPE";
	public static final  String AT002 = "UPDATE_AUDIT_TYPE";
	public static final  String AT003 = "DELETE_AUDIT_TYPE";
	public static final  String AT004 = "LOGIN_AUDIT_TYPE";
	
	public static final  String TABLE = "TABLENAME";
	public static final  String FIELD = "FIELDNAME";
	public static final  String OLDVALUE = "OLDVALUE";
	public static final  String NEWVALUE = "NEWVALUE";
	public static final  String NOVALUE = "NULL";
	
	//Action Alias
	public static final  String LOGIN_USER = "LOGIN_USER";
	public static final  String LOGIN = "LOGIN_ACTION";
	public static final  String LOGOUT = "LOGOUT_ACTION";
	
	public static final String CREATE_ACCOUNTING_HEAD = "CREATE_AR_ACCOUNTING_HEAD";
	public static final String UPDATE_ACCOUNTING_HEAD = "UPDATE_AR_ACCOUNTING_HEAD";
	
	
	public static final String OPEN_FINANCIAL_PERIOD = "OPEN_AR_FINANCIAL_PERIOD";
	public static final String CHANGE_FINANCIAL_PERIOD = "CHANGE_AR_FINANCIAL_PERIOD";
	
	public static final String CREATE_GL_REGISTER = "CREATE_AR_GL_REGISTER";
	public static final String UPDATE_GL_REGISTER = "UPDATE_AR_GL_REGISTER";
	
	public static final String CREATE_REVENUE_GLMAP = "CREATE_AR_REVENUE_GLMAP";
	public static final String UPDATE_REVENUE_GLMAP = "UPDATE_AR_REVENUE_GLMAP";
	
	public static final String CREATE_AR_EVENT = "CREATE_AR_EVENT";
	public static final String UPDATE_AR_EVENT = "UPDATE_AR_EVENT";
	
	public static final String UPDATE_SYSTEM_PARAMETER = "UPDATE_SYSTEM_PARAMETER";
	
	public static final String TRACK_AR_EVENT = "TRACK_AR_EVENT";
	
	
	//Financial Period
	public static final String TBLMFINANCIALPERIOD="TBLMFINANCIALPERIOD";
	public static final String TBLMFINANCIALPERIOD_OPENDATE="OPENDATE";
	public static final String TBLMFINANCIALPERIOD_CLOSEDATE="CLOSEDATE";
	public static final String TBLMFINANCIALPERIOD_GRACEDAYS="GRACEDAYS";
	public static final String TBLMFINANCIALPERIOD_GRACECLOSEDATE="GRACECLOSEDATE";
	public static final String TBLMFINANCIALPERIOD_STATUSID="STATUSID";
	public static final String TBLMFINANCIALPERIOD_CREATEDATE="CREATEDATE";
	public static final String TBLMFINANCIALPERIOD_CREATEDBY="CREATEDBY";
	public static final String TBLMFINANCIALPERIOD_UPDATEDATE="UPDATEDATE";
	public static final String TBLMFINANCIALPERIOD_UPDATEDBY="UPDATEDBY";
//	public static final String TBLMFINANCIALPERIOD_REMARKS="REMARKS";
	

	//GL Register
	public static final String TBLMGLREGISTER = "TBLMGLREGISTER";
	public static final String TBLMGLREGISTER_NAME = "NAME";
	public static final String TBLMGLREGISTER_ALIAS = "ALIAS";
	public static final String TBLMGLREGISTER_DESCRIPTION = "DESCRIPTION";
	public static final String TBLMGLREGISTER_GLCODE = "GLCODE";
	public static final String TBLMGLREGISTER_ACCOUNTINGHEADID = "ACCOUNTINGHEADID";
	public static final String TBLMGLREGISTER_PARENTGLID = "PARENTGLID";
	public static final String TBLMGLREGISTER_REMARKS = "REMARKS";
	public static final String TBLMGLREGISTER_UPDATEDBY = "UPDATEBY";
	public static final String TBLMGLREGISTER_UPDATEDATE = "UPDATEDDATE";
	
	
	//Data Class
	public static final String CREATE_DATA_CLASS = "CREATE_AR_DATA_CLASS";
	public static final String UPDATE_DATA_CLASS = "SEARCH_AR_DATA_CLASS";
	
	public static final String TBLMEVENTDATA="TBLMEVENTDATA";
	public static final String TBLMEVENTDATA_NAME="NAME";
	public static final String TBLMEVENTDATA_ALIAS="ALIAS";
	public static final String TBLMEVENTDATA_EVENTDATATYPE="EVENTDATATYPE";
	public static final String TBLMEVENTDATA_DATACLASS="DATACLASS";
	public static final String TBLMEVENTDATA_AREVENT="AREVENT";
	public static final String TBLMEVENTDATA_UPDATEDBY="UPDATEDBY";
	public static final String TBLMEVENTDATA_UPDATEDATE="UPDATEDATE";

	//Revenue GL Map
	public static final String TBLMREVENUEGLMAP="TBLMREVENUEGLMAP";
	public static final String TBLMREVENUEGLMAP_NAME ="NAME";
	public static final String TBLMREVENUEGLMAP_ALIAS="ALIAS";
	public static final String TBLMREVENUEGLMAP_DESCRIPTION="DESCRIPTION";
	public static final String TBLMREVENUEGLMAP_REVENUETYPE="REVENUETYPE";
	public static final String TBLMREVENUEGLMAP_REVENUECODE="REVENUECODE";
	public static final String TBLMREVENUEGLMAP_GLCODE="GLCODE";
	public static final String TBLMREVENUEGLMAP_UPDATEDBY = "UPDATEDBY";
	public static final String TBLMREVENUEGLMAP_UPDATEDATE = "UPDATEDDATE";
	
	//Agent Schedule
	public static final String CREATE_AGENT_SCHEDULE = "CREATE_AGENT_SCHEDULE";
	public static final String UPDATE_AGENT_SCHEDULE = "SEARCH_AGENT_SCHEDULE";
	public static final String TBLMAGENTSCHEDULE = "TBLMAGENTSCHEDULE";
	public static final String TBLMAGENTSCHEDULE_AGENTID = "AGENTID";
	public static final String TBLMAGENTSCHEDULE_NAME = "NAME";
	public static final String TBLMAGENTSCHEDULE_PRIORITY = "PRIORITY";
	public static final String TBLMAGENTSCHEDULE_DESCRIPTION = "DESCRIPTION";
	public static final String TBLMAGENTSCHEDULE_EXECUTIONTYPEID = "EXECUTIONTYPEID";
	public static final String TBLMAGENTSCHEDULE_REQUIREDNUMBEROFEXECUTIONS = "REQUIREDNUMBEROFEXECUTIONS";
	public static final String TBLMAGENTSCHEDULE_EXECUTIONSTARTDATE = "EXECUTIONSTARTDATE";
	public static final String TBLMAGENTSCHEDULE_EXEPERIODINMIN = "EXEPERIODINMIN";
	public static final String TBLMAGENTSCHEDULE_LASTEXECUTIONDATE = "LASTEXECUTIONDATE";
	public static final String TBLMAGENTSCHEDULE_REASONFORSCHEDULE = "REASONFORSCHEDULE";
	public static final String TBLMAGENTSCHEDULE_EXECUTIONSTATUSID = "EXECUTIONSTATUSID";
	public static final String TBLMAGENTSCHEDULE_SCHEDULEPATTERN = "SCHEDULEPATTERN";
	public static final String TBLMAGENTSCHEDULE_STATUSID = "STATUSID";
	public static final String TBLMAGENTSCHEDULE_UPDATEDBY="UPDATEDBY";
	public static final String TBLMAGENTSCHEDULE_UPDATEDATE="UPDATEDATE";
	
	
	//Inventory
	public static final String TBLMINVENTORY = "TBLMINVENTORY";
	public static final String TBLMINVENTORY_STATUSID = "STATUSID";
/*	public static final String TBLMINVENTORY_ACCEPTED = "ACCEPTED";
	public static final String TBLMINVENTORY_NEW = "NEW";
	public static final String TBLMINVENTORY_REFURNISHED = "REFURNISHED";
	public static final String TBLMINVENTORY_STANDBY = "STANDBY";
*/	public static final String TBLMINVENTORY_UPDATEDBY="UPDATEDBY";
	public static final String TBLMINVENTORY_UPDATEDATE="UPDATEDATE";
	public static final String TBLMINVENTORY_SUBSTATUSID = "SUBSTATUSID";
	
	//Manual Agent Schedule
	public static final String TBLTAGENTRUNQUEUE = "TBLTAGENTRUNQUEUE";
	public static final String TBLTAGENTRUNQUEUE_AGENTSCHEDULEID = "AGENTSCHEDULEID";
	public static final String TBLTAGENTRUNQUEUE_EXECUTIONDUEDATETIME = "EXECUTIONDUEDATETIME";
	public static final String TBLTAGENTRUNQUEUE_AGENTID = "AGENTID";
	public static final String TBLTAGENTRUNQUEUE_EXECUTIONTYPEID = "EXECUTIONTYPEID";
	public static final String TBLTAGENTRUNQUEUE_PRIORITY = "PRIORITY";
	public static final String TBLTAGENTRUNQUEUE_AGENTRUNDETAILID = "AGENTRUNDETAILID";
	public static final String TBLTAGENTRUNQUEUE_EXECUTIONSTATUSID = "EXECUTIONSTATUSID";	

	
	//System Parameter
	public static final String TBLMSYSTEMPARAMETER = "TBLMSYSTEMPARAMETER";
	public static final String TBLMSYSTEMPARAMETER_VALUE = "VALUE";
	public static final String TBLMSYSTEMPARAMETER_LASTMODIDATE = "LASTMODIDATE";
	public static final String TBLMSYSTEMPARAMETER_LASTMODIBY = "LASTMODIBY";
	
	
	//Event Request Register
	public static final String TBLTAREVENTREQUESTREGISTER = "TBLTAREVENTREQUESTREGISTER";
	public static final String TBLTAREVENTREQUESTREGISTER_AREVENTID = "AREVENTID";
	public static final String TBLTAREVENTREQUESTREGISTER_HEADERTEXT = "HEADERTEXT";
	public static final String TBLTAREVENTREQUESTREGISTER_BILLINGACCOUNTID = "BILLINGACCOUNTID";
	public static final String TBLTAREVENTREQUESTREGISTER_BILLINGACCOUNTNUMBER = "BILLINGACCOUNTNUMBER";
	public static final String TBLTAREVENTREQUESTREGISTER_DOCUMENTDATE = "DOCUMENTDATE";
	public static final String TBLTAREVENTREQUESTREGISTER_POSTINGDATE = "POSTINGDATE";
	public static final String TBLTAREVENTREQUESTREGISTER_TRANSACTIONDATE = "TRANSACTIONDATE";
	public static final String TBLTAREVENTREQUESTREGISTER_FINANCIALYEAR = "FINANCIALYEAR";
	public static final String TBLTAREVENTREQUESTREGISTER_DOCUMENTTYPE = "DOCUMENTTYPE";
	public static final String TBLTAREVENTREQUESTREGISTER_REFERENCEDOCUMENTNUMBER = "REFERENCEDOCUMENTNUMBER";
	public static final String TBLTAREVENTREQUESTREGISTER_AMOUNT = "AMOUNT";
	
	
	//Warehouse
	public static final String CREATE_WAREHOUSE = "CREATE_WAREHOUSE";
	public static final String UPDATE_WAREHOUSE = "UPDATE_WAREHOUSE";
	public static final String DELETE_WAREHOUSE = "DELETE_WAREHOUSE";
	public static final String VIEW_WAREHOUSE   = "VIEW_WAREHOUSE";
	
	public static final String RESOURCE_TYPE_MASTER = "RESOURCE_TYPE_MASTER";
	public static final String RESOURCE_SUBTYPE_MASTER = "RESOURCE_SUBTYPE_MASTER";
	public static final String CREATE_RESOURCESUBTYPE = "CREATE_RESOURCESUBTYPE";
	
	public static final String CREATE_RESOURCETYPE = "CREATE_RESOURCETYPE";
	
	public static final String UPDATE_RESOURCETYPE = "UPDATE_RESOURCETYPE";
	public static final String UPDATE_RESOURCESUBTYPE = "UPDATE_RESOURCESUBTYPE";
	
	public static final String CHANGE_INVENTORY_STATUS = "CHANGE_INVENTORY_STATUS";
	public static final String CHANGE_INVENTORY_SUBSTATUS = "CHANGE_INVENTORY_SUBSTATUS";
	public static final String CANCEL_TRANSFER_ORDER = "CANCEL_TRANSFER_ORDER";
	public static final String ACCEPTREJECT_TRANSFER_ORDER = "ACCEPT-REJECT_TRANSFER_ORDER";
	
	public static final String LOGIN_ACTION = "LOGIN_ACTION";
	public static final String LOGOUT_ACTION = "LOGOUT_ACTION";
	
	
	public static final String TBLMWAREHOUSE   = "TBLMWAREHOUSE";
	public static final String TBLMWAREHOUSE_NAME   = "NAME";
	public static final String TBLMWAREHOUSE_DESC   = "DESCRIPTION";
	public static final String TBLMWAREHOUSE_LOCATION   = "LOCATION";
	public static final String TBLMWAREHOUSE_PARENTWAREHOUSE   = "PARENTWAREHOUSE";
	public static final String TBLMWAREHOUSE_REASON   = "REASON";
	public static final String TBLMWAREHOUSE_WAREHOUSETYPE   = "WAREHOUSETYPE";
	public static final String TBLMWAREHOUSE_OWNER   = "OWNER";
	public static final String TBLMWAREHOUSE_CONTACTNO   = "CONTACTNO";
	public static final String TBLMWAREHOUSE_EMAILID   = "EMAILID";
	public static final String TBLMWAREHOUSE_WAREHOUSECODE  = "WAREHOUSECODE";

	
	public static final String CREATE_WAREHOUSETYPE = "CREATE_WAREHOUSETYPE";
	public static final String UPDATE_WAREHOUSETYPE = "UPDATE_WAREHOUSETYPE";
	
	public static final String TBLSWAREHOUSETYPE   = "TBLSWAREHOUSETYPE";
	public static final String TBLSWAREHOUSETYPE_NAME   = "NAME";
	public static final String TBLSWAREHOUSETYPE_DESC   = "DESCRIPTION";
	public static final String TBLSWAREHOUSETYPE_REASON   = "REASON";
	
	
	public static final String TBLSRESOURCETYPE   = "TBLSRESOURCETYPE";
	public static final String TBLSRESOURCETYPE_NAME   = "NAME";
	public static final String TBLSRESOURCETYPE_DESC   = "DESCRIPTION";
	public static final String TBLSRESOURCETYPE_REASON   = "REASON";
	
	
	public static final String TBLSRESOURCESUBTYPE   = "TBLSRESOURCESUBTYPE";
	public static final String TBLSRESOURCESUBTYPE_NAME   = "NAME";
	public static final String TBLSRESOURCESUBTYPE_TYPE = "RESOURCETYPEID";
	public static final String TBLSRESOURCESUBTYPE_DESC   = "DESCRIPTION";
	public static final String TBLSRESOURCESUBTYPE_REASON   = "REASON";
	
	//attribute
	public static final String SEARCH_ATTRIBUTE = "SEARCH_ATTRIBUTE";
	public static final String CREATE_ATTRIBUTE = "CREATE_ATTRIBUTE";
	public static final String UPDATE_ATTRIBUTE = "UPDATE_ATTRIBUTE";
	
	public static final String TBLMATTRIBUTE   = "TBLMATTRIBUTE";
	public static final String TBLMATTRIBUTE_NAME="NAME";
	public static final String TBLMATTRIBUTE_NEW_USEDBY="NEW_USEDBY";
	public static final String TBLMATTRIBUTE_DATATYPE="DATATYPE";
	public static final String TBLMATTRIBUTE_MANDATORY="MANDATORY";
	public static final String TBLMATTRIBUTE_UNIQUE="ISUNIQUE";
	public static final String TBLMATTRIBUTE__REASON="REASON";
	
	
	//resource
	public static final String CREATE_RESOURCE = "CREATE_RESOURCE";
	public static final String UPDATE_RESOURCE = "UPDATE_RESOURCE";
	public static final String SEARCH_RESOURCE = "SEARCH_RESOURCE";
	
	
	//Document Template 
	public static final String CREATE_DOCUMENT_TEMPLATE = "CREATE_DOCUMENT_TEMPLATE";
	public static final String UPDATE_DOCUMENT_TEMPLATE_BASICDETAILS = "UPDATE_DOCUMENT_TEMPLATE_BASICDETAILS";
	public static final String UPDATE_DOCUMENT_TEMPLATE = "UPDATE_DOCUMENT_TEMPLATE";
	
	public static final String TBLMDOCUMENTTEMPLATE = "TBLMDOCUMENTTEMPLATE";
	public static final String TBLMDOCUMENTTEMPLATE_NAME = "TBLMDOCUMENTTEMPLATE_NAME";
	public static final String TBLMDOCUMENTTEMPLATE_DESCRIPTION = "TBLMDOCUMENTTEMPLATE_DESCRIPTION";
	public static final String TBLMDOCUMENTTEMPLATE_VALIDFROMDATE = "TBLMDOCUMENTTEMPLATE_VALIDFROMDATE";
	public static final String TBLMDOCUMENTTEMPLATE_VALIDTODATE = "TBLMDOCUMENTTEMPLATE_NAME";
	
	public static final String TBLMMAILDOCUMENTTEMPLATEDETAIL = "TBLMMAILDOCUMENTTEMPLATEDETAIL";
	public static final String TBLMMAILDOCUMENTTEMPLATEDETAIL_SUBJECT = "TBLMMAILDOCUMENTTEMPLATEDETAIL_SUBJECT";
	
	
	
	public static final String TBLMRESOURCE   = "TBLMRESOURCE";
	public static final String TBLMRESOURCE_NAME   = "NAME";
	public static final String TBLMRESOURCE_MODEL   = "MODEL";
	public static final String TBLMRESOURCE_VENDOR   = "VENDOR";
	public static final String TBLMRESOURCE_RESOURCETYPEID   = "RESOURCETYPEID";
	public static final String TBLMRESOURCE_DESC ="DESCRIPTION";
	public static final String TBLMRESOURCE_REASON="REASON";
	public static final String TBLMRESOURCE_REFID="REFERENCEID";
	
	public static final String TBLMRESOURCE_NEW_REFID="NEW_REFID";
	
	public static final String ADMIN_STAFFID="STF0001";
	
	
	
	
	
	
	
	//inventory
	public static final String UPLOAD_INVENTORY = "UPLOAD_INVENTORY";
	public static final String TRANSFER_INVENTORY = "TRANSFER_INVENTORY";
	public static final String PLACE_ORDER = "PLACE_ORDER";
	
	//Threshold
	public static final String CONFIGURE_THRESHOLD = "CONFIGURE_THRESHOLD";
	public static final String UPDATE_THRESHOLD  = "UPDATE_THRESHOLD";
	public static final String DELETE_THRESHOLD  = "DELETE_THRESHOLD";
	public static final String TBLMWAREHOUSEALERT   = "TBLMWAREHOUSEALERT";
	public static final String THRESHOLDVALUE   = "THRESHOLDVALUE";
	
	//Transfer Order
	public static final String CREATE_TRANSFER_ORDER = "TRANSFER_INVENTORY";
	public static final String TRANSFER_INVENTORY_SUMMARY = "TRANSFER_INVENTORY_SUMMARY";
	//Place order
	public static final String CREATE_PLACE_ORDER = "CREATE_PLACE_ORDER";
	public static final String CANCEL_PLACE_ORDER   = "CANCEL_ORDER";
	public static final String ACCEPT_REJECT_PLACE_ORDER   = "ACCEPT_REJECT_ORDER";
	public static final String UPDATE__PLACE_ORDER   = "UPDATE_ORDER";
	
	//user-warehouse mapping
	public static final String USER_WAREHOUSE_MAPPING = "USER_WAREHOUSE_MAPPING";
	
	public static final String TBLMORDER   = "TBLMORDER";
	public static final String TBLMORDER_NO   = "ORDER_NUMBER";
	public static final String TBLMORDER_REMARK   = "REMARK";
	public static final String TBLMORDER_STATUS   = "STATUS";
	public static final String TBLMORDER_ACCEPT_QUANTITY   = "ACCEPT_QUANTITY";
	public static final String TBLMORDER_TRANSFER_REMARK   = "TRANSFER_REMARK";
	public static final String TBLMORDER_TRANSFER_ORDER_NO   = "TRANSFER_ORDER_NO";
	public static final String TBLMORDER_ACCEPT_REJECT_DATE   = "ACCEPT_REJECT_DATE";
	
	//Bulk Status Change For Inventory
	public static final String BULK_STATUS_CHANGE_FOR_INVENTORY = "BULK_STATUS_CHANGE_FOR_INVENTORY";	
}
