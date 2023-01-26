143.251.103.192/27

SubnetMask:	255.255.255.252
Rosso:	110|0 00|00 --> 143.251.103.192
Giallo:	110|0 01|00 --> 143.251.103.196
Blu:	110|0 10|00 --> 143.251.103.200

Aranc:	110|0 11|00 --> 143.251.103.204
Verde:	110|1 00|00 --> 143.251.103.208
Viola:	110|1 01|00 --> 143.251.103.212

#Router 0
enable
conf t
interface Fa0/0
ip address 143.251.103.214 255.255.255.252
no shutdown
exit
interface Fa4/0
ip address 143.251.103.201 255.255.255.252
no shutdown
exit
interface Fa5/0
ip address 143.251.103.194 255.255.255.252
no shutdown
exit
exit
exit

#Router 1
enable
conf t
interface Fa0/0
ip address 143.251.103.210 255.255.255.252
no shutdown
exit
interface Fa4/0
ip address 143.251.103.197 255.255.255.252
no shutdown
exit
interface Fa5/0
ip address 143.251.103.202 255.255.255.252
no shutdown
exit
exit
exit

#Router 2
enable
conf t
interface Fa0/0
ip address 143.251.103.206 255.255.255.252
no shutdown
exit
interface Fa4/0
ip address 143.251.103.193 255.255.255.252
no shutdown
exit
interface Fa5/0
ip address 143.251.103.198 255.255.255.252
no shutdown
exit
exit
exit