import java.net.Socket;

public class S_TCP_serv_func extends Thread {

    private Socket tocli;

    public S_TCP_serv_func(Socket socket){
        tocli = socket;
    }

    public void run() {
        byte[] buf = new byte[500];
        int l;

        try {

            while (true){
                l = tocli.getInputStream().read(buf);
                if(buf[0]=='E'){
                    System.out.println("Connessione chiusa con: "+tocli.getInetAddress()+":"+tocli.getPort());
                    tocli.close();
                    return;
                }
                tocli.getOutputStream().write(buf, 0, l);
            }

        } catch (Exception e) {
            System.out.println("Errore connessione con: "+tocli.getInetAddress()+":"+tocli.getPort()+" - "+e.getMessage());
            return;
        }
    }
    
}
