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

set +x

if [[ ${virtualbox_dir} == '' ]]; then    
    export readonly virtualbox_dir=$(cd "$(dirname $0)";pwd)
fi

# base boxes
export readonly box_name____hashicorp_ubuntu_bionic="ubuntu-bionic-20201201"
export readonly box_name____org_ubuntu18="ubuntu18-20201211"
export readonly box_name____org_monitor="monitor-20201211"
export readonly box_name____org_store="store-20201211"
export readonly box_name____org_proxy="proxy-20201211"
export readonly box_name____org_builder="builder-20201211"
export readonly box_name____org_k8s_master="k8s-master-20201211"
export readonly box_name____org_k8s_node="k8s-node-20201211"

# final boxes
export readonly box_name____org_monitor0="monitor0-20201211"

export readonly box_name____org_store1="store1-20201211"
export readonly box_name____org_store2="store2-20201211"
export readonly box_name____org_store3="store3-20201211"
export readonly box_name____org_store4="store4-20201211"
export readonly box_name____org_store5="store5-20201211"

export readonly box_name____org_proxy1="proxy1-20201211"
export readonly box_name____org_proxy2="proxy2-20201211"

export readonly box_name____org_builder1="builder1-20201211"
export readonly box_name____org_builder2="builder2-20201211"

export readonly box_name____org_k8s_master1="k8s-master1-20201211"
export readonly box_name____org_k8s_master2="k8s-master2-20201211"

export readonly box_name____org_k8s_node1="k8s-node1-20201211"
export readonly box_name____org_k8s_node2="k8s-node2-20201211"
export readonly box_name____org_k8s_node3="k8s-node3-20201211"
export readonly box_name____org_k8s_node4="k8s-node4-20201211"
export readonly box_name____org_k8s_node5="k8s-node5-20201211"

set -x
