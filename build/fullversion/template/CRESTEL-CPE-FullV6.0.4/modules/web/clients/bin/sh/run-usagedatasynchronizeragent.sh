#!/bin/sh
# FileName - runbillpreview.sh

# Default locations of jars we depend on
echo "These agents have been deprecated. Please visit New Agents section of JISP Billing to execute all agents."

#cd /opt/billing/agentrun
#JAVA_HOME=/export/home/opt/j2sdk1.4.2
#JAR_HOME=/export/home/manoj/baiju/jboss-3.2.1/client
#
#unset TMP_CLASSPATH
#TMP_CLASSPATH=.
#for i in ${JAR_HOME}/*.jar ; do
#  if [ "$TMP_CLASSPATH" != "" ]; then
#    TMP_CLASSPATH=${TMP_CLASSPATH}:$i
#  else
#    TMP_CLASSPATH=$i
#  fi
#done
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
#unzip -o -qq /opt/billing/ear/billingx.ear
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
#echo `date` " Integration Agent Started" >> integrationagent.log
#
#$JAVA_HOME/jre/bin/java -classpath .:"$TMP_CLASSPATH" com/elitecore/billing/agent/UsageDataSynchronizerAgent $1 >> UsageDataSynchronizer.log
#
#echo `date` " Integration Agent Stopped" >> integrationagent.log 2>&1
#
#
#
#
#
#
#
#