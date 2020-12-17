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

set -x

if [[ ${virtualbox_dir} == '' ]]; then
    export readonly virtualbox_dir=$(cd "$(dirname $0)";pwd)
fi

export readonly org="engineer365.org"

export readonly download_site="https://download.engineer365.org:40443"
export readonly upload_site_for_scp="192.168.4.2:/hdd/engineer365/download"
export readonly box_download_path="${download_site}/vagrant/box"

# users
export readonly admin_user="admin"
export readonly dev_user="dev"

source ${virtualbox_dir}/ip_and_host.sh
source ${virtualbox_dir}/box_name.sh


function import_box() {
    set -e

    local box_name=$1
    local box_name_fq="${org}/${box_name}"
    local box_file="${box_name}.box"

    rm -f ${this_dir}/${box_file}
    wget "${box_download_path}/${org}/${box_file}"

    vagrant box add ${this_dir}/${box_file} --name ${box_name_fq} --force

    rm ${this_dir}/${box_file}
}

function build_box() {
    set -e

    local box_name=$1
    local box_name_fq="${org}/${box_name}"
    local box_file="${box_name}.box"

    up_box $1

    rm -f ${this_dir}/${box_file}
    vagrant package --output ${this_dir}/${box_file}

    vagrant box add ${this_dir}/${box_file} --name ${box_name_fq} --force
    vagrant destroy --force

    ## upload to the mirror site
    scp -P 30022 ${this_dir}/${box_file} ${upload_site_for_scp}/vagrant/box/${org}
    rm -f ${this_dir}/${box_file}
}


function up_box() {
    set -e

    local box_name=$1
    local box_name_fq="${org}/${box_name}"
    local box_file="${box_name}.box"

    vagrant up
}


function destroy_box() {
    set -e

    local box_name=$1
    local box_name_fq="${org}/${box_name}"
    local box_file="${box_name}.box"

    vagrant destroy
}

