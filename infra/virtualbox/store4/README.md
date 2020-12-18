# Store4虚拟机 - Harbor ...

## 初始化启动
  ```shell
  # 下载vagrant virtualbox镜像，导入
  ./import.sh

  # 启动
  ./up.sh

  # 验证：http://192.168.50.20:80是否能否访问
  # 外部访问需要用反向代理，譬如https://docker.engineer365.org:40443


   docker.engineer365.org

  # SSH登录
  vagrant ssh
  ```

## Harbor
   ## 设置admin的密码
      - 打开浏览器，访问https://docker.engineer365.org:40443
        - 用户名：admin，初始密码Engineer12345
        - 右上角菜单：admin -> 修改密码
   - 安装目录：/opt/harbor
   - 数据目录：/data/harbor
   - 重启和升级等：https://goharbor.io/docs/2.0.0/install-config/reconfigure-manage-lifecycle/)
   - 初始化Engineer365的项目和用户：
     - 选择左侧菜单“系统管理”/“用户管理”/“创建用户”
       - 为每一个团队成员创建一个用户账号
       - 为builder(jenkins)创建一个用户账号
         - 用户名：jenkins
         - 邮箱：jenkins@engineer365.org
         - 密码： ***
     - 选择左侧菜单“项目”/“新建项目”
       - 项目名称：engineer365，访问级别：不公开
       - 创建后点击进入该项目，选择“成员”tab，点击“+成员”
         - 把每一个团队成员都加入成为“受限访客”（Limited Guest）。
         - 把jenkins用户加入成为“开发者”（Developer）。
         - 关于项目成员角色，参见https://goharbor.io/docs/2.0.0/administration/managing-users/user-permissions-by-role/
   - 从这个docker registry拉取镜像时前需要一次性登录
     - docker login -u 用户名 docker.engineer365.org:40443
     - 配置jenkins登录builder.engineer365.org:40443


## 制作镜像
  ```shell
  ./build.sh
  ```
