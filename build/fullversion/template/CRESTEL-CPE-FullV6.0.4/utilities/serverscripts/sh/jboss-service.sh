if [ ${#} -lt 1 ] ;
then
     echo "USAGE : sh jboss-service.sh  [stop/status] port "
     echo "USAGE : sh jboss-service.sh  restart port [JBOSS_HOME]"
     echo "USAGE : sh jboss-service.sh  start [JBOSS_HOME]"
     exit 1;
fi

OPS=`uname`
process=""

if [ "$1" = "stop" -o "$1" = "restart" -o "$1" = "status" ] ; then
	if [ -z "$2" ]; then
     		echo "USAGE : Please specify port "
     		exit 1;
	fi  

	if [ "$OPS" = "SunOS" ]; then
	for pid in `ps -ef -o pid | tail +2` 
	do 
		foundport=`/usr/proc/bin/pfiles $pid 2>&1 | grep "sockname:" | grep "port: $2$"`
		if [ "$foundport" != "" ] 
		then 
			JBOSS_STATUS_FLAG=true	
			process=$pid
		fi 
	done
	fi

        if [ "$OPS" = "Linux" ]; then
			process=`netstat -anp | grep $2 | awk '{print $7}' | sed 's/\([[:digit:]]*\)\/[[:print:]]*/\1/'`

            if [ ! -z "$process" ]; then
                 JBOSS_STATUS_FLAG=true
            fi

        fi

	if [ "$JBOSS_STATUS_FLAG" != "true" ]; then
		echo "JBoss is not running"
	else
		echo "JBoss is running"
	fi
	if [  "$1" = "stop" -o "$1" = "restart"  -a "$JBOSS_STATUS_FLAG" = "true" ]; then
		kill -9 $process
		echo "JBoss stopped"
	fi
fi

if [ "$1" = "start" -o "$1" = "restart" ] ; then

        if [ "$1" = "restart" -a ! -z "$3" ]; then 
                JBOSS_HOME=$3
        fi
        if [ "$1" = start -a ! -z "$2" ]; then
                JBOSS_HOME=$2
        fi

	echo "Starting jboss from $JBOSS_HOME"
	cd $JBOSS_HOME/bin
	sh startup.sh 
fi




