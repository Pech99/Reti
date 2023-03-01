
#ISLab SW
enable
conf t
vlan 11
name 11_Azz
exit
vlan 12
name 12_AzzChi
exit
Interface Fa0/1
switchport mode trunk
switchport trunk allow vlan all
exit
Interface Fa1/1
switchport mode access
switchport access vlan 11
exit
Interface Fa2/1
switchport mode access
switchport access vlan 11
exit
Interface Fa3/1
switchport mode access
switchport access vlan 12
exit
Interface Fa4/1
switchport mode access
switchport access vlan 12
exit
exit
exit

#R0
enable
conf t
Interface Fa0/0
ip address 84.127.255.254 255.128.0.0
no shutdown
exit
Interface Fa1/0
ip address 84.128.0.1 255.255.255.252
no shutdown
exit
router rip
network 84.127.255.254
network 84.128.0.1
passive-interface Fa0/0
version 2
exit
exit
exit

#R1
enable
conf t
Interface Fa1/0
ip address 84.128.0.2 255.255.255.252
no shutdown
exit
Interface Fa2/0
ip address 197.104.0.82 255.255.255.252
no shutdown
exit
router rip
network 84.128.0.2
network 197.104.0.82
passive-interface Fa0/0
version 2
exit
exit
exit

#R2
enable
conf t
Interface Fa0/0
ip address 197.104.0.30 255.255.255.224
no shutdown
exit
Interface Fa1/0
ip address 197.104.0.81 255.255.255.252
no shutdown
exit
Interface Fa2/0
ip address 197.104.0.86 255.255.255.252
no shutdown
exit
Interface Fa3/0.11
encapsulation dot1Q 11
ip address 197.104.0.254 255.255.255.128
exit
Interface Fa3/0.12
encapsulation dot1Q 12
ip address 197.104.1.126 255.255.255.128
exit
Interface Fa3/0
no shutdown
exit
router rip
network 197.104.0.30
network 197.104.0.81
network 197.104.0.86
network 197.104.0.254
network 197.104.1.126
passive-interface Fa0/0
passive-interface Fa3/0.11
passive-interface Fa3/0.12
version 2
exit
exit
exit

#R3
enable
conf t
Interface Fa0/0
ip address 197.104.0.62 255.255.255.224
no shutdown
exit
Interface Fa1/0
ip address 197.104.0.85 255.255.255.252
no shutdown
exit
Interface Fa2/0
ip address 197.104.0.90 255.255.255.252
no shutdown
exit
router rip
network 197.104.0.62
network 197.104.0.85
network 197.104.0.90
passive-interface Fa0/0
version 2
exit
exit
exit

#R4
enable
conf t
Interface Fa1/0
ip address 197.104.0.89 255.255.255.252
no shutdown
exit
Interface Fa2/0
ip address 197.104.0.94 255.255.255.252
no shutdown
exit
router rip
network 197.104.0.89
network 197.104.0.94
version 2
exit
exit
exit

#R5
enable
conf t
Interface Fa0/0
ip address 197.104.0.78 255.255.255.240
no shutdown
exit
Interface Fa1/0
ip address 197.104.0.93 255.255.255.252
no shutdown
exit
router rip
network 197.104.0.78
network 197.104.0.93
passive-interface Fa0/0
version 2
exit
exit
exit


#save
enable
write mem
exit
exit
exit