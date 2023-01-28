import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.DatagramPacket;

public class S_UDP_serv {
    public static void main(String[] args) {
        
        DatagramSocket sock;
        DatagramPacket dp = new DatagramPacket(new byte[1000], 1000);

        int port = 0;
        if(args.length==1 && args[0].charAt(0)=='p'){
            try {
                port = Integer.parseInt(args[0].substring(1));
            } catch (Exception e) {
                System.out.println("Porta non valida");
                port = 0;
            }
        }
        
        try {
            sock = new DatagramSocket(port);
            System.out.println("Listen on: "+sock.getInetAddress()+":"+sock.getLocalPort());

            while(true){
                try {
                    sock.receive(dp);
                    if (dp.getData()[0]=='.'){
                        break; 
                    }
                    System.out.println("req from:"+dp.getAddress()+":"+dp.getPort()+"\n");
                    sock.send(dp);
                    
                } catch (Exception e) {
                    System.out.println("Errore connessione: "+e.getMessage());
                }
            }
            
            sock.close();

        } catch (Exception e) {
            System.out.println("ERRORE!!!");
            System.out.println(e.getMessage());
            return;
        }

    }
}
