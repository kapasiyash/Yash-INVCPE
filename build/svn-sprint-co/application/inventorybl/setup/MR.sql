--MR01

update TBLSWAREHOUSETYPE set SYSTEMGENERATED = 'N' where WAREHOUSETYPEID = 1;

update tblmwarehouse set LASTMODIFIEDDATE=null , LASTMODIFIEDDATEBYSTAFFID=null where alias='CENTRAL' and editable='N';

UPDATE TBLISYSTEMACTION SET NAME = 'Resource Subtype Master' WHERE actionalias = 'RESOURCE_SUBTYPE_MASTER';
UPDATE TBLISYSTEMACTION SET NAME = 'Search ResourceSubtype' WHERE actionalias = 'SEARCH_RESOURCESUBTYPE';
UPDATE TBLISYSTEMACTION SET NAME = 'View ResourceSubtype' WHERE actionalias = 'VIEW_RESOURCESUBTYPE';
UPDATE TBLISYSTEMACTION SET NAME = 'Update ResourceSubtype' WHERE actionalias = 'UPDATE_RESOURCESUBTYPE';


Insert into tblsinvstatustransition values(517,1,6);
Insert into tblsinvstatustransition values(518,4,2);
Insert into tblsinvstatustransition values(519,4,5);
Insert into tblsinvstatustransition values(520,4,6);
Insert into tblsinvstatustransition values(521,4,7);
Insert into tblsinvstatustransition values(522,4,10);


UPDATE TBLISYSTEMACTION SET NAME = 'Agent List' WHERE  actionalias = 'SEARCH_AGENT';
UPDATE TBLISYSTEMACTION SET NAME = 'Search Agent In Queue' WHERE  actionalias = 'SEARCH_AGENT_IN_QUEUE';


--MR02
UPDATE TBLISYSTEMACTION SET NAME = 'Agent List' WHERE  actionalias = 'SEARCH_AGENT';
UPDATE TBLISYSTEMACTION SET NAME = 'Search Agent In Queue' WHERE  actionalias = 'SEARCH_AGENT_IN_QUEUE';


update tblmmessagetemplate set template='Resource Subtype name:${name} has been Updated successfully' where templateid=504 and actionid=148;

update tblmmessagetemplate set template='Resource Subtype name:${name} has been created successfully' where templateid=502 and actionid=142;

update tblisystemaction set name='Update Resource Subtype' where actionalias='UPDATE_RESOURCESUBTYPE';
update tblisystemaction set name='Search Resource Subtype' where actionalias='SEARCH_RESOURCESUBTYPE';
update tblisystemaction set name='View Resource Subtype' where actionalias='VIEW_RESOURCESUBTYPE';
update tblisystemaction set name='Resource Subtype Master' where actionalias='RESOURCE_SUBTYPE_MASTER';


Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid) from tblisystemaction)+1,142,3,5,'Create ResourceSubtype','CREATE_RESOURCESUBTYPE','Create ResourceSubtype','Y','Y','N','UTY001','/WEB-INF/pages/core/master/item/create-resource-subtype.zul','N');


Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid) from tblisystemaction)+1,141,3,5,'Create ResourceType','CREATE_RESOURCETYPE','Create ResourceType','Y','Y','N','UTY001','/WEB-INF/pages/core/master/item/create-resource-type.zul','N');

--MR03

update tblmmessagetemplate set actionId=(select actionid from tblisystemaction where actionalias='CREATE_RESOURCESUBTYPE') where templateid=501;

update tblmmessagetemplate set actionId=(select actionid from tblisystemaction where actionalias='CREATE_RESOURCESUBTYPE') where templateid=502;



Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid) from tblisystemaction)+1,106,7,1,'Change Inventory Status','CHANGE_INVENTORY_STATUS','Change Inventory Status','Y','Y','N','UTY001','/WEB-INF/pages/core/master/item/change-inventory-status.zul','Y');

Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid) from tblisystemaction)+1,106,9,1,'Inventory History Detail','INVENTORY_HISTORY_DETAIL','Inventory History Detail','Y','Y','N','UTY001','/WEB-INF/pages/core/inventory/search/Inventoryhistorydetail.zul','Y');

Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid) from tblisystemaction)+1,106,8,1,'Change Inventory SubStatus','CHANGE_INVENTORY_SUBSTATUS','Change Inventory SubStatus','Y','Y','N','UTY001','/WEB-INF/pages/core/inventory/search/changeInventorySubStatus.zul','Y');


