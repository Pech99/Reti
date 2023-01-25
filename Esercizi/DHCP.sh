10_red: 10.0.10.x
11_blu: 10.0.11.x
12_grn: 10.0.12.x

# Router
enable
conf t
interface Fa0/0.10
encapsulation dot1Q 10
ip address 10.0.10.254 255.255.255.0
ip helper-address 10.0.12.1
exit
interface Fa0/0.11
encapsulation dot1Q 11
ip address 10.0.11.254 255.255.255.0
ip helper-address 10.0.12.1
exit
interface Fa0/0
no shutdown
exit
interface Fa1/0
ip address 10.0.12.254 255.255.255.0
no shutdown
exit
exit
exit


# Switch 1 e 2
enable
conf t
vlan 10
name 10_red
exit
vlan 11
name 11_blu
exit
vlan 12
name 12_grn
exit
interface Fa0/1
switchport mode trunk
switchport trunk allowed vlan all
exit
interface Fa1/1
switchport mode trunk
switchport trunk allowed vlan all
exit
interface Fa2/1
switchport mode access
switchport access vlan 10
exit
interface Fa3/1
switchport mode access
switchport access vlan 11
exit
exit

# save
enable
write mem
exit
exit




