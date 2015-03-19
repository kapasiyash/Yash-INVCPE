package com.elitecore.cpe.web.constants;

/**
 * @author yash.kapasi
 *
 */
public class Pages {

	// Admin User
	public static final String VIEW_ADMIN_USER = "/WEB-INF/pages/core/user/admin/view-admin-user.zul";
	public static final String UPDATE_ADMIN_USER = "/WEB-INF/pages/core/user/admin/update-admin-user.zul";
	
	
	public static final String AR_EVENT = "/WEB-INF/pages/core/event/create-event.zul";
	public static final String AR_EVENT_ENTITY = "/WEB-INF/pages/core/event/create-event-entity.zul";
	
	public static final String USER_DASHBOARD = "/WEB-INF/pages/core/user/dashboard/user-dashboard.zul";
//	public static final String USER_DASHBOARD = "index.zul";
	public static final String USER_LOGOUT = "/WEB-INF/pages/core/user/dashboard/user-dashboard.zul";
	
	
	public static final String CREATE_DATA_CLASS="/WEB-INF/pages/core/configuration/dataclass/create-data-class.zul";
	public static final String VIEW_DATA_CLASS="/WEB-INF/pages/core/configuration/dataclass/view-data-class.zul";
	public static final String DELETE_DATA_CLASS = "/WEB-INF/pages/core/configuration/dataclass/delete-data-class.zul";
	public static final String UPDATE_DATA_CLASS = "/WEB-INF/pages/core/configuration/dataclass/update-data-class.zul";
	
	
	//Agent
		public static final String VIEW_AGENT="/WEB-INF/pages/core/system/agent/view-agent.zul";
		public static final String UPDATE_AGENT_PARAMETER="/WEB-INF/pages/core/system/agent/update-agent-parameter.zul";
		public static final String DELETE_AGENT="/WEB-INF/pages/core/system/agent/delete-agent.zul";
		public static final String CREATE_AGENT_SCHEDULR="/WEB-INF/pages/core/system/agent/create-agent-schedule.zul";
		
		public static final String VIEW_AGENT_SCHEDULE="/WEB-INF/pages/core/system/agent/view-agent-schedule.zul";
		public static final String VIEW_AGENT_HISTORY="/WEB-INF/pages/core/system/agent/view-popup-history.zul";
		
		public static final String VIEW_AGENT_IN_QUEUE="/WEB-INF/pages/core/system/agent/view-agent-runin-queue.zul";
		
		
		public static final String VIEW_AGENT_HISTORY_ACTION="/WEB-INF/pages/core/system/agent/view-history.zul";
		public static final String UPDATE_AGENT_SCHEDULE="/WEB-INF/pages/core/system/agent/update-agent-schedule.zul";
		public static final String DELETE_AGENT_SCHEDULE="/WEB-INF/pages/core/system/agent/delete-agent-schedule.zul";
		
		// Cron Expression Builder GUI
		public static final String CRON_EXPRESSION_BUILDER="/WEB-INF/pages/core/configuration/cronexpr/cron-expression-builder.zul";
		
	//View, Delete and Update Event and Event Entity	
		public static final String VIEW_AR_EVENT = "/WEB-INF/pages/core/event/view-event.zul";
		public static final String DELETE_AR_EVENT = "/WEB-INF/pages/core/event/delete-event.zul";
		public static final String UPDATE_AR_EVENT = "/WEB-INF/pages/core/event/update-event.zul";
		public static final String CONFIGURE_AR_EVENT = "/WEB-INF/pages/core/event/configure-event.zul";
		public static final String CONFIGURE_PROC_SCHEDULE = "/WEB-INF/pages/core/event/configure-proc-schedule.zul";
		public static final String SEARCH_EVENT_PROCEDURE_LOG = "/WEB-INF/pages/core/event/trackproc/search-event-proc.zul";
		
		public static final String VIEW_AR_EVENT_ENTITY = "/WEB-INF/pages/core/event/view-event-entity.zul";
		public static final String DELETE_AR_EVENT_ENTITY = "/WEB-INF/pages/core/event/delete-event-entity.zul";
		public static final String UPDATE_AR_EVENT_ENTITY = "/WEB-INF/pages/core/event/update-event-entity.zul";
		
		
		public static final String CREATE_EVENT_PLUGIN = "/WEB-INF/pages/core/plugins/create-event-plugin.zul";
		public static final String VIEW_EVENT_PLUGIN = "/WEB-INF/pages/core/plugins/view-event-plugin.zul";
		public static final String DELETE_EVENT_PLUGIN = "/WEB-INF/pages/core/plugins/delete-event-plugin.zul";
		public static final String UPDATE_EVENT_PLUGIN = "/WEB-INF/pages/core/plugins/update-event-plugin.zul";
		
