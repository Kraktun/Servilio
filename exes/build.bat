@echo off
cd /d  "%~dp0"
set current=%~dp0
cd ..
echo BUILDING JAR
call gradlew fatJar
move build\libs\*.jar %current%
echo BUILDING ZIP
call gradlew distZip
move build\distributions\*.zip %current%

