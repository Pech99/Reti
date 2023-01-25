import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class srv {
    public static void main(String[] args) {

        ServerSocket sSer;
        try {
            sSer = new ServerSocket(0);
        } catch (Exception e) {
            System.out.print("Impossibile avviare il server");
            return;
        }
        
        System.out.print("Server in ascolto su: "+sSer.getInetAddress()+":"+Integer.toString(sSer.getLocalPort()));


        while (true){



        }

    }
}

