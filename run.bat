@echo off
set "CLASSPATH=lib\mysql-connector-j-8.4.0.jar;."
javac -d . backend\*.java dao\*.java frontend\*.java
java -cp %CLASSPATH% frontend.Main
pause
