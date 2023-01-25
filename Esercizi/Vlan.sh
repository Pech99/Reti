10_Giallo: 192.168.0.X GA: 192.168.0.254
11_Verde:  192.168.1.X GA: 192.168.1.254

# Router (R1)
enable
conf t
hostname R1
interface Fa4/0.10
ip address 192.168.0.254 255.255.255.0
encapsulation dot1Q 10
exit
interface Fa4/0.11
ip address 192.168.1.254 255.255.255.0
encapsulation dot1Q 11
exit
interface Fa4/0
no shutdown
exit
exit
exit


# Switch 1 e 2
enable
conf t
vlan 10
name 10_Giallo
exit
vlan 11
name 11_Verde
exit
interface Fa0/1
switchport mode trunk
switchport trunk allowed vlan all
exit
interface Fa1/1
switchport mode access 
switchport access vlan 10
exit
interface Fa2/1
switchport mode access 
switchport access vlan 11
exit
interface Fa3/1
switchport mode access 
switchport access vlan 11
exit
exit
exit


# Switch 1
enable
conf t
interface Fa4/1
switchport mode trunk
switchport trunk allowed vlan all
exit
exit
exit

# Save
enable
write mem
exit
exit
exit



