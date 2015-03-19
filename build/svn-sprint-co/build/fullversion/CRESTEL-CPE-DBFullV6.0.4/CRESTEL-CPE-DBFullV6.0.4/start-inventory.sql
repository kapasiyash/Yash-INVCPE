set define on;
set echo on;

prompt Creating Space

DEFINE sysDBUser="&&sys_db_user"
DEFINE sysDBPassword="&&sys_db_password"
DEFINE paymentDBUser="&&payment_db_user"
DEFINE paymentDBPassword="&&payment_db_password"
DEFINE beDBUser="&&be_db_user"
DEFINE beDBPassword="&&be_db_password"
DEFINE partnerDBUser="&&partner_db_user"
DEFINE partnerDBPassword="&&partner_db_password"
 
prompt &datafile_path
prompt &database_env
create tablespace &&database_env datafile '&&datafile_path/&&database_env..dbf' size 50 M AUTOEXTEND ON ;
prompt Completed
prompt

prompt Creating Users
create user &&database_env identified by &&database_env DEFAULT TABLESPACE &&database_env;
grant CONNECT,RESOURCE,DBA to &&database_env;
prompt completed
prompt

set define on;

conn &&database_env/&&database_env@//&&db_ip:&&db_port/&&db_sid 
prompt


set define on;
prompt Grant PARTNER objects in CPE 
conn &&partnerDBUser/&&partnerDBPassword@//&&db_ip:&&db_port/&&db_sid
@01_PARTNER_TO_INVENTORY_Grant.sql &&database_env &&partnerDBUser
prompt Completed - Grant PARTNER objects in CPE

prompt Creating PARTNER SYNONYMS in CPE
conn &&database_env/&&database_env@//&&db_ip:&&db_port/&&db_sid 
@02_PARTNER_TO_INVENTORY_Synonym.sql &&partnerDBUser
prompt Complete - PARTNER SYNONYMS Created Successfully

prompt Creating Views in CPE
conn &&database_env/&&database_env@//&&db_ip:&&db_port/&&db_sid 
@03_PARTNER_TO_INVENTORY_Views.sql
prompt Complete - Views Created Successfully

prompt Creating Table
set define off;

@ app-setup.sql;






