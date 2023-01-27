import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class S_def_TCP_socket {
    public static void main(String[] args) {
        
        Socket sCli = new Socket();
        try {
            sCli.bind( new InetSocketAddress(InetAddress.getLocalHost(), 0));
        } catch (Exception e) {
            System.out.println("ERRORE!!!");
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Listen on port: ");
        System.out.println(sCli.getLocalPort());


        try {
            sCli.close();
        } catch (Exception e) {
            System.out.println("ERRORE!!!");
            System.out.println(e.getMessage());
        }
    }
}
