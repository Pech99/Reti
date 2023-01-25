import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class cli {
    public static void main(String[] args) {
        //DatagramSocket sCli = new DatagramSocket(0, null);
        
        InetAddress host;
        int port;
        
        
        if (args.length == 2) {
            try {
                host = InetAddress.getByName(args[0]);
            } catch (Exception e) {
                System.out.print("Host non valido");
                return;
            }
            port = Integer.parseInt(args[1]);
            if (port<=0 || port > 36363){
                System.out.print("Porta non valida");
                return;
            }

        } else {
            try {
                host = InetAddress.getLocalHost();
            } catch (Exception e) {
                System.out.print("Impossibile ottenere un IP");
                return;
            }
            port = 0;
        }
        
        InetSocketAddress isa = new InetSocketAddress(host, port);
        System.out.print("Server in ascolto su: "+isa.getAddress()+":"+Integer.toString(isa.getPort()));

        



            




    }

}







