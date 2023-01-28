
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class S_TCP_serv_conc {
    public static void main(String[] args) {
        ArrayList<Socket> cli = new ArrayList<Socket>();
        ServerSocket sServ;
        Socket newCli;

        byte[] buf = new byte[1000];
        int l = 0;    

        //Inizializzaizone dei parametri
        int port = 0;
        if(args.length==1 && args[0].charAt(0)=='p'){
            try {
                port = Integer.parseInt(args[0].substring(1));
            } catch (Exception e) {
                System.out.println("Porta non valida");
                port = 0;
            }
        }

        //Creazione della server socket
        try {
            sServ = new ServerSocket(port);
            sServ.setSoTimeout(10);
            System.out.println("Listen on: "+sServ.getInetAddress()+":"+sServ.getLocalPort());

        } catch (Exception e) {
            System.out.println("ERRORE!!!");
            System.out.println(e.getMessage());
            return;
        }

        //Ciclo principale
        while (true){
            try {
                newCli = sServ.accept();
                newCli.setSoTimeout(1);
                cli.add(newCli);
                System.out.println("Nuova Connessione: "+newCli.getInetAddress()+":"+newCli.getPort());
            } catch (Exception e) { }

            for (int i = 0; i<cli.size(); i++) {
                //provo a leggere, se finisce il tempo, passo al prossimo, se esplode, elimino la connessione.
                //se la lettura è andata a buon finie, rispondo
                try {
                    l = cli.get(i).getInputStream().read(buf);
                    if (buf[0]=='S'){
                        cli.get(i).getOutputStream().write(buf, 0, l);
                    } else if (buf[0]=='E'){
                        System.out.println("Connessione chiusa con: "+cli.get(i).getInetAddress()+":"+cli.get(i).getPort());
                        cli.get(i).close();
                        cli.remove(i);
                    }
                    
                } catch (Exception e) {
                    if (e.getMessage() != "Read timed out"){
                        cli.remove(i);
                        System.out.println("Errore su una connessione: "+e.getMessage());
                    }
                }
            }
            
        }
    }
}
