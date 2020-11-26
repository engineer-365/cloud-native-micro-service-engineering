# 演示项目：云原生的微服务开发工程

## 想法：
   - 采用云原生的微服务架构: Docker, Kubernetes。和Spring Cloud体系相比，侵入性低，更灵活，更方便支持其他语言。
   - 选择一个需求上能涵盖主要技术栈的产品应用，因此目前考虑是一个书店电商系统，只实现基本功能
   - 以后端为主，最初会以JAVA的Spring Boot全家桶开始，包括MySQL、Redis、RabbitMQ、ElasticSearch等基本的主流技术栈，然后一步步增加别的，包括支持GO等其他语言。
   - 坚持做自动化测试：单元测试、集成测试、性能测试、压力测试、端对端功能测试。测试覆盖率>=80%。（题外话：很多其他开源的产品演示性项目单元测试是0）。
   - 前后端分离。前端最初用React或Vue的全家桶开发Web应用，以后陆续扩展到小程序和手机App，建立大前端体系。
   - 持续集成（CI）、持续部署（CD），自动化运维、集中式日志、监控、分布式调用链路追踪。
   - DevOps，GitOps。
   - 整合常见开发工具，譬如Maven, SonarQube, Slack，GITHUB等。
   - 为所使用的每一个技术都建立一个指南文档。
   - 后续会加入大数据方案。
   - 在考虑演进成Serverless，目前还没定，也可能会是另一个新项目。

## 目标：

  - 一期开发：
    - 阶段性目标：
      - 最小化工程化和功能实现
      - 模块化的单体应用
      - 前后端分离，完整、可运行
      - 在开发者本地环境中手动部署和运行
    - 阶段性技术栈：
      - 后端 -> Java技术栈:
         - Spring MVC: REST API(JSON), Swagger (Spring Fox)
         - 测试: Junit5 (单元测试), Mockito (Mock), JaCoCo (测试覆盖率分析)
         - 数据库访问：Spring Data JPA，QueryDSL（动态查询），Flyway（数据库升级管理）
         - 日志：Logback
         - Jar包依赖管理：Maven
         - Spring Boot Actuator：监控数据采集和管理
         - 其他：Guava（工具类），Lombok (Java Bean代码生成)
      - 前端 -> React全家桶：
         - Webpack
         - 路由：React Router
         - UI库：Material Design或Ant Design
         - REST客户端：Axios
         - 测试：Jest
      - Nginx
      - 关系数据库：MySQL单节点，H2（测试用）
      - 持续集成（CI）：Jenkins, GITHUB Action, SonarQube (代码质量检查)
      - Maven私有仓库：Nexus
      - 开发者本地Docker开发环境：Docker-compose
      - 用Helm Chart或Kustomize手动部署到Kubernetes
      - Spring Boot Admin

  - 二期开发：
    - 阶段性目标：
      - 主要功能全部实现
      - 初步实现云原生的微服务架构
      - 自动部署
    - 阶段性技术栈
      - 后端增加：
        - 参数校验: javax.validation (JSR-303, Hibernate Validator)
        - 测试: Cucumber, TestContainers (容器化测试)，JMeter (性能测试和压测)
        - 权限控制：Spring Security (OAuth + JWT)
        - 日志：JSON日志格式
        - REST客户端: Resilience4J，Spring RestTemplate，或Open Feign
        - 缓存：Jedis, Spring Data Redis, Caffeine
        - 全文检索：Spring Data Elasticsearch
        - 消息队列：Spring AMQP, RabbitMQ
        - 定时任务：TBD
      - 前端增加：
        - 状态管理：Redux或MobX
        - 测试：Selenium
    - 关系数据库：高可用MySQL（MySQL Router + Group Replication）
    - 持续部署（CD）：ArgoCD（自动部署到Kubernetes）
    - 私有Docker仓库：Habor
    - 分布式缓存：Redis, Redis Sentinel
    - ELK (Elasticsearch + Logstash + Kibana): 全文检索，日志管理
    - 配置管理：Consul，Vault（MySQL等密码管理），Git2Consul
    - 分布式调用链路追踪：Opencensus, Jaeger
    - 监控报警：Prometheus，Grafana

  - 三期开发：
    - 阶段性目标：
      - 主要功能全部实现
      - 完整实现云原生的微服务架构
      - 支持多语言
      - 云原生的持续集成和持续部署
      - 云原生的自动化运维
    - 阶段性技术栈
      - 后端：
        - 支持GOLANG，Python，Node.JS开发微服务
        - 部分功能改用Servless实现
        - GraphQL API
        - gRPC
        - WebSocket
        - Workflow
      - 前端：
        - GraphQL API
        - 参与全链路数据采集追踪
      - API网关：Kong，Ambassador，或Traefix？
      - Lstio
      - NoSQL数据库：MongoDB
      - 自动化运维：Ansible，Vagrant，Terraform

  - 四期开发：
    - 阶段性目标：
      - 对接云供应商：阿里云，AWS，Azure等
      - 大数据分析：Flink，Hive, Kafka等
      - 全面高可用

## 问答：

- 太多了，一看就跟不上，就连这个简介都看不懂
  一步一步来，很多方面发起者也仅大致了解，一起在实践中学习。
  现在的趋势就是云原生、微服务、DevOps，列出的这些技术就是在跟着大趋势走，不敢说走在技术最前沿，但走在了主流的前列里面。

  从最简单的spring项目开始

其中会使用到Spring Cloud体系内的部分组件，但不是Spring Cloud的路线，

go后面才加，会用go写微服务，但一开始是java
其它是正规公司工程环境里用的，自己多少知道，一起学习实践


最近正好有计划做一个类似的，想做的平台是基于vertex的kotlin和actix的rust，以及尽量全套的异步组件，顺便试试postgresql@https://github.com/fastgh/fgit
https://github.com/fastgh/fgit  12:35:53
嗯，了解点异步/非阻塞。java里异步还没主流，我的主要想法之一是先用主流的东西，有了一个平台，再在这个平台上尝试别的
这样，在平台上把某个微服务改成异步，或者增加一个新的异步的微服务，应该不算难事
而且，我觉得可以借此体会一下从传统方式向异步的迁移过程，那样一个过程，过程本身也挺有价值

使用云原生当然方式做的好处之一，就是代码侵入性少


一开始关注后端就好了，也不仅仅限制于前后端分离，需要的是提供如何将自己的前端技术栈接入该后端项目的指南，像react，vue，或者后端渲染，或者模版引擎等等
点还是后端，前端一开始需要有一个最小化的原型，光一个接口的定义不够整出一个接入的规范/指南，因为，目标不是做出一个产品，目标是一个工程性的系统性的东西，需要配套接口以外的不少东西


可以提供一些项目额外的包，例如该项目叫 awesome, 就可以提供 awesome-react-starter, awesome-vue-starter, awesome-blazor-starter, 毕竟专注后端，在一个项目集成与前端相关的一些东西多少会有些不好



这
看得懂就不用一起学了。一步一步来，从最简单的spring项目开始。其它是成熟公司工程环境里用的，自己多少知道，一起学习实践