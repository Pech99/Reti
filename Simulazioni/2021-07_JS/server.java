import java.net.ServerSocket;
import java.net.Socket;

import Frame.frame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;


public class server {

    private static ServerSocket soc;
    private static Socket toCli;

    public static void main(String[] args) {
        
        frame f;

        try {
            soc = new ServerSocket(0);
            System.out.println("Server in port: p"+soc.getLocalPort());
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

            try {
                send(frame.figGest(getFigure()));
            } catch (Exception e) {
                continue;
            }

            int step = 0;
            int figura = 0;
            while (true){
                try {
                    f = recive();

                    if (step == 0 && f.getTipe()=='S'){
                        figura = (int) f.getPayload();
                        send(frame.dataRQ(getRQ(figura)));
                        step++;
                        continue;
                    
                    } else if (step == 1 && f.getTipe()=='D'){
                        ArrayList<Integer> result = new  ArrayList<Integer>();
                        result.add(calcolaPerimetro(figura, (ArrayList<Integer>) f.getPayload()));
                        result.add(calcolaArea(figura, (ArrayList<Integer>) f.getPayload()));
                        send(frame.result(result));
                        step = 0;
                        continue;

                    } else if (f.getTipe()=='E'){
                        break;
                    } else {
                        toCli.close();
                        break;
                    }
                    
                } catch (Exception e) {
                    System.out.println(e);
                    break;
                }
            }
        }
    }

    private static frame recive() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(toCli.getInputStream());
        frame f = (frame) in.readObject();
        System.out.println(toCli.getInetAddress()+":"+toCli.getPort()+" <- "+f);
        return f;
    }

    private static void send(frame f) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(toCli.getOutputStream());
        out.writeObject(f);
        System.out.println(toCli.getInetAddress()+":"+toCli.getPort()+" -> "+f);
    }

    private static ArrayList<String> getFigure() {
        ArrayList<String> figs = new  ArrayList<String>();
        figs.add("quadrato");
        figs.add("rettangolo");
        figs.add("cerchio");
        figs.add("triangolo rettangolo");
        figs.add("triangolo equilatero");
        return figs;
    }

    private static ArrayList<String> getRQ(int indf) {
        ArrayList<String> rq = new  ArrayList<String>();
        if (indf==0 || indf==4){           //quadrato / triangolo equilatero
            rq.add("Inserisci la lunghezza del lato: ");
        
        } else if (indf==1){    //rettangolo
            rq.add("Inserisci la lunghezza del primo lato: ");
            rq.add("Inserisci la lunghezza del secondo lato: ");

        } else if (indf==2){    //cerchio
            rq.add("Inserisci il raggio: ");

        } else if (indf==3){    //triangolo rettangolo
            rq.add("Inserisci la lunghezza del primo cateto: ");
            rq.add("Inserisci la lunghezza del secondo cateto: ");
        
        } else if (indf==3){    //triangolo rettangolo
            rq.add("Inserisci la lunghezza del lato: ");

        }

        return rq;
    }

    private static int calcolaArea(int indf,  ArrayList<Integer> data) {
        int area = -1; 
        if (indf==0){           //quadrato
            area = data.get(0)*data.get(0);
        
        } else if (indf==1){    //rettangolo
            area = data.get(0)*data.get(1);

        } else if (indf==2){    //cerchio
            area = data.get(0)*data.get(0)*3;

        } else if (indf==3){    //triangolo rettangolo
            area = data.get(0)*data.get(1)/2;

        } else if (indf==4){    //triangolo equilatero
            area = data.get(0)*data.get(0)/2;
        }

        return area;
    }

    private static int calcolaPerimetro(int indf,  ArrayList<Integer> data) {
        int perimetro = -1; 
        if (indf==0){           //quadrato
            perimetro = data.get(0)*4;
        
        } else if (indf==1){    //rettangolo
            perimetro = (data.get(0)+data.get(1))*2;

        } else if (indf==2){    //cerchio
            perimetro = 2*data.get(0)*3;

        } else if (indf==3){    //triangolo rettangolo
            perimetro = data.get(0)+data.get(1);

        } else if (indf==4){    //triangolo equilatero
            perimetro = data.get(0)*3;
        }

        return perimetro;
    }
    
}