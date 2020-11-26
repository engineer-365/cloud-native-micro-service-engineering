# 演示项目：云原生的微服务开发工程

下面长文，但主要的目的其实只有一个：招兵不买马:-)，欢迎大家报名参加。

这个项目是开放的，欢迎提交pr。不过，通常我们还是对参加者有一点要求，感兴趣的话请坚持看完，看到底。

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

  看得懂就不用一起学了。

  一步一步来，很多方面我也仅大致了解，我们在实践中一起学习。

  现在的趋势就是云原生、微服务、DevOps，列出的这些技术就是在跟着大趋势走，不敢说走在技术最前沿，但走在了主流的前列里面。因此，目前为止，很多技术要么只能在它们的官方网站上才学得到，要么在网上零零散散的而且大部分还是重复的或过时的，跟着书学或跟着培训班学效果不好，而且很可能也学到的都是过时的。真正的技术只可能在系统化的实践中才学得到学得好。

  列出的这些大部分是已经在成熟公司里用起来的技术，确实太多技术了（实际上还只是一小部分），对刚入门的开发者来说确实会非常懵，但一旦用过了、过了这个门槛，我们会发现其实完全没那么难，相当一部分其实只是知道不知道的区别，真正决定自己能走得多远站得多高的还是自己的基础能力和努力程度。

- 为什么不是Spring Cloud，为什么不是Dubbo （Spring Cloud Dubbo）？

  微服务架构有两个主要路线，一是Sping Cloud，二是云原生，云原生的微服务架构里，服务治理的实现大部分不需要修改代码，意味着代码侵入性小，跨语言支持方便，扩展性强。个人更喜欢云原生的方案。

  另一方面，Spring Cloud的开源演示项目已经非常多了，相对来说云原生方案因为比较新，数量还不多，而且，云原生架构对基础架构和系统工程的要求更高，这也是这个项目的目标包括了很多工程性的技术而不局限于纯粹的代码开发的原因，我们希望在学习实践的同时也能通过这个项目为促进云原生方案的更多普及作出一些贡献。

  Dubbo最初是阿里的RPC类项目，中间被暂停废弃过一段实践，现在阿里在大力推动，包括和云原生的集成，Spring Cloud Dubbo就是成果之一。坦白讲，不太放心，看着Dubbo的历史，再看看Dubbo现在这么依赖阿里一己之力...。另外，私心里:-），我觉得Dubbo有阿里官方会大力在推动和支撑的，个人没啥很多好参与的。和Dubbo接近的是gRPC，gRPC的社区要开放和成熟许多，gRPC明确的是在这个项目的目标里。

  如果你看到了项目里用到了一些Spring Cloud体系内的组件，那是正常的，但总体来说，并不是Spring Cloud的方案。

- 我不喜欢或者不习惯你提到的技术，或者我觉得应该尝试一些更新的技术，能不能换成xx？譬如，有群友说：“最近正好有计划做一个类似的，想做的平台是基于vertex的kotlin和actix的rust，以及尽量全套的异步组件，顺便试试postgresql@https://github.com/fastgh/fgit
https://github.com/fastgh/fgit  12:35:53
嗯，了解点异步/非阻塞。java里异步还没主流，我的主要想法之一是先用主流的东西，有了一个平台，再在这个平台上尝试别的
这样，在平台上把某个微服务改成异步，或者增加一个新的异步的微服务，应该不算难事
而且，我觉得可以借此体会一下从传统方式向异步的迁移过程，那样一个过程，过程本身也挺有价值

使用云原生当然方式做的好处之一，就是代码侵入性少


一开始关注后端就好了，也不仅仅限制于前后端分离，需要的是提供如何将自己的前端技术栈接入该后端项目的指南，像react，vue，或者后端渲染，或者引擎等等
点还是后端，前端一开始需要有一个最小化的原型，光一个接口的定义不够整出一个接入的规范/指南，因为，目标不是做出一个产品，目标是一个工程性的系统性的东西，需要配套接口以外的不少东西


可以提供一些项目额外的包，例如该项目叫 awesome, 就可以提供 awesome-react-starter, awesome-vue-starter, awesome-blazor-starter, 毕竟专注后端，在一个项目集成与前端相关的一些东西多少会有些不好



这
看得懂就不用一起学了。一步一步来，从最简单的spring项目开始。其它是成熟公司工程环境里用的，自己多少知道，一起学习实践
