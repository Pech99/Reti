package Frame;

import Attivita.Attivita;
import java.io.Serializable;
import java.util.ArrayList;

public class Frame implements Serializable {
    
    /**
     * P frame con payload
     * A frame di ACK
     * N frame di NACK (pay = errore String)
     */
    private final char tipe;
    private final int sqNun;
    private final String user;
    private final Object pay;

    /*
    public Frame(int sqNun, Object pay){
        this.sqNun = sqNun;
        this.tipe = 'P';
        this.user = "";
        this.pay = pay;
    }
    */
    
    private Frame(int sqNun, char tipe, String user, Object pay){
        this.sqNun = sqNun;
        this.tipe = tipe;
        this.user = user;
        this.pay = pay;
    }

    public static Frame NACK(int sqNun, String err){
        return new Frame(sqNun, 'N', null, err);
    }

    public static Frame ACK(int sqNun, String note){
        return new Frame(sqNun, 'A', null, note);
    }

    public static Frame Login(int sqNun, String user){
        return new Frame(sqNun, 'L', user, null);
    }

    public static Frame AddAttivita(int sqNun, String user, Attivita att){
        return new Frame(sqNun, 'C', user, att);
    }

    public static Frame Request(int sqNun, String user, Integer giorno){
        return new Frame(sqNun, 'Q', user, giorno);
    }

    public static Frame Response(int sqNun, ArrayList<Attivita> att){
        return new Frame(sqNun, 'S', null, att);
    }

    public int getSequenceNunber(){
        return this.sqNun;
    }

    public int getTipe(){
        return this.tipe;
    }

    public String getUser(){
        return this.user;
    }

    public Object getPayload(){
        return this.pay;
    }
    
    public String toString(){
        return new String(  
            "[sqNun: "+this.sqNun+" "+
            "tipe: "+this.tipe+" "+
            "user: "+this.user+" "+
            "pay: {"+this.pay+"}]"
        );
    }
}
