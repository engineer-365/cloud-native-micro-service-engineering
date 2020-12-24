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

cd /tmp

# offline install harbor#######################################################
# https://github.com/goharbor/harbor
# https://goharbor.io/docs/2.0.0/install-config/
HARBOR_VER=2.1.2
HARBOR_INSTALLER=harbor-offline-installer-v${HARBOR_VER}.tgz

# https://github.com/goharbor/harbor/releases/download/v2.1.2/harbor-offline-installer-v2.1.2.tgz.asc

wget --quiet "${download_site}/harbor/${HARBOR_VER}/${HARBOR_INSTALLER}"
tar -C /opt/ -xzf ${HARBOR_INSTALLER}
cd /opt/harbor

mv /home/vagrant/files/harbor/harbor.yml ./

# Default installation without Notary, Clair, or Chart Repository Service
./install.sh

docker-compose down --remove-orphans
mv /home/vagrant/files/harbor/common/config/nginx/nginx.conf /opt/harbor/common/config/nginx/nginx.conf
docker-compose up -d

# TODO:
# create project/users via CLI, see https://goharbor.io/docs/2.0.0/install-config/configure-user-settings-cli/
