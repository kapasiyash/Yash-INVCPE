set define on;
set echo on;

DEFINE partnerDBUser="&&partner_db_user"
DEFINE partnerDBPassword="&&partner_db_password"

ALTER TABLE  TBLMWAREHOUSEALERT ADD(RESOURCEID NUMBER(5,0));

ALTER TABLE  TBLMWAREHOUSEALERT ADD(AUTOMATICORDER char(1 byte));

ALTER TABLE  TBLMORDER ADD(RESOURCEID NUMBER(5,0));

  CREATE TABLE TBLTTHRESHOLDNOTIHISTORY
   (	"NOTIFICATIONHISTORYID" NUMBER, 
	"WAREHOUSTHRESHOLDID" NUMBER(5,0), 
	"RESOURCETYPEID"	NUMBER(5,0), 
	"RESOURCESUBTYPEID"	NUMBER(5,0), 
  "RESOURCEID"	NUMBER(5,0), 
  "NOTIFICATIONSENT" char(1 byte), 
  "NOTIFICATIONAUDITID"	NUMBER, 
  "PLACEORDERGENERATED" char(1 byte), 
  "PLACEORDERNO"	VARCHAR2(20 BYTE),
  "CREATEDBYSTAFFID"	VARCHAR2(7 BYTE),
  "CREATEDATE" TIMESTAMP 
   ) ;

CREATE SEQUENCE  TBLTTHRESHOLDNOTIHISTORY_SEQ   MINVALUE 1   MAXVALUE 9999999999999999999999999999   INCREMENT BY 1   START WITH 1;

CREATE TABLE TBLMORDERAGENTHISTORY
  (
    ORDERAGENTHISTORYID    NUMBER(7,0) PRIMARY KEY,
    ORDERID     NUMBER(7,0),
    ORDERNO     VARCHAR2(20 BYTE),
    ORDERTYPE VARCHAR2(20 BYTE),
    FROMWAREHOUSEID  NUMBER(5,0),
    TOWAREHOUSEID    NUMBER(5,0),
    AGENTRUNDETAILID NUMBER(9,0),
    AGENTRUNDATE TIMESTAMP (6),
    STATUS VARCHAR2(30 BYTE),
    EMAILSENDDATE DATE,
    SMSSENDDATE DATE
  );

  CREATE SEQUENCE TBLMAGENTHISTORY_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER NOCYCLE ;

- Document Category
 Insert into TBLMDOCUMENTCATEGORY (DOCUMENTCATEGORYID,NAME,DESCCRIPTION,ALIAS,ENABLEEMAIL,ENABLESMS) values (108,'Pending Order Notification ','Template For Pending Order Notification','PENDING_ORDER_NOTIFICATION','Y','N');
 
-- Tblmagent
Insert into TBLMAGENT (AGENTID,NAME,DESCRIPTION,IMPLCLASS,EXECUTIONTYPEID,EXEPERIODINMIN,PRIORITY,ISPARAMETERREQUIRED,COMMONSTATUSID,ISVISIBLE,ISACTIVE,CREATEDATE,CREATEDBY,LASTMODIDATE,LASTMODIBY,LASTEXETIME,ISSCHEDULABLE) values ('AGT03','Order notification Agent','This is Pending Order Notification Agent. Primary role of this agent is to send notification to warehouse owner if their order remain pending for more than allowed days.','com.elitecore.cpe.bl.agents.customs.OrderNotificationAgent','EXT01',1440,4,'N',null,'Y','Y',to_timestamp('10-03-15 02:32:31.000000000 PM','DD-MM-RR HH12:MI:SS.FF AM'),'STF0001',to_timestamp('10-03-15 02:32:31.000000000 PM','DD-MM-RR HH12:MI:SS.FF AM'),'STF0001',to_timestamp('23-03-15 06:23:31.057000000 PM','DD-MM-RR HH12:MI:SS.FF AM'),'Y');

--tblmagentconfig
Insert into TBLMAGENTCONFIG (AGENTID,ARMECONCURRENCYLIMIT,ARECONCURRENCYLIMIT,PARMECHUNKSIZE,ARMECHUNKSIZELIMIT,PARECHUNKSIZE,ARECHUNKSIZELIMIT,OVERRULEAMECHUNKSIZE,OVERRULEAECHUNKSIZE,REASON) values ('AGT03',2,50,-1,2,-1,100,'Y','Y','Running Order Notification Agent');

--TBLRDOCCATEGORYMESSAGETAGREL 
Insert into TBLRDOCCATEGORYMESSAGETAGREL (DOCUMENTCATEGORYID,MESSAGETAGID) values (108,113);
Insert into TBLRDOCCATEGORYMESSAGETAGREL (DOCUMENTCATEGORYID,MESSAGETAGID) values (108,111);


commit;