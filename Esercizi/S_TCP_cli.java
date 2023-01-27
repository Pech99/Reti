import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class S_TCP_cli {
    public static void main(String[] args) {
        
        Socket sCli = new Socket();
        byte[] buf = new byte[1000];
        int l;

        int port = 0;
        if(args.length==1){
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                port = 0;
            }
        }

        try {
            sCli.connect( new InetSocketAddress(InetAddress.getLocalHost(), port));
            System.out.print("Connect to: "+sCli.getLocalPort()+"\n");
            
            l = System.in.read(buf);
            sCli.getOutputStream().write(buf, 0, l);

            l = sCli.getInputStream().read();
            System.out.println(new String(buf, 0, l));

            sCli.close();
            
        } catch (Exception e) {
            System.out.println("ERRORE!!!");
            System.out.println(e.getMessage());
            return;
        }
    }
}
