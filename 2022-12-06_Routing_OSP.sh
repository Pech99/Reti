# Gialla: 192.168.0.0/24 
# BaseAddres:      192.168.0.0
# BroadCast:       192.168.0.255
# Getaway:         192.168.0.1
# Primo IP:        192.168.0.2
# Ultimo IP:       192.168.0.254
# Net Mask:        255.255.255.0

# azzurra: 192.168.1.0/24 
# BaseAddres:      192.168.1.0
# BroadCast:       192.168.1.255
# Getaway:         192.168.1.1
# Primo IP:        192.168.1.2
# Ultimo IP:       192.168.1.254
# Net Mask:        255.255.255.0

# Verde: 192.168.2.0/24 
# BaseAddres:      192.168.2.0
# BroadCast:       192.168.2.255
# Getaway:         192.168.2.1
# Primo IP:        192.168.2.2
# Ultimo IP:       192.168.2.254
# Net Mask:        255.255.255.0

# Rossa: 192.168.20.0/30 
# BaseAddres:      192.168.20.0
# BroadCast:       192.168.20.3
# Primo IP:        192.168.20.1
# Ultimo IP:       192.168.20.2
# Net Mask:        255.255.255.252

# Arancio: 192.168.20.4/30 
# BaseAddres:      192.168.20.4
# BroadCast:       192.168.20.7
# Primo IP:        192.168.20.5
# Ultimo IP:       192.168.20.6
# Net Mask:        255.255.255.252

# Blu: 192.168.20.8/30 
# BaseAddres:      192.168.20.8
# BroadCast:       192.168.20.11
# Primo IP:        192.168.20.9
# Ultimo IP:       192.168.20.10
# Net Mask:        255.255.255.252

# PT Router 0
enable
conf term
interface Fa0/0
ip address 192.168.0.1 255.255.255.0
exit
interface Fa4/0
ip address 192.168.20.1 255.255.255.252
exit
interface Fa5/0
ip address 192.168.20.5 255.255.255.252
exit
interface FastEthernet0/0
no shutdown
exit
interface FastEthernet4/0
no shutdown
exit
interface FastEthernet5/0
no shutdown
exit
router ospf 1
area 1 stud
network 192.168.0.0 0.0.0.255  area 1
network 192.168.20.0 0.0.0.3 area 1
network 192.168.20.4 0.0.0.3 area 1
exit
exit
exit

# il comando passive va applicatto ad ogni sub interfaccia

# PT Router 1
enable
conf term
interface Fa0/0
ip address 192.168.1.1 255.255.255.0
exit
interface Fa4/0
ip address 192.168.20.9 255.255.255.252
exit
interface Fa5/0
ip address 192.168.20.2 255.255.255.252
exit
interface FastEthernet0/0
no shutdown
exit
interface FastEthernet4/0
no shutdown
exit
interface FastEthernet5/0
no shutdown
exit
router ospf 1
area 1 stud
network 192.168.1.0 0.0.0.255 area 1
network 192.168.20.0 0.0.0.3 area 1
network 192.168.20.8 0.0.0.3 area 1
exit
exit
exit

# PT Router 2
enable
conf term
interface Fa0/0
ip address 192.168.2.1 255.255.255.0
exit
interface Fa4/0
ip address 192.168.20.6 255.255.255.252
exit
interface Fa5/0
ip address 192.168.20.10 255.255.255.252
exit
interface FastEthernet0/0
no shutdown
exit
interface FastEthernet4/0
no shutdown
exit
interface FastEthernet5/0
no shutdown
exit
router ospf 1
area 1 stud
network 192.168.2.0 0.0.0.255 area 1
network 192.168.20.4 0.0.0.3 area 1
network 192.168.20.8 0.0.0.3 area 1
exit
exit
exit



# Save
enable
write mem
exit
exit


