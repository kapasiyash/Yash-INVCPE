
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




commit;
