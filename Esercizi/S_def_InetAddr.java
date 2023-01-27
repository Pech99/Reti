import java.net.InetAddress;

public class S_def_InetAddr {
    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Passare da linea di comando un dominio da tradurre");
            return;
        }

        InetAddress add;
        try {
            add = InetAddress.getByName(args[0]);
        } catch (Exception e) {
            System.out.println("no IP address for the host could be found");
            return;
        }

        System.out.println(add.getHostAddress());
    }
}
