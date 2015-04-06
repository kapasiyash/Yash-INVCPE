set define on;
set echo on;

alter table TBLMWAREHOUSE add constraint TBLMWAREHOUSE_UK_WHCODE unique (WAREHOUSECODE)  deferrable initially immediate novalidate; 
update TBLMSYSTEMPARAMETER set SYSTEMPARAMETERGROUPID=2 where alias='MOBILE_NO_FOR_RESOURCE_SMS_NOTIFICATION';
commit;
