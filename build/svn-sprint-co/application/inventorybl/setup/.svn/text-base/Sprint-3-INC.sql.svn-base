set define on;
set echo on;


DEFINE partnerDBUser="&&partner_db_user"
DEFINE partnerDBPassword="&&partner_db_password"


set define on;

conn &&database_env/&&database_env@//&&db_ip:&&db_port/&&db_sid 
prompt


set define on;
prompt Grant partner objects in CPE 
conn &&partnerDBUser/&&partnerDBPassword@//&&db_ip:&&db_port/&&db_sid

GRANT SELECT ON &&partnerDBUser..TBLMSTAFF TO &&database_env;
GRANT SELECT ON &&partnerDBUser..TBLMGROUP TO &&database_env;
GRANT SELECT ON &&partnerDBUser..TBLMGROUPACTIONREL TO &&database_env;
GRANT SELECT ON &&partnerDBUser..TBLMACTION TO &&database_env;
GRANT SELECT ON &&partnerDBUser..TBLMSYSTEMUSERGROUPREL TO &&database_env;



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


prompt Completed - Grant partner objects in CPE

prompt Creating partner SYNONYMS in CPE
conn &&database_env/&&database_env@//&&db_ip:&&db_port/&&db_sid 

CREATE OR REPLACE SYNONYM TBLMSTAFF FOR &&partnerDBUser..TBLMSTAFF;
CREATE OR REPLACE SYNONYM TBLMGROUP FOR &&partnerDBUser..TBLMGROUP;
CREATE OR REPLACE SYNONYM TBLMGROUPACTIONREL FOR &&partnerDBUser..TBLMGROUPACTIONREL;
CREATE OR REPLACE SYNONYM TBLMACTION FOR &&partnerDBUser..TBLMACTION;
CREATE OR REPLACE SYNONYM TBLMSYSTEMUSERGROUPREL FOR &&partnerDBUser..TBLMSYSTEMUSERGROUPREL;

prompt Complete - partner SYNONYMS Created Successfully

prompt Creating Views in CPE
conn &&database_env/&&database_env@//&&db_ip:&&db_port/&&db_sid 

CREATE VIEW VW_USER
AS
SELECT STAFFID, TO_NUMBER(SUBSTR(STAFFID,4,LENGTH(STAFFID))) STAFFID_NUM,NAME, USERNAME, PASSWORD, ENCRYPTIONTYPEID, EMAILADDRESS, PHONE, LASTLOGINTIME, MOBILE, SYSTEMUSERID
FROM TBLMSTAFF WHERE STAFFID NOT IN ('STFAGN1','STFDIS1','STFRES1');

---- ACL-ACTIONREL
CREATE VIEW VW_ACL_ACTION_REL
AS
SELECT GREL.GROUPID AS ACLGROUPID,ACTION.ALIAS AS ACTIONALIAS FROM TBLMGROUPACTIONREL GREL
INNER JOIN TBLMACTION ACTION 
ON GREL.ACTIONID=ACTION.ACTIONID ORDER BY GREL.GROUPID;

----- USER-ACL REL
CREATE VIEW VW_USER_ACL_REL
AS
SELECT STAFF.STAFFID AS USERID,USERGRPREL.GROUPID AS ACLGROUPID FROM TBLMSTAFF STAFF INNER JOIN TBLMSYSTEMUSERGROUPREL USERGRPREL
ON STAFF.SYSTEMUSERID=USERGRPREL.SYSTEMUSERID ORDER BY USERGRPREL.GROUPID;

commit;

prompt Complete - Views Created Successfully

prompt Creating Table
set define off;

CREATE TABLE TBLMMESSAGETAG
(
    MESSAGETAGID    NUMBER(6,0) NOT NULL,
    MESSAGETEXT     VARCHAR2(40 BYTE) NOT NULL,
	DESCRIPTION		VARCHAR2(255 BYTE) ,
    MESSAGETAG      VARCHAR2(40 BYTE) NOT NULL,    
    CONSTRAINT PKTBMESSAGETAG PRIMARY KEY (MESSAGETAGID)
);



