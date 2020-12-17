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

etc_hosts_file=$virtualbox_dir/etc_hosts
rm -f $etc_hosts_file



# ip and host
export readonly monitor_vip="192.168.50.10"
export readonly monitor_vhost="monitor.${org}"
echo "${monitor_vip}    ${monitor_vhost}" >> $etc_hosts_file
export readonly monitor1_ip="192.168.50.11"
export readonly monitor1_host="monitor1.${org}"
echo "${monitor1_ip}    ${monitor1_host}" >> $etc_hosts_file

export readonly store_vip="192.168.50.20"
export readonly store_vhost="store.${org}"
echo "${store_vip}    ${store_vhost}" >> $etc_hosts_file
export readonly store1_ip="192.168.50.21"
export readonly store1_host="store1.${org}"
echo "${store1_ip}    ${store1_host}" >> $etc_hosts_file
export readonly store2_ip="192.168.50.22"
export readonly store2_host="store2.${org}"
echo "${store2_ip}    ${store2_host}" >> $etc_hosts_file
export readonly store3_ip="192.168.50.23"
export readonly store3_host="store3.${org}"
echo "${store3_ip}    ${store3_host}" >> $etc_hosts_file
export readonly store4_ip="192.168.50.24"
export readonly store4_host="store4.${org}"
echo "${store4_ip}    ${store4_host}" >> $etc_hosts_file
export readonly store5_ip="192.168.50.25"
export readonly store5_host="store5.${org}"
echo "${store5_ip}    ${store5_host}" >> $etc_hosts_file

export readonly proxy_vip="192.168.50.100"
export readonly proxy_vhost="proxy.${org}"
echo "${proxy_vip}    ${proxy_vhost}" >> $etc_hosts_file
export readonly proxy1_ip="192.168.50.101"
export readonly proxy1_host="proxy1.${org}"
echo "${proxy1_ip}    ${proxy1_host}" >> $etc_hosts_file
export readonly proxy2_ip="192.168.50.102"
export readonly proxy2_host="proxy2.${org}"
echo "${proxy2_ip}    ${proxy2_host}" >> $etc_hosts_file

export readonly builder_vip="192.168.50.120"
export readonly builder_vhost="builder.${org}"
echo "${builder_vip}    ${builder_vhost}" >> $etc_hosts_file
export readonly builder1_ip="192.168.50.121"
export readonly builder1_host="builder1.${org}"
echo "${builder1_ip}    ${builder1_host}" >> $etc_hosts_file
export readonly builder2_ip="192.168.50.122"
export readonly builder2_host="builder2.${org}"
echo "${builder2_ip}    ${builder2_host}" >> $etc_hosts_file

export readonly k8s_master_vip="192.168.50.150"
export readonly k8s_master_vhost="k8s-master.${org}"
echo "${k8s_master_vip}    ${k8s_master_vhost}" >> $etc_hosts_file
export readonly k8s_master1_ip="192.168.50.151"
export readonly k8s_master1_host="k8s-master1.${org}"
echo "${k8s_master1_ip}    ${k8s_master1_host}" >> $etc_hosts_file
export readonly k8s_master2_ip="192.168.50.152"
export readonly k8s_master2_host="k8s-master2.${org}"
echo "${k8s_master2_ip}    ${k8s_master2_host}" >> $etc_hosts_file

export readonly k8s_node1_ip="192.168.50.171"
export readonly k8s_node1_host="k8s-node1.${org}"
echo "${k8s_node1_ip}    ${k8s_node1_host}" >> $etc_hosts_file
export readonly k8s_node2_ip="192.168.50.172"
export readonly k8s_node2_host="k8s-node2.${org}"
echo "${k8s_node2_ip}    ${k8s_node2_host}" >> $etc_hosts_file
export readonly k8s_node3_ip="192.168.50.173"
export readonly k8s_node3_host="k8s-node3.${org}"
echo "${k8s_node3_ip}    ${k8s_node3_host}" >> $etc_hosts_file
export readonly k8s_node4_ip="192.168.50.174"
export readonly k8s_node4_host="k8s-node4.${org}"
echo "${k8s_node4_ip}    ${k8s_node4_host}" >> $etc_hosts_file
export readonly k8s_node5_ip="192.168.50.175"
export readonly k8s_node5_host="k8s-node5.${org}"
echo "${k8s_node5_ip}    ${k8s_node5_host}" >> $etc_hosts_file

