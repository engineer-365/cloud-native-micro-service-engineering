## 注：
  - 以下命令暂时仅支持ubuntu，其它操作系统请参照着改动。可下载到文件可以参见https://download.engineer365.org:40443/
  - 需要一台物理机器。因为第一阶段是使用VirtualBox虚拟机，每台虚拟机内存设置为4GB，所以建议32G内存，或者需修改Vagrantfile、降低内存大小设置

## 准备工作

```shell
DOWNLOAD_SITE=https://download.engineer365.org:40443
sudo mkdir /data/ && sudo chown -R $USER:$USER /data/
cd /data/
```

## 安装VirtualBox

   https://www.virtualbox.org
   https://mirrors.tuna.tsinghua.edu.cn/virtualbox/

```shell
sudo ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone
sudo apt-get install -y linux-headers-$(uname -r) build-essential gcc make python zip cmake uuid tree jq

VIRTUALBOX_DEB=virtualbox-6.1_6.1.14-140239_Ubuntu_$(lsb_release -cs)_amd64.deb
wget --quiet "${DOWNLOAD_SITE}/virtualbox/${VIRTUALBOX_DEB}"
sudo dpkg -i ${VIRTUALBOX_DEB}
sudo apt --fix-broken install
rm ${VIRTUALBOX_DEB}
```

## 安装vagrant
   
   https://www.vagrantup.com/intro

```shell
VAGRANT_DEB=vagrant_2.2.14_x86_64.deb
wget --quiet "${DOWNLOAD_SITE}/vagrant/${VAGRANT_DEB}"
sudo dpkg -i ${VAGRANT_DEB}
rm ${VAGRANT_DEB}

sudo echo 'export VAGRANT_EXPERIMENTAL="disks"' >> /etc/profile
source /etc/profile

vagrant plugin install vagrant-vbguest
# Installed the plugin 'vagrant-vbguest (0.28.0)'!

vagrant plugin install vagrant-disksize
# Installed the plugin 'vagrant-disksize (0.1.3)'!
```



