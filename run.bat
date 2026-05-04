@echo off
set "LIB_DIR=lib"
set "BIN_DIR=bin"

set "MAIN_CLASS=%~1"
if "%MAIN_CLASS%"=="" set "MAIN_CLASS=ui.App"

echo ---------------------------------------------------------
echo Running %MAIN_CLASS% with native access enabled...
echo (You can also run: .\run.bat ui.StudentForm or .\run.bat ui.AdminDashboard)
echo ---------------------------------------------------------
java --enable-native-access=ALL-UNNAMED -cp "bin;lib/sqlite-jdbc-3.45.1.0.jar;lib/slf4j-api-2.0.12.jar;lib/slf4j-simple-2.0.12.jar;lib/mysql-connector-j-9.7.0.jar" %MAIN_CLASS%
