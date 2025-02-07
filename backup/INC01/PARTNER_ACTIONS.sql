
insert into tblmsystemmodules values('MOD601','CPE_SYSTEM','CPE','CPE System',sysdate,sysdate,'N','MOD000',17);
insert into tblmsystemmodules values('MOD602','WAREHOUSE','WareHouse','WareHouse',sysdate,sysdate,'N','MOD601',999);
insert into tblmsystemmodules values('MOD603','WAREHOUSE_MASTER','WareHouse Master','WareHouse Master',sysdate,sysdate,'N','MOD602',999);
insert into tblmsystemmodules values('MOD604','WAREHOUSE_SUMMARY','WareHouse Summary','WareHouse Summary',sysdate,sysdate,'N','MOD602',999);
insert into tblmsystemmodules values('MOD605','WAREHOUSETYPE_MASTER','WareHouse Type Master','WareHouse Type Master',sysdate,sysdate,'N','MOD602',999);
insert into tblmsystemmodules values('MOD606','WAREHOUSE_TREEVIEW','WareHouse Hierarchy','WareHouse Hierarchy',sysdate,sysdate,'N','MOD602',999);
insert into tblmsystemmodules values('MOD607','RESOURCE','Resource','Resource',sysdate,sysdate,'N','MOD601',999);
insert into tblmsystemmodules values('MOD608','RESOURCE_TYPE_MASTER','Resource Type Master','Resource Type Master',sysdate,sysdate,'N','MOD607',999);
insert into tblmsystemmodules values('MOD609','RESOURCE_SUBTYPE_MASTER','Resource Subtype Master','Resource Subtype Master',sysdate,sysdate,'N','MOD607',999);
insert into tblmsystemmodules values('MOD610','RESOURCE_MASTER','Resource Master','Resource Master',sysdate,sysdate,'N','MOD607',999);
insert into tblmsystemmodules values('MOD611','INVENTORY','Inventory','Inventory',sysdate,sysdate,'N','MOD601',999);
insert into tblmsystemmodules values('MOD612','UPLOAD_INVENTORY','Upload Inventory','Upload Inventory',sysdate,sysdate,'N','MOD611',999);
insert into tblmsystemmodules values('MOD613','ORDER_MASTER','Order Management','Order Management',sysdate,sysdate,'N','MOD611',999);
insert into tblmsystemmodules values('MOD614','TRANSFER_INVENTORY','Transfer Inventory','Transfer Inventory',sysdate,sysdate,'N','MOD611',999);
insert into tblmsystemmodules values('MOD615','SEARCH_INVENTORY','Search Inventory','Search Inventory',sysdate,sysdate,'N','MOD611',999);
insert into tblmsystemmodules values('MOD616','SEARCH_INVENTORY_BATCH','Search Inventory Batch','Search Inventory Batch',sysdate,sysdate,'N','MOD611',999);
insert into tblmsystemmodules values('MOD617','ORDER_DETAIL','Order Detail','Order Detail',sysdate,sysdate,'N','MOD611',999);
insert into tblmsystemmodules values('MOD618','SYSTEM_PARAMETER','System Configuration','System Configuration',sysdate,sysdate,'N','MOD601',999);
insert into tblmsystemmodules values('MOD619','ATTRIBUTE_MASTER','Attribute Management','Attribute Management',sysdate,sysdate,'N','MOD618',999);
insert into tblmsystemmodules values('MOD620','SYSTEM_PARAMETER_MENU','System Parameter','System Parameter',sysdate,sysdate,'N','MOD618',999);
insert into tblmsystemmodules values('MOD621','DOCUMENT_TEMPLATE_MASTER','Document Template Management','Document Template Management',sysdate,sysdate,'N','MOD618',999);
insert into tblmsystemmodules values('MOD622','AUDIT_MANAGEMENT','Audit Management','Audit Management',sysdate,sysdate,'N','MOD601',999);
insert into tblmsystemmodules values('MOD623','CONFIGURE_AR_AUDIT','Configure Audit','Configure Audit',sysdate,sysdate,'N','MOD622',999);
insert into tblmsystemmodules values('MOD624','CPE_SEARCH_AUDIT','Search CPE Audit','Search Audit',sysdate,sysdate,'N','MOD622',999);
insert into tblmsystemmodules values('MOD625','SEARCH_WS_AUDIT','Search Web Service Audit','Search Web Service Audit',sysdate,sysdate,'N','MOD622',999);

