set define on;
set echo on;

DEFINE partnerDBUser="&&partner_db_user"
DEFINE partnerDBPassword="&&partner_db_password"

INSERT INTO TBLISYSTEMACTION(ACTIONID, PARENTACTIONID, SEQUENCENUMBER, MODULEID, NAME, ACTIONALIAS, DESCRIPTION, ISAUDITABLE, ENABLEAUDIT, ENABLENOTIFICATION, USERTYPEID, PAGEURL, ENABLEVISIBLE)
 VALUES ((select max(actionid) from tblisystemaction)+1, '137', '3', '6', 'Delete Thresholds', 'DELETE_THRESHOLDS', 'Delete Thresholds', 'Y', 'Y', 'N', 'UTY001', '/WEB-INF/pages/core/master/warehouse/deleteThreshold.zul', 'Y');

 commit;

conn &&partnerDBUser/&&partnerDBPassword@//&&db_ip:&&db_port/&&db_sid

insert into tblmaction values('ACN60053','Delete Thresholds','SCR001','DELETE_THRESHOLDS','Delete Thresholds','ACT02',null,4,'N','MOD603',null,null,0,'FIXED',null);

 commit;

conn &&database_env/&&database_env@//&&db_ip:&&db_port/&&db_sid 

commit;