CREATE TABLE TBLMDOCUMENTCATEGORY
(
    DOCUMENTCATEGORYID       NUMBER(6,0) NOT NULL,
    NAME         	VARCHAR2(40 BYTE) NOT NULL,
    DESCCRIPTION 	VARCHAR2(100 BYTE),
    ALIAS		VARCHAR2(50 BYTE),
    ENABLEEMAIL 	CHAR(1 BYTE),
    ENABLESMS 		CHAR(1 BYTE),
    CONSTRAINT PKTBDOCUMENTCATEGORYPK PRIMARY KEY (DOCUMENTCATEGORYID)
);

COMMENT ON TABLE TBLMDOCUMENTCATEGORY IS 'Document Category table is used to store category of document based on Document Category Id';

CREATE TABLE TBLRDOCCATEGORYMESSAGETAGREL
(
    DOCUMENTCATEGORYID  NUMBER(6,0) NOT NULL,
    MESSAGETAGID    NUMBER(6,0) NOT NULL,
	CONSTRAINT PKTBDOCCATEGORYMESSAGETAGREL PRIMARY KEY(DOCUMENTCATEGORYID,MESSAGETAGID),
	CONSTRAINT FK1TBDOCTMPLTDOCTEMPLATEID FOREIGN KEY (DOCUMENTCATEGORYID) REFERENCES TBLMDOCUMENTCATEGORY (DOCUMENTCATEGORYID),
	CONSTRAINT FK2TBMSGTAGMESSAGETAGID FOREIGN KEY (MESSAGETAGID) REFERENCES TBLMMESSAGETAG (MESSAGETAGID)
);

COMMENT ON TABLE TBLRDOCCATEGORYMESSAGETAGREL IS 'Document Category Message Tag relation table is used to map massagetag with  Document Category Id';

CREATE TABLE TBLMDOCUMENTTEMPLATE 
(
	DOCUMENTTEMPLATEID	NUMBER(6)		NOT NULL	,
	NAME					VARCHAR(80)		NOT NULL	,
	DESCRIPTION				VARCHAR(255)	NOT NULL	,
	VALIDFROMDATE			DATE			NOT NULL	,
	VALIDTODATE			DATE			NOT NULL	,
	CREATEDBY              VARCHAR2 (10)      	NOT NULL	,
	CREATEDATE             TIMESTAMP      	NOT NULL	,
	LASTMODIBY            VARCHAR2 (10)      	NOT NULL	,
	LASTMODIDATE          TIMESTAMP      	NOT NULL	,	
	DOCUMENTCATEGORYID	NUMBER(6)		NOT NULL	,
	CONSTRAINT PKTBDOCTMP PRIMARY KEY(DOCUMENTTEMPLATEID),
	CONSTRAINT UKTBDOCTMP UNIQUE(NAME),	
	CONSTRAINT FK3TBDOCTMPLTDOCTEMPLATEID FOREIGN KEY(DOCUMENTCATEGORYID) REFERENCES TBLMDOCUMENTCATEGORY(DOCUMENTCATEGORYID)
);


CREATE SEQUENCE  SEQ_DOCUMENTTEMPLATE   MINVALUE 1   MAXVALUE 9999999999999999999999999999   INCREMENT BY 1   START WITH 1;

COMMENT ON TABLE TBLMDOCUMENTTEMPLATE IS 'Document Template  table is used to store template description and keep track of user , last modify date etc.';

CREATE TABLE TBLMMAILDOCUMENTTEMPLATEDETAIL 
(	
	DOCUMENTTEMPLATEDETAILID	NUMBER(6)		NOT NULL		,	
	DOCUMENTTEMPLATEID			NUMBER(6)		NOT NULL		,
	SUBJECT						VARCHAR(255)	NOT NULL		,
	TEMPLATE					BLOB			NOT NULL		,
	MIMETYPE					VARCHAR(25)		NOT NULL		,
	CONSTRAINT PKTBTMPDET PRIMARY KEY(DOCUMENTTEMPLATEDETAILID),
	CONSTRAINT FK1TBDTMDOCTMPID FOREIGN KEY(DOCUMENTTEMPLATEID) REFERENCES TBLMDOCUMENTTEMPLATE(DOCUMENTTEMPLATEID)
);


