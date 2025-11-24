#!/bin/bash

#./mvnw -P-webapp -DskipTests
#./mvnw spring-boot:run -Dspring.devtools.restart.enabled=false
#./mvnw -DskipTests -Dskip.npm -Dskip.yarn -Dskip.client spring-boot:run
./mvnw -DskipTests \
       -Dskip.client \
       -Dskip.npm \
       -Dskip.yarn \
       -Dskip.webapp \
       spring-boot:run