--MR04


drop sequence TBLMMESSAGETEMPLATE_SEQ;

drop sequence TBLMDATATAGS_SEQ;


CREATE SEQUENCE  TBLMMESSAGETEMPLATE_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 510 CACHE 20 NOORDER  NOCYCLE ;

CREATE SEQUENCE  TBLMDATATAGS_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 510 CACHE 20 NOORDER  NOCYCLE ;


update tblsinventorystatus set name='Recovered' where alias='RELEASED';

alter table tblmtransferorder add (REMARKS varchar2(1000));

alter table tblmtransferorder add (INVENTORYORDERSTATUSID varchar2(7));

ALTER TABLE tblsresourcetype modify ( ALIAS VARCHAR2(50 BYTE));
ALTER TABLE TBLSRESOURCESUBTYPE modify ( ALIAS VARCHAR2(50 BYTE));
ALTER TABLE TBLSWAREHOUSETYPE modify ( ALIAS VARCHAR2(50 BYTE));
insert into TBLMSYSTEMPARAMETER values(8,1,'Inventory Type for Threshold','THRESHOLD_COUNT','Available','Available,Repaired with Refurbished','Inventory Type for Threshold','Y',sysdate,'STF0001','Y','CFT02','REG05');


alter table tblmresource modify(prefix CHAR(3 BYTE) null);

 CREATE TABLE TBLMORDER (PLACEORDERID NUMBER(7,0) NOT NULL ENABLE, PLACEORDERNO VARCHAR2(20 BYTE),
 FROMWAREHOUSEID NUMBER(5,0),TOWAREHOUSEID NUMBER(5,0),
 RESOURCETYPEID NUMBER(5,0) NOT NULL ENABLE,RESOURCESUBTYPEID NUMBER(5,0) ,
 QUANTITY NUMBER(5,0),CREATEDATE DATE NOT NULL ENABLE,
 CREATEDBYSTAFFID VARCHAR2(7 BYTE) NOT NULL ENABLE,
 LASTMODIFIEDDATE DATE,LASTMODIFIEDDATEBYSTAFFID VARCHAR2(7 BYTE),REMARKS VARCHAR2(1000 BYTE),
 ORDERSTATUSID VARCHAR2(7 BYTE),
	TRANSFERORDERNO VARCHAR2(20 BYTE),
	ACCEPTQUANTITY	NUMBER(5,0),
	TRANSFERREMARKS	VARCHAR2(1000 BYTE),
	ACCEPTREJECTDATE	DATE,
  CONSTRAINT TBLMORDER_PK PRIMARY KEY (PLACEORDERID),
  CONSTRAINT TBLMORDER_TBLMWAR_FK1 FOREIGN KEY (FROMWAREHOUSEID)
	  REFERENCES TBLMWAREHOUSE (WAREHOUSEID) ENABLE, 
	 CONSTRAINT TBLMORDER_TBLMWAR_FK2 FOREIGN KEY (TOWAREHOUSEID)
	  REFERENCES TBLMWAREHOUSE (WAREHOUSEID) ENABLE,
  CONSTRAINT TBLMORDER_ORDERSTATUS_FK1 FOREIGN KEY (ORDERSTATUSID)
	  REFERENCES TBLSINVENTORYORDERSTATUS (ORDERSTATUSID) ENABLE );



CREATE SEQUENCE  TBLMORDER_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;

