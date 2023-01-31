import java.net.DatagramSocket;
import java.net.DatagramPacket;


/**
 * A: ACK           [A]
 * R: request       [R|T]
 * I: information   [I|S|R]
 * N: notifie       [N|S|T|R]
 */
public class server {
    public static void main(String[] args) {
        String[] tInfo = new String[10];

        int port = 0;

        DatagramSocket serv;

        if(args.length == 1 && args[0].charAt(0)=='p'){
            try {
                port = Integer.parseInt(args[0].substring(1));
            } catch (Exception e) {
                System.out.println("Errore nel parcyng della porta");
            }
        }

        try {
            serv = new DatagramSocket(port);
            System.out.println("Listen on: "+serv.getLocalPort());

        } catch (Exception e) {
            System.out.println("Errore: "+e.getMessage());
            return;
        }

        /**
         * R: request       [R|T]
         * N: notifie       [N|T|S|R]
         * I: information   [I|S|R]
         * A: ACK           [A]
         * E: non trovato   [E]
         */
        while (true){
            try {
                DatagramPacket dp = new DatagramPacket(new byte[50], 50);
                serv.receive(dp);
                System.out.print(dp.getAddress()+":"+dp.getPort()+" - "+new String(dp.getData()).trim()+"\n");

                if(dp.getData()[0]=='R'){
                    int t = Integer.parseInt(new String(dp.getData()).substring(1, 2));
                    byte[] buf = new byte[50];

                    if(tInfo[t]==null){
                        byte[] resp = new byte[10];
                        resp[0]='E';
                        dp.setData(resp, 0, 1);
                        serv.send(dp);
                    } else {
                        buf = ("I"+tInfo[t]).getBytes();
                        dp.setData(buf, 0, tInfo[t].length()+1 );
                        serv.send(dp);
                    }

                } else if(dp.getData()[0]=='N'){
                    int t = Integer.parseInt(new String(dp.getData()).substring(1, 2));
                    tInfo[t] = new String(dp.getData()).substring(2, dp.getLength()).trim();
                    
                    byte[] resp = new byte[10];
                    resp[0]='A';
                    dp.setData(resp, 0, 1);
                    serv.send(dp);

                } else {
                    continue;
                }
                
            } catch (Exception e) {
                System.out.println("\nErrore: "+e.getMessage());
            }
        }

    }
}
