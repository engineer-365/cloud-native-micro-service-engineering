# 二手书店的服务器端

## 示范的技术

  - Spring Boot:
    - 官网：https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

  - JaCoCo:
    测试覆盖率工具
    - 官方文档： https://www.jacoco.org/jacoco/trunk/doc/

  - Flyway:
    数据库初始化和升级管理
    - 官网：https://flywaydb.org/

  - QueryDSL:
    替代JPA自身的Criteria API，和Criteria API一样支持灵活的动态数据库查询语句，但相比JPA Criteria API：
    1. 通过插件把数据库表的表名和列名绑定到类型安全的Java Bean property，方便重构，也减少直接使用字符串时容易发生的拼写错误
    2. 查询条件用DSL风格的级联方式表达，接近直接写SQL，简单直观，容易学习使用

    - 官网：http://www.querydsl.com/
    - 简介文章：https://www.baeldung.com/intro-to-querydsl
    - 参考手册：http://www.querydsl.com/static/querydsl/latest/reference/html_single/

  - H2:
    仅用于单元测试。
    - 用于单元测试的优点：
      1. 快 - 对于单元测试这非常重要
      2. 不需要数据清理 - 因为H2是内存数据库，默认设置下数据不放磁盘，因此简化了单元测试的准备工作

  - Lombok:
    代码生成插件，通过简单的annotation来消除一些常见的无聊的Java代码，譬如Getter和Setter。

    - 官网：https://projectlombok.org
    - 简介文章：
      1. https://blog.csdn.net/ThinkWon/article/details/101392808
      2. https://www.jianshu.com/p/365ea41b3573

    注意：IDE里使用需要安装和该IDE集成的插件，否则IDE会不会触发代码生成、找不到生成的代码而报编译错误。
    常见的IDE插件：
      1. VS Code：https://marketplace.visualstudio.com/items?itemName=GabrielBB.vscode-lombok
      2. IDEA：待补充
      3. Eclipse：待补充


## 本地构建

  - 编译打包：
     ```./mvnw package```
     第一次构建会从maven官网下载依赖到的很多第三方包，会很慢，最好是开着梯子。
     后续会搭建自己的代理/镜像服务器，因为代理/镜像服务器也是实际工程（国内）的一部分

  -  可选：下载第三方包的源代码和javadoc，方便调试和学习
     ```mvn dependency:sources```
     ```mvn dependency:resolve -Dclassifier=javadoc```
     
  - 启动/查看log／停止
    - Mac或Linux
      - ```dev/up.sh```
      - ```dev/log.sh```
      - ```dev/down.sh```
    - Windows
      - TODO：bat脚本待写，以下命令待验证
        ```shell
        cd dev
        docker-compose up --build -d --remove-orphans
        ```
        ```shell
        cd dev
        docker-compose logs
        ```
        ```shell
        cd dev
        docker-compose down --remove-orphans
        ```
   
  - 简单验证RESTful API：
    - VSCode的REST插件: 见[./manual-test.rest](./manual-test.rest)
    - Curl: ```curl -v --request GET --header 'content-type: application/json' --url http://localhost:28080/platform/user/api/v1/rest/user/_/xxx```