insert into tblmsystemmodules values('MOD626','CPE_AGENT_MANAGEMENT','CPE Agent Management','CPE Agent Management',sysdate,sysdate,'N','MOD601',999);
insert into tblmsystemmodules values('MOD627','CPE_SEARCH_AGENT','CPE Agent List','Agent List',sysdate,sysdate,'N','MOD626',999);
insert into tblmsystemmodules values('MOD628','CPE_SEARCH_AGENT_SCHEDULE','CPE Agent Schedule','Agent Schedule',sysdate,sysdate,'N','MOD626',999);
insert into tblmsystemmodules values('MOD629','SEARCH_AGENT_IN_QUEUE','Search Agent in Queue','Search Agent in Queue',sysdate,sysdate,'N','MOD626',999);


insert into tblmaction values('ACN60000','Warehouse Master(Menu)','SCR001','WAREHOUSE_MASTER','Warehouse Master','ACT02',null,4,'N','MOD603',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60001','Create Warehouse','SCR001','CREATE_WAREHOUSE','Create Warehouse','ACT02',null,4,'N','MOD603',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60002','Update Warehouse','SCR001','UPDATE_WAREHOUSE','Update Warehouse','ACT02',null,4,'N','MOD603',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60003','Delete Warehouse','SCR001','DELETE_WAREHOUSE','Delete Warehouse','ACT02',null,4,'N','MOD603',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60004','Transfer Inventory Detail','SCR001','TRANSFER_INVENTORY_SUMMARY','Transfer Inventory Detail','ACT02',null,4,'N','MOD603',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60005','Configure Threshold','SCR001','CONFIGURE_THRESHOLD','Configure Threshold','ACT02',null,4,'N','MOD603',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60006','Accept-Reject Transfer Order','SCR001','ACCEPT-REJECT_TRANSFER_ORDER','Accept-Reject Transfer Order','ACT02',null,4,'N','MOD603',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60007','Cancel Transfer Order','SCR001','CANCEL_TRANSFER_ORDER','Cancel Transfer Order','ACT02',null,4,'N','MOD603',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60008','WareHouse Summary(Menu)','SCR001','WAREHOUSE_SUMMARY','WareHouse Summary','ACT02',null,4,'N','MOD604',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60009','WareHouseType Master(Menu)','SCR001','WAREHOUSETYPE_MASTER','WareHouseType Master','ACT02',null,4,'N','MOD605',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60010','Create WareHouseType','SCR001','CREATE_WAREHOUSETYPE','Create WareHouseType','ACT02',null,4,'N','MOD605',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60011','Update WareHouseType','SCR001','UPDATE_WAREHOUSETYPE','Update WareHouseType','ACT02',null,4,'N','MOD605',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60012','WareHouse Hierarchy(Menu)','SCR001','WAREHOUSE_TREEVIEW','WareHouse Hierarchy','ACT02',null,4,'N','MOD606',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60013','Resource Type Master(Menu)','SCR001','RESOURCE_TYPE_MASTER','WareHouseResource Type Master','ACT02',null,4,'N','MOD608',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60014','Create Resource Type','SCR001','CREATE_RESOURCETYPE','Create Resource Type','ACT02',null,4,'N','MOD608',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60015','Update Resource Type','SCR001','UPDATE_RESOURCETYPE','Update Resource Type','ACT02',null,4,'N','MOD608',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60016','Resource Subtype Master(Menu)','SCR001','RESOURCE_SUBTYPE_MASTER','Resource Subtype Master','ACT02',null,4,'N','MOD609',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60017','Create Resource Subtype','SCR001','CREATE_RESOURCESUBTYPE','Create Resource Subtype','ACT02',null,4,'N','MOD609',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60018','Update Resource Subtype','SCR001','UPDATE_RESOURCESUBTYPE','Update Resource Subtype','ACT02',null,4,'N','MOD609',null,null,0,'FIXED',null);


