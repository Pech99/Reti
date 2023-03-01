

# S1
enable
conf t
vlan 11
name 11_A1
exit
vlan 12
name 12_A2
exit
interface Fa0/1
switchport mode access
switchport access vlan 11
exit
interface Fa 1/1
switchport mode access
switchport access vlan 11
exit
interface Fa 2/1
switchport mode access
switchport access vlan 12
exit
interface Fa 3/1
switchport mode access
switchport access vlan 12
exit
interface Fa 9/1
switchport mode trunk
switchport trunk allow vlan all
exit
exit
exit


# S3
enable
conf t
vlan 13
name 13_C1
exit
vlan 14
name 14_C2
exit
interface Fa0/1
switchport mode access
switchport access vlan 13
exit
interface Fa 1/1
switchport mode access
switchport access vlan 13
exit
interface Fa 2/1
switchport mode access
switchport access vlan 14
exit
interface Fa 3/1
switchport mode access
switchport access vlan 14
exit
interface Fa 9/1
switchport mode trunk
switchport trunk allow vlan all
exit
exit
exit

# R1
enable
conf t
interface Fa0/0.11
encapsulation dot1Q 11
ip address 74.129.89.190 255.255.255.192
exit
interface Fa0/0.12
encapsulation dot1Q 12
ip address 74.129.89.126 255.255.255.128
exit
interface Fa0/0
no shutdown
exit
interface Fa1/0
ip address 74.129.90.97 255.255.255.252
no shutdown
exit
interface Fa2/0
ip address 74.129.90.102 255.255.255.252
no shutdown
exit
router rip
version 2
network 74.129.89.190 
network 74.129.89.126 
network 74.129.90.97 
network 74.129.90.102
passive-interface Fa0/0.11
passive-interface Fa0/0.12
exit
exit
exit


# R2
enable
conf t
interface Fa0/0
ip address 74.129.88.254 255.255.255.0
no shutdown
exit
interface Fa1/0
ip address 74.129.90.105 255.255.255.252
no shutdown
exit
interface Fa2/0
ip address 74.129.90.98 255.255.255.252
no shutdown
exit
router rip
version 2
network 74.129.88.254 
network 74.129.90.105 
network 74.129.90.98
passive-interface Fa0/0
exit
exit
exit


# R3
enable
conf t
interface Fa0/0.13
encapsulation dot1Q 13
ip address 74.129.90.62 255.255.255.192
exit
interface Fa0/0.14
encapsulation dot1Q 14
ip address 74.129.89.254 255.255.255.192
exit
interface Fa0/0
no shutdown
exit
interface Fa1/0
ip address 74.129.90.101 255.255.255.252
no shutdown
exit
interface Fa2/0
ip address 74.129.90.110 255.255.255.252
no shutdown
exit
router rip
version 2
network 74.129.90.62
network 74.129.89.254 
network 74.129.90.101 
network 74.129.90.110
passive-interface Fa0/0.13
passive-interface Fa0/0.14
exit
exit
exit

# R4
enable
conf t
interface Fa0/0
ip address 74.129.90.94 255.255.255.224
no shutdown
exit
interface Fa1/0
ip address 74.129.90.109 255.255.255.252
no shutdown
exit
interface Fa2/0
ip address 74.129.90.106 255.255.255.252
no shutdown
exit
router rip
version 2
network 74.129.90.94 
network 74.129.90.109 
network 74.129.90.106
passive-interface Fa0/0
exit
exit
exit




enable
write mem
exit
exit
exit









