@echo off
echo Compiling...
if not exist bin mkdir bin
dir /s /B *.java > sources.txt
javac -d bin @sources.txt
del sources.txt