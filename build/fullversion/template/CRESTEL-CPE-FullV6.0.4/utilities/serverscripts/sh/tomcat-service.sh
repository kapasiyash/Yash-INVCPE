if [ ${#} -lt 1 ] ;
then
     echo "USAGE : sh tomcat-service.sh  [stop/status] port "
     echo "USAGE : sh tomcat-service.sh  restart port [TOMCAT_HOME]"
     echo "USAGE : sh tomcat-service.sh  start [TOMCAT_HOME]"
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
                        TOMCAT_STATUS_FLAG=true
                        process=$pid
                fi 
        done
        fi

        if [ "$OPS" = "Linux" ]; then
                process=`netstat -anp | grep $2 | awk '{print $7}' | sed 's/\([[:digit:]]*\)\/[[:print:]]*/\1/'`
                if [ ! -z "$process" ]; then
                        TOMCAT_STATUS_FLAG=true
                fi
        fi

        if [ "$TOMCAT_STATUS_FLAG" != "true" ]; then
                echo "Tomcat is not running"
        else
                echo "Tomcat is running"
        fi
        if [  "$1" = "stop" -o "$1" = "restart"  -a "$TOMCAT_STATUS_FLAG" = "true" ]; then
                kill -9 $process
                echo "Tomcat stopped"
        fi
fi

if [ "$1" = "start" -o "$1" = "restart" ] ; then
        if [ "$1" = "restart" -a ! -z "$3" ]; then 
                TOMCAT_HOME=$3
        fi
        if [ "$1" = start -a ! -z "$2" ]; then
                TOMCAT_HOME=$2
        fi

        echo "Starting tomcat from $TOMCAT_HOME"
        cd $TOMCAT_HOME/bin
        sh startup.sh 
fi
