#!/bin/bash

#
#  MIT License
#
#  Copyright (c) 2020 engineer365.org
#
#  Permission is hereby granted, free of charge, to any person obtaining a copy
#  of this software and associated documentation files (the "Software"), to deal
#  in the Software without restriction, including without limitation the rights
#  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
#  copies of the Software, and to permit persons to whom the Software is
#  furnished to do so, subject to the following conditions:
#
#  The above copyright notice and this permission notice shall be included in all
#  copies or substantial portions of the Software.
#
#  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
#  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
#  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
#  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
#  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
#  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
#  SOFTWARE.

#set -e
set -x

readonly download_site=$1
readonly org=$2
readonly admin_user=$3
readonly dev_user=$4

echo "export LC_ALL=en_US.UTF-8" >> /etc/profile

# apt mirror ###################################################################
cat > /etc/apt/sources.list <<EOF
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic main restricted universe multiverse
deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-security main restricted universe multiverse
deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-security main restricted universe multiverse 
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse
deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
EOF

echo "/etc/apt/sources.list: "
cat /etc/apt/sources.list
echo ""

apt-get update
apt-get -y upgrade

# set timezone
ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone

# TODO: more utilities
apt-get install -y linux-headers-$(uname -r) build-essential gcc make python zip cmake uuid tree jq

# install docker ###############################################################
mkdir /etc/docker/
cat > /etc/docker/daemon.json <<EOF
{
    "registry-mirrors": [
      "https://docker.mirrors.ustc.edu.cn",
      "https://registry.docker-cn.com/",
      "https://hub-mirror.c.163.com"
    ]
}
EOF

echo "/etc/docker/daemon.json: "
cat /etc/docker/daemon.json
echo ""

# https://download.docker.com/linux/ubuntu/dists/bionic/pool/stable/amd64/containerd.io_1.3.9-1_amd64.deb
CONTAINERD_DEB=containerd.io_1.3.9-1_amd64.deb
wget --quiet "${download_site}/docker/${CONTAINERD_DEB}"
dpkg -i ${CONTAINERD_DEB}
rm ${CONTAINERD_DEB}

# https://download.docker.com/linux/ubuntu/dists/bionic/pool/stable/amd64/docker-ce-cli_19.03.14~3-0~ubuntu-bionic_amd64.deb
DOCKER_CLI_DEB=docker-ce-cli_19.03.14~3-0~ubuntu-bionic_amd64.deb
wget --quiet "${download_site}/docker/${DOCKER_CLI_DEB}"
dpkg -i ${DOCKER_CLI_DEB}
rm ${DOCKER_CLI_DEB}

# https://download.docker.com/linux/ubuntu/dists/bionic/pool/stable/amd64/docker-ce_19.03.14~3-0~ubuntu-bionic_amd64.deb
DOCKER_DEB=docker-ce_19.03.14~3-0~ubuntu-bionic_amd64.deb
wget --quiet "${download_site}/docker/${DOCKER_DEB}"
dpkg -i ${DOCKER_DEB}
rm ${DOCKER_DEB}

# install docker-compose #######################################################

# https://github.com/docker/compose/releases/download/1.27.4/docker-compose-Linux-x86_64
DOCKER_COMPOSE_BINARY=docker-compose-Linux-x86_64-1.27.4
curl --silent -L "${download_site}/docker/${DOCKER_COMPOSE_BINARY}" -o /usr/local/bin/docker-compose
chmod a+x /usr/local/bin/docker-compose

systemctl daemon-reload
systemctl restart docker

## set up users: "admin", "dev" ################################################
groupadd ${org}
echo "AllowGroups root vagrant ${org}" >> /etc/ssh/sshd_config
#sed -i -e "s/PermitRootLogin yes/PermitRootLogin no/" /etc/ssh/sshd_config
echo "PermitRootLogin no" >> /etc/ssh/sshd_config
systemctl restart ssh

## set up admin user -----------------------------------------------------------
useradd -g ${admin_user} --home-dir /home/${admin_user} --create-home --shell /bin/bash ${admin_user}
usermod -aG sudo ${admin_user}
usermod -aG docker ${admin_user}

# echo "${admin_user}  ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers
echo "${admin_user}  ALL=(ALL) NOPASSWD:ALL" | tee /etc/sudoers.d/${admin_user}

mkdir -p /home/${admin_user}/.ssh
ssh-keygen -P "" -t rsa -C "${admin_user}@${org}" -f /home/${admin_user}/.ssh/id_rsa
cat /home/${admin_user}/.ssh/id_rsa.pub >> /home/${admin_user}/.ssh/authorized_keys
chown -R ${admin_user}:${admin_user} /home/${admin_user}/.ssh

## set up dev user -------------------------------------------------------------
useradd --user-group --home-dir /home/${dev_user} --create-home --shell /bin/bash ${dev_user}
usermod -aG sudo ${dev_user}
usermod -aG docker ${dev_user}

echo "${dev_user}  ALL=(ALL) NOPASSWD:/usr/bin/docker,/usr/local/bin/docker-compose" | tee /etc/sudoers.d/${dev_user}

mkdir -p /home/${dev_user}/.ssh
ssh-keygen -P "" -t rsa -C "${dev_user}@${org}" -f /home/${dev_user}/.ssh/id_rsa
cat /home/${dev_user}/.ssh/id_rsa.pub >> /home/${dev_user}/.ssh/authorized_keys
chown -R ${dev_user}:${dev_user} /home/${dev_user}/.ssh


apt autoremove

echo "export download_site=${download_site}" >> /etc/profile

