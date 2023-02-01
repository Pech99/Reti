64+32+16+8+4+2+1=127
32+16+8+4+2+1=63 
16+8+4+2+1=31
8+4+2+1=15
4+2+1=7
2+1=3  



# S1
enable
conf t
vlan 11
name 11_SP-A
exit
vlan 12
name 12_SP-S
exit
interface Fa1/1
switchport mode trunk
switchport trunk allowed vlan all
exit
interface Fa1/1
switchport mode access
switchport access vlan 11
exit
interface Fa1/1
switchport mode access
switchport access vlan 12
exit
exit
exit

# S2
enable
conf t
vlan 13
name 13_SP-U
exit
vlan 14
name 14_SP-V
exit
interface Fa1/1
switchport mode trunk
switchport trunk allowed vlan all
exit
interface Fa1/1
switchport mode access
switchport access vlan 13
exit
interface Fa1/1
switchport mode access
switchport access vlan 14
exit
exit
exit

# S3
enable
conf t
vlan 15
name 15_F-M
exit
vlan 16
name 16_F-P
exit
interface Fa1/1
switchport mode trunk
switchport trunk allowed vlan all
exit
interface Fa1/1
switchport mode access
switchport access vlan 15
exit
interface Fa1/1
switchport mode access
switchport access vlan 16
exit
exit
exit

# R1
enable
conf t
interface Fa0/0.11
encapsulation dot1Q 11
ip address 204.26.26.190 255.255.255.192
exit
interface Fa0/0.12
encapsulation dot1Q 12
ip address 204.26.27.54 255.255.255.248
exit
interface Fa1/0.13
encapsulation dot1Q 13
ip address 204.26.26.126 255.255.255.128
exit
interface Fa1/0.14
encapsulation dot1Q 14
ip address 204.26.27.30 255.255.255.224
ip helper-address 204.26.27.49
exit
interface Fa0/0
no shutdown
exit
interface Fa1/0
no shutdown
exit
interface Fa2/0
ip address 204.26.27.57 255.255.255.252
no shutdown
exit
interface Fa3/0
ip address 172.16.128.1 255.255.255.252
no shutdown
exit
router ospf 1
area 1 stub
network 204.26.26.190 0.0.0.63 area 1
network 204.26.27.54 0.0.0.7 area 1
network 204.26.26.126 0.0.0.127 area 1
network 204.26.27.30 0.0.0.63 area 1
network 204.26.27.57 0.0.0.3 area 1
network 172.16.128.1 0.0.0.3 area 1
passive-interface Fa0/0.11
passive-interface Fa0/0.12
passive-interface Fa1/0.13
passive-interface Fa1/0.14
exit
exit
exit

# R2
enable
conf t
interface Fa0/0
ip address 204.26.27.58 255.255.255.252
no shutdown
exit
interface Fa1/0
ip address 204.26.27.61 255.255.255.252
no shutdown
exit
router ospf 1
area 1 stub
network 204.26.27.58 0.0.0.3 area 1
network 204.26.27.61 0.0.0.3 area 1
exit
exit
exit

# R3
enable
conf t
interface Fa0/0.15
encapsulation dot1Q 15
ip address 204.26.27.46 255.255.255.240
exit
interface Fa0/0.16
encapsulation dot1Q 16
ip address 204.26.26.254 255.255.255.192
exit
interface Fa0/0
no shutdown
exit
interface Fa1/0
ip address 204.26.27.62 255.255.255.252
no shutdown
exit
router ospf 1
area 1 stub
network 204.26.27.46 0.0.0.63 area 1
network 204.26.26.254 0.0.0.31 area 1
network 204.26.27.62 0.0.0.3 area 1
passive-interface Fa0/0.15
passive-interface Fa0/0.16
exit
exit
exit

# R0
enable
conf t
interface Fa0/0
ip address 172.16.0.1 255.255.128.0
no shutdown
exit
interface Fa1/0
ip address 172.16.128.2 255.255.255.252
no shutdown
exit
router ospf 1
area 1 stub
network 172.16.0.0 0.0.127.255 area 1
network 172.16.128.2 0.0.0.3 area 1
passive-interface Fa0/0
exit
exit
exit

