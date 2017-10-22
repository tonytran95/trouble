@echo off
echo.
echo Current external IP Address
powershell -Command "& {irm ipinfo.io/ip}"
java -jar TroubleServer.jar
pause