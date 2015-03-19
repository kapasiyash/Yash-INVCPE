
update tblmmessagetemplate set actionId=(select actionid from tblisystemaction where actionalias='CREATE_RESOURCESUBTYPE') where templateid=501;

update tblmmessagetemplate set actionId=(select actionid from tblisystemaction where actionalias='CREATE_RESOURCESUBTYPE') where templateid=502;



Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid) from tblisystemaction)+1,106,7,1,'Change Inventory Status','CHANGE_INVENTORY_STATUS','Change Inventory Status','Y','Y','N','UTY001','/WEB-INF/pages/core/master/item/change-inventory-status.zul','Y');

Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid) from tblisystemaction)+1,106,9,1,'Inventory History Detail','INVENTORY_HISTORY_DETAIL','Inventory History Detail','Y','Y','N','UTY001','/WEB-INF/pages/core/inventory/search/Inventoryhistorydetail.zul','Y');

Insert into TBLISYSTEMACTION (ACTIONID,PARENTACTIONID,SEQUENCENUMBER,MODULEID,NAME,ACTIONALIAS,DESCRIPTION,ISAUDITABLE,ENABLEAUDIT,ENABLENOTIFICATION,USERTYPEID,PAGEURL,ENABLEVISIBLE) values ((select max(actionid) from tblisystemaction)+1,106,8,1,'Change Inventory SubStatus','CHANGE_INVENTORY_SUBSTATUS','Change Inventory SubStatus','Y','Y','N','UTY001','/WEB-INF/pages/core/inventory/search/changeInventorySubStatus.zul','Y');


commit;