# aliases
export readonly mysql_router_vip=$proxy_vip
export readonly mysql_router_vhost="mysql-router.${org}"
echo "${mysql_router_vip}    ${mysql_router_vhost}" >> $etc_hosts_file
export readonly mysql_router1_ip=$proxy1_ip
export readonly mysql_router1_host="mysql-router1.${org}"
echo "${mysql_router1_ip}    ${mysql_router1_host}" >> $etc_hosts_file
export readonly mysql_router2_ip=$proxy2_ip
export readonly mysql_router2_host="mysql-router2.${org}"
echo "${mysql_router2_ip}    ${mysql_router2_host}" >> $etc_hosts_file
export readonly mysql_master1_ip=$store1_ip
export readonly mysql_master1_host="mysql-master1.${org}"
echo "${mysql_master1_ip}    ${mysql_master1_host}" >> $etc_hosts_file
export readonly mysql_master2_ip=$store2_ip
export readonly mysql_master2_host="mysql-master2.${org}"
echo "${mysql_master2_ip}    ${mysql_master2_host}" >> $etc_hosts_file
export readonly mysql_slave1_ip=$store3_ip
export readonly mysql_slave1_host="mysql-slave1.${org}"
echo "${mysql_slave1_ip}    ${mysql_slave1_host}" >> $etc_hosts_file
export readonly mysql_slave2_ip=$store4_ip
export readonly mysql_slave2_host="mysql-slave2.${org}"
echo "${mysql_slave2_ip}    ${mysql_slave2_host}" >> $etc_hosts_file
export readonly mysql_slave3_ip=$store5_ip
export readonly mysql_slave3_host="mysql-slave3.${org}"
echo "${mysql_slave3_ip}    ${mysql_slave3_host}" >> $etc_hosts_file


export readonly mongodb_router_vip=$proxy_vip
export readonly mongodb_router_vhost="mongodb-router.${org}"
echo "${mongodb_router_vip}    ${mongodb_router_vhost}" >> $etc_hosts_file
export readonly mongodb_router1_ip=$proxy1_ip
export readonly mongodb_router1_host="mongodb-router1.${org}"
echo "${mongodb_router1_ip}    ${mongodb_router1_host}" >> $etc_hosts_file
export readonly mongodb_router2_ip=$proxy2_ip
export readonly mongodb_router2_host="mongodb-router2.${org}"
echo "${mongodb_router2_ip}    ${mongodb_router2_host}" >> $etc_hosts_file

export readonly mongodb_master1_ip=$store1_ip
export readonly mongodb_master1_host="mongodb-master1.${org}"
echo "${mongodb_master1_ip}    ${mongodb_master1_host}" >> $etc_hosts_file
export readonly mongodb_slave0_ip=$store2_ip
export readonly mongodb_slave0_host="mongodb-slave0.${org}"
echo "${mongodb_slave0_ip}    ${mongodb_slave0_host}" >> $etc_hosts_file
export readonly mongodb_slave1_ip=$store3_ip
export readonly mongodb_slave1_host="mongodb-slave1.${org}"
echo "${mongodb_slave1_ip}    ${mongodb_slave1_host}" >> $etc_hosts_file
export readonly mongodb_slave2_ip=$store4_ip
export readonly mongodb_slave2_host="mongodb-slave2.${org}"
echo "${mongodb_slave2_ip}    ${mongodb_slave2_host}" >> $etc_hosts_file
export readonly mongodb_slave3_ip=$store5_ip
export readonly mongodb_slave3_host="mongodb-slave3.${org}"
echo "${mongodb_slave3_ip}    ${mongodb_slave3_host}" >> $etc_hosts_file

export readonly redis_vip=$proxy_vip
export readonly redis_vhost="redis.${org}"
echo "${redis_vip}    ${redis_vhost}" >> $etc_hosts_file
export readonly redis1_ip=$proxy1_ip
export readonly redis1_host="redis1.${org}"
echo "${redis1_ip}    ${redis1_host}" >> $etc_hosts_file
export readonly redis2_ip=$proxy2_ip
export readonly redis2_host="redis2.${org}"
echo "${redis2_ip}    ${redis2_host}" >> $etc_hosts_file

export readonly ingress_vip=$proxy_vip
export readonly ingress_vhost="ingress.${org}"
echo "${ingress_vip}    ${ingress_vhost}" >> $etc_hosts_file
export readonly ingress1_ip=$proxy1_ip
export readonly ingress1_host="ingress1.${org}"
echo "${ingress1_ip}    ${ingress1_host}" >> $etc_hosts_file
export readonly ingress2_ip=$proxy2_ip
export readonly ingress2_host="ingress2.${org}"
echo "${ingress2_ip}    ${ingress2_host}" >> $etc_hosts_file

