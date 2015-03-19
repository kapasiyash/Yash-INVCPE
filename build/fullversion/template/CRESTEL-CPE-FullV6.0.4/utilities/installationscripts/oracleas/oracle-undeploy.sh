echo " ############################################"
echo " #           DEPLOY CRESTEL	          #"
echo " ############################################"
 
echo "JAVA_HOME = " $JAVA_HOME
echo "ANT_HOME  = " $ANT_HOME

ant -f undeploy-oracle.xml
