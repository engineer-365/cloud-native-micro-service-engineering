# 初始化启动
  ```shell
  # 下载vagrant virtualbox镜像，导入
  ./import.sh

  # 启动
  ./up.sh

  # 因为预先加载了不少plugin，jenkins启动过程中跨国下载慢，会等待20分钟
  vagrant ssh -c "sudo cat /var/lib/jenkins/secrets/initialAdminPassword"
  # 譬如：aee27d4d95a84b7c93f784da36fd5840

  # 验证：http://192.168.50.121:8080是否能否访问
  # 外部访问需要用反向代理，譬如https://builder.engineer365.org:40443

  # SSH登录
  vagrant ssh
  ```
 
# 设置maven
  - https://builder.engineer365.org:40443/configureTools
  - “默认全局 settings 提供”： - /var/lib/jenkins/.m2/settings.xml
  - "Maven 安装"
    - 点击"新增maven"
    - name: "Maven 3.5.3"
    - 去掉“自动安装”
    - "MAVEN_HOME" :/opt/maven
  - "JDK 安装":
    - “别名”填写："openjdk 11.0.7+10"
    - 去掉“自动安装”
    - "JAVA_HOME"： /opt/jdk-11
    
# 创建第一个job

  https://builder.engineer365.org:40443/view/all/newJob

  - 选择“构建一个maven项目”，任务名字为“fleashop-server”, 点击“确定”按钮
  - 选择“GitHub 项目”：
    - 项目 URL： https://github.com/engineer-365/cloud-native-micro-service-engineering/
    - “源码管理”选择“git“：  https://github.com/engineer-365/cloud-native-micro-service-engineering.git
    - ”指定分支（为空时代表any）“：“*/main”
    - “源码库浏览器”： 自动   “githubweb"
    - URL:  https://github.com/engineer-365/cloud-native-micro-service-engineering/
    - Build:
      - Root POM: server/pom.xml
      - “Branches to build”：“
    - 选择“丢弃旧的构建”，“保持构建的天数”设为30
    - 选择“Delete workspace before build starts”
    - 选择“Add timestamps to the Console Output”
    - 选择“Abort the build if it's stuck”，“Timeout minutes”设为60

# 若干plugin docs
  - https://plugins.jenkins.io/maven-plugin/
  - https://plugins.jenkins.io/docker-workflow/
  - https://www.jenkins.io/doc/book/pipeline/
  - https://plugins.jenkins.io/workflow-aggregator/
  - https://www.jenkins.io/doc/book/blueocean
  
# 制作镜像
  ```shell
  ./build.sh
  ```