insert into tblmaction values('ACN60019','Resource Master(Menu)','SCR001','RESOURCE_MASTER','Resource Master','ACT02',null,4,'N','MOD610',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60020','Create Resource','SCR001','CREATE_RESOURCE','Create Resource','ACT02',null,4,'N','MOD610',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60021','Update Resource','SCR001','UPDATE_RESOURCE','Update Resource','ACT02',null,4,'N','MOD610',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60022','Upload Inventory(Menu)','SCR001','UPLOAD_INVENTORY_MGT','Upload Inventory','ACT02',null,4,'N','MOD612',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60023','Cancel Order','SCR001','CANCEL_ORDER','Cancel Order','ACT02',null,4,'N','MOD613',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60024','Accept-Reject Order','SCR001','ACCEPT_REJECT_ORDER','Accept-Reject Order','ACT02',null,4,'N','MOD613',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60025','Update Order','SCR001','UPDATE_ORDER','Update Order','ACT02',null,4,'N','MOD613',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60026','Place Order','SCR001','CREATE_PLACE_ORDER','Place Order','ACT02',null,4,'N','MOD613',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60027','Transfer Inventory(Menu)','SCR001','TRANSFER_INVENTORY_MGT','Transfer Inventory','ACT02',null,4,'N','MOD614',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60028','Search Inventory(Menu)','SCR001','SEARCH_INVENTORY','Search Inventory','ACT02',null,4,'N','MOD615',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60029','Search Inventory Batch(Menu)','SCR001','SEARCH_INVENTORY_BATCH','Search Inventory Batch','ACT02',null,4,'N','MOD616',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60030','Order Detail(Menu)','SCR001','ORDER_DETAIL','Order Detail','ACT02',null,4,'N','MOD617',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60031','Change Inventory Status','SCR001','CHANGE_INVENTORY_STATUS','Change Inventory Status','ACT02',null,4,'N','MOD615',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60032','Change Inventory SubStatus','SCR001','CHANGE_INVENTORY_SUBSTATUS','Change Inventory SubStatus','ACT02',null,4,'N','MOD615',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60033','Inventory History Detail','SCR001','INVENTORY_HISTORY_DETAIL','Inventory History Detail','ACT02',null,4,'N','MOD615',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60034','Update System Parameter','SCR001','UPDATE_SYSTEM_PARAMETER','Update System Parameter','ACT02',null,4,'N','MOD620',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60035','Attribute Management(Menu)','SCR001','ATTRIBUTE_MASTER','Attribute Management','ACT02',null,4,'N','MOD619',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60036','Create Attribute','SCR001','CREATE_ATTRIBUTE','Create Attribute','ACT02',null,4,'N','MOD619',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60037','Update Attribute','SCR001','UPDATE_ATTRIBUTE','Update Attribute','ACT02',null,4,'N','MOD619',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60038','Create Document Template','SCR001','CREATE_DOCUMENT_TEMPLATE','Create Document Template','ACT02',null,4,'N','MOD621',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60039','Update Document Template','SCR001','UPDATE_DOCUMENT_TEMPLATE','Update Document Template','ACT02',null,4,'N','MOD621',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60040','Update Document Template Basic Details','SCR001','UPDATE_DOCUMENT_TEMPLATE_BASICDETAILS','Update Document Template Basic Details','ACT02',null,4,'N','MOD621',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60041','Configure Audit(Menu)','SCR001','CONFIGURE_AR_AUDIT','Configure Audit','ACT02',null,4,'N','MOD623',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60042','Search Audit(Menu)','SCR001','SEARCH_AUDIT','Search Audit','ACT02',null,4,'N','MOD624',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60043','Search Web Service Audit(Menu)','SCR001','SEARCH_WS_AUDIT','Search Web Service Audit','ACT02',null,4,'N','MOD625',null,null,0,'FIXED',null);


insert into tblmaction values('ACN60044','CPE Agent List(Menu)','SCR001','SEARCH_AGENT','Agent List','ACT02',null,4,'N','MOD627',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60045','Update Agent Parameter','SCR001','UPDATE_AGENT_PARAMETER','Update Agent Parameter','ACT02',null,4,'N','MOD627',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60046','Agent Schedule','SCR001','SEARCH_AGENT_SCHEDULE','Agent Schedule','ACT02',null,4,'N','MOD628',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60047','Update Agent Schedule','SCR001','UPDATE_AGENT_SCHEDULE','Update Agent Schedule','ACT02',null,4,'N','MOD628',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60048','Remove Agent Schedule','SCR001','REMOVE_AGENT_SCHEDULE','Remove Agent Schedule','ACT02',null,4,'N','MOD628',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60049','Search Agent In Queue(Menu)','SCR001','SEARCH_AGENT_IN_QUEUE','Search Agent In Queue','ACT02',null,4,'N','MOD629',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60050','Search Place Order','SCR001','SEARCH_PLACE_ORDER','Search Place Order','ACT02',null,4,'N','MOD603',null,null,0,'FIXED',null);
insert into tblmaction values('ACN60051','View WareHouse Hierarchy','SCR001','VIEW_WAREHOUSE_HIERARCHY','View WareHouse Hierarchy','ACT02',null,4,'N','MOD603',null,null,0,'FIXED',null);

insert into tblmaction values('ACN60052','Document Template Management(Menu)','SCR001','DOCUMENT_TEMPLATE_MASTER','Document Template Management','ACT02',null,4,'N','MOD621',null,null,0,'FIXED',null);

commit;

