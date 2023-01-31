Internet 
172.16.0.0/16 
  
Interne: 172.16.0.0 - 172.16.127.255/17  255.255.255.128
R0 - R1: 172.16.128.0 - 172.16.128.3/30 
R1 - R2: 172.16.128.4 - 172.16.128.8/30 
  
Azienda 
204.26.27.0/24 
  
11_SP-A 6   --> 204.26.27.0 - 204.26.27.15/28       255.255.255.240
12_SP-S 5   --> 204.26.27.16 - 204.26.27.23/29      255.255.255.248
13_SP-U 60  --> 204.26.27.64 - 204.26.27.127/26     255.255.255.192
14_SP-V 25  --> 204.26.27.32 - 204.26.27.63/27      255.255.255.224
15_F-U 60   --> 204.26.27.128 - 204.26.27.191/26    255.255.255.192
16_F-P 45   --> 204.26.27.192 - 204.26.27.255/26    255.255.255.192

# R0
enable
conf t
interface Fa0/0.11
encapsulation dot1Q 11
ip address 204.26.27.14 255.255.255.240
exit
interface Fa0/0.12
encapsulation dot1Q 12
ip address 204.26.27.22 255.255.255.248
exit
interface Fa0/0.13
encapsulation dot1Q 13
ip address 204.26.27.126 255.255.255.192
exit
interface Fa0/0.14
encapsulation dot1Q 14
ip address 204.26.27.62 255.255.255.224
exit
interface Fa0/0
no shutdown
exit
interface Fa1/0
ip address 172.16.128.1 255.255.255.252
no shutdown
exit
router ospf 1
area 1 stub
network 204.26.27.14 0.0.0.15 area 1
network 204.26.27.22 0.0.0.7 area 1
network 204.26.27.126 0.0.0.63 area 1
network 204.26.27.62 0.0.0.30 area 1
network 172.16.128.1 0.0.0.127 area 1
passive-interface Fa0/0.11
passive-interface Fa0/0.12
passive-interface Fa0/0.13
passive-interface Fa0/0.14
exit
exit
exit

# R1
enable
conf t
interface Fa0/0
ip address 172.16.127.254 255.255.255.128
no shutdown
exit
interface Fa1/0
ip address 172.16.128.2 255.255.255.252
no shutdown
exit
interface Fa2/0
ip address 172.16.128.5 255.255.255.252
no shutdown
exit
router ospf 1
area 1 stub
network 172.16.127.254 0.0.127.255 area 1
network 172.16.128.2 0.0.0.3 area 1
network 172.16.128.5 0.0.0.3 area 1
passive-interface Fa0/0
exit
exit
exit

# R2
enable
conf t
interface Fa0/0.15
encapsulation dot1Q 15
ip address 204.26.27.190 255.255.255.192
exit
interface Fa0/0.16
encapsulation dot1Q 16
ip address 204.26.27.254 255.255.255.192
exit
interface Fa0/0
no shutdown
exit
interface Fa1/0
ip address 172.16.128.6 255.255.255.252
no shutdown
exit
router ospf 1
area 1 stub
network 204.26.27.190 0.0.0.127 area 1
network 204.26.27.254 0.0.0.127 area 1
network 172.16.128.6 0.0.0.3 area 1
passive-interface Fa0/0.15
passive-interface Fa0/0.16
exit
exit
exit


# S0
enable
conf t
vlan 11
name 11_SP-A
exit
vlan 12
name 12_SP-S
exit
vlan 13
name 13_SP-U
exit
vlan 14
name 14_SP-V
exit
interface Fa0/1
switchport mode trunk
switchport trunk allowed vlan all
exit
interface Fa1/1
switchport mode access
switchport access vlan 11
exit
interface Fa2/1
switchport mode access
switchport access vlan 12
exit
interface Fa3/1
switchport mode access
switchport access vlan 13
exit
interface Fa4/1
switchport mode access
switchport access vlan 14
exit
exit
exit

# S1
enable
conf t
vlan 15
name 15_F-U
exit
vlan 16
name 16_F-P
exit
interface Fa0/1
switchport mode trunk
switchport trunk allowed vlan all
exit
interface Fa1/1
switchport mode access
switchport access vlan 15
exit
interface Fa2/1
switchport mode access
switchport access vlan 16
exit
exit
exit


# save
enable
write mem
exit
exit
exit




