#!/bin/bash
IP=$(/sbin/ifconfig eth0 | grep -i "inet addr" | cut -d":" -f2 | cut -d' ' -f1)
#echo "IPADDRESS = $IP"
#echo "Status of the $IP at Time: $(date)  $(df -kh)" | mail -s "[ STATUS ] At [ $IP ] Disk space usage" sohil.kakadia@elitecore.com ritesh.pandey@elitecore.com renish@elitecore.com niraj@elitecore.com abdulla.saiyed@elitecore.com brijesh.kansagra@elitecore.com paresh.patel@elitecore.com amaradeep.gundala@elitecore.com
echo "Server : $IP" >> /loadscript/statusmail.txt
echo "Date & Time : $(date)" >> /loadscript/statusmail.txt
echo " " >> /loadscript/statusmail.txt
echo " " >> /loadscript/statusmail.txt
df -kh >> /loadscript/statusmail.txt

cat /loadscript/statusmail.txt | mail -s "[ STATUS ] [ $IP ] Disk space Usage" $(cat /loadscript/email.txt)
rm -f /loadscript/statusmail.txt


