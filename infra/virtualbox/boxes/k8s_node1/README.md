  注：2020/12/13: 目前是临时的设置，只用来简单地启动Java应用服务，还没安装K8S。

# 1. 制作K8S worker node 1镜像

     下面步骤是存储类服务器VirtualBox虚拟机使用的基础镜像。

     1. 第一种方法是自己制作，比较耗时
  
        ```shell
        ./build.sh
        ```

     2. 第二种方法，是可以从我们的镜像网站下载并添加

        ```shell
        ./import.sh
        ```

# 2. 常用vagrant命令
     
     启动：```vagrant up```
     SSH登录：```vagrant ssh```
