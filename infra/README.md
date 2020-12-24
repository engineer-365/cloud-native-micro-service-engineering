# 使用VirtualBox虚拟机运行测试环境

  这个子项目的目的，是遵循"基础设施代码化管理(Infrastructure As Code)"的最佳实践，

  > As a best practice, infrastructure-as-code mandates that whatever work is needed to provision computing resources it must be done via code only.

  > 作为最佳实践，基础设施及代码授权所有准备计算资源所需要做的工作都可以通过代码来完成

  参见：
    - https://www.jianshu.com/p/8f5b3cbf0928
    - https://www.oreilly.com/library/view/infrastructure-as-code/9781491924334/
    - https://www.ibm.com/cloud/learn/infrastructure-as-code

  项目的第一阶段的目标是：

  - 1台虚拟机部署Jenkins, 做持续集成（CI）和持续部署（CD）; 后续阶段加入多个Jenkins K8S节点。
  - 1台虚拟机部署Harbor, 作为Docker Registry; 后续阶段部署多个Registry节点，并和其它外部Docker Registry同步。
  - 1台虚拟机部署MySQL主节点, 后续阶段升级成MySQL 1主2从，以及MGR集群。
  - 1台虚拟机部署K8S Master, 后续阶段扩展成高可用的K8S Master集群。
  - 2台虚拟机部署K8S Worker。

## 注意：
  - 以下命令暂时仅在Ubuntu 20上测试验证过，Windows等其它操作系统请参照着修改 （非常希望能作为PR提交给我们）。
  - 因为第一阶段是使用VirtualBox虚拟机，需要一台物理机器，每台虚拟机内存设置为4GB，所以全部部署的话会启动6台虚拟机，所以建议至少32G内存，或者自行修改Vagrantfile降低内存大小设置。
  - 第一阶段使用VirtualBox虚拟机，用Vagrant和shell脚本管理，同时，Vagrant和VirtualBox也会作为个人开发环境一直使用下去。
  - 第二阶段使用Ansible替换shell脚本，和Nomad。
  - 第三阶段使用Terraform等，在OpenStack或公有云上建立测试环境。

## 1. 准备工作

   ```shell
   # download.engineer365.org放了Vagrant和VirtualBox等会用到的部分二进制安装包，
   # 后续的vagrant设置过程也会使用从这里下载一些安装包
   DOWNLOAD_SITE=https://download.engineer365.org:40443

   # 使用/data目录作为各个虚拟机的安装目录
   sudo mkdir -p /data/virtualbox_vms && sudo chown -R $USER:$USER /data/
   cd /data/
   ```

## 2. 安装VirtualBox 6.1.14

   ```shell
   sudo ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone
   sudo apt-get install -y linux-headers-$(uname -r) build-essential gcc make python zip cmake uuid tree jq

   VIRTUALBOX_DEB=virtualbox-6.1_6.1.14-140239_Ubuntu_$(lsb_release -cs)_amd64.deb
   wget --quiet "${DOWNLOAD_SITE}/virtualbox/${VIRTUALBOX_DEB}"
   sudo dpkg -i ${VIRTUALBOX_DEB}
   sudo apt --fix-broken install
   rm ${VIRTUALBOX_DEB}

   # the default folder is $HOME/'VirtualBox VMs', so move VirtualBox VM folders,
   VBoxManage setproperty machinefolder /data/virtualbox_vms
   ```

   VirtualBox的安装包还可以在以下2个下载地址下载：
   - https://www.virtualbox.org/wiki/Download_Old_Builds_6_1
   - https://mirrors.tuna.tsinghua.edu.cn/virtualbox/

## 3. 安装vagrant

   https://www.vagrantup.com/intro

   ```shell
   VAGRANT_DEB=vagrant_2.2.14_x86_64.deb
   wget --quiet "${DOWNLOAD_SITE}/vagrant/${VAGRANT_DEB}"
   sudo dpkg -i ${VAGRANT_DEB}
   rm ${VAGRANT_DEB}

   # “disks”还只是vagrant的实验特性，需要设置VAGRANT_EXPERIMENTAL环境变量来启用
   sudo echo 'export VAGRANT_EXPERIMENTAL="disks"' >> /etc/profile
   source /etc/profile

   # vagrant-vbguest(0.28.0)是一个vagrant插件，用于安装VirtualBox Guest扩展
   vagrant plugin install vagrant-vbguest
   # Installed the plugin 'vagrant-vbguest (0.28.0)'!

   # vagrant-disksize (0.1.3)是一个vagrant插件，用于修改Virtual Box磁盘大小（默认大小偏小，仅10G空间）
   vagrant plugin install vagrant-disksize
   ```

## 3. 启动虚拟机

     ```shell
     /virtualbox
     ├── boxes     # Vagrant box的构建目录
     ├── builder1  # Jenkins虚拟机
     │   └── up.sh # Jenkins虚拟机的启动脚本，其他虚拟机类似，下面不再重复
     ├── etc_hosts # 所有虚拟机的ip列表。构建Vagrant box的过程中生成
     ├── k8s_node1 # K8S Master
     ├── k8s_node2 # K8S Worker 1
     ├── k8s_node3 # K8S Worker 2
     ├── script    # 一些共用脚本
     ├── store1    # MySQL主节点
     ├── store4    # Harbor Docker Registry
     ```

## 5. 可选：构建各虚拟机用的Vagrant Box

   Vagrant Box是给Vagrant针对VirtualBox镜像做的打包格式，以`.box`作为文件扩展名。
   构建过程比较耗时，所以启动脚本已经配置为从https://download.engineer365.org:40443/vagrant/box/下载到我们已经构建好的box文件。

   构建所需的Vagrantfile都在目录`/virtualbox/boxes`的子目录里，每个子目录代表一个Vagrant box，如下所示。

   ```shell
   /virtualbox/boxes
   ├── builder  # 所有builder虚拟机的共用基础box，安装JDK/GO/Node/Maven等构建环境
   ├── builder1 # 第一个builder虚拟机，是Jenkins主节点
   ├── k8s_node1 # K8S Master虚拟机
   ├── store1 # 5个store虚拟机之一，MySQL主节点
   ├── store4 # 5个store虚拟机之一，Harbor Docker Registry
   └── ubuntu-bionic # 基础的ubuntu 18 (bionic) box，增加了docker等软件包
   ```

   每个子目录的结构都相似，譬如子目录/virtualbox/boxes/builder1有如下结构：

   ```shell
   /virtualbox/boxes/builder1
   ├── build.sh # 启动构建的bash脚本，在宿主机上执行
   ├── provision.sh # 虚拟机启动成功后的初始化脚本，在虚拟机内执行，譬如安装JDK
   ├── post-install # 虚拟机启动成功后会用到的文件，不同的虚拟机不一样
   │   └── etc
   │       ├── default
   │       │   └── jenkins
   │       └── nginx
   │           └── sites-enabled
   │               ├── mirrors.jenkins-ci.org.conf
   │               └── updates.jenkins-ci.org.conf
   ├── pre-install # 虚拟机启动成功前会拷贝进虚拟机内的文件，不同的虚拟机不一样
   │   └── root
   │       └── jenkins-tool
   │           ├── executors.groovy
   │           └── plugins.yaml
   ├── README.md
   └── Vagrantfile # Vagrant设置文件
   ```


