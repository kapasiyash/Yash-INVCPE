mkdir installationdir

${ANT_HOME}/bin/ant -f build-version-install.xml all install-db -Dfile.path=`pwd` -Dfile.name=$1 -Dinstallation.dir=`pwd`/installationdir 
