# 初始化和制作vagrant box的基础镜像

  我们约定使用全部服务器VirtualBox虚拟机都使用Ubuntu 18 (Bionic)，下面步骤是制作一个专用的Ubuntu 18基础镜像。

1. 添加官方制作的ubuntu/bionic64的vagrant box
   
   - 第一种方法是从vagrant官网下载添加

     ```shell
     vagrant box add ubuntu/bionic64
     ```

     可选的，添加成功后，可以执行以下命令导出保存成本地的box文件
    
     ```shell
     vagrant box repackage ubuntu/bionic64 virtualbox 20201201.0.0
     mv package.box ubuntu-bionic-20201201.box
     ```

   - 第二种方法，从vagrant官网直接添加vagrant box非常耗时，所以也可以从我们的镜像网站下载并添加

     ```shell
     ./import_hashicorp.sh
     ```

2. 制作我们专用的Ubuntu 18的基础镜像

   - 第一种方法是自己制作，比较耗时
  
     ```shell
     ./build.sh
     ```

   - 第二种方法，是可以从我们的镜像网站下载并添加

     ```shell
     ./import.sh
     ```

3. 登录进虚拟机，设置密码
   
   ```shell
   vagrant ssh
   # 以下在虚拟机内
   sudo passwd dev
   sudo passwd admin
