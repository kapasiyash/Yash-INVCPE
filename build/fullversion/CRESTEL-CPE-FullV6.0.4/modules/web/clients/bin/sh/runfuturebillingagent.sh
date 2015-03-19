#!/bin/sh
# FileName - runEscalationFlowDaemon.sh

#REM Default locations of jars we depend on 
echo "These agents have been deprecated. Please visit New Agents section of JISP Billing to execute all agents."


#cd ${BILLING_HOME}/agentrun
#JAR_HOME=${JBOSS_HOME}/client
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
#JAR_HOME=${JBOSS_HOME}/lib
#
#for i in ${JAR_HOME}/*.jar ; do
#  if [ "$TMP_CLASSPATH" != "" ]; then
#    TMP_CLASSPATH=${TMP_CLASSPATH}:$i
#  else
#    TMP_CLASSPATH=$i
#  fi
#done
#unzip -o -qq ${BILLING_HOME}/ear/billing.ear
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
#echo `date` " Agent Started" >> startbillingfutureagent.log
#$JAVA_HOME/jre/bin/java -classpath .:"$TMP_CLASSPATH" com.elitecore.billing.agent.StartBillingFutureAgent  >> startbillingfutureagent.log
#echo `date` " Agent Stopped" >> startbillingfutureagent.log 2>&1
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
#