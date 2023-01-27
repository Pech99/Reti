10_Rossa:   192.168.0.X 255.255.255.0
11_Blu:     192.168.1.X 255.255.255.0
Gialla:     10.X.X.X    255.0.0.0

# S0 e S1
enable
conf t
vlan 10
name 10_Rossa
exit
vlan 11
name 11_Blu
exit
interface Fa0/1
switchport mode access
switchport access vlan 10
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
interface Fa4/1
switchport mode trunk
switchport trunk allowed vlan all
exit
interface Fa5/1
switchport mode trunk
switchport trunk allowed vlan all
exit
exit
exit

# R
enable
conf t
ip access-list extended 110
permit IP 192.168.1.0 0.0.0.255 any
permit TCP any any established
exit
ip access-list extended 111
permit IP 192.168.0.0 0.0.0.255 any
permit TCP any any established
permit TCP any host 192.168.1.1 eq www
exit
interface Fa4/0.10
encapsulation dot1Q 10
ip address 192.168.0.254 255.255.255.0
ip access-group 110 out
exit
interface Fa4/0.11
encapsulation dot1Q 11
ip address 192.168.1.254 255.255.255.0
ip helper-address 192.168.0.1
ip access-group 111 out
exit
interface Fa4/0
no shutdown
exit
interface Fa5/0
ip address 10.0.0.10 255.0.0.0
no shutdown
exit
exit
exit


# save
enable
write mem
exit
exit





