import java.net.Socket;
import java.net.ServerSocket;

public class S_TCP_serv_mt {
    public static void main(String[] args) {
        
        ServerSocket serv;
        Socket tocli;
        Thread t;

        int port = 0;
        if (args.length == 1 && args[0].charAt(0)=='p'){
            try {
                port = Integer.parseInt(args[0].substring(1));
            } catch (Exception e) {
                System.out.println("Errore nel parcing della porta");
            }
        }

        try {
            serv = new ServerSocket(port);
            System.out.println("In ascolto sulla porta: "+serv.getLocalPort());
        } catch (Exception e) {
            System.out.println("Errore: "+e.getMessage());
            return;
        }

        try {
            while (true){
                tocli = serv.accept();
                System.out.println("Nuona connessione con: "+tocli.getInetAddress()+":"+tocli.getPort());
                t = new S_TCP_serv_func(tocli);
                t.start();
            }
        } catch (Exception e) {
            System.out.println("Errore: "+e.getMessage());
        }

    }

}

