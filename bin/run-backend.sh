#!/bin/bash

# Directory where native libraries (.so) are placed for the FLIR SDK
NATIVE_LIB_DIR="$(pwd)/native-libs/x86_64"

# Export LD_LIBRARY_PATH so the OS linker can find the native libs
export LD_LIBRARY_PATH="$NATIVE_LIB_DIR:${LD_LIBRARY_PATH:-}"

# JVM arguments: point java.library.path to the native libs and enable remote debug on 5005
JVM_ARGS="-Djava.library.path=$NATIVE_LIB_DIR -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

# Run Spring Boot with same skip flags as before, passing JVM args to the spring-boot plugin
./mvnw -DskipTests \
       -Dskip.client \
       -Dskip.npm \
       -Dskip.yarn \
       -Dskip.webapp \
       spring-boot:run -Dspring-boot.run.jvmArguments="$JVM_ARGS"