insert into tblmprimarykey values(TBLMPRIMARYKEY_SEQ.nextval,'ORDER_DATA','PL',10,null,1);


 insert into tblisystemaction values((select max(actionid)+1 from tblisystemaction),null,2,1,'Order Management','ORDER_MASTER','Place Order Master','Y','Y','N',
       'UTY001','','N');

 insert into tblisystemaction values((select max(actionid)+1 from tblisystemaction),(select actionid 
       from tblisystemaction where actionalias='ORDER_MASTER'),3,1,'Place Order','CREATE_PLACE_ORDER','Place Order','Y','Y','N',
       'UTY001','/WEB-INF/pages/core/master/warehouse/PlaceOrder.zul','Y');



       

        insert into tblisystemaction values((select max(actionid)+1 from tblisystemaction),(select actionid 
       from tblisystemaction where actionalias='ORDER_MASTER'),4,1,'Cancel Order','CANCEL_ORDER','Cancel Order','Y','Y','N',
       'UTY001','/WEB-INF/pages/core/master/warehouse/cancelPlaceOrder.zul','Y');


          insert into tblisystemaction values((select max(actionid)+1 from tblisystemaction),(select actionid 
       from tblisystemaction where actionalias='ORDER_MASTER'),5,1,'Accept/Reject Order','ACCEPT_REJECT_ORDER','Accept/Reject Order','Y','Y','N',
       'UTY001','/WEB-INF/pages/core/master/warehouse/viewPlaceOrder.zul','Y');
       
        insert into tblisystemaction values((select max(actionid)+1 from tblisystemaction),(select actionid 
       from tblisystemaction where actionalias='ORDER_MASTER'),6,1,'Update Order','UPDATE_ORDER','Update Order','Y','Y','N',
       'UTY001','/WEB-INF/pages/core/master/warehouse/TransferPlaceOrder.zul','Y');
       
           insert into TBLMDATATAGS(tagid,tagname,param1,param2)values(TBLMDATATAGS_SEQ.nextval,'${placeordernumber}',null,null);
          
        insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS)values 
    ( TBLMMESSAGETEMPLATE_SEQ.nextval,
      (select actionid from tblisystemaction where actionalias = 'CREATE_PLACE_ORDER'  ), 
      'Place order having order number:${transferordernumber}   From warehouse name: ${fromwarehouse} to warehouse name:${towarehouse} has been created successfully','${transferordernumber};${fromwarehouse};${towarehouse}');
       
       insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS)values 
    ( TBLMMESSAGETEMPLATE_SEQ.nextval,
      (select actionid from tblisystemaction where actionalias = 'CANCEL_ORDER'  ), 
      'Place order having order number:${transferordernumber}   From warehouse name: ${fromwarehouse} to warehouse name:${towarehouse} has been cancelled successfully',
       '${transferordernumber};${fromwarehouse};${towarehouse}');
       
       insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS)values 
    ( TBLMMESSAGETEMPLATE_SEQ.nextval,
      (select actionid from tblisystemaction where actionalias = 'ACCEPT_REJECT_ORDER'  ), 
      'Place order having order number:${transferordernumber}   From warehouse name: ${fromwarehouse} to warehouse name:${towarehouse} has been ${status} successfully',
       '${transferordernumber};${fromwarehouse};${towarehouse};${status}');
       
       insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS)values 
    ( TBLMMESSAGETEMPLATE_SEQ.nextval,
      (select actionid from tblisystemaction where actionalias = 'UPDATE_ORDER'  ), 
      'Transfer order:${transferordernumber} intiated against place order:${placeordernumber}',
       '${transferordernumber};${placeordernumber}');
       
       
Insert into TBLSINVENTORYORDERSTATUS (ORDERSTATUSID,NAME,ALIAS,DESCRIPTION,SYSTEMGENERATED) values ('101','In Progress','IN_PROGRESS','In Progress','N');
Insert into TBLSINVENTORYORDERSTATUS (ORDERSTATUSID,NAME,ALIAS,DESCRIPTION,SYSTEMGENERATED) values ('102','Partially Accepted','PARTIALLY_ACCEPTED','Partially Accepted','N');
Insert into TBLSINVENTORYORDERSTATUS (ORDERSTATUSID,NAME,ALIAS,DESCRIPTION,SYSTEMGENERATED) values ('103','Accepted','ACCEPTED','Accepted','N');
Insert into TBLSINVENTORYORDERSTATUS (ORDERSTATUSID,NAME,ALIAS,DESCRIPTION,SYSTEMGENERATED) values ('104','Rejected','REJECTED','Rejected','N');
Insert into TBLSINVENTORYORDERSTATUS (ORDERSTATUSID,NAME,ALIAS,DESCRIPTION,SYSTEMGENERATED) values ('105','Cancelled','CANCELLED','Cancelled','N');
Insert into TBLSINVENTORYORDERSTATUS (ORDERSTATUSID,NAME,ALIAS,DESCRIPTION,SYSTEMGENERATED) values ('106','Completed','COMPLETED','Completed','N');

       
       
       
    



insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values ( TBLMMESSAGETEMPLATE_SEQ.nextval,(select actionid from tblisystemaction where actionalias = 'CHANGE_INVENTORY_STATUS'  ),'Changed Inventory Status from:${oldStatus} to:${newStatus} Successfuly','${oldStatus};${newStatus}');


insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values ( TBLMMESSAGETEMPLATE_SEQ.nextval,(select actionid from tblisystemaction where actionalias = 'CHANGE_INVENTORY_SUBSTATUS'  ),'Inventory substatus:${substatus}  has been changed Successfully','${substatus}');

Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (TBLMDATATAGS_SEQ.nextval,'${oldStatus}',null,null);
Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (TBLMDATATAGS_SEQ.nextval,'${newStatus}',null,null);
Insert into TBLMDATATAGS (TAGID,TAGNAME,PARAM1,PARAM2) values (TBLMDATATAGS_SEQ.nextval,'${substatus}',null,null);

update tblisystemaction set isauditable='N' where actionalias='INVENTORY_HISTORY_DETAIL';
update tblisystemaction set isauditable='N' where actionalias='SEARCH_AUDIT';
update tblisystemaction set isauditable='N' where actionalias='VIEW_AUDIT_ENTRY';
update tblisystemaction set isauditable='N' where actionalias='SEARCH_ATTRIBUTE';
update tblisystemaction set isauditable='N' where actionalias='VIEW_RESOURCESUBTYPE';
update tblisystemaction set isauditable='N' where actionalias='VIEW_RESOURCETYPE';
update tblisystemaction set isauditable='N' where actionalias='SEARCH_RESOURCESUBTYPE';
update tblisystemaction set isauditable='N' where actionalias='SEARCH_RESOURCETYPE';
update tblisystemaction set isauditable='N' where actionalias='VIEW_RESOURCETYPE';
update tblisystemaction set isauditable='N' where actionalias='SEARCH_RESOURCE';

insert into tblisystemaction values((select max(actionid)+1 from tblisystemaction),(select actionid from tblisystemaction where actionalias='WAREHOUSE_MASTER'),6,6,'Cancel Transfer Order','CANCEL_TRANSFER_ORDER','Cancel Transfer Order','Y','Y','N','UTY001','/WEB-INF/pages/core/master/warehouse/cancelTransferInventory.zul','Y');

insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values ( TBLMMESSAGETEMPLATE_SEQ.nextval,(select actionid from tblisystemaction where actionalias = 'CANCEL_TRANSFER_ORDER'  ),'Cancelled Transfer Order with Transfer orderId:${name}','${name}');


insert into tblisystemaction values((select max(actionid)+1 from tblisystemaction),(select actionid from tblisystemaction where actionalias='WAREHOUSE_MASTER'),7,6,'Accept/Reject Transfer Order','ACCEPT-REJECT_TRANSFER_ORDER','Accept/Reject Transfer Order','Y','Y','N','UTY001','/WEB-INF/pages/core/master/warehouse/rejectTransferInventory.zul','Y');

insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values ( TBLMMESSAGETEMPLATE_SEQ.nextval,(select actionid from tblisystemaction where actionalias = 'ACCEPT-REJECT_TRANSFER_ORDER'  ),'Accept Rejected Transfer Order with Transfer orderId:${name}','${name}');

--MR05
delete from TBLTWSAUDIT;

ALTER TABLE TBLTWSAUDIT modify ( RESPONSECODE VARCHAR2(50 BYTE));



 CREATE TABLE TBLSINVENTORYSUBSTATUS
   (	"INVENTORYSUBSTATUSID" NUMBER(5,0), 
	"NAME" VARCHAR2(50), 
	"SYSTEMGENERATED" CHAR(1), 
	"ALIAS" VARCHAR2(20), 
	"DESCRIPTION" VARCHAR2(20)
   ) ;


