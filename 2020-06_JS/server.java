import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import frame.frame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class server {
    
    private static ServerSocket ser;
    private static Socket toA;
    private static Socket toB;

    private static String nameA = "";
    private static String nameB = "";
    

    public static void main(String[] args) {
        
        frame f;

        try {
            ser = new ServerSocket(0);
            System.out.println("Listening on port: p"+ser.getLocalPort());
        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        while (true){
            try {
                toA = connect();
                System.out.println("Connected to: "+toA.getInetAddress()+":"+toA.getPort());
                
                toB = connect();
                System.out.println("Connected to: "+toB.getInetAddress()+":"+toB.getPort());

                send(toA, frame.next());
                send(toB, frame.ACK());

            } catch (Exception e) {
                System.out.println(e);
                continue;
            }

            while (true){
                try {
                    if(!recive(toA).isACK()){
                        System.out.println("Errore nel protocollo");
                        reset();
                        break;
                    }

                } catch (Exception e) {
                    System.out.println(e);
                    reset();
                    break;
                }

                while (true){
                    try {

                        f = recive(toA);
                        if(f.isExit()){
                            send(toB, frame.exit());
                            break;
                        } else if(!f.isMessage()){
                            System.out.println("Errore nel protocollo");
                            reset();
                            break;
                        }
                        send(toB, f);

                        f = recive(toB);
                        if(f.isExit()){
                            send(toA, frame.exit());
                            break;
                        } else if(!f.isMessage()){
                            System.out.println("Errore nel protocollo");
                            reset();
                            break;
                        }
                        send(toA, f);
        
                    } catch (Exception e) {
                        System.out.println(e);
                        reset();
                        break;
                    }
                }
            }
        }
    }

    public static void send(Socket to, frame f) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(to.getOutputStream());
        out.writeObject(f);
        System.out.println(to.getInetAddress()+":"+to.getPort()+" <- "+f);
        return;
    }

    public static frame recive(Socket from) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(from.getInputStream());
        frame f = (frame) in.readObject();
        System.out.println(from.getInetAddress()+":"+from.getPort()+" -> "+f);
        return f;
    }

    public static void reset(){
        try {
            nameA = "";
            nameB = "";
            
            toA.close();
            toB.close();
            
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }

    public static Socket connect(){
        Socket s;
        frame f;
        while (true){
            try {
                s = ser.accept();
                f = recive(s);

                if (!f.isLogin()){
                    continue;
                }

                if(f.getPay()==nameA || f.getPay()==nameB){
                    send(s, frame.NACK());
                    s.close();
                    continue;
                }

                return s;
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }
        }
    }

}