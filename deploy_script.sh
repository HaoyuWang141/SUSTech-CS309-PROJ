#!/bin/bash

cd springboot/
mvn clean package -DskipTests

cd target/
nohup java -jar dormitory.jar > ./nohup.out 2>&1 &