COMMENT ON TABLE TBLMMAILDOCUMENTTEMPLATEDETAIL IS 'Document Template detail  table provide complete detail of email document template with it Mime_type and subject';

CREATE SEQUENCE SEQ_MAILDOCUMENTTEMPLATEDETAIL MINVALUE 1   MAXVALUE 9999999999999999999999999999   INCREMENT BY 1   START WITH 1;


CREATE TABLE TBLMSMSDOCUMENTTEMPLATEDETAIL 
(	
	DOCUMENTTEMPLATEDETAILID	NUMBER(6)		NOT NULL		,	
	DOCUMENTTEMPLATEID			NUMBER(6)		NOT NULL		,
	TEMPLATE					BLOB			NOT NULL		,
	MIMETYPE					VARCHAR(25)		NOT NULL		,
	CONSTRAINT PKTBTMPDETE PRIMARY KEY(DOCUMENTTEMPLATEDETAILID),
	CONSTRAINT FK1TBDTMDOCTMPDID FOREIGN KEY(DOCUMENTTEMPLATEID) REFERENCES TBLMDOCUMENTTEMPLATE(DOCUMENTTEMPLATEID)
);

COMMENT ON TABLE TBLMSMSDOCUMENTTEMPLATEDETAIL IS 'Document Template detail  table provide complete detail of sms document template with it Mime_type and subject';


CREATE SEQUENCE SEQ_SMSDOCUMENTTEMPLATEDETAIL MINVALUE 1   MAXVALUE 9999999999999999999999999999   INCREMENT BY 1   START WITH 1;



Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values
((select max(actionid)+1 from tblisystemaction),null,3,2,'Document Template Management','DOCUMENT_TEMPLATE_MASTER','Document Template Master','Y','Y','N','UTY001','/WEB-INF/pages/core/configuration/notification/search-document-template.zul','Y');

Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) 
values ((select max(actionid)+1 from tblisystemaction),(select actionid from tblisystemaction where actionalias = 'DOCUMENT_TEMPLATE_MASTER'),3,2,'Create Document Template','CREATE_DOCUMENT_TEMPLATE','Create Document Template','Y','Y','N','UTY001','/WEB-INF/pages/core/configuration/notification/create-document-template.zul','Y');

Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) 
values ((select max(actionid)+1 from tblisystemaction),(select actionid from tblisystemaction where actionalias = 'DOCUMENT_TEMPLATE_MASTER'),4,2,'Search Document Template','SEARCH_DOCUMENT_TEMPLATE','Search Document Template','Y','Y','N','UTY001','/WEB-INF/pages/core/configuration/notification/search-document-template.zul','Y');


Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) 
values ((select max(actionid)+1 from tblisystemaction),(select actionid from tblisystemaction where actionalias = 'DOCUMENT_TEMPLATE_MASTER'),5,2,'Update Document Template Basic Details','UPDATE_DOCUMENT_TEMPLATE_BASICDETAILS','Update Document Template basic details','Y','Y','N','UTY001','/WEB-INF/pages/core/configuration/notification/update-document-template-basicdetails.zul','Y');

Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) 
values ((select max(actionid)+1 from tblisystemaction),(select actionid from tblisystemaction where actionalias = 'DOCUMENT_TEMPLATE_MASTER'),6,2,'Update Document Template','UPDATE_DOCUMENT_TEMPLATE','Update Document Template','Y','Y','N','UTY001','/WEB-INF/pages/core/configuration/notification/update-document-template.zul','Y');


