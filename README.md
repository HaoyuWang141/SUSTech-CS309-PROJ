# SUSTech CS309 PROJ

## Introduction

南方科技大学 2023年秋季学期

CS309 OOAD 课程 project


## Overview  


## GitHub CI/CD

Actions: `main.yml`

```yaml
name: Deploy to Tencent Server

on:
  push:
    branches:
      - main
    paths:
      - 'springboot/**'

jobs:
  deploy:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v2

      - name: deploy
        uses: garygrossgarten/github-action-ssh@release
        with:
          command: |
            cd ~/ooad_proj/
            git pull origin main
            ./deploy_script.sh
          host: ${{ secrets.HOST }}
          username: lighthouse
          password: ${{ secrets.PASSWORD }}
          
```



## Server deploy process

maven 3.6.x 不支持 Java17;

spring-boot 工程固定跑在 8082 端口上；

mvn package 生成的 jar 包名固定为 `dormitory.jar`

```bash
#!/bin/bash

cd springboot/
which mvn
/home/lighthouse/apache-maven-3.9.4/bin/mvn clean package -DskipTests

cd target/
kill $(lsof -t -i:8082)
nohup java -jar dormitory.jar > ./nohup.out 2>&1 &
cat ./nohup.out
```



