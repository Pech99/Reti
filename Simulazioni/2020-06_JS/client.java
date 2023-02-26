import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import frame.frame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class client {
    
    private static Socket toSer;

    public static void main(String[] args) {
        frame f;
        String msg;
        
        char role;
        InetAddress add = null;
        int port = -1;

        for (String a : args) {
            if (a.startsWith("p")){
                try {
                    port = Integer.parseInt(a.substring(1));
                } catch (Exception e) {
                    System.out.print("Porta non valida");
                }
            } else if (a.startsWith("p")){
                try {
                    add = InetAddress.getByName(a.substring(1));
                } catch (Exception e) {
                    System.out.print("Indirizzo non valido");
                }
            }
        }

        if(port == -1){
            System.out.print("Inserire almeno il nuermo di porta con p<PORT>");
            return;
        }


        try {
            if(add==null){
                add = InetAddress.getLocalHost();
            }
            toSer = new Socket(add, port);
    
            //login
            System.out.print("Inserisci il tuo nome: ");
            send(frame.login(getString()));
    
            f = recive();
            if (f.isNACK()){
                System.out.println("Nome gia' registrato");
                return;
            } else if(!f.isACK() && !f.isNext()){
                System.out.println("Errore nel protocollo");
                return;
            }

            if(f.isNext()){
                role = 'A';
            } else {
                role = 'B';
            }
            
        } catch (Exception e) {
            System.out.print(e);
            return;
        }

        while (true){
            try {
                if(role=='A'){
                    System.out.println("Enter to start a conversation");
                    getString();
                    send(frame.ACK());
    
                    //invio il primo messaggio
                    System.out.print("- ");
                    msg = getString();
                    send(frame.message(msg));
                }
    
            } catch (Exception e) {
                System.out.print(e);
                return;
            }
    
            while (true){
                try {
    
                    //ricevo il messaggio
                    System.out.print("> ");
                    f = recive();
                    if(f.isExit()){
                        break;
                    } else if(!f.isMessage()){
                        System.out.println("\n\nErrore nel protocollo");
                        return;
                    }
                    System.out.println(f.getPay());
    
                    //invio il messaggio
                    System.out.print("- ");
                    msg = getString();
                    if(msg.equals(".")){
                        send(frame.exit());
                        break;
                    }
                    send(frame.message(msg));
    
                } catch (Exception e) {
                    System.out.print(e);
                    return;
                }
            }
        }
    }


    public static void send(frame f) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(toSer.getOutputStream());
        out.writeObject(f);
        //System.out.print(toSer.getInetAddress()+":"+toSer.getPort()+" <- "+f);
        return;
    }

    public static frame recive() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(toSer.getInputStream());
        frame f = (frame) in.readObject();
        //System.out.print(toSer.getInetAddress()+":"+toSer.getPort()+" -> "+f);
        return f;
    }

    public static String getString() throws IOException {
        int l = 50;
        byte[] buf = new byte[l];
        int n = System.in.read(buf, 0, l);
        return new String(buf, 0, n).trim();
    }
}