insert into tblmdocumentcategory values (101,'Create Resource','Template For Resource Creation','CREATE_RESOURCE','Y','N');
insert into tblmdocumentcategory values (102,'Resource Threshold','Template For Resource Threshold','RESOURCE_THRESHOLD','Y','N');
insert into tblmdocumentcategory values (103,'Place Order','Template For Place Order','PLACE_ORDER','Y','N');
insert into tblmdocumentcategory values (104,'Accept/Reject Place Order','Template For Accept/Reject Place Order','ACCEPT-REJECT_PLACE_ORDER','Y','N');
insert into tblmdocumentcategory values (105,'Transfer Order','Template For Transfer Order','TRANSFER_ORDER','Y','N');
insert into tblmdocumentcategory values (106,'Accept/Reject Transfer Order','Template For Accept/Reject Transfer Order','ACCEPT-REJECT_TRANSFER_ORDER','Y','N');
insert into tblmdocumentcategory values (107,'Transfer Order After PlaceOrder','Template For Transfer Order After PlaceOrder','TRANSFER_ORDER_PLACEORDER','Y','N');


insert into  tblmmessagetag values(101,'Resource Number','Tag for resource Number','${CPE_RESOURCE_NUMBER}');
insert into  tblmmessagetag values(102,'Resource Name','Tag for resource Name','${CPE_RESOURCE_NAME}');
insert into  tblmmessagetag values(103,'Model Number','Tag for Resource model number','${CPE_MODEL_NUMBER}');
insert into  tblmmessagetag values(104,'Vendor','Tag for Resource Vendor','${CPE_VENDOR}');
insert into  tblmmessagetag values(105,'Description','Tag for Resource Description','${CPE_DESCRIPTION}');
insert into  tblmmessagetag values(106,'ReferenceId','Tag for Resource referenceid','${CPE_REFERENCEID}');
insert into  tblmmessagetag values(107,'Resource Type name','Tag for Resource type name','${CPE_RESOURCE_TYPENAME}');
insert into  tblmmessagetag values(108,'Resource Subtype name','Tag for Resource Subtype name','${CPE_RESOURCE_SUBTYPENAME}');

insert into  tblmmessagetag values(109,'Warehouse Name ','Tag for Warehouse Name','${CPE_WAREHOUSE_NAME}');
insert into  tblmmessagetag values(110,'Threshold Limit','Tag for Threshold Limit','${CPE_THRESHOLD_LIMIT}');
insert into  tblmmessagetag values(111,'Order Number','Tag for Order Number','${CPE_ORDER_NUMBER}');
insert into  tblmmessagetag values(112,'From Warehouse','Tag for From Warehouse','${CPE_FROM_WAREHOUSE}');
insert into  tblmmessagetag values(113,'To Warehouse','Tag for To Warehouse','${CPE_TO_WAREHOUSE}');
insert into  tblmmessagetag values(114,'Quantity','Tag for Quantity','${CPE_QUANTITY}');
insert into  tblmmessagetag values(115,'Accepted Quantity','Tag for Accepted Quantity','${CPE_ACCEPTED_QUANTITY}');
insert into  tblmmessagetag values(116,'Available Quantity','Tag for Available Quantity','${CPE_AVAILABLE_QUANTITY}');
insert into  tblmmessagetag values(117,'Remark','Tag for Order Remark','${CPE_REMARK}');

insert into TBLRDOCCATEGORYMESSAGETAGREL values(101,101);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(101,102);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(101,103);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(101,104);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(101,105);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(101,106);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(101,107);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(101,108);

insert into TBLRDOCCATEGORYMESSAGETAGREL values(102,102);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(102,109);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(102,110);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(102,111);

insert into TBLRDOCCATEGORYMESSAGETAGREL values(103,111);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(103,112);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(103,113);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(103,107);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(103,108);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(103,114);


insert into TBLRDOCCATEGORYMESSAGETAGREL values(104,111);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(104,112);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(104,113);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(104,107);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(104,108);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(104,114);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(104,115);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(104,116);

