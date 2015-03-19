mkdir installationdir

ant -f build-version-install.xml all install-db -Dfile.path=%cd%\ -Dfile.name=%1 -Dinstallation.dir=%cd%\installationdir 
