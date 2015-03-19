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

commit;
