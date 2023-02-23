import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;


/**
 * A: ACK           [A]
 * R: request       [R|T]
 * I: information   [I|S|R]
 * N: notifie       [N|S|T|R]
 */
public class utente {
    public static void main(String[] args) {
        int port = 62912;
        String add = "127.0.0.1";

        DatagramSocket utente;
        InetAddress serv;

        for (String s : args) {
            if(s.charAt(0)=='p'){
                try {
                    port = Integer.parseInt(s.substring(1));
                } catch (Exception e) {
                    System.out.println("Errore nel parcyng della porta");
                    return;
                }
            } else if(s.charAt(0)=='a') {
                add = s.substring(1);
            }
        }

        if(port==-1){
            System.out.println("Errore: inserire almeno la porta del server");
            return;
        }

        try {
            serv = InetAddress.getByName(add);
        } catch (Exception e) {
            System.out.println("Errore: "+e.getMessage());
            return;
        }

        try {
            utente = new DatagramSocket();
            utente.setSoTimeout(5000);
        } catch (Exception e) {
            System.out.println("Errore: "+e.getMessage());
            return;
        }

        while (true){
            try {
                byte[] data = new byte[50];
                byte[] buf = new byte[50];
                DatagramPacket dp = new DatagramPacket(data, 50);

                System.out.println("Codice del treno: ");
                System.in.read(buf);

                if (buf[0]=='.') {
                    return;
                }
                
                data[0]='R';
                data[1]=buf[0];
                
                dp.setData(data, 0, 2);
                dp.setAddress(serv);
                dp.setPort(port);
                utente.send(dp);

                utente.receive(dp);
                System.out.println(dp.getAddress()+":"+dp.getPort()+" - "+new String(dp.getData()));

                if(dp.getData()[0]== 'I'){
                    System.out.println("Stazione: "+new String(dp.getData()).charAt(1)+" - Ritardo: "+new String(dp.getData()).substring(1));
                } else if(dp.getData()[0]== 'E'){
                    System.out.println("Nessuna informazione disponibile");
                }

            } catch (Exception e) {
                System.out.println("Errore: "+e.getMessage());
            }
        }

    }
}
