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

commit;
