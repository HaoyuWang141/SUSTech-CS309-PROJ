#!/bin/bash

cd springboot/
which mvn
/home/lighthouse/apache-maven-3.9.4/bin/mvn clean package -DskipTests

cd target/
nohup java -jar dormitory.jar > ./nohup.out 2>&1 &
