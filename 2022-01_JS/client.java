import java.net.InetAddress;
import java.net.Socket;

import Frame.Frame;

import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class client {

    public static int port = -1;
    public static InetAddress serv = null;
    public static Socket toServ;

    public static void main(String[] args) {
        
        Frame f;
        String nick;
        int nop;
        char op;
        ArrayList<Integer> ops;

        if(args.length<1){
            System.out.println("Inserire almeno la porta del server p<port>");
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
                    serv = InetAddress.getByName(a.substring(1));
                } catch (Exception e) {
                    System.out.println("indirizzo non valido");
                    return;
                }
            }
        }

        if (port == -1) {
            System.out.println("Inserire almeno la porta del server p<port>");
            return;
        }

        if (serv == null){
            try {
                serv = InetAddress.getLocalHost();
            } catch (Exception e) {
                System.out.println(e);
                return;
            }
        }

        try {
            toServ = new Socket(serv, port);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        //inserimento nick
        try {
            System.out.print("Inserisci il tuo nick: ");
            nick = getString();

            f = Frame.login(nick);
            write(f);

            f = read();
            if (f.getTipe()!='A'){
                System.out.println("Errore di collegamento");
                return;
            }
        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        while (true){
            try {
                ops = new ArrayList<Integer>();
                //inserimento #operandi
                System.out.print("Inserisci il numero di operandi: ");
                nop = getInt();
                if (nop>5 || nop<1){
                    System.out.println("numero di operandi non valido");
                    write(Frame.RST());
                    continue;
                }
                f = Frame.opNunber(nick, nop);
                write(f);

                f = read();
                if (f.getTipe()!='A'){
                    System.out.println("Errore di collegamento");
                    write(Frame.RST());
                    continue;
                }

                //inserimento operazione
                System.out.print("Inserisci l'operazione (+, *, -): ");
                op = getChar();
                if (op!='+' && op!='*' && op!='-'){
                    System.out.println("Operazione non valida");
                    write(Frame.RST());
                    continue;
                }

                f = Frame.operation(nick, op);
                write(f);

                f = read();
                if (f.getTipe()!='A'){
                    System.out.println("Errore di collegamento");
                    write(Frame.RST());
                    continue;
                }

                //inseriemnto operandi
                int n;
                for(int i = 0; i<nop; i++){
                    System.out.print("Inserisci l'operando "+i+": ");
                    n = getInt();
                    ops.add(n);
                }

                f = Frame.request(nick, ops);
                write(f);

                f = read();
                if (f.getTipe()!='R'){
                    System.out.println("Errore di collegamento");
                    write(Frame.RST());
                    continue;
                }
    
                //print result
                System.out.println("\n"+"Il risultato e': "+(Integer) f.getPayload());
                
                //exit or more operation
                System.out.println("\n1) Nuova operazoione\n2) exit");
                if (getInt()==2){
                    f = Frame.exit();
                    write(f);
                    break;
                }
    
            } catch (Exception e) {
                System.out.println(e);
                return;
            }

        }
    }

    public static void write(Frame f) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(toServ.getOutputStream());
        out.writeObject(f);
    }

    public static Frame read() throws IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(toServ.getInputStream());
        return (Frame) in.readObject();
    }

    public static int getInt() throws IOException {
        int l = 15;
        int  n;
        String s;
        byte[] buf = new byte[l];

        n = System.in.read(buf, 0, l);
        s = new String(buf, 0, n);
        
        return Integer.parseInt(s.trim());
    }

    public static char getChar() throws IOException {
        int l = 5;
        int  n;
        String s;
        byte[] buf = new byte[l];

        n = System.in.read(buf, 0, l);
        s = new String(buf, 0, n);
        s = s.trim();
        
        return s.charAt(0);
    }

    public static String getString() throws IOException {
        int l = 100;
        int  n;
        String s;
        byte[] buf = new byte[l];

        n = System.in.read(buf, 0, l);
        s = new String(buf, 0, n);
        return s.trim();
    }

}