export readonly kibana_vip=$proxy_vip
export readonly kibana_vhost="kibana.${org}"
echo "${kibana_vip}    ${kibana_vhost}" >> $etc_hosts_file
export readonly kibana1_ip=$proxy1_ip
export readonly kibana1_host="kibana1.${org}"
echo "${kibana1_ip}    ${kibana1_host}" >> $etc_hosts_file
export readonly elasticsearch_vip=$store_vip
export readonly elasticsearch_vhost="elasticsearch.${org}"
echo "${elasticsearch_vip}    ${elasticsearch_vhost}" >> $etc_hosts_file
export readonly elasticsearch1_ip=$store3_ip
export readonly elasticsearch1_host="elasticsearch1.${org}"
echo "${elasticsearch1_ip}    ${elasticsearch1_host}" >> $etc_hosts_file
export readonly elasticsearch2_ip=$store4_ip
export readonly elasticsearch2_host="elasticsearch2.${org}"
echo "${elasticsearch2_ip}    ${elasticsearch2_host}" >> $etc_hosts_file
export readonly logstash_vip=$store_vip
export readonly logstash_vhost="logstash.${org}"
echo "${logstash_vip}    ${logstash_vhost}" >> $etc_hosts_file
export readonly logstash1_ip=$store4_ip
export readonly logstash1_host="logstash1.${org}"
echo "${logstash1_ip}    ${logstash1_host}" >> $etc_hosts_file
export readonly logstash2_ip=$store5_ip
export readonly logstash2_host="logstash2.${org}"
echo "${logstash2_ip}    ${logstash2_host}" >> $etc_hosts_file

export readonly etcd_vip=$store_vip
export readonly etcd_vhost="etcd.${org}"
echo "${etcd_vip}    ${etcd_vhost}" >> $etc_hosts_file
export readonly etcd1_ip=$store3_ip
export readonly etcd1_host="etcd1.${org}"
echo "${etcd1_ip}    ${etcd1_host}" >> $etc_hosts_file
export readonly etcd2_ip=$store4_ip
export readonly etcd2_host="etcd2.${org}"
echo "${etcd2_ip}    ${etcd2_host}" >> $etc_hosts_file
export readonly etcd3_ip=$store5_ip
export readonly etcd3_host="etcd3.${org}"
echo "${etcd3_ip}    ${etcd3_host}" >> $etc_hosts_file

export readonly prometheus_vip=$store_vip
export readonly prometheus_vhost="prometheus.${org}"
echo "${prometheus_vip}    ${prometheus_vhost}" >> $etc_hosts_file
export readonly prometheus1_ip=$store1_ip
export readonly prometheus1_host="prometheus1.${org}"
echo "${prometheus1_ip}    ${prometheus1_host}" >> $etc_hosts_file
export readonly prometheus2_ip=$store2_ip
export readonly prometheus2_host="prometheus2.${org}"
echo "${prometheus2_ip}    ${prometheus2_host}" >> $etc_hosts_file

export readonly grafana_vip=$proxy_vip
export readonly grafana_vhost="grafana.${org}"
echo "${grafana_vip}    ${grafana_vhost}" >> $etc_hosts_file
export readonly grafana1_ip=$proxy1_ip
export readonly grafana1_host="grafana1.${org}"
echo "${grafana1_ip}    ${grafana1_host}" >> $etc_hosts_file


export readonly docker_vip=$store_vip
export readonly docker_vhost="docker.${org}"
echo "${docker_vip}    ${docker_vhost}" >> $etc_hosts_file
export readonly docker1_ip=$store4_ip
export readonly docker1_host="docker1.${org}"
echo "${docker1_ip}    ${docker1_host}" >> $etc_hosts_file
export readonly docker2_ip=$store5_ip
export readonly docker2_host="docker2.${org}"


export readonly sonar_vip=$store_vip
export readonly sonar_vhost="sonar.${org}"
echo "${sonar_vip}    ${sonar_vhost}" >> $etc_hosts_file
export readonly sonar1_ip=$store4_ip
export readonly sonar1_host="sonar1.${org}"
echo "${sonar1_ip}    ${sonar1_host}" >> $etc_hosts_file
export readonly sonar2_ip=$store5_ip
export readonly sonar2_host="sonar2.${org}"


export readonly nexus_vip=$store_vip
export readonly nexus_vhost="nexus.${org}"
echo "${nexus_vip}    ${nexus_vhost}" >> $etc_hosts_file
export readonly nexus1_ip=$store1_ip
export readonly nexus1_host="nexus1.${org}"
echo "${nexus1_ip}    ${nexus1_host}" >> $etc_hosts_file
export readonly nexus2_ip=$store2_ip
export readonly nexus2_host="nexus2.${org}"


export readonly npm_vip=$store_vip
export readonly npm_vhost="npm.${org}"
echo "${npm_vip}    ${npm_vhost}" >> $etc_hosts_file
export readonly npm1_ip=$store2_ip
export readonly npm1_host="npm1.${org}"
echo "${npm1_ip}    ${npm1_host}" >> $etc_hosts_file
export readonly npm2_ip=$store3_ip
export readonly npm2_host="npm2.${org}"

set -x
