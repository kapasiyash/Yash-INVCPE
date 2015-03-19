delete from tbliauditentry cascade;
delete from tblisystemaudit cascade;
delete from tbltattributetrans cascade;
delete from tblmresourceattributerel cascade;
delete from tblmattribute  WHERE name not in  ('MAC Address','Serial Number');
delete from tblmbatchsummary cascade;

delete from tblminvalidinventory cascade;
delete from tblminventory cascade;
delete from tblmorder cascade;

delete from tblmresourceattributerel cascade;
delete from tblmbatch cascade;

delete from tblmtransferorder cascade;

delete from tblmwarehousealert cascade;
delete from tblmwarehouseinventorystatus cascade;
delete from tblmwarehouse where alias not in ('CENTRAL');

delete from tblsresourcesubtype cascade;
delete from tblswarehousetype where alias not in ('CENTRAL');
delete from tbltagentrundetail cascade;
delete from tbltagentrunqueue  cascade;
delete from tblmagentschedule  cascade;
delete from TBLTINVENTORYRESERVEDETAIL cascade;
delete from tbltinventoryreserve  cascade;
delete from TBLTTRANSFERORDERDETAIL cascade;
delete from tbltwhinventorystatushistory cascade;

delete from tblmresource cascade;
delete from tblsresourcetype cascade;

delete from tblmprimarykey where alias not in ('WAREHOUSE_DATA','ATTRIBUTE_DATA','ITEM_DATA','BATCH_DATA',
'INVENTORY_DATA','RESOURCE_DATA','TRANSFERORDER_DATA','RESERVE_DATA','ORDER_DATA');

commit;