		public static final String VIEW_ACCOUNTING_HEAD = "/WEB-INF/pages/core/accounting/view-accounting-head.zul";
		public static final String VIEW_REVENUE_GLMAP = "/WEB-INF/pages/core/accounting/view-revenue-glmap.zul";
		public static final String VIEW_GL_REGISTER = "/WEB-INF/pages/core/accounting/view-gl-register.zul";
		public static final String UPDATE_REVENUE_GLMAP = "/WEB-INF/pages/core/accounting/update-revenue-glmap.zul";
		public static final String UPDATE_ACCOUNTING_HEAD = "/WEB-INF/pages/core/accounting/update-accounting-head.zul";
		public static final String UPDATE_GL_REGISTER = "/WEB-INF/pages/core/accounting/update-gl-register.zul";
		public static final String DELETE_AR_ACCOUNTING_HEAD = "/WEB-INF/pages/core/accounting/delete-accounting-head.zul";
		public static final String DELETE_AR_REVENUE_GLMAP = "/WEB-INF/pages/core/accounting/delete-revenue-glmap.zul";
		public static final String DELETE_GL_REGISTER = "/WEB-INF/pages/core/accounting/delete-gl-register.zul";
		
		//Event Tracker
		public static final String VIEW_EVENT_TRACKER = "/WEB-INF/pages/core/event/track/view-event-track.zul";
		
		//Rules Configure
		public static final String CONFIGURE_RULE = "/WEB-INF/pages/core/system/rules/configure-rule.zul";
		
		//Audit
		public static final String VIEW_AUDIT = "/WEB-INF/pages/core/audit/view-audit.zul";
		public static final String VIEW_WS_AUDIT = "/WEB-INF/pages/core/audit/view-wsaudit.zul";
		public static final String VIEW_AUDIT_ENTRY = "/WEB-INF/pages/core/audit/view-audit-entry.zul";
		
		
		//Financial Period
		public static final String OPEN_FINANCIAL_PERIOD = "/WEB-INF/pages/core/system/financialperiod/open-financial-period.zul";
		public static final String SEARCH_FINANCIAL_PERIOD = "/WEB-INF/pages/core/system/financialperiod/search-financial-period.zul";
		public static final String VIEW_FINANCIAL_PERIOD = "/WEB-INF/pages/core/system/financialperiod/view-financial-period.zul";
		
		public static final String CREATE_ACCOUNTING_HEAD = "/WEB-INF/pages/core/accounting/create-accounting-head.zul";
		
		public static final String CREATE_AR_EVENT = "/WEB-INF/pages/core/event/create-event.zul";
		
		public static final String CREATE_AR_DATA_CLASS = "/WEB-INF/pages/core/configuration/dataclass/create-data-class.zul";
		
		public static final String CREATE_PLUGIN_CLASS = "/WEB-INF/pages/core/plugins/create-event-plugin.zul";
		
		public static final String CREATE_AGENT_SCHEDULE = "/WEB-INF/pages/core/system/agent/create-agent-schedule.zul";
		
		public static final String CREATE_GL_REGISTER = "/WEB-INF/pages/core/accounting/create-gl-register.zul";
		
		public static final String CREATE_REVENUE_GLMAP = "/WEB-INF/pages/core/accounting/create-revenue-glmap.zul";
		
		public static final String CREATE_TREE_AUDIT = "/WEB-INF/pages/core/audit/create-tree.zul";
		
		public static final String CREATE_WH_TREE_AUDIT = "/WEB-INF/pages/core/master/warehouse/tree/create-warehouse-tree.zul";
		
		// GL Register Report
		public static final String VIEW_GLREGISTER_SUMMARY = "/WEB-INF/pages/core/event/report/gl-register-report.zul";
		
		//Add GL
		public static final String ADD_GLCODE = "/WEB-INF/pages/core/event/eventgl/add-glcode.zul";
		