Insert into TBLSINVENTORYSUBSTATUS (INVENTORYSUBSTATUSID,NAME,SYSTEMGENERATED,ALIAS,DESCRIPTION) values (1,'New','N','NEW','New');
Insert into TBLSINVENTORYSUBSTATUS (INVENTORYSUBSTATUSID,NAME,SYSTEMGENERATED,ALIAS,DESCRIPTION) values (2,'Accepted','N','ACCEPTED','Accepted');
Insert into TBLSINVENTORYSUBSTATUS (INVENTORYSUBSTATUSID,NAME,SYSTEMGENERATED,ALIAS,DESCRIPTION) values (3,'Refurnished','N','REFURNISHED','Refurnished');
Insert into TBLSINVENTORYSUBSTATUS (INVENTORYSUBSTATUSID,NAME,SYSTEMGENERATED,ALIAS,DESCRIPTION) values (4,'Stand-By','N','STAND_BY','Stand-By');
Insert into TBLSINVENTORYSUBSTATUS (INVENTORYSUBSTATUSID,NAME,SYSTEMGENERATED,ALIAS,DESCRIPTION) values (5,'Returned','N','RETURNED','Returned');

CREATE SEQUENCE  TBLSINVSUBSTATUSTRANSITION_SEQ   MINVALUE 1   MAXVALUE 9999999999999999999999999999   INCREMENT BY 1   START WITH 1;   

CREATE TABLE TBLSINVSUBSTATUSTRANSITION 
   (	"SUBSTATUSTRANSITIONID" NUMBER(5,0), 
	"STATUSID" NUMBER(5,0), 
	"ALLOWEDSUBSTATUSID" NUMBER(5,0)
   ) ;


Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),2,1);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),2,2);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),2,3);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),2,4);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),3,1);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),3,4);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),4,1);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),4,3);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),4,4);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),5,1);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),5,3);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),5,4);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),6,2);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),6,4);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),6,1);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),7,1);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),7,4);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),7,5);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),8,1);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),8,2);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),8,4);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),9,1);
Insert into TBLSINVSUBSTATUSTRANSITION (SUBSTATUSTRANSITIONID,STATUSID,ALLOWEDSUBSTATUSID) values ((TBLSINVSUBSTATUSTRANSITION_SEQ.nextval),9,4);


insert into tblssysprmcustomfieldtype(customfieldtypeid,name,alias,description) values ('CFT06','BandBox','BANDBOX','BandBox for multiple select in combobox');

update tblmsystemparameter set customfieldtypeid='CFT06' where alias='THRESHOLD_COUNT';


Insert into TBLMSYSTEMPARAMETER (SYSTEMPARAMETERID,SYSTEMPARAMETERGROUPID,NAME,ALIAS,VALUE,VALUESOURCE,DESCRIPTION,ALLOWOVERRIDE,LASTMODIDATE,LASTMODIBY,ISVISIBLE,CUSTOMFIELDTYPEID,CONSTRAINTEXPRESSIONID) values (9,1,'Inventory For New Order','INVENTORY_FOR_NEW_ORDER','Available','Available,Repaired with Refurbished','Inventory For New Order','Y',sysdate,'STF0001','Y','CFT06','REG05');

update tblmsystemparameter set valuesource=(select inventorystatusid from tblsinventorystatus where alias='AVAILABLE')||':Available,'||
(select inventorystatusid from tblsinventorystatus where alias='REPAIRED')||':Repaired with Refurbished' where alias='INVENTORY_FOR_NEW_ORDER';


update tblmsystemparameter set valuesource=(select inventorystatusid from tblsinventorystatus where alias='AVAILABLE')||':Available,'||
(select inventorystatusid from tblsinventorystatus where alias='REPAIRED')||':Repaired with Refurbished' where alias='THRESHOLD_COUNT';


alter table tblsinventorystatus add (transferrable char(1));

update tblsinventorystatus set transferrable='N';