insert into TBLRDOCCATEGORYMESSAGETAGREL values(105,111);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(105,112);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(105,113);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(105,114);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(105,117);

insert into TBLRDOCCATEGORYMESSAGETAGREL values(106,111);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(106,112);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(106,113);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(106,114);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(106,117);

insert into TBLRDOCCATEGORYMESSAGETAGREL values(107,111);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(107,112);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(107,113);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(107,114);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(107,117);



insert into TBLRDOCCATEGORYMESSAGETAGREL values(105,101);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(105,102);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(105,107);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(105,108);

insert into TBLRDOCCATEGORYMESSAGETAGREL values(106,101);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(106,102);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(106,107);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(106,108);

insert into TBLRDOCCATEGORYMESSAGETAGREL values(107,101);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(107,102);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(107,107);
insert into TBLRDOCCATEGORYMESSAGETAGREL values(107,108);


insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values ( TBLMMESSAGETEMPLATE_SEQ.nextval,(select actionid from tblisystemaction where actionalias = 'CREATE_DOCUMENT_TEMPLATE'  ),'Document Template Created with name:${name}','${name}');

insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values ( TBLMMESSAGETEMPLATE_SEQ.nextval,(select actionid from tblisystemaction where actionalias = 'UPDATE_DOCUMENT_TEMPLATE_BASICDETAILS'  ),'Basic Details of Document Template with name:${name} Updated Successfully','${name}');


insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values ( TBLMMESSAGETEMPLATE_SEQ.nextval,(select actionid from tblisystemaction where actionalias = 'UPDATE_DOCUMENT_TEMPLATE'  ),'Document Template with name:${name} Updated Successfully','${name}');

  CREATE TABLE TBLTNOTIFICATIONAUDIT
   (	"NOTIFICATIONAUDITID" NUMBER, 
	"NOTIFICATIONTYPE" VARCHAR2(10), 
	"TOEMAIL" VARCHAR2(100), 
	"CCEMAIL" VARCHAR2(100), 
  "BCCEMAIL" VARCHAR2(100), 
  "MOBNUM" VARCHAR2(100), 
  "SUBJECT" VARCHAR2(500), 
  "CONTENT" BLOB,
  "CREATEDATE" TIMESTAMP ,
  "DOCUMENTCATEGORYID"	NUMBER(6,0),
  "DELIVERYSTATUS" VARCHAR2(20),
  "DELIVERYSYSTEM"  VARCHAR2(20),
  "RESPONSECODE" VARCHAR2(20),
  "RESPONSEMESSAGE" VARCHAR2(1000)
   ) ;


CREATE SEQUENCE  SEQ_NOTIFICATIONAUDIT   MINVALUE 1   MAXVALUE 9999999999999999999999999999   INCREMENT BY 1   START WITH 1;   


update tblmsystemparameter set description='This parameter will decide which status of inventory need to consider for Threshold calculation. Currently allowed value is Available or Repaired with Refurbished.' where alias='THRESHOLD_COUNT';

update tblmsystemparameter set description='This parameter will decide which status of inventory need to consider for New Order. Currently allowed value is Available or Repaired with Refurbished.' where alias='INVENTORY_FOR_NEW_ORDER';

update tblmsystemparameter set description='Need to specify comma separated (To) email id on which new resource creation notification should be send from system.' where alias='EMAIL_ALIAS_RESOURCE_TO';

update tblmsystemparameter set description='Need to specify comma separated (Cc) email id on which new resource creation notification should be send from system.' where alias='EMAIL_ALIAS_RESOURCE_CC';


update tblisystemaction set pageurl='/WEB-INF/pages/core/master/warehouse/ConfigureThreshold.zul' where actionalias='CONFIGURE_THRESHOLD';

Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid)+1 from tblisystemaction),null,10,6,'Search Place Order','SEARCH_PLACE_ORDER','Search Place Order','N','Y','N','UTY001','/WEB-INF/pages/core/master/warehouse/SearchPlaceOrderDetail.zul','N');
Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid)+1 from tblisystemaction),null,11,6,'View WareHouse Hierarchy','VIEW_WAREHOUSE_HIERARCHY','View WareHouse Hierarchy','N','Y','N','UTY001','/WEB-INF/pages/core/master/warehouse/tree/view-warehouse-hierarchy.zul','N');


