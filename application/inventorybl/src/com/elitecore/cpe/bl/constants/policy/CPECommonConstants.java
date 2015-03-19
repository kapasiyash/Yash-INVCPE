package com.elitecore.cpe.bl.constants.policy;

public class CPECommonConstants {


	
	//Primary Key Generation
	public static final String AR_EVENT="AR_EVENT";
	public static final String AR_EVENT_ENTITY="AR_EVENT_ENTITY";
	public static final String AR_EVENT_DATA="AR_EVENT_DATA";
	public static final String AR_RESPONSE_DATA="AR_RESPONSE_DATA";
	public static final String AR_RECEIVER_DATA="AR_RECEIVER_DATA";
	public static final String AR_SENDER_DATA="AR_SENDER_DATA";
	public static final String AR_EVENT_PLUGIN="AR_EVENT_PLUGIN";
	public static final String AR_PROCESSOR="AR_PROCESSOR";
	public static final String AR_EVENT_DISPATCH="AR_EVENT_DISPATCH";
	public static final String AR_REQUEST_TO_DISPATCH_CONVERT="AR_REQUEST_TO_DISPATCH_CONVERT";
	public static final String AR_DISP_RESPONSE_TO_AUDIT = "AR_DISP_RESPONSE_TO_AUDIT";
	public static final String AR_EVENT_CONFIGURATION="AR_EVENT_CONFIGURATION";
	public static final String AR_ACCOUNTING_HEAD="AR_ACCOUNTING_HEAD";
	public static final String AR_REVENUE_GL_MAP="AR_REVENUE_GL_MAP";
	public static final String AR_GL_REGISTER="AR_GL_REGISTER";
	public static final String AR_ENTITY_PROFILE = "AR_ENTITY_PROFILE";
	public static final String AR_REQUEST_REGISTER = "AR_REQUEST_REGISTER";
	public static final String AR_EVENT_TRACKER = "AR_EVENT_TRACKER";
//	public static final String WAREHOUSE_DATA = "WAREHOUSE_DATA";
//	public static final String ATTRIBUTE_DATA = "ATTRIBUTE_DATA";
	public static final String BATCH_DATA = "BATCH_DATA";
//	public static final String INVENTORY_DATA = "INVENTORY_DATA";
	public static final String RESOURCE_DATA = "RESOURCE_DATA";
	public static final String TRANSFERORDER_DATA = "TRANSFERORDER_DATA";
	public static final String RESERVE_DATA = "RESERVE_DATA";
	public static final String ORDER_DATA = "ORDER_DATA";
	
	// Default Event
	public static final String AR_DEFAULT_EVENT_ALIAS="DEFAULT_EVENT";
	
	
	//Customer Accounting Effect
	public static final String AR_CREDIT_EFFECT="Cr";
	public static final String AR_DEBIT_EFFECT="Dr";
	
	
	//Parameter Type
	
	public static final String AR_TEXTBOX = "TEXTBOX_TYPE";
	public static final String AR_DATEBOXBOX = "DATEBOX_TYPE";
	public static final String AR_COMBOBOX = "COMBOBOX_TYPE";
	public static final String AR_LISTBOX = "LISTBOX_TYPE";
	public static final String AR_DOUBLEBOX = "DOUBLEBOX_TYPE";
	
	// Componenet Type
	public static final String COMP_TEXTBOX = "TextBox";
	public static final String COMP_COMBOBOX = "ComboBox";
	public static final String COMP_LISTBOX = "ListBox";
	
	public static final String AR_COMBOTYPE_STATIC = "STATIC";
	public static final String AR_COMBOTYPE_DYNAMIC = "DYNAMIC";
	public static final String AR_LISTTYPE_DYNAMIC = "DYNAMIC";
	
	
	//Entity Type
	public static final String ENTITY_TYPE_EVENT = "AR_EVENT";
	public static final String ENTITY_TYPE_ACCOUNTINGHEAD = "AR_ACCOUNTINGHEAD";
	public static final String ENTITY_TYPE_REVENUEGLMAP = "AR_REVENUE_GLMAP";
	public static final String ENTITY_TYPE_GLREGISTER = "AR_GL_REGISTER";
	
	
	//Financial period Status
	public static final String FINANCIAL_STATUS_OPEN = "Open";
	public static final String FINANCIAL_STATUS_REOPEN = "Re-Open";
	public static final String FINANCIAL_STATUS_CLOSED = "Closed";
	
	public static final Long FINANCIAL_STATUS_OPEN_ID = 1L;
	public static final Long FINANCIAL_STATUS_REOPEN_ID = 2L;
	public static final Long FINANCIAL_STATUS_CLOSED_ID = 3L;
	
	
	
	// Policy Status
	public static final Long POLICY_STATUS_ACTIVE = 1L;
	public static final Long POLICY_STATUS_INACTIVE = 2L;
	public static final Long POLICY_STATUS_DEACTIVATION_IN_PROGRESS = 3L;
	
	public static final String ACTIVE = "Active";
	public static final String INACTIVE = "Inactive";
	public static final String DEACTIVE_IN_PROGRESS = "Deactivation in Progress";
	
