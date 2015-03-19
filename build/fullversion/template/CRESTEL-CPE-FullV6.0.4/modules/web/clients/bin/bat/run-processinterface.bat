@echo off

set ANT_HOME=C:\apache-ant-1.6.0

%ANT_HOME%\bin\ant -f ..\agent-processinterface.xml -Darg.value="%*"