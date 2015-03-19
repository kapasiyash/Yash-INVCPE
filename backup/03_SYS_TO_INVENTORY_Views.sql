---------------AUDIT----------------------------------
CREATE OR REPLACE FORCE VIEW VIEWAUDITDATA
AS
SELECT
a.systemauditid AS SYSTEMAUDITID,
a.auditdate as AUDITDATE,
a.actionid AS ACTIONID,
c.actionalias,
a.reason AS REASON,
a.remarks AS REMARKS,
a.ipaddress AS IPADDRESS,
d.name AS AUDITTYPE,
b.username AS USERNAME
FROM tblisystemaudit A
INNER JOIN tblmstaff B
ON b.staffid=a.userid
INNER JOIN tblisystemaction C
on C.actionid=a.actionid
INNER JOIN tblsaudittype D
on d.audittypeid=a.audittypeid;
/

COMMIT;
