How to Use Disk Space Utility:

1. Make a folder "loadscript" as below:
	mkdir /loadscript
	
2. Put the loadcheck.sh and email.txt in the "loadscript" folder.

3. Put all the email address in email.txt seperated by blank space in "loadscript" folder.

4. To Execute:         sh loadcheck.sh /u02 80
			Where "/u02" is the partition to be checked and "80" is the limit to be checked and if this limit is crossed then
			then a email will float to all the email addresses put in email.txt


Precautions:

1. Mail Service should be configured and running.



Important:

if you want to run this utility over a certain period of time periodically then put this in crontab as follows:

1 2 * * * /bin/sh /load/loadcheck.sh /u02 80
			
