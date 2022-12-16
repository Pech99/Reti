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
ip access-list extended 110
permit tcp any host 192.168.200.2 eq 80
permit tcp any any established
deny ip any any
exit
interface Fa1/0.10 
encapsulation dot1Q 10
ip address 192.168.100.254 255.255.255.0
exit
interface Fa1/0.11
encapsulation dot1Q 11
ip address 192.168.200.254 255.255.255.0
exit
interface Fa1/0
no shutdown
exit
interface Fa0/0
ip address 10.0.0.254 255.255.255.0
ip access-group 110 in
no shutdown
exit
exit
exit



# save
enable
write mem
exit
exit
exit
