#Switch
enable
conf t
vlan _NUNBER_
name _NAME_
exit

interface Fa0/1
switchport mode trunk
switchport trunk allowed vlan all
exit

interface Fa1/1
switchport mode access 
switchport access vlan _NUNBER_
exit

exit
exit


#Router
enable
conf t

#ACL
ip access-list extended 110
permit IP 192.168.1.0 0.0.0.255 any
permit TCP any any established
exit
ip access-list extended 111
permit IP 192.168.0.0 0.0.0.255 any
permit TCP any any established
permit TCP any host 192.168.1.1 eq www
exit

#interface
interface Fa0/0. _NUNBER_
encapsulation dot1Q _NUNBER_
ip address _ADDRESS_ _MASK_
ip helper-address _ADDRESS_
ip access-group 110 out
ip nat inside
exit
interface Fa0/0
no shutdown
exit

interface Fa1/0
ip address _ADDRESS_ _MASK_
ip access-group 111 out
ip nat outside
no shutdown
exit

#RIP
router rip
version 2
network _ADDRESS_
passive-interface Fa0/0
exit

#OSPF
router ospf 1
area 1 stub 
network _ADDRESS_ _WILDCARD_ area 1
passive-interface Fa0/0
exit

#Static & NAT
ip route _ADDRESSTO_ _MASK_ _ADDRESSUSE_
access-list 100 permit ip any any
ip nat inside source list 100 interface Fa0/0

exit
exit


#save
enable
write mem
exit
exit
exit

