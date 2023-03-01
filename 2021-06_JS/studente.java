import java.net.InetAddress;
import java.net.Socket;

import utente.utente;
import frame.frame;

import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class studente {

    private static Socket toSer;
    
    public static void main(String[] args) {
        InetAddress add = null;
        int port = -1;

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

            System.out.print("Inserisci la matricola: ");
            String matr = getString();

            send(frame.login(utente.studente(mail, matr)));

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

            f = recive();
            
            if (!f.isnotifica() && !f.isprop()){
                return;
            }

            if (f.isnotifica()){
                System.out.println( (String) f.getPayload());
                toSer.close();
                return;
            }

            ArrayList<String> scelta = (ArrayList<String>) f.getPayload();
            System.out.println("Seleziona l'aula: ");
            for (int i = 0; i<scelta.size();i++) {
                System.out.println("  "+i+") "+scelta.get(i));
            }
            sel = getInt();
            send(frame.select(sel));

            f = recive();
            if (!f.isnotifica()){
                return;
            }

            System.out.println( (String) f.getPayload());
            toSer.close();
            return;
            
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
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

}
