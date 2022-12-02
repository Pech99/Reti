
# PT Router 3
enable
configure
terminal
interface Fa0/0.10
encapsulation dot1Q 10
ip address 192.168.0.254 255.255.255.0
exit
interface Fa0/0.11
encapsulation dot1Q 11
ip address 192.168.1.254 255.255.255.0
exit
interface Fa0/0.12
encapsulation dot1Q 12
ip address 192.168.2.254 255.255.255.0
exit
interface FastEthernet0/0
no shutdown
exit

interface Fa1/0.10
encapsulation dot1Q 10
ip address 192.168.0.254 255.255.255.0
exit
interface Fa1/0.11
encapsulation dot1Q 11
ip address 192.168.1.254 255.255.255.0
exit
interface Fa1/0.12
encapsulation dot1Q 12
ip address 192.168.2.254 255.255.255.0
exit
interface FastEthernet1/0
no shutdown
exit

interface Fa2/0.10
encapsulation dot1Q 10
ip address 192.168.0.254 255.255.255.0
exit
interface Fa2/0.11
encapsulation dot1Q 11
ip address 192.168.1.254 255.255.255.0
exit
interface Fa2/0.12
encapsulation dot1Q 12
ip address 192.168.2.254 255.255.255.0
exit
interface FastEthernet2/0
no shutdown
exit
exit



# Switch
enable
configure
terminal
vlan 10
name 10_Orange
vlan 11
name 11_Yellow
vlan 12
name 12_Blu
exit
interface FastEthernet 0/1
switchport mode access
switchport access vlan 10
exit
interface FastEthernet 1/1
switchport mode access
switchport access vlan 11
exit
interface FastEthernet 2/1
switchport mode access
switchport access vlan 12
exit
interface FastEthernet 3/1
switchport mode trunk
switchport trunk allowed vlan all
exit
exit
