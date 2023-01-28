import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.InetSocketAddress;

public class S_UDP_cli {
    public static void main(String[] args) {
        
        DatagramSocket sock;
        byte[] buf = new byte[1000];
        int l;

        int port = -1;
        String addr = "127.0.0.1";

        for (String s : args) {
            if (s.charAt(0)=='p'){
                try {
                    port = Integer.parseInt(s.substring(1));
                } catch (Exception e) {
                    System.out.println("Porta non valida");
                    return;
                }
            } else if (s.charAt(0)=='a'){
                addr = s.substring(1);
            } else {
                System.out.println("Argomenti non validi");
                return;
            }
        }

        if(port==-1){
            System.out.println("Inserire almeno una porta valida");
        }

        
        try {
            sock = new DatagramSocket();
            InetSocketAddress da = new InetSocketAddress(addr, port);
            System.out.print("Connected to: "+da.getAddress()+":"+da.getPort()+"\n");

            while (true){
                l = System.in.read(buf, 0, buf.length);

                
                DatagramPacket dp = new DatagramPacket(buf, l);
                dp.setSocketAddress(da);
                sock.send(dp);
                
                if (buf[0]=='.'){
                    break; 
                }

                sock.receive(dp);
                System.out.println("> "+new String(dp.getData(), 0, dp.getLength()));
            }

            sock.close();

        } catch (Exception e) {
            System.out.println("ERRORE!!!");
            System.out.println(e.getMessage());
            return;
        }



    }
}
