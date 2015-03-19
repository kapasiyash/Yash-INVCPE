#!/bin/bash
partition=$1
limit=$2
usage=$(df -kh $partition | grep -i $partition | awk '{print $5}' | cut -d"%" -f1)

#echo "Usage = $usage"
IP=$(/sbin/ifconfig eth0 | grep -i "inet addr" | cut -d":" -f2 | cut -d' ' -f1)
#echo "IPADDRESS = $IP"
if test $usage -ge $limit
then 
	echo "Server : $IP" >> /loadscript/mail.txt
	echo "Partition : \"$partition\" is over used with $usage% usage." >> /loadscript/mail.txt
	echo "Date & Time : $(date)" >> /loadscript/mail.txt
	echo " " >> /loadscript/mail.txt
	echo " " >> /loadscript/mail.txt
	df -kh >> /loadscript/mail.txt
#	echo " Partition \"$partition\" is over used with $usage% usage. Please take care of it asap.\n$(df -kh)" | mail -s "[ ALERT ]At $IP -  Diskspace of partition  $partition OverUsage" sohil.kakadia@elitecore.com ritesh.pandey@elitecore.com
	cat /loadscript/mail.txt | mail -s "[ ALERT ] At [ $IP ] - Disk space of partition $partition is over used" $(cat /loadscript/email.txt)
	rm -f /loadscript/mail.txt
fi

