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

export readonly virtualbox_dir=$(dirname $script_dir)

source ${script_dir}/vars.sh
source ${script_dir}/ip_and_host.sh
source ${script_dir}/box_name.sh


function up_vm() {
    import_box $1
    vagrant up
}

function import_raw_box() {
    local org_name=$1    
    local box_name=$2
    local box_name_fq="${org_name}/${box_name}"
    
    vagrant box list | grep "${box_name_fq}" | wc -l > ${box_name}.box.counter
    if [ "$(cat ${box_name}.box.counter)" != '1' ]; then
        local box_file="${box_name}.box"
        rm -f ${box_file}
        
        wget --quiet "${box_download_path}/${org_name}/${box_file}"
        
        vagrant box add ${box_file} --name "${box_name_fq}" --force
        rm ${box_file}
    fi

    rm ${box_name}.box.counter
}


function import_box() {
    local box_name=$1
    local box_name_fq="${org}/${box_name}"

    import_raw_box ${org} ${box_name}
}


function build_box() {
    set -e

    local box_name=$1
    local box_name_fq="${org}/${box_name}"
    local box_file="${box_name}.box"

    vagrant up

    rm -f ${this_dir}/${box_file}
    vagrant package --output ${this_dir}/${box_file}

    vagrant box add ${this_dir}/${box_file} --name ${box_name_fq} --force
    vagrant destroy --force
    rm -rf ${this_dir}/.vagrant

    # upload to the mirror site
    scp -P 30022 ${this_dir}/${box_file} ${upload_site_for_scp}/vagrant/box/${org}
    rm -f ${this_dir}/${box_file}
}


