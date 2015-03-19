
alter table TBLTTRANSFERORDERDETAIL add (previousstatus NUMBER(5,0));
alter table TBLTTRANSFERORDERDETAIL add (previoussubstatus NUMBER(5,0));


update tblisystemaction set isauditable='N' where actionalias in ('UPDATE_THRESHOLD','SEARCH_INVENTORY_BATCH','VIEW_WAREHOUSE','SEARCH_RESOURCE','SEARCH_ATTRIBUTE','SEARCH_AGENT_IN_QUEUE','VIEW_AGENT_HISTORY','THRESHOLD_MASTER','VIEW_AUDIT_ENTRY','SEARCH_WS_AUDIT','SEARCH_RESOURCETYPE','VIEW_RESOURCETYPE','SEARCH_RESOURCESUBTYPE','VIEW_RESOURCESUBTYPE','WAREHOUSE_SUMMARY','WAREHOUSE_TREEVIEW','SEARCH_DOCUMENT_TEMPLATE','SEARCH_PLACE_ORDER','VIEW_WAREHOUSE_HIERARCHY','');

update tblisystemaction set parentactionid=(select parentactionid from tblisystemaction where actionalias='THRESHOLD_MASTER') where actionalias='CONFIGURE_THRESHOLD';

Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid) from tblisystemaction)+1,null,7,2,'Login-Logout Management','LOGIN_LOGOUT_MANAGEMENT','Login-Logout Management','Y','Y','N','UTY001',null,'N');
Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid) from tblisystemaction)+1,(select actionid from tblisystemaction where actionalias='LOGIN_LOGOUT_MANAGEMENT'),8,2,'Login Action','LOGIN_ACTION','Login Action','Y','Y','N','UTY001',null,'Y');
Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid) from tblisystemaction)+1,(select actionid from tblisystemaction where actionalias='LOGIN_LOGOUT_MANAGEMENT'),8,2,'Logout Action','LOGOUT_ACTION','Logout Action','Y','Y','N','UTY001',null,'Y');

insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values ( TBLMMESSAGETEMPLATE_SEQ.nextval,(select actionid from tblisystemaction where actionalias = 'LOGIN_ACTION'  ),'User:${name} logged in to the system','${name}');
insert into TBLMMESSAGETEMPLATE(TEMPLATEID,ACTIONID,TEMPLATE,SUPPORTEDTAGS) values ( TBLMMESSAGETEMPLATE_SEQ.nextval,(select actionid from tblisystemaction where actionalias = 'LOGOUT_ACTION'  ),'User:${name} logged out to the system','${name}');


commit;
