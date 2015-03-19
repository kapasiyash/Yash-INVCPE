set define on;
set echo on;


DEFINE partnerDBUser="&&partner_db_user"
DEFINE partnerDBPassword="&&partner_db_password"


set define on;

conn &&database_env/&&database_env@//&&db_ip:&&db_port/&&db_sid 
prompt


set define on;
prompt Grant partner objects in CPE 
conn &&partnerDBUser/&&partnerDBPassword@//&&db_ip:&&db_port/&&db_sid
@01_PARTNER_TO_INVENTORY_Grant.sql &&database_env &&partnerDBUser
@PARTNER_ACTIONS.sql
prompt Completed - Grant partner objects in CPE

prompt Creating partner SYNONYMS in CPE
conn &&database_env/&&database_env@//&&db_ip:&&db_port/&&db_sid 
@02_PARTNER_TO_INVENTORY_Synonym.sql &&partnerDBUser
prompt Complete - partner SYNONYMS Created Successfully

prompt Creating Views in CPE
conn &&database_env/&&database_env@//&&db_ip:&&db_port/&&db_sid 
@03_PARTNER_TO_INVENTORY_Views.sql
prompt Complete - Views Created Successfully

prompt Creating Table
set define off;

@ Crestel-CPE-DB-6.0.1-INCMR01.sql;