		public static final String DEBIT_LAYOUT = "/WEB-INF/pages/core/event/eventgl/debitvlayout.zul";
		public static final String CREDIT_LAYOUT = "/WEB-INF/pages/core/event/eventgl/creditvlayout.zul";
		
		public static final String USER_HOME = "/WEB-INF/pages/core/dashboard/home-dashboard.zul";
		public static final String ADMIN_HOME = "/WEB-INF/pages/core/dashboard/admin-dashboard.zul";
		
		public static final String CREATE_WAREHOUSE_EVENT = "/WEB-INF/pages/core/master/warehouse/CreateWarehouse.zul";
		public static final String VIEW_WAREHOUSE_EVENT = "/WEB-INF/pages/core/master/warehouse/ViewWarehouse.zul";
		
		public static final String CREATE_WAREHOUSETYPE_EVENT = "/WEB-INF/pages/core/master/warehouse/CreateWarehouseType.zul";
		public static final String VIEW_WAREHOUSETYPE_EVENT = "/WEB-INF/pages/core/master/warehouse/ViewWarehouseType.zul";

		
		public static final String CREATE_ATTRIBUTE_EVENT = "/WEB-INF/pages/core/master/attribute/CreateAttribute.zul";
		public static final String VIEW_ATTRIBUTE_EVENT = "/WEB-INF/pages/core/master/attribute/ViewAttribute.zul";
		
		//Added By Rinkal Sadariya
				
		public static final String CREATE_ITEM_EVENT = "/WEB-INF/pages/core/master/item/createItem.zul";
		public static final String VIEW_ITEM_EVENT = "/WEB-INF/pages/core/master/item/viewItem.zul";
		
		public static final String VIEW_BATCHDETAIL_EVENT = "/WEB-INF/pages/core/inventory/search/ViewInventoryBatchDetail.zul";
		
		
		public static final String CREATE_RESOURCE_TYPE = "/WEB-INF/pages/core/master/item/create-resource-type.zul";
		public static final String CREATE_RESOURCE_SUBTYPE = "/WEB-INF/pages/core/master/item/create-resource-subtype.zul";
		public static final String VIEW_RESOURCE_TYPE = "/WEB-INF/pages/core/master/item/view-resource-type.zul";
		public static final String VIEW_RESOURCE_SUBTYPE = "/WEB-INF/pages/core/master/item/view-resource-subtype.zul";
		
		
		public static final String VIEW_TRANSFERINVENTORY_ACTION = "/WEB-INF/pages/core/master/warehouse/viewTransferInventory.zul";
		public static final String CANCEL_TRANSFERINVENTORY_ACTION = "/WEB-INF/pages/core/master/warehouse/cancelTransferInventory.zul";
		public static final String REJECT_TRANSFERINVENTORY_ACTION = "/WEB-INF/pages/core/master/warehouse/rejectTransferInventory.zul";
		
		
		public static final String VIEW_INVENTORY_EVENT= "/WEB-INF/pages/core/inventory/search/viewInventory.zul";
		public static final String VIEW_CSVFORMAT_EVENT= "/WEB-INF/pages/core/inventory/UploadInventoryCSVFormat.zul";
		public static final String VIEW_BATCHSUMMARY_EVENT = "/WEB-INF/pages/core/inventory/search/ViewBatchSummary.zul";
		public static final String VIEW_PLACEORDER_ACTION = "/WEB-INF/pages/core/master/warehouse/viewPlaceOrder.zul";
		public static final String CANCEL_PLACEORDER_ACTION = "/WEB-INF/pages/core/master/warehouse/cancelPlaceOrder.zul";
		public static final String TRANSFER_PLACEORDER_ACTION = "/WEB-INF/pages/core/master/warehouse/TransferPlaceOrder.zul";
		public static final String VIEW_PLACEORDER_DETAIL_ACTION = "/WEB-INF/pages/core/master/warehouse/viewPlaceOrderDetail.zul";
		
		public static final String VIEW_TRANSFERORDER_DETAIL_ACTION = "/WEB-INF/pages/core/inventory/transfer/viewTransferOrderDetail.zul";
		
		
		//Document Template
		public static final String VIEW_DOCUMENT_TEMPLATE="/WEB-INF/pages/core/configuration/notification/view-document-template.zul";
		public static final String CREATE_DOCUMENT_TEMPLATE="/WEB-INF/pages/core/configuration/notification/create-document-template.zul";
		
}