update tblsinventorystatus set transferrable='Y' where alias='AVAILABLE';
update tblsinventorystatus set transferrable='Y' where alias='FAULTY';
update tblsinventorystatus set transferrable='Y' where alias='RELEASED';
update tblsinventorystatus set transferrable='Y' where alias='REPAIRED';
update tblsinventorystatus set transferrable='Y' where alias='SCRAPPED';


Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid)+1 from tblisystemaction),null,8,6,'Warehouse Summary','WAREHOUSE_SUMMARY','Warehouse Summary','Y','Y','N','UTY001','/WEB-INF/pages/core/master/warehouse/warehouse-summary.zul','Y');


Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid)+1 from tblisystemaction),null,10,1,'Order Detail','ORDER_DETAIL','Order Detail','Y','Y','N','UTY001','/WEB-INF/pages/core/inventory/orderdetail/search-order-detail.zul','Y');


Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid)+1 from tblisystemaction),null,9,6,'Warehouse Hierarchy','WAREHOUSE_TREEVIEW','Warehouse Hierarchy','Y','Y','N','UTY001','/WEB-INF/pages/core/master/warehouse/tree/warehouse-treeview.zul','Y');


--MR06
alter table tblminventory add (externalbatchnumber varchar2(50));

Insert into TBLMSYSTEMPARAMETER (SYSTEMPARAMETERID,SYSTEMPARAMETERGROUPID,NAME,ALIAS,VALUE,VALUESOURCE,DESCRIPTION,ALLOWOVERRIDE,LASTMODIDATE,LASTMODIBY,ISVISIBLE,CUSTOMFIELDTYPEID,CONSTRAINTEXPRESSIONID) values (10,1,'Email Alias for resource notify(TO)','EMAIL_ALIAS_RESOURCE_TO','abc@elitecore.com',null,'Email Alias for resource notification (TO)','Y',sysdate,'STF0001','Y','CFT01','REG05');
Insert into TBLMSYSTEMPARAMETER (SYSTEMPARAMETERID,SYSTEMPARAMETERGROUPID,NAME,ALIAS,VALUE,VALUESOURCE,DESCRIPTION,ALLOWOVERRIDE,LASTMODIDATE,LASTMODIBY,ISVISIBLE,CUSTOMFIELDTYPEID,CONSTRAINTEXPRESSIONID) values (11,1,'Email Alias for resource notify(CC)','EMAIL_ALIAS_RESOURCE_CC','abc@elitecore.com',null,'Email Alias for resource notification (CC)','Y',sysdate,'STF0001','Y','CFT01','REG05');

alter table tblmwarehouse add (warehousecode varchar2(50));

alter table tblminventory add (GuranteeWarrantyMode varchar2(20));
alter table tblminventory add (warrantydate timestamp(6));
alter table tblminventory add (warrantytype varchar2(20));

update tblisystemaction set isauditable='N' where actionalias='WAREHOUSE_SUMMARY';
update tblisystemaction set isauditable='N' where actionalias='WAREHOUSE_TREEVIEW';

--MR07

update tblsinventorystatus set name='Allocated' where alias='IN_USE';

update tblsinventorystatus set alias='ALLOCATED' where name='Allocated';


Insert into TBLMPRIMARYKEY (PRIMARYKEYID,ALIAS,PREFIX,LENGTH,POSTFIX,CURRENTVALUE) values (TBLMPRIMARYKEY_SEQ.nextval,'ITEM_DATA','CPE',4,null,1);
Insert into TBLMPRIMARYKEY (PRIMARYKEYID,ALIAS,PREFIX,LENGTH,POSTFIX,CURRENTVALUE) values (TBLMPRIMARYKEY_SEQ.nextval,'INVENTORY_DATA','IV',8,null,1);
Insert into TBLMPRIMARYKEY (PRIMARYKEYID,ALIAS,PREFIX,LENGTH,POSTFIX,CURRENTVALUE) values (TBLMPRIMARYKEY_SEQ.nextval,'ATTRIBUTE_DATA','ATB',5,null,1);
Insert into TBLMPRIMARYKEY (PRIMARYKEYID,ALIAS,PREFIX,LENGTH,POSTFIX,CURRENTVALUE) values (TBLMPRIMARYKEY_SEQ.nextval,'BATCH_DATA','BT',7,null,1);


commit;



