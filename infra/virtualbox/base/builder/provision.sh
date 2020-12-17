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

set -e
set -x

#docker pull --quiet jenkinsci/blueocean:1.24.3
#docker pull --quiet nginx:1.19.5

# /root/tmp folder is used to save downloaded file.
# It will be cleared after provisioning is done.
mkdir -p /root/tmp
cd /root/tmp

# /opt is the installation folder for various of software

# wget --quiet "${download_site}/other_tools/putget/putget.linux"
# chmod a+x putget.linux
# mv putget.linux /usr/local/bin/putget

################################################################################
# install golang
GO_VER=1.14.13
GO_TGZ=go${GO_VER}.linux-amd64.tar.gz

wget --quiet "${download_site}/golang/${GO_VER}/${GO_TGZ}"
tar -C /usr/local/ -xzf ${GO_TGZ}

# TODO: take GOPROXY be optional
echo "export GOPROXY=https://goproxy.io" >> /etc/profile
echo 'export GOPATH=$HOME/go' >> /etc/profile
echo 'export PATH=$GOPATH/bin:$PATH:/usr/local/go/bin' >> /etc/profile
echo "export GO111MODULE=on" >> /etc/profile
echo '' >> /etc/profile

################################################################################
# install jdk 11, which is the default jdk
JDK11_VER=11.0.9.1+1
JDK11_DOCKER=openjdk:11.0.9.1-jdk
JDK11_TGZ=OpenJDK11U-jdk_x64_linux_hotspot_${JDK11_VER}.tar.gz

docker pull --quiet ${JDK11_DOCKER}

wget --quiet "${download_site}/jdk/11/${JDK11_TGZ}"
tar -C /opt/ -xzf ${JDK11_TGZ}
ln -s /opt/jdk-${JDK11_VER} /opt/jdk-11
ln -s /opt/jdk-11/bin/java /usr/bin/java
echo 'export JAVA_HOME=/opt/jdk-11' >> /etc/profile
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> /etc/profile
echo '' >> /etc/profile

################################################################################
# install jdk 8, not the default jdk
JDK8_VER=8u275-b01
JDK8_TGZ=OpenJDK8U-jdk_x64_linux_hotspot_${JDK8_VER}.tar.gz

wget --quiet "${download_site}/jdk/8/${JDK8_TGZ}"
tar -C /opt/ -xzf ${JDK8_TGZ}
ln -s /opt/jdk${JDK8_VER} /opt/jdk-8

################################################################################
# install maven
MAVEN_VER=3.6.3
MAVEN_TGZ=apache-maven-${MAVEN_VER}-bin.tar.gz
MAVEN_DOCKER=maven:${MAVEN_VER}-jdk-11

docker pull --quiet ${MAVEN_DOCKER}

wget --quiet "${download_site}/maven/${MAVEN_VER}/${MAVEN_TGZ}"
tar -C /opt/ -xzf ${MAVEN_TGZ}
ln -s /opt/apache-maven-${MAVEN_VER} /opt/maven
echo 'export MAVEN_HOME=/opt/maven' >> /etc/profile
echo 'export PATH=$MAVEN_HOME/bin:$PATH' >> /etc/profile
echo '' >> /etc/profile


################################################################################
# install jmeter
JMETER_VER=5.4
JMETER_TGZ=apache-jmeter-${JMETER_VER}.tgz

wget --quiet "${download_site}/jmeter/${JMETER_VER}/${JMETER_TGZ}"
tar -C /opt/ -xzf ${JMETER_TGZ}
ln -s /opt/apache-jmeter-${JMETER_VER} /opt/jmeter
echo 'export JMETER_HOME=/opt/jmeter' >> /etc/profile
echo 'export PATH=$JMETER_HOME/bin:$PATH' >> /etc/profile
echo '' >> /etc/profile


################################################################################
# install node.js
NODEJS_VER=14.15.1
NODEJS_TGZ=node-v${NODEJS_VER}-linux-x64.tar.gz

wget --quiet "${download_site}/node.js/14/${NODEJS_TGZ}"
tar -C /usr/local/lib/ -xzf ${NODEJS_TGZ}
ln -s /usr/local/lib/node-v${NODEJS_VER}-linux-x64 /usr/local/lib/node.js
echo 'export PATH=/usr/local/lib/node.js/bin:$PATH' >> /etc/profile
echo '' >> /etc/profile


################################################################################
# install gradle
GRADLE_VER=6.7.1
GRADLE_ZIP=gradle-${GRADLE_VER}-bin.zip

wget --quiet "${download_site}/gradle/${GRADLE_VER}/${GRADLE_ZIP}"
unzip -q ${GRADLE_ZIP} -d /opt/
ln -s /opt/gradle-${GRADLE_VER} /opt/gradle
echo 'export GRADLE_HOME=/opt/gradle' >> /etc/profile
echo 'export PATH=$GRADLE_HOME/bin:$PATH' >> /etc/profile
echo '' >> /etc/profile


################################################################################
# clear temporary files
rm -f /root/tmp/*
