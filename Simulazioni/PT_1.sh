Internet 
172.16.0.0/16 
  
Interne: 127.16.0.0 - 127.16.127.255/17 
R1 - R2: 127.16.128.0 - 127.16.128.3/30 
R2 - R3: 127.16.128.4 - 127.16.128.8/30 
  
Azienda 
204.26.27.0/24 
  
11_SP-A 6   --> 204.26.27.0 - 204.26.27.15/28 
12_SP-S 5   --> 204.26.27.16 - 204.26.27.23/29 
13_SP-U 60  --> 204.26.27.64 - 204.26.27.127/26 
14_SP-V 25  --> 204.26.27.32 - 204.26.27.63/27 
15_F-U 60   --> 204.26.27.128 - 204.26.27.191/26 
16_F-P 45   --> 204.26.27.192 - 204.26.27.255/26 

# R0
enable
conf t
interface Fa0/0.11
ip address 204.26.27.15



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




