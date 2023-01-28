import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class S_TCP_cli {
    public static void main(String[] args) {
        
        Socket sCli = new Socket();
        byte[] buf = new byte[1000];
        int l;


        int port = -1;
        String addr = "";

        for (String s : args) {
            if (s.charAt(0)=='p'){
                try {
                    port = Integer.parseInt(s.substring(1));
                } catch (Exception e) {
                    System.out.println("Porta non valida");
                    return;
                }
            } else if (s.charAt(0)=='a'){
                addr = s.substring(1);
            } else {
                System.out.println("Argomenti non validi");
                return;
            }
        }

        if(port==-1){
            System.out.println("Inserire almeno una porta valida");
        }

        try {
            if (addr == "" ){
                sCli.connect( new InetSocketAddress(InetAddress.getLocalHost(), port));
            } else {
                sCli.connect( new InetSocketAddress(InetAddress.getByName(addr), port));
            }

            System.out.print("Connect to: "+sCli.getInetAddress()+":"+sCli.getLocalPort()+"\n");
            
            while (true){
                l = System.in.read(buf, 1, buf.length-1);

                if (buf[1]=='.'){
                    sCli.getOutputStream().write("E".getBytes(), 0, 1);
                    break;
                }

                buf[0]='S';
                sCli.getOutputStream().write(buf, 0, l);
    
                l = sCli.getInputStream().read(buf);
                System.out.println("> "+new String(buf, 1, l-1));

            }

            sCli.close();
            
        } catch (Exception e) {
            System.out.println("ERRORE!!!");
            System.out.println(e.getMessage());
            return;
        }
    }
}