Insert into TBLMPRIMARYKEY (PRIMARYKEYID,ALIAS,PREFIX,LENGTH,POSTFIX,CURRENTVALUE) values (TBLMPRIMARYKEY_SEQ.nextval,'BATCH_DATA','BT',7,null,1);
Insert into TBLMPRIMARYKEY (PRIMARYKEYID,ALIAS,PREFIX,LENGTH,POSTFIX,CURRENTVALUE) values (TBLMPRIMARYKEY_SEQ.nextval,'ITEM_DATA','CPE',4,null,1);
Insert into TBLMPRIMARYKEY (PRIMARYKEYID,ALIAS,PREFIX,LENGTH,POSTFIX,CURRENTVALUE) values (TBLMPRIMARYKEY_SEQ.nextval,'INVENTORY_DATA','IV',8,null,1);
Insert into TBLMPRIMARYKEY (PRIMARYKEYID,ALIAS,PREFIX,LENGTH,POSTFIX,CURRENTVALUE) values (TBLMPRIMARYKEY_SEQ.nextval,'ATTRIBUTE_DATA','ATB',5,null,1);

CREATE TABLE TBLMUSERWAREHOUSEMAPPING 
(
  USERWAREHOUSEID NUMBER(6,0) PRIMARY KEY,
  USERID CHAR(7 BYTE),
  WAREHOUSEID	NUMBER(5,0)
);


CREATE SEQUENCE  SEQ_TBLMUSERWAREHOUSEMAPPING   MINVALUE 1   MAXVALUE 9999999999999999999999999999   INCREMENT BY 1   START WITH 1;

Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) 
values ((select max(actionid)+1 from tblisystemaction),null,7,2,'User Warehouse Mapping','USER_WAREHOUSE_MAPPING','User Warehouse Mapping','Y','Y','N','UTY001','/WEB-INF/pages/core/system/userwarehousemap/user-warehouse-mapping.zul','Y');

alter table tblmwarehousealert add (email varchar2(50));
alter table tblmwarehousealert add (mobile varchar2(50));
alter table tblmwarehousealert add (quantity number(5,0));

Insert into TBLMSYSTEMPARAMETER (SYSTEMPARAMETERID,SYSTEMPARAMETERGROUPID,NAME,ALIAS,VALUE,VALUESOURCE,DESCRIPTION,ALLOWOVERRIDE,LASTMODIDATE,LASTMODIBY,ISVISIBLE,CUSTOMFIELDTYPEID,CONSTRAINTEXPRESSIONID) values (12,1,'Threshold Automatic Re-order','THRESHOLD_AUTOMATIC_RE_ORDER','No','Yes,No','Threshold Automatic Re-order','Y',sysdate,'STF0001','Y','CFT02','REG05');
Insert into TBLMSYSTEMPARAMETER (SYSTEMPARAMETERID,SYSTEMPARAMETERGROUPID,NAME,ALIAS,VALUE,VALUESOURCE,DESCRIPTION,ALLOWOVERRIDE,LASTMODIDATE,LASTMODIBY,ISVISIBLE,CUSTOMFIELDTYPEID,CONSTRAINTEXPRESSIONID) values (13,1,'Threshold Re-order Quantity','THRESHOLD_RE_ORDER_QUANTITY','5',null,'Threshold Re-order Quantity','Y',sysdate,'STF0001','Y','CFT01','REG02');

insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values ( TBLMMESSAGETEMPLATE_SEQ.nextval,(select actionid from tblisystemaction where actionalias = 'USER_WAREHOUSE_MAPPING'  ),'User Warehouse Mapping Updated for UserId:${name} ','${name}');

commit;






