set filedate=%DATE:~4%
set filetime=%TIME:~1,-3%
set filename=Billing_DB_%filedate:/=-%_%filetime::=%.log

mkdir installationdir

ant -quiet -f build-version-install.xml install-db -Dfile.path=%cd% -Dfile.name=%1 -Dinstallation.dir=%cd%\installationdir
