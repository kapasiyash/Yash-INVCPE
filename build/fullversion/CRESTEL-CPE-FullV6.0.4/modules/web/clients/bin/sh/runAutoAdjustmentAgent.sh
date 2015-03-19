#!/bin/sh
# FileName - runbillalert.sh

#REM Default locations of jars we depend on

echo "These agents have been deprecated. Please visit New Agents section of JISP Billing to execute all agents." 

#cd /opt/billing/agentrun
#JAR_HOME=/opt/jboss/jboss-3.2.1/client
#
#unset TMP_CLASSPATH
#for i in ${JAR_HOME}/*.jar ; do
#  if [ "$TMP_CLASSPATH" != "" ]; then
#    TMP_CLASSPATH=${TMP_CLASSPATH}:$i
#  else
#    TMP_CLASSPATH=$i
#  fi
#done
#
#
#JAR_HOME=/opt/jboss/jboss-3.2.1/lib
#
#for i in ${JAR_HOME}/*.jar ; do
#  if [ "$TMP_CLASSPATH" != "" ]; then
#    TMP_CLASSPATH=${TMP_CLASSPATH}:$i
#  else
#    TMP_CLASSPATH=$i
#  fi
#done
#unzip -o -qq /opt/billing/ear/billing.ear
#
#JAR_HOME=.
#
#for i in ${JAR_HOME}/*.jar ; do
#  if [ "$TMP_CLASSPATH" != "" ]; then
#    TMP_CLASSPATH=${TMP_CLASSPATH}:$i
#  else
#    TMP_CLASSPATH=$i
#  fi
#done
#
##echo $TMP_CLASSPATH
#
#echo `date` " Agent Started" >> billAlert.log
#$JAVA_HOME/jre/bin/java -classpath .:"$TMP_CLASSPATH" com.elitecore.billing.agent.AutoAdjustmentAgent >> billAlert.log
#echo `date` " Agent Stopped" >> billAlert.log 2>&1
#
#
#
#
#
#
#
#
#
#
#