https://askubuntu.com/questions/317338/how-can-i-increase-disk-size-on-a-vagrant-vm

https://github.com/sprotheroe/vagrant-disksize

vagrant plugin install vagrant-disksize

https://stackoverflow.com/questions/49822594/vagrant-how-to-specify-the-disk-size

https://www.vagrantup.com/docs/disks/usage


https://www.virtualbox.org/manual/ch08.html


There was an error while executing `VBoxManage`, a CLI used by Vagrant
for controlling VirtualBox. The command and stderr is shown below.

Command: ["storageattach", "b8379a9b-163c-4771-8b9b-250a1cc1807e", "--storagectl", "SATA Controller", "--port", "0", "--device", "0", "--type", "hdd", "--medium", "/home/qiangyt/VirtualBox VMs/vagrant_getting_started_default_1607330498044_81698/vagrant_primary.vdi", "--comment", "This disk is managed externally by Vagrant. Removing or adjusting settings could potentially cause issues with Vagrant."]

Stderr: VBoxManage: error: Could not find a controller named 'SATA Controller'


Solution: 
to find the name of your storage driver
VBoxManage list vms -l | grep -i storage



https://gist.github.com/leifg/4713995
https://stackoverflow.com/questions/21050496/vagrant-virtualbox-second-disk-path
https://gist.github.com/christopher-hopper/9755310
https://github.com/taigaio/taiga-vagrant/blob/master/Vagrantfile
https://stackoverflow.com/questions/36113556/vagrant-set-the-location-of-the-virtual-hard-drive-for-virtualbox


vagrant package --base ubuntu18
