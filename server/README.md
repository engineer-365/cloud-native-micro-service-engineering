# 二手书店的服务器端

## 准备工作
  - 安装JDK 11

  - 安装docker: https://docs.docker.com/engine/install/

    - Linux (Ubuntu X86-64): https://docs.docker.com/engine/install/ubuntu/
      ```shell
      sudo apt-get remove docker docker-engine docker.io containerd runc
      sudo apt-get update
      sudo apt-get -y install apt-transport-https ca-certificates curl gnupg-agent software-properties-common
      curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
      sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
      sudo apt-get update
      sudo apt-get install docker-ce docker-ce-cli containerd.io
      ```
      非root用户如何使用docker：https://docs.docker.com/engine/install/linux-postinstall/·

    - Windows：https://docs.docker.com/docker-for-windows/install/
      下载：https://desktop.docker.com/win/stable/Docker%20Desktop%20Installer.exe

    - Mac：https://docs.docker.com/docker-for-mac/install/
      下载：https://desktop.docker.com/mac/stable/Docker.dmg

  - 安装docker-compose: https://docs.docker.com/compose/install
    - Linux (Ubuntu X86-64): https://docs.docker.com/engine/install/ubuntu/
      ```shell
      sudo curl -L "https://github.com/docker/compose/releases/download/1.27.4/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
      sudo chmod +x /usr/local/bin/docker-compose
      ```

    - Windows和Mac：已包括在docker安装包里

  - <TODO> 1） 说明如何使用国内docker镜像 2）docker离线安装

## 本地构建

    <TODO> 说明如何使用国内maven镜像，和相关maven包的离线安装

  1. 编译打包：

     注：因为集成测试里包括了编译打包的步骤，所以也可以跳过这一步直接执行下一步的```集成测试```

     ```./mvnw package```

  2. 集成测试：

     ```./mvnw verify```
     注意，集成测试使用了docker-compose，所以Linux环境下需要加上```sudo```。Windows和Mac环境下因为docker实际是跑在了Virtual Box虚拟机里，所以不需要管理员权限。

  3. 可选：
     - 下载第三方包的源代码和javadoc，方便调试和学习
       - ```./mvnw dependency:sources```
       - ```./mvnw dependency:resolve -Dclassifier=javadoc```
     - 生成IDE项目文件
       - ```./mvnw idea:idea```
       - ```./mvnw eclipse:eclipse```
       然后就可以导入idea或eclipse

  4. 启动/查看log／停止
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

  5. 简单验证RESTful API：

     - VSCode的REST插件: 见[./dev/manual-test.rest](./dev/manual-test.rest)

     - Curl: ```curl -v --request GET --header 'content-type: application/json' --url http://localhost:28080/platform/user/api/v1/rest/user/_/xxx```


## 示范的技术

  1. Spring Boot:

     - 官网：https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

  2. JaCoCo:

     测试覆盖率工具
     - 官方文档： https://www.jacoco.org/jacoco/trunk/doc/

  3. Flyway:

     数据库初始化和升级管理
     - 官网：https://flywaydb.org/

  4. QueryDSL:

     替代JPA自身的Criteria API，和Criteria API一样支持灵活的动态数据库查询语句，但相比JPA Criteria API：

     1. 通过插件把数据库表的表名和列名绑定到类型安全的Java Bean property，方便重构，也减少直接使用字符串时容易发生的拼写错误
     2. 查询条件用DSL风格的级联方式表达，接近直接写SQL，简单直观，容易学习使用

     - 官网：http://www.querydsl.com/
     - 简介文章：https://www.baeldung.com/intro-to-querydsl
     - 参考手册：http://www.querydsl.com/static/querydsl/latest/reference/html_single/

  5. H2:

     仅用于单元测试。
     - 用于单元测试的优点：
       1. 快 - 对于单元测试这非常重要
       2. 不需要数据清理 - 因为H2是内存数据库，默认设置下数据不放磁盘，因此简化了单元测试的准备工作

  6. Lombok:

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

  7. testcontainers:

     容器化测试工具。本项目中用于集成测试。
     - 官网：https://www.testcontainers.org/
     - 和JUnit5的集成：https://www.testcontainers.org/quickstart/junit_5_quickstart/，以及https://www.testcontainers.org/test_framework_integration/junit_5/
     - 和docker-compose的集成：https://www.testcontainers.org/modules/docker_compose/

## 常见问题

  > 1. ```./mvnw package```或```./mvnw verify```卡住不动了，怎么办？

       第一次构建时，首先会下载maven wrapper，然后再从maven官网下载依赖到的很多第三方包，确实非常慢，可能需要半小时，最好是开着梯子。
       其中，最开始下载maven wrapper的jar包时，因为没有任何提示文字，会给人以卡住不动的错觉。虽然我们推荐使用maven wrapper以便使用统一的
       maven版本（目前是3.6.3），不过大多数情况使用自己原本安装的maven一般也是可以的，那么，直接把命令重的```./mvnw```替换成```mvn```就可以了，
       这样可以省去下载maven wrapper的时间。

       注意：构建正式的docker镜像（即Dockerfile.release）时，因为容器内的maven repository是隔离的，所以构建和依赖的第三方包还是会重新下载一遍，而且，每次修改pom.xml以后再构建正式的docker镜像，都会触发重新下载第三方包，这是正常现象。开发用的docker镜像（即Dockerfile.dev)没有做构建，而是直接使用了本地环境的编译好的jar包（在target目录下），所以不存在这个现象。

       后续会搭建我们的代理/镜像服务器（因为各种代理/镜像服务器也是国内开发工程的一部分）

  > 2. 怎样初始化MySQL数据库?

       开发环境下，我们使用docker-compose管理开发服务器和MySQL，包括后续其他的Redis等也会这样做，所以不需要另外再安装MySQL或初始化数据库表。其中的MySQL，就定义在了dev/docker-compose.*.yml文件中，用户名和密码等参数在dev/.env文件中。

       另外，我们使用了Flyway，开发环境下，Flyway默认打开，服务器启动时Flyway会自动执行数据库初始化SQL和升级用SQL，这些SQL脚本遵循Flyway的规则和缺省设置，放在了src/main/resource/db目录下。

       所以，概括说就是，不需要手动初始化或升级MySQL数据库。

  > 3. 开发环境下的MySQL的数据目录在哪里？

       ```dev/work/mysql```

