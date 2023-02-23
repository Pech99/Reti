import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;


/**
 * A: ACK           [A]
 * R: request       [R|T]
 * I: information   [I|S|R]
 * N: notifie       [N|T|S|R]
 */
public class stazione {
    public static void main(String[] args) {
        int port = -1;
        String add = "127.0.0.1";
        byte[] data = new byte[50];
        byte[] buf = new byte[50];
        byte sCode = 's';


        DatagramPacket dp = new DatagramPacket(new byte[50], 50);
        DatagramSocket staz;
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
            } else if(s.charAt(0)=='s'){
                sCode = s.getBytes()[1];
            }
        }

        if(port==-1){
            System.out.println("Errore: inserire almeno la porta del server");
            System.out.println("a<server> p<portaServer> s<nomeStazioen>");
            return;
        }

        try {
            serv = InetAddress.getByName(add);
        } catch (Exception e) {
            System.out.println("Errore: "+e.getMessage());
            return;
        }

        try {
            staz = new DatagramSocket();
            staz.setSoTimeout(5000);
        } catch (Exception e) {
            System.out.println("Errore: "+e.getMessage());
            return;
        }

        while (true){
            try {
                System.out.println("Codice del treno: ");
                System.in.read(buf);
                
                if (buf[0]=='.') {
                    return;
                }

                System.out.println("Ritardo: ");
                System.in.read(data, 3, data.length-3);
                
                
                data[0]='N';
                data[1]=buf[0];
                data[2]=sCode;

                dp.setData(data);
                dp.setAddress(serv);
                dp.setPort(port);
                staz.send(dp);

                staz.receive(dp);
                System.out.println(dp.getAddress()+":"+dp.getPort()+" - "+new String(dp.getData(), 0, dp.getLength()));
                if(dp.getData()[0] == 'A'){
                    System.out.println("Recezione avvenuta correttamente");
                }

            } catch (Exception e) {
                System.out.println("Errore: "+e.getMessage());
            }
        }

    }
}
