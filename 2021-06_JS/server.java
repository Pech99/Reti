import java.net.ServerSocket;
import java.net.Socket;

import aula.aula;
import utente.utente;
import frame.frame;

import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class server {

    private static ServerSocket serv;
    private static Socket toCli;
    
    private static ArrayList<aula> DB;
    

    public static void main(String[] args) {
        frame f;
        utente u;
        DB = inizDB();

        try {
            serv = new ServerSocket(0);
            System.out.println("Serv on: p"+serv.getLocalPort());

        } catch (Exception e) {
            System.out.println(e);
            return;
        }

        while (true){

            try {
                toCli = serv.accept();
                System.out.println("Connected to: "+toCli.getInetAddress()+":"+toCli.getPort());
            
                f = recive();

                if (!f.islogin()){
                    toCli.close();
                    continue;
                }

                u = (utente) f.getPayload();

                if(u.getTipe()=='D'){
                    docente(u);
                } else if(u.getTipe()=='S'){
                    studente(u);
                }
            
                toCli.close();
            
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }
        }
    }

    public static void docente(utente u){
        frame f;
        try {
            //invio la lista delle aule
            send(frame.aule(getAule()));

            //ricevo la scelta del docente
            f = recive();
            if (!f.isselect()){
                return;
            }

            int aula = (int) f.getPayload();

            //invio presenza
            send(frame.studPres(DB.get(aula).getPres()));
            f = recive();
            if (!f.isACK()){
                return;
            }

            //invio streaming
            send(frame.studDist(DB.get(aula).getDist()));
            f = recive();
            if (!f.isACK()){
                return;
            }

        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        
    }
    
    public static void studente(utente u){
        frame f;
        try {
            //invio la lista delle aule
            send(frame.aule(getAule()));
            
            //ricevo la scelta dello studente
            f = recive();
            if (!f.isselect()){
                return;
            }

            int aula = (int) f.getPayload();
            if(!DB.get(aula).hasSpace()){           //prenotato lo straming
                DB.get(aula).addDist(u);
                send(frame.notifica("Prenotato per lo streaming"));

            } else {                                // scelta dello studente
                ArrayList<String> prop = new ArrayList<String>();
                prop.add("Prenota lo Streaming");
                prop.add("Prenota in Presenza");
                send(frame.prop(prop));
                
                f = recive();
                if (!f.isselect()){
                    return;
                }

                if ((int) f.getPayload() == 0){
                    DB.get(aula).addDist(u);
                    send(frame.notifica("Prenotato per lo streaming"));
                } else {
                    int posto = DB.get(aula).addPres(u);
                    send(frame.notifica("Prenotato il posto numero :"+posto));
                }
            }

        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }

    public static void send(frame f) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(toCli.getOutputStream());
        out.writeObject(f);
        System.out.println(toCli.getInetAddress()+":"+toCli.getPort()+" <- "+f);
    }

    public static frame recive() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(toCli.getInputStream());
        frame f = (frame) in.readObject();
        System.out.println(toCli.getInetAddress()+":"+toCli.getPort()+" -> "+f);
        return f;
    }

    public static ArrayList<String> getAule() {
        ArrayList<String> aule = new ArrayList<String>();
        for (aula a : DB) {
            aule.add(a.getInsegnamento());
        }
        return aule;
    }

    public static ArrayList<aula> inizDB() {
        ArrayList<aula> strutturaDati = new ArrayList<aula>();
        strutturaDati.add(new aula("Alfa", "Programmazione", 4));
        strutturaDati.add(new aula("Beta", "Sistemi Operativi", 3));
        strutturaDati.add(new aula("Gamma", "reti", 2));
        return strutturaDati;
    }

}
