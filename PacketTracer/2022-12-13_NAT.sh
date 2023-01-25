# blu (internet) 10.0.0.0/24
# arancio (Laboratorio) 192.168.100.0/24
# giallo (Uffici) 192.168.200.0/24

# inner pt-switch
enable
conf t
vlan 10
name 10_Orange_Lab
vlan 11
name 11_Yellow_Uff
exit
interface FastEthernet 0/1
switchport mode access
switchport access vlan 10
exit
interface FastEthernet 1/1
switchport mode access
switchport access vlan 10
exit
interface FastEthernet 2/1
switchport mode access
switchport access vlan 11
exit
interface FastEthernet 3/1
switchport mode access
switchport access vlan 11
exit
interface FastEthernet 4/1
switchport mode access
switchport access vlan 11
exit
interface FastEthernet 5/1
switchport mode trunk
switchport trunk allowed vlan all
exit
exit
exit

#router
enable
conf t
interface Fa1/0.10
encapsulation dot1Q 10
ip address 192.168.100.254 255.255.255.0
ip nat inside
exit
interface Fa1/0.11
encapsulation dot1Q 11
ip address 192.168.200.254 255.255.255.0
ip nat inside
exit
interface Fa1/0
no shutdown
exit
interface Fa0/0
ip address 10.0.0.254 255.255.255.0
ip nat outside
no shutdown
exit
access-list 110 permit ip any any
ip nat inside source list 110 interface Fa1/0
exit
exit
exit
exit



# save
enable
write mem
exit
exit
exit
