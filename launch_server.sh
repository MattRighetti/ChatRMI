#!/bin/bash
echo "Launching server"
echo "Insert localhost or public IP"
read IP

echo "Using remote codebase..."
java \
-Djava.rmi.server.useCodebaseOnly=false \
-Djava.rmi.server.logCalls=true \
-Djava.rmi.server.codebase="http://$IP:8080/server/ http://$IP:8080/common/" \
-cp ./classloading/server:./classloading/common \
Server