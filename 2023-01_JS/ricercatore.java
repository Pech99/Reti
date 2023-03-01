import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import Attivita.Attivita;
import Frame.Frame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ricercatore {
    
    private static int port = -1;
    private static DatagramSocket toServ;
    private static InetAddress sAdd = null;

    public static void main(String[] args) {
        
        String user;
        int lastSeq = 0;

        if ( args.length<1 ){
            return;
        }

        for (String a : args) {
            if (a.charAt(0)=='p'){
                try {
                    port = Integer.parseInt(a.substring(1));
                } catch (Exception e) {
                    System.out.println("Porta non valida");
                    return;
                }
            } else if (a.charAt(0)=='a'){
                try {
                    sAdd = InetAddress.getByName(a.substring(1));
                } catch (Exception e) {
                    System.out.println("Indirizzo non valido");
                }
            }
        }

        if (port==-1){
            System.out.println("Non è stata inserita alcuna porta");
            return;
        }

        if (sAdd==null){
            try {
                sAdd = InetAddress.getLocalHost();
            } catch (Exception e) {
                System.out.println(e);
                return;
            }
        }

        try {
            toServ = new DatagramSocket(0, sAdd);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        try {
            System.out.println("Username: ");
            user = getString();
            
            Frame f = Frame.Login(lastSeq, user);
            send(f);

            f = recive();
            if (f.getSequenceNunber() != lastSeq || f.getTipe()!='A'){
                System.out.println("Errore di comunicazione");
                return;
            }
            lastSeq++;
            
        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        while (true) {
            try {

                System.out.println("\nSelezione l'operazione da compiere:\n  1) Nuova attivita'\n  2) Impegni Giorno");
                int select = getInt();

                if (select == 1){
                    int giorno, ora, durata;
                    String tipo;

                    System.out.println("\nSeleziona il tipo di attivita':\n  1) ricerca\n  2) didattica\n  3) istituzionali\n  4) altro");
                    select = getInt();

                    if (select == 1) { tipo = "ricerca"; }
                    else if (select == 2) { tipo = "didattica"; }
                    else if (select == 3) { tipo = "istituzionali"; }
                    else if (select == 4) { tipo = "altro"; }
                    else {
                        System.out.println("Codice non valido\n");
                        continue;
                    }

                    System.out.print("Inserisci il giorno (0 < Giorno < 20): ");
                    giorno = getInt();
                    if (giorno<0 || giorno>20 ){
                        System.out.println("Giorno non valido\n");
                        continue;
                    }

                    System.out.print("Inserisci l'ora di inizio (8 < Ora < 18): ");
                    ora = getInt();
                    if ( ora<8 || ora>18 ){
                        System.out.println("Ora non valida\n");
                        continue;
                    }

                    System.out.print("Inserisci la durata: ");
                    durata = getInt();

                    
                    Attivita att = new Attivita(giorno, ora, durata, tipo);
                    Frame f = Frame.AddAttivita(lastSeq, user, att);
                    send(f);

                    f = recive();
                    if (f.getSequenceNunber() != lastSeq || (f.getTipe()!='A' && f.getTipe()!='N')){
                        lastSeq++;
                        System.out.println("Errore di comunicazione");
                        continue;
                    }
                    lastSeq++;

                    if (f.getTipe()=='A'){
                        System.out.println("Attività aggiunta correttamente\nNuemro di ore occupate: "+(String) f.getPayload());
                    } else if (f.getTipe()=='N') {
                        System.out.println("Errore durante l'inserimento: "+(String) f.getPayload());
                    }
                    continue;

                } else if (select == 2){
                    
                    int giorno; 
                    System.out.println("Inserisci il giorno (0 < Giorno < 20): ");
                    giorno = getInt();
                    if (giorno<0 || giorno>20 ){
                        System.out.println("Giorno non valido\n");
                        continue;
                    }

                    Frame f = Frame.Request(lastSeq, user, giorno);
                    send(f);

                    f = recive();
                    if (f.getSequenceNunber() != lastSeq || f.getTipe()!='S'){
                        lastSeq++;
                        System.out.println("Errore di comunicazione");
                        continue;
                    }
                    lastSeq++;

                    ArrayList<Attivita> att = (ArrayList<Attivita>) f.getPayload();
                    System.out.println("Arrivita' svolte nel giorno "+giorno);
                    for (Attivita a : att) {
                        System.out.println("Ora: "+a.getOra()+"\tDurata: "+a.getDuration()+"\tCategoria: "+a.getCategoria());
                    }
                    System.out.println();

                    continue;

                } else {
                    System.out.println("Codice non valido\n");
                }

            } catch (Exception e) {
                System.out.println(e);
                return;
            }
        }
    }

    public static void send(Frame frame) throws IOException {
        ByteArrayOutputStream f = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(f);
        out.writeObject(frame);
        out.flush();
        out.close();

        byte[] buf =  f.toByteArray();
        DatagramPacket p = new DatagramPacket(buf, buf.length, sAdd, port);
        toServ.send(p);
    }

    public static Frame recive() throws IOException {
        int l = 1000;
        byte[] buf = new byte[l];
        DatagramPacket p = new DatagramPacket(buf, l);
        toServ.receive(p);

        ByteArrayInputStream f = new ByteArrayInputStream(buf, 0, p.getLength());
        ObjectInputStream in = new ObjectInputStream(f);
        try {
            return (Frame) in.readObject();
        } catch (Exception e) { }
        return null;
    }

    public static int getInt() throws IOException{
        byte[] buf = new byte[100];
        int l = System.in.read(buf, 0, buf.length);
        return Integer.parseInt(new String(buf, 0, l).trim());
    }

    public static String getString() throws IOException{
        byte[] buf = new byte[100];
        int l = System.in.read(buf, 0, buf.length);
        return new String(buf, 0, l).trim();
    }

}
