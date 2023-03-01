import java.net.InetAddress;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import frame.frame;


public class client {

    public static DatagramSocket soc;
    public static InetAddress toServ = null;
    public static int toServP;

    public static void main(String[] args) {
        
        int seq = 0;
        int score = 0;
        
        if (args.length<1){
            System.out.println("Inserire almeno la porta con p<port>");
            return;
        }

        for (String a : args) {
            if(a.charAt(0)=='p'){
                try {
                    toServP = Integer.parseInt(a.substring(1));
                } catch (Exception e) {
                    System.out.println("Porta non valida");
                }
            } else if(a.charAt(0)=='a'){
                try {
                    toServ = InetAddress.getByName(a.substring(1));
                } catch (Exception e) {
                    System.out.println("Indirizzo non valido");
                }
            }
        }
        try {
            soc = new DatagramSocket(0);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        if (toServ == null){
            try {
                toServ = InetAddress.getLocalHost();
            } catch (Exception e) {
                System.out.println(e);
                return;
            }
        }

        try {
            frame f = new frame(seq, 'p');
            send(f);
    
            f = recive();
            if (f.getSeqNun()!=seq || f.getPayload()!='k') {
                System.out.println("errore nella comunicazione");
                return;
            }
            seq++;
        } catch (Exception e) {
            System.out.println(e);
            return;
        }


        while (true) {
            try {
                System.out.print("Inserisci la tua mossa (c, f, s):");
                char mossa = getChar();

                if (mossa != 'c' && mossa != 'f' && mossa != 's' && mossa != '.'){
                    System.out.print("Mossa non valida\n");
                    continue;
                }

                if (mossa == '.'){
                    frame f;
                    if(score > 0){
                        f = new frame(seq, 'i');
                    } else {
                        f = new frame(seq, 'y');
                    }
                    send(f);
                    
                    f = recive();
                    if (f.getSeqNun()!=seq || f.getPayload()!='b') {
                        System.out.println("errore nella comunicazione");
                    }
                    seq++;
                    
                    return;
                }

                frame f = new frame(seq, mossa);
                send(f);
    
                f = recive();
                if (f.getSeqNun()!=seq) {
                    System.out.println("errore nella comunicazione");
                    return;
                }
                seq++;

                score += score(mossa, f.getPayload());

                System.out.println("Mossa del sever: "+f.getPayload()+"\tscore: "+score);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
 
    }

    public static char getChar() throws IOException{
        int l = 10;
        int n;
        String s;

        byte[] buf = new byte[l];
        n = System.in.read(buf);

        s = new String(buf, 0, n);
        s.trim();
        if(s.length()==0){
            throw new IOException();
        }
        return s.charAt(0);
    }

    public static int score(int cli, int serv){

        if(cli == serv){
            return 0;
        }

        if( (cli == 'c' && serv == 's') ||
            (cli == 'f' && serv == 'c') ||
            (cli == 's' && serv == 'f') ){
                return 1;
        }

        return -1;
    }

    public static void send(frame f) throws IOException{
        ByteArrayOutputStream a = new ByteArrayOutputStream(500);
        ObjectOutputStream out = new ObjectOutputStream(a);
        out.writeObject(f);

        DatagramPacket p = new DatagramPacket(a.toByteArray(), a.size(), toServ, toServP);
        soc.send(p);
    }

    public static frame recive() throws IOException{
        int l = 80;
        byte[] buf = new byte[l];
        DatagramPacket p = new DatagramPacket(buf, l);

        soc.receive(p);

        ByteArrayInputStream a = new ByteArrayInputStream(p.getData(), 0, p.getLength());
        ObjectInputStream in = new ObjectInputStream(a);
        try {
            return (frame) in.readObject();
        } catch (Exception e) { }
        return null;
    }

}