alter table tblminventory add (externalbatchnumber varchar2(50));

Insert into TBLMSYSTEMPARAMETER (SYSTEMPARAMETERID,SYSTEMPARAMETERGROUPID,NAME,ALIAS,VALUE,VALUESOURCE,DESCRIPTION,ALLOWOVERRIDE,LASTMODIDATE,LASTMODIBY,ISVISIBLE,CUSTOMFIELDTYPEID,CONSTRAINTEXPRESSIONID) values (10,1,'Email Alias for resource notify(TO)','EMAIL_ALIAS_RESOURCE_TO','abc@elitecore.com',null,'Email Alias for resource notification (TO)','Y',sysdate,'STF0001','Y','CFT01','REG05');
Insert into TBLMSYSTEMPARAMETER (SYSTEMPARAMETERID,SYSTEMPARAMETERGROUPID,NAME,ALIAS,VALUE,VALUESOURCE,DESCRIPTION,ALLOWOVERRIDE,LASTMODIDATE,LASTMODIBY,ISVISIBLE,CUSTOMFIELDTYPEID,CONSTRAINTEXPRESSIONID) values (11,1,'Email Alias for resource notify(CC)','EMAIL_ALIAS_RESOURCE_CC','abc@elitecore.com',null,'Email Alias for resource notification (CC)','Y',sysdate,'STF0001','Y','CFT01','REG05');

alter table tblmwarehouse add (warehousecode varchar2(50));

alter table tblminventory add (GuranteeWarrantyMode varchar2(20));
alter table tblminventory add (warrantydate timestamp(6));
alter table tblminventory add (warrantytype varchar2(20));

update tblisystemaction set isauditable='N' where actionalias='WAREHOUSE_SUMMARY';
update tblisystemaction set isauditable='N' where actionalias='WAREHOUSE_TREEVIEW';


commit;
