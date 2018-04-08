#!/bin/bash
java \
  -Djava.rmi.server.useCodebaseOnly=false \
  -Djava.rmi.server.logCalls=false \
  -cp ./classloading/client:./classloading/common \
  Client