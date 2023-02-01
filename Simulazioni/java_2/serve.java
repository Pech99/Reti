import java.net.InetAddress;
import java.net.Socket;
import java.util.jar.Manifest;
import java.net.ServerSocket;


public class serve {
    public static void main(String[] args) {
        ServerSocket serv;
        Socket toCli;
        
        int l, port = 0;
        byte[] buf;
        byte op;
        Double a, b;

        if(args.length == 1 && args[0].charAt(0) == 'p' ) {
            try {
                port = Integer.parseInt(args[0].substring(1));
            } catch (Exception e) {
                System.out.print("Errore nella porta");
                return;
            }
        }

        try {
            serv = new ServerSocket(port);
        } catch (Exception e) {
            System.out.print("Errore: "+e.getMessage());
            return;
        }

        while (true){
            try {
                toCli = serv.accept();
            } catch (Exception e) {
                System.out.print("Errore: "+e.getMessage());
                return;
            }

            while(true){
                try {
                    // recezione operazione
                    buf = new byte[50];
                    l = toCli.getInputStream().read(buf);
                    
                    if (l == 1 && buf[0]!='.'){
                        break;
                    }

                    if (l != 1 || (buf[0]!='+' && buf[0]!='-' && buf[0]!='*' && buf[0]!='/')) {
                        toCli.getOutputStream().write("N".getBytes());
                        continue;
                    }

                    op = buf[0];
                    toCli.getOutputStream().write("A".getBytes());

                    //recezione addendo a
                    buf = new byte[50];
                    l = toCli.getInputStream().read(buf);
                    try {
                        a = Double.parseDouble(new String(buf, 0, l));
                    } catch (Exception e) {
                        toCli.getOutputStream().write("N".getBytes());
                        continue;
                    }
                    toCli.getOutputStream().write("A".getBytes());

                    //recezione addendo b
                    buf = new byte[50];
                    l = toCli.getInputStream().read(buf);
                    try {
                        a = Double.parseDouble(new String(buf, 0, l));
                    } catch (Exception e) {
                        toCli.getOutputStream().write("N".getBytes());
                        continue;
                    }
                    toCli.getOutputStream().write("A".getBytes());


                } catch (Exception e) {
                    System.out.print("Errore: "+e.getMessage());
                    break;
                }
            }

        }
    }
}
