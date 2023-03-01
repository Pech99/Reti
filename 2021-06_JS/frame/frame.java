package frame;

import utente.utente;
import java.util.ArrayList;

import java.io.Serializable;

/**
 * K - ACK
 * L - login        --> utente
 * A - aule         --> ArrayList<String>
 * R - prop         --> ArrayList<String>
 * S - select       --> int
 * N - notifica     --> String
 * P - studPres     --> ArrayList<utenti>
 * D - studDist     --> ArrayList<utenti>
 */
public class frame implements Serializable {

    private final char tipe;
    private final Object pay;

    private frame(char tipe, Object pay){
        this.tipe = tipe;
        this.pay = pay;
    }

    //K - ACK
    public static frame ACK(){
        return new frame('K', null);
    }

    //L - login        --> utente
    public static frame login(utente p){
        return new frame('L', p);
    }

    //R - prop          --> ArrayList<String>
    public static frame prop(ArrayList<String> p){
        return new frame('R', p);
    }

    //N - notifica     --> String
    public static frame notifica(String p){
        return new frame('N', p);
    }

    //S - select       --> int
    public static frame select(int p){
        return new frame('S', p);
    }

    //A - aule         --> ArrayList<String>
    public static frame aule(ArrayList<String> p){
        return new frame('A', p);
    }

    //P - studPres     --> ArrayList<utenti>
    public static frame studPres(ArrayList<utente> p){
        return new frame('P', p);
    }

    //D - studDist     --> ArrayList<utenti>
    public static frame studDist(ArrayList<utente> p){
        return new frame('D', p);
    }
    
    
    //K - ACK
    public boolean isACK(){
        return this.getTipe()=='K';
    }

    //L - login        --> utente
    public boolean islogin(){
        return this.getTipe()=='L';
    }

    //R - prop          --> ArrayList<String>
    public boolean isprop(){
        return this.getTipe()=='R';
    }

    //N - notifica     --> String
    public boolean isnotifica(){
        return this.getTipe()=='N';
    }

    //S - select       --> int
    public boolean isselect(){
        return this.getTipe()=='S';
    }

    //A - aule         --> ArrayList<String>
    public boolean isaule(){
        return this.getTipe()=='A';
    }

    //P - studPres     --> ArrayList<utenti>
    public boolean isstudPres(){
        return this.getTipe()=='P';
    }

    //D - studDist     --> ArrayList<utenti>
    public boolean isstudDist(){
        return this.getTipe()=='D';
    }



    public String toString(){
        return "tipe: "+this.tipe+", pay: {"+this.pay+"}";
    }

    private char getTipe(){
        return this.tipe;
    }

    public Object getPayload(){
        return this.pay;
    }
}
