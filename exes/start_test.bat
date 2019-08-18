@echo off
setlocal enabledelayedexpansion
cd /d  "%~dp0"
set filename=Servilio.jar
::Start only last version (by date) of the program
::According to https://devblogs.microsoft.com/oldnewthing/20120801-00/?p=6993
for /f %%s in ('dir /b/a-d/od/t:c Servilio-*.jar') do ( 
	set filename=%%s
) 
cmd /c start java -jar %filename% -t=4 --option=1 --inputFolder="%~dp0\test"
