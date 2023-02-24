import java.net.InetAddress;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import frame.frame;

//inizio:   9:20
//bozza:    10:55
//perf:     11:05
//fine:     11:10
//cons:     11:15

public class server {

    public static DatagramSocket sSoc;
    public static InetAddress toCli;
    public static int toCliP;

    public static void main(String[] args) {

        int cs = 0;
        int cw = 0;
        
        try {
            sSoc = new DatagramSocket(0);
            
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        
        System.out.print("Serve in ascolto sulla porta: p"+sSoc.getLocalPort());
        
        while (true) {
            try {
                frame f = recive();
                System.out.print("\n"+toCli+"/"+toCliP+" -> "+f);
                
                if(f.getPayload()=='p'){
                    frame r = new frame(f.getSeqNun(), 'k');
                    send(r);
                    cs++;
                    continue;
                }
                
                if(f.getPayload()=='c' || f.getPayload()=='f' || f.getPayload()=='s'){
                    int rand = Math.round(Math.round(Math.random()*10)) % 3;
                    char mossa;

                    if (rand == 0 ){ mossa = 'c'; } else 
                    if (rand == 1 ){ mossa = 'f'; } else
                                   { mossa = 's'; }
                    
                    frame r = new frame(f.getSeqNun(), mossa);
                    send(r);

                    System.out.print("\t=> mossa: "+mossa);
                    continue;
                }

                if(f.getPayload() =='y' || f.getPayload() =='i'){
                    if (f.getPayload()=='y'){
                        cw++;
                    }
                    
                    frame r = new frame(f.getSeqNun(), 'b');
                    send(r);

                    System.out.print("\t=> vinte: "+cw+"/"+cs);
                    continue;
                }
    
            } catch (Exception e) {
                System.out.println("\n\n"+e);
            }   
        }
    }

    public static void send(frame f) throws IOException{
        ByteArrayOutputStream a = new ByteArrayOutputStream(500);
        ObjectOutputStream out = new ObjectOutputStream(a);
        out.writeObject(f);

        DatagramPacket p = new DatagramPacket(a.toByteArray(), a.size(), toCli, toCliP);
        sSoc.send(p);
    }

    public static frame recive() throws IOException{
        int l = 80;
        byte[] buf = new byte[l];
        DatagramPacket p = new DatagramPacket(buf, l);

        sSoc.receive(p);
        toCli = p.getAddress();
        toCliP = p.getPort();

        ByteArrayInputStream a = new ByteArrayInputStream(p.getData(), 0, p.getLength());
        ObjectInputStream in = new ObjectInputStream(a);
        try {
            return (frame) in.readObject();
        } catch (Exception e) { }
        return null;
    }

}