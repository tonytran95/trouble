#!/bin/bash
echo Compiling...
mkdir -p bin
find -name "*.java" > sources.txt
javac -d bin @sources.txt
rm -rf sources.txt