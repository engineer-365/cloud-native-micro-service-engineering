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

################################################################################
# install jenkins via ubuntu package manager
# see https://www.jenkins.io/doc/book/installing/linux/#debianubuntu
#
# This package installation will:
#
# - Setup Jenkins as a daemon launched on start. See /etc/init.d/jenkins for more details.
# - Create a ‘jenkins’ user to run this service.
# - Direct console log output to the file /var/log/jenkins/jenkins.log. Check this file if you are troubleshooting Jenkins.
# - Populate /etc/default/jenkins with configuration parameters for the launch, e.g JENKINS_HOME
# - Set Jenkins to listen on port 8080. Access this port with your browser to start configuration.
# - admin password is initialized in /var/lib/jenkins/secrets/initialAdminPassword

JENKINS_VER=2.269
JENKINS_PLUGIN_MGR_VER=2.5.0

# the nginx is used  to reverse-proxy as mirror ###############################

apt-get install -y nginx=1.14.0-0ubuntu1.7
cp /home/vagrant/post-install/etc/nginx/sites-enabled/* /etc/nginx/sites-enabled/
nginx -s reload

echo "export JENKINS_UC=http://updates.jenkins-ci.org" >> /etc/profile
echo "export JENKINS_URL=http://localhost:8080" >> /etc/profile
echo "export JENKINS_UC_DOWNLOAD=https://mirrors.tuna.tsinghua.edu.cn/jenkins/" >> /etc/profile

source /etc/profile

echo "192.168.50.20    docker.engineer365.org"  >> /etc/hosts
echo "192.168.50.20    nexus.engineer365.org"   >> /etc/hosts
echo "127.0.0.1        builder.engineer365.org" >> /etc/hosts
echo "127.0.0.1        mirrors.jenkins-ci.org"  >> /etc/hosts
echo "127.0.0.1        updates.jenkins-ci.org"  >> /etc/hosts

# offline install #############################################################

# download offline installer
wget --quiet ${download_site}/jenkins/${JENKINS_VER}/jenkins_${JENKINS_VER}_all.deb
apt install daemon
dpkg -i jenkins_${JENKINS_VER}_all.deb
rm jenkins_${JENKINS_VER}_all.deb

# wait jenkins to launch first time
sleep 30

cp /home/vagrant/post-install/etc/default/jenkins /etc/default/jenkins

# set up jenkins CLI
# see https://www.jenkins.io/doc/book/managing/cli/
cd /home/vagrant/jenkins-tool/

wget --quiet ${download_site}/jenkins/jenkins-plugin-manager-${JENKINS_PLUGIN_MGR_VER}.jar
ln -s /home/vagrant/jenkins-tool/jenkins-plugin-manager-${JENKINS_PLUGIN_MGR_VER}.jar /home/vagrant/jenkins-tool/jenkins-plugin-manager.jar

# optional - useful for jenkins adminstration work later
# wget --quiet ${JENKINS_URL}/jnlpJars/jenkins-cli.jar

# install plugin ##############################################################
# see https://github.com/jenkinsci/plugin-installation-manager-tool
# download plugin to /usr/share/jenkins/ref/plugins
# java -jar jenkins-plugin-manager-*.jar --war /your/path/to/jenkins.war --plugin-file /your/path/to/plugins.txt --plugins delivery-pipeline-plugin:1.3.2 deployit-plugin
java -jar /home/vagrant/jenkins-tool/jenkins-plugin-manager.jar \
     --verbose \
     --jenkins-update-center https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/ \
     --jenkins-experimental-update-center https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/experimental/ \
     --jenkins-plugin-info ${download_site}/jenkins/plugin-versions.json \
     --plugin-file /home/vagrant/jenkins-tool/plugins.yaml \
     --plugin-download-directory /var/lib/jenkins/plugins/ \
     --plugins delivery-pipeline-plugin:1.4.2 \
     deployit-plugin
#     --skip-failed-plugins
# enable jenkins to talk with docker ##########################################

usermod -aG sudo jenkins
usermod -aG docker jenkins
echo "jenkins  ALL=(ALL) NOPASSWD:/usr/bin/docker,/usr/local/bin/docker-compose" | tee /etc/sudoers.d/jenkins
# maven mirror in settings.xml
cp /home/vagrant/.m2/settings.xml /var/lib/jenkins/.m2

systemctl restart jenkins

# the plugin initialization is really time-consuming, due to download from internet
# need to do some investigation 
sleep 1200

JENKINS_USER_ID=$(id -u jenkins)
#chmod a+rw /var/run/docker.sock
mkdir -p /run/user/${JENKINS_USER_ID}
ln -s /var/run/docker.sock  /run/user/${JENKINS_USER_ID}/docker.sock



