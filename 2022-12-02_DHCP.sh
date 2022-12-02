
# PT Router
enable
configure
terminal
interface Fa4/0.10
encapsulation dot1Q 10
ip address 192.168.0.254 255.255.255.0
exit
interface Fa4/0.11
encapsulation dot1Q 11
ip address 192.168.1.254 255.255.255.0
exit
interface FastEthernet4/0
no shutdown
exit
exit
exit




# Switch
enable
configure
terminal
vlan 10
name 10_Blu
vlan 11
name 11_Pink 
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
switchport mode trunk
switchport trunk allowed vlan all
exit
interface FastEthernet 5/1
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


