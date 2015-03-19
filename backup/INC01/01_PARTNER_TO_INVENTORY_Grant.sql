DEFINE CPEDBUSER = &&1
DEFINE partnerDBUser = &&2
prompt CPEDBUSER = &&CPEDBUSER                                                                                           
prompt partnerDBUser = &&partnerDBUser                                           
                                                      
GRANT SELECT ON &&partnerDBUser..TBLMSTAFF TO &&CPEDBUSER;
GRANT SELECT ON &&partnerDBUser..TBLMGROUP TO &&CPEDBUSER;
GRANT SELECT ON &&partnerDBUser..TBLMGROUPACTIONREL TO &&CPEDBUSER;
GRANT SELECT ON &&partnerDBUser..TBLMACTION TO &&CPEDBUSER;
GRANT SELECT ON &&partnerDBUser..TBLMSYSTEMUSERGROUPREL TO &&CPEDBUSER;
