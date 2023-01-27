
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class S_TCP_serv {
    public static void main(String[] args) {
        ServerSocket sServ;
        Socket toCli;

        byte[] buf = new byte[1000];
        int l;    

        // inizializzaizone della porta
        int port = 0;
        if(args.length==1){
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                port = 0;
            }
        }

        try {
            //creazione della server socket
            sServ = new ServerSocket(port);

            System.out.println("Listen on: "+sServ.getInetAddress()+":"+sServ.getLocalPort());

            while (true){
                toCli = sServ.accept();
                System.out.print("Connessione Stabilita");
                System.out.println(" - da: "+toCli.getInetAddress()+":"+toCli.getPort());

                try {
                    while (true){
                        l = toCli.getInputStream().read(buf);
                        if (buf[0]=='S'){
                            toCli.getOutputStream().write(buf, 0, l);
        
                        } else if (buf[0]=='E'){
                            break;
                        }
                    }
        
                    toCli.close();
                    System.out.println("Connessione Chiusa");
                    
                } catch (Exception e) {
                    System.out.println("Errore sulla Connessione");

                }
            }
            
            //sServ.close();

        } catch (Exception e) {
            System.out.println("ERRORE!!!");
            System.out.println(e.getMessage());
            return;
        }
    }
}
