TODAY=`(set \`date +%Y_%m_%d_%H%M%S\`; echo $1)`
LOG=Pin_Application_$TODAY.log

 
mkdir installationdir

${ANT_HOME}/bin/ant -f build-version-install.xml -Dfile.path=`pwd` -Dfile.name=$1 -Dinstallation.dir=`pwd`/installationdir > $LOG &
