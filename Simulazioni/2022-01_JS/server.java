import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import Frame.Frame;

import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class server {

    public static ServerSocket soc;
    public static Socket toCli;

    public static void main(String[] args) {
        Frame f;
        String nick;
        int nop;
        char op;
        ArrayList<Integer> ops = new ArrayList<Integer>();
        
        try {
            soc = new ServerSocket(0);
            System.out.println("Server in ascolto sulla porta: p"+soc.getLocalPort());
        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        while (true){
            
            try {
                toCli = soc.accept();
                System.out.println("Connected to: "+toCli.getInetAddress()+":"+toCli.getPort());
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }

            //recezione del nick
            try {
                f = read();
                if (f.getTipe()!='L'){
                    System.out.print("Errore di collegamento");
                    continue;
                }
                write(Frame.ACK());
                nick = f.getNick();

            } catch (Exception e) {
                System.out.println(e);
                continue;
            }

            while (true){
                try {
                    //recezione operandi
                    f = read();

                    if (f.getTipe()=='E'){
                        break;
                    }
                    
                    if (f.getTipe()!='N'){
                        System.out.println("Errore di collegamento");
                        continue;
                    }
                    write(Frame.ACK());
                    nop = (int) f.getPayload();

                    //recezione operazione
                    f = read();
                    if (f.getTipe()!='O'){
                        System.out.println("Errore di collegamento");
                        continue;
                    }
                    op = (char) f.getPayload();
                    write(Frame.ACK());
                    
                    //recezione operandi
                    f = read();
                    if (f.getTipe()!='Q'){
                        System.out.println("Errore di collegamento");
                        continue;
                    }
                    ops = (ArrayList<Integer>) f.getPayload();

                    f = Frame.response(compute(op, ops));
                    write(f);

                } catch (Exception e) {
                    System.out.println(e);
                    break;
                }
            }
        }
    }

    public static void write(Frame f) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(toCli.getOutputStream());
        out.writeObject(f);
    }

    public static Frame read() throws IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(toCli.getInputStream());
        Frame f = (Frame) in.readObject();
        System.out.println(f);
        return f;
    }

    public static int compute(char op, ArrayList<Integer> ops){
        int result = 0;
        if (op == '+'){
            for (int e : ops) {
                result += e;
            }

        } else if (op == '*'){
            result = 1;
            for (int e : ops) {
                result *= e;
            }

        } else if (op == '-'){
            for (int e : ops) {
                result -= e;
            }
        }
        return result;
    }
}