	public static final Long SCHEDULE_STATUS_ACTIVE = 1L;
	public static final Long SCHEDULE_STATUS_INACTIVE = 2L;
	 
	
	public static final String MODE = "Mode";
	public static final Long POLICY_MODE_AUTO = 1L;
	public static final Long POLICY_MODE_OPTIN = 2L;
	public static final Long POLICY_MODE_HYBRID = 3L;
	 public static final String MODE_OPTIN = "Opt-In";
	 public static final String MODE_HYBRID = "Hybrid";
	
	public static final Long PREPAID = 1L;
	public static final Long POSTPAID = 2L;
	public static final String PREPAID_SERVICE="Pre-paid";
	public static final String POSTPAID_SERVICE="Post-paid";
	
	public static final Long BONUS_POLICY =	1L;
	public static final Long ACCUMULATION_POLICY =	2L;
	public static final Long HYBRID_BONUS_POLICY = 3L;
	
	public static final Long DAILY = 1L;
	public static final Long WEEKLY = 2L;
	public static final Long MONTHLY = 3L;
	
	public static final Long PLM = 1L;
	public static final Long POINT = 2L;
	
	public static final String ELIGIBLITY_ALIAS = "ELIGIBILITY_POLICY";
	public static final String HYBRID_ALIAS = "HYBRID_POLICY";
	public static final String BONUS_ALIAS = "BONUS_POLICY";
	public static final String ACCUMULATION_ALIAS = "ACCUMULATION_POLICY";
	public static final String EXPIRY_ALIAS = "EXPIRY_POLICY";
	public static final String REDEEMPTION = "REDEEMPTION";
	public static final String PLM_ACCUMULATION_ALIAS= "PLM_BENEFIT";
	
	
	
	
	public static final Long CASH_BUCKET = 1L;
	public static final Long BILL_BUCKET = 2L;
	
	public static final String EVENTS_MANUAL_ADJUSTMENT = "MANUAL_ADJUSTMENT";
	public static final String EVENTS_VOUCHER_PURCHASE = "VOUCHER_PURCHASE";
	public static final String POINT_GIFT_VOUCHER = "GIFT_VOUCHER";
	
	public static final String POINT_MANUAL_ADJUSTMENT ="MANUAL_ADJUSTMENT";
	public static final String POINT_EXPIRY ="POINT_EXPIRY";
	public static final String POINT_REDEEMPTION ="REDEEMPTION";
	
	public static final Long TRASACTION_MANUAL = 1L;
	public static final Long TRASACTION_GIFT = 4L;
	

	public static final Long POINT_EXPIRY_ID = 3L;
	
	
	public static final String JAN="JANUARY";
	public static final String FEB="FEBRUARY";
	public static final String MAR="MARCH";
	public static final String APR="APRIL";
	public static final String MAY="MAY";
	public static final String JUN="JUNE";
	public static final String JULY="JULY";
	public static final String AUG="AUGUST";
	public static final String SEPT="SEPTEMBER";
	public static final String OCT="OCTOBER";
	public static final String NOV="NOVEMBER";
	public static final String DEC="DECEMBER";
	
	
	// Reflection API
	public static final String OBJECT_CLASS = "java.lang.Object";
	
	
	
	
	// For Response VO
	public static final Long SUCCESS_RESPONSE=0L;
	public static final Long ERROR_RESPONSE=-1L;
	
	
	// External System Message Type Constants
	
	public static final Long MESSAGETYPE_BILLING_ACCUMULATION=1L;
	public static final Long MESSAGETYPE_PAYMENT_ACCUMULATION=2L;
	
	
	public static final Long ACTION_SUCCESS_RESPONSE = 0L;
	public static final Long ACTION_FAILURE_RESPONSE = -1L;
	
	//Entity Types
	public static final String ENTITY_LOOKUP_POLICY = "LookUp Policy";
	public static final String ENTITY_LOOKUP_POLICY_OPT = "LookUp Policy Opt";
	public static final String ENTITY_EXPIRY_POLICY = "Expiry Policy";
	public static final String ENTITY_ACL_GROUP = "ACL Group";
	public static final String ENTITY_BENIFIT_POLICY = "Benefit";
	public static final String ENTITY_USER= "User";
	public static final String ENTITY_BUCKETTYPE = "Bucket Type";
	public static final String CREATE_BONUS_POLICY="Create Bonus Policy";
	
	public static final String CUSTOMER_ACCOUNT_NUMBER = "Customer Account Number";
	public static final String BILLING_ACCOUNT_NUMBER = "Billing Account Number";
	public static final String REWARD_POINTS = "Rewards Points";
	public static final String PLM_NAME = "PLM Benefit Name";
	public static final String POLICY_KEY = "Policy Key";
	
	public static final char AGENT_OVERRULE_CHUNKSIZE_YES = 'Y';
	public static final char AGENT_OVERRULE_CHUNKSIZE_NO = 'N';
	
	
	//PANEL
	public static final String USER_PANEL = "USER";
	public static final String ADMIN_PANEL = "ADMIN";
	
	 //Added By Rinkal Sadariya
	public static final String ITEM_DATA = "ITEM_DATA";
	
	
	//WS Status
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	
	public static final long AUTOMATIC_PLACEORDER = 1;
	public static final long MANUAL_PLACEORDER = 0;
	

}
