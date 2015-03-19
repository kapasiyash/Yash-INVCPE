echo "which port:> " 
read port 

for pid in `ps -ef -o pid | tail +2` 
do 
foundport=`/usr/proc/bin/pfiles $pid 2>&1 | grep "sockname:" | grep "port: $port$"`
if [ "$foundport" != "" ] 
then 
echo "proc: $pid, $foundport" 
fi 
done

