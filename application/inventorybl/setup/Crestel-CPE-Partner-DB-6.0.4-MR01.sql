set define on;
set echo on;

DEFINE partnerDBUser="&&partner_db_user"
DEFINE partnerDBPassword="&&partner_db_password"

conn &&partnerDBUser/&&partnerDBPassword@//&&db_ip:&&db_port/&&db_sid

insert into tblmsystemmodules values('MOD630','BULK_STATUS_CHANGE_FOR_INVENTORY','Bulk Status Change For Inventory','Bulk Status Change For Inventory',sysdate,sysdate,'N','MOD611',999);

insert into tblmaction values('ACN60054','Bulk Status Change For Inventory(Menu)','SCR001','BULK_STATUS_CHANGE_FOR_INVENTORY_MGT','Bulk Status Change For Inventory','ACT02',null,4,'N','MOD630',null,null,0,'FIXED',null);

insert into tblmsystemmodules values('MOD631','USER_WAREHOUSE_MAPPING_MGT','User Warehouse Mapping','User Warehouse Mapping',sysdate,sysdate,'N','MOD618',999);

insert into tblmaction values('ACN60055','User Warehouse Mapping(Menu)','SCR001','USER_WAREHOUSE_MAPPING','User Warehouse Mapping','ACT02',null,4,'N','MOD631',null,null,0,'FIXED',null);

commit;

