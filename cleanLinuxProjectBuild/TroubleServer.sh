#!/bin/bash
echo Current external IP Address
dig +short myip.opendns.com @resolver1.opendns.com
echo
java -jar TroubleServer.jar