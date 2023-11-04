#!/bin/bash

cd springboot/
which mvn
/home/lighthouse/apache-maven-3.9.4/bin/mvn clean package -DskipTests

cd target/
kill $(lsof -t -i:8082)
nohup java -jar dormitory.jar > ./nohup.out 2>&1 &
cat ./nohup.out