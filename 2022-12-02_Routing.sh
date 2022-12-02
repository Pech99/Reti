
# PT Router 1
enable
configure
terminal
interface Fa0/0
ip address 192.168.20.1 255.255.255.252
exit
interface Fa1/0
ip address 192.168.1.254 255.255.255.0
exit
interface FastEthernet0/0
no shutdown
exit
interface FastEthernet1/0
no shutdown
exit
ip route 192.168.20.4 255.255.255.252 192.168.20.2
ip route 192.168.10.0 255.255.255.0 192.168.20.2
exit
exit
exit

# PT Router 2
enable
configure
terminal
interface Fa0/0
ip address 192.168.20.2 255.255.255.252
exit
interface Fa1/0
ip address 192.168.20.5 255.255.255.252
exit
interface FastEthernet0/0
no shutdown
exit
interface FastEthernet1/0
no shutdown
exit
ip route 192.168.1.0 255.255.255.0 192.168.20.1
ip route 192.168.10.0 255.255.255.0 192.168.20.6
exit
exit
exit

# PT Router 3
enable
configure
terminal
interface Fa0/0
ip address 192.168.20.6 255.255.255.252
exit
interface Fa1/0
ip address 192.168.10.254 255.255.255.0
exit
interface FastEthernet0/0
no shutdown
exit
interface FastEthernet1/0
no shutdown
exit
ip route 192.168.20.0 255.255.255.252 192.168.20.5
ip route 192.168.1.0 255.255.255.0 192.168.20.5
exit
exit
exit

# Save
enable
write mem
exit
exit


