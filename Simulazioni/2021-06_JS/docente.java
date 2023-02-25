import java.net.InetAddress;
import java.net.Socket;

import utente.utente;
import frame.frame;

import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class docente {

    private static Socket toSer;
    
    public static void main(String[] args) {
        InetAddress add = null;
        int port = -1;

        ArrayList<utente> studenti;
        frame f;

        if (args.length<1){
            System.out.println("Inserire almeno la porta con p<PORT>");
            return;
        }

        for (String a : args) {
            if(a.charAt(0)=='p'){
                try {
                    port = Integer.parseInt(a.substring(1));
                } catch (Exception e) {
                    System.out.println("Porta non valida");
                    return; 
                }

            } else if(a.charAt(0)=='a'){
                try {
                    add = InetAddress.getByName(a.substring(1));
                } catch (Exception e) {
                    System.out.println("Indirizzo non valido: "+e);
                    return;
                }
            }
        }

        if(port == -1){
            System.out.println("Inserire almeno la porta con p<PORT>");
            return;
        }

        try {
            if(add==null){
                add = InetAddress.getLocalHost();
            }
            toSer = new Socket(add, port);

        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        try {
            System.out.print("Inserisci la mail: ");
            String mail = getString();

            send(frame.login(utente.docente(mail)));

            
            f = recive();
            if (!f.isaule()){
                return;
            }

            ArrayList<String> aule = (ArrayList<String>) f.getPayload();
            System.out.println("Seleziona l'aula: ");
            for (int i = 0; i<aule.size();i++) {
                System.out.println("  "+i+") "+aule.get(i));
            }
            int sel = getInt();
            send(frame.select(sel));

            //ricevo stud in presenza
            f = recive();
            if (!f.isstudPres()){
                return;
            }

            studenti = (ArrayList<utente>) f.getPayload();
            System.out.println("Studenti in aula:");
            for (int i = 0; i<studenti.size();i++) {
                System.out.println("  "+i+") "+studenti.get(i));
            }
            send(frame.ACK());

            f = recive();
            if (!f.isstudDist()){
                return;
            }

            studenti = (ArrayList<utente>) f.getPayload();
            System.out.println("Studenti in remoto:");
            for (int i = 0; i<studenti.size();i++) {
                System.out.println("  "+i+") "+studenti.get(i));
            }
            send(frame.ACK());

            toSer.close();
            return;
            
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }

    public static String getString() throws IOException {
        int l = 50;
        byte[] buf = new byte[l];
        int n = System.in.read(buf, 0, l);
        return new String(buf, 0, n).trim();
    }

    public static int getInt() throws IOException {
        int l = 50;
        byte[] buf = new byte[l];
        int n = System.in.read(buf, 0, l);
        return Integer.parseInt(new String(buf, 0, n).trim());
    }
    
    public static void send(frame f) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(toSer.getOutputStream());
        out.writeObject(f);
        //System.out.println(toSer.getInetAddress()+":"+toSer.getPort()+" <- "+f);
    }

    public static frame recive() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(toSer.getInputStream());
        frame f = (frame) in.readObject();
        //System.out.println(toSer.getInetAddress()+":"+toSer.getPort()+" -> "+f);
        return f;
    }
}
