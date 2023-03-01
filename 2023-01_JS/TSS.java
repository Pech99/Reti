import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import Frame.Frame;
import Attivita.Attivita;
import TimeSheet.TimeSheet;

import java.util.ArrayList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class TSS {
    
    private static int port = 0;
    private static DatagramSocket sSoc;
    private static InetAddress toCli;
    private static int toCliP;

    
    
    public static void main(String[] args) {
        ArrayList<TimeSheet> DB = new ArrayList<TimeSheet>();
        Frame f;
        
        if (args.length > 0 && args[0].charAt(0)=='p'){
            try {
                port = Integer.parseInt(args[0].substring(1));
            } catch (Exception e) {
                System.out.println("Porta non valida");
                return;
            }
        }

        try {
            sSoc = new DatagramSocket(port);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        System.out.println("Listeningo on port: p"+sSoc.getLocalPort());

        while (true) {
            try {
                f = recive();
                System.out.println(toCli + "/"+toCliP+ ":"+f);
                String user = f.getUser();
                TimeSheet TS = findTS(DB, user);
            
                //Login
                if(f.getTipe()=='L'){
                    if (TS !=null) {
                        send(Frame.ACK(f.getSequenceNunber(), ""));
                    } else {
                        send(Frame.NACK(f.getSequenceNunber(), "Impossibile trovare o creare l'utente"));
                    }
                    continue;
                }
                
                //aggiungi attività
                if(f.getTipe()=='C'){
                    Attivita att = (Attivita) f.getPayload();

                    if (TS.addAttivita(att)){
                        send(Frame.ACK(f.getSequenceNunber(), Integer.toString(TS.countOre(att.getDay())) ));
                    } else {
                        send(Frame.NACK(f.getSequenceNunber(), "L'evento si sovrappone" ));
                    }

                    continue;
                }

                //richiesta
                if(f.getTipe()=='Q'){
                    Integer day = (Integer) f.getPayload();
                    ArrayList<Attivita> att = TS.getAttivitaByDay(Integer.valueOf(day));
                    send(Frame.Response(f.getSequenceNunber(), att));
                    continue;
                }
            
            } catch (Exception e) {
                System.out.println(e);
                return;
            }
        }
    }

    public static TimeSheet findTS(ArrayList<TimeSheet> DB, String user){
        for (TimeSheet TS : DB) {
            if (TS.getUser().equals(user)){
                return TS;
            }
        }

        TimeSheet TS = new TimeSheet(user);
        DB.add(TS);
        return TS;
    }

    public static void send(Frame frame) throws IOException {
        ByteArrayOutputStream f = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(f);
        out.writeObject(frame);
        out.flush();
        out.close();

        byte[] buf =  f.toByteArray();
        DatagramPacket p = new DatagramPacket(buf, buf.length, toCli, toCliP);
        sSoc.send(p);
    }

    public static Frame recive() throws IOException {
        int l = 1000;
        byte[] buf = new byte[l];
        DatagramPacket p = new DatagramPacket(buf, l);
        sSoc.receive(p);

        toCli = p.getAddress();
        toCliP = p.getPort();

        ByteArrayInputStream f = new ByteArrayInputStream(buf, 0, p.getLength());
        ObjectInputStream in = new ObjectInputStream(f);
        try {
            return (Frame) in.readObject();
        } catch (Exception e) { 
            System.out.println(e);
        }
        return null;
    }


}
