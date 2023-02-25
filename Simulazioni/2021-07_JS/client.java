import java.net.InetAddress;
import java.net.Socket;

import Frame.frame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;

public class client {

    private static Socket toSer;

    public static void main(String[] args) {

        int port = -1;
        InetAddress add = null;
        frame f;
        ArrayList<String> fig;

        if (args.length < 1){
            System.out.println("Inserisci almeno la porta del server con p<PORT>");
        }

        for (String a : args) {
            if (a.charAt(0)=='p') {
                try {
                    port = Integer.parseInt(a.substring(1));
                } catch (Exception e) {
                    System.out.println("Porta non valida, Inserisci la porta con p<PORT>\n"+e);
                    return;
                }
            }

            if (a.charAt(0)=='a') {
                try {
                    add = InetAddress.getByName(a.substring(1));
                } catch (Exception e) {
                    System.out.println("Indirizzo non valido, Inserisci l'indirizzo con a<ADDRESS>\n"+e);
                }
            }
        }

        if (add==null){
            try {
                add = InetAddress.getLocalHost();
            } catch (Exception e) {
                System.out.println(e);
                return;
            }
        }

        try {
            toSer = new Socket(add, port);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        //recezione lista figure
        try {
            f = recive();
            if (f.getTipe()=='G'){
                fig = (ArrayList<String>) f.getPayload();
            } else {
                System.out.println("errore del protocollo");
                return;
            }
        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        while (true){
            try {

                //scelta della figura
                System.out.println("Scegli al figura di cui calcolare 2p e area:");
                for (int i = 0; i<fig.size(); i++) {
                    System.out.println(i+") "+fig.get(i));
                }
                send(frame.figSelect(getInt()));

                //inserimento dati
                f = recive();
                if (f.getTipe()=='Q'){
                    ArrayList<String> datarq = (ArrayList<String>) f.getPayload();
                    ArrayList<Integer> data = new ArrayList<Integer>();
                    for (int i = 0; i<datarq.size(); i++) {
                        System.out.print(datarq.get(i));
                        data.add(getInt());
                    }
                    send(frame.data(data));
                } else {
                    System.out.println("errore del protocollo");
                    send(frame.NACK());
                    continue;
                }

                //recezione risultati
                f = recive();
                if (f.getTipe()=='R'){
                    ArrayList<Integer> result = (ArrayList<Integer>) f.getPayload();

                    if (result.size()!=2){
                        System.out.println("errore del protocollo");
                        send(frame.NACK());
                        continue;
                    }
                    int p = result.get(0);
                    int a = result.get(1);
                    System.out.println("Perimetro: "+p+"\nArea: "+a);

                } else {
                    System.out.println("errore del protocollo");
                    send(frame.NACK());
                    continue;
                }
                
                System.out.println("\n1) calcolo nuova figura\n2) exit");
                if (getInt()==2){
                    send(frame.exit());
                    break;
                }
                
            } catch (Exception e) {
                try {
                    send(frame.NACK());
                    continue;
                } catch (Exception ex) {
                    System.out.println(ex);
                    return;
                }
            }
        }
    }

    private static frame recive() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(toSer.getInputStream());
        return (frame) in.readObject();
    }

    private static void send(frame f) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(toSer.getOutputStream());
        out.writeObject(f);
    }

    private static int getInt() throws IOException {
        int l = 10;
        byte[] buf = new byte[l];
        System.in.read(buf, 0, l);
        return Integer.parseInt(new String(buf).trim());
    }
}