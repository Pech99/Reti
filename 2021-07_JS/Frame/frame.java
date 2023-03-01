package Frame;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * N - NACK
 * E - exit
 * G - figure gestite (figGest)       --> ArrayList<String>
 * S - figura selezionata (figSelect) --> int
 * Q - richiesta dati (dataRQ)        --> ArrayList<String>
 * D - dati (data)                    --> ArrayList<int>
 * R - risposta (result)              --> ArrayList<int>
 */
public class frame implements Serializable{
    
    private final char tipe;
    private final Object pay;

    private frame(char tipe, Object pay){
        this.tipe = tipe;
        this.pay = pay;
    }

    //N - NACK
    public static frame NACK(){
        return new frame('N', null);
    }
    
    //E - exit
    public static frame exit(){
        return new frame('E', null);
    }
    
    //G - figure gestite       --> ArrayList<String>
    public static frame figGest(ArrayList<String> fig){
        return new frame('G', fig);
    }
    
    //S - figura selezionata   --> int
    public static frame figSelect(int fig){
        return new frame('S', fig);
    }
    
    //Q - richiesta dati       --> ArrayList<String>
    public static frame dataRQ(ArrayList<String> data){
        return new frame('Q', data);
    }
    
    //D - dati                 --> ArrayList<Integer>
    public static frame data(ArrayList<Integer> data){
        return new frame('D', data);
    }
    
    //R - risposta             --> ArrayList<Integer>
    public static frame result(ArrayList<Integer> result){
        return new frame('R', result);
    }

    public char getTipe(){
        return this.tipe;
    }

    public Object getPayload(){
        return this.pay;
    }

    public String toString(){
        return "tipe: "+this.tipe+" pay: {"+this.pay+"}";
    }


}
