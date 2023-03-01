package frame;

import java.io.Serializable;

/**
    A - ACK
    N - NACK
    L - Login -- String
    M - Message --> String
    X - Next
    E - Exit
 */
public class frame implements Serializable {
    
    private final char tipe;
    private final String pay;

    private frame(char tipe, String pay){
        this.tipe = tipe;
        this.pay = pay;
    }

    private char getTipe(){
        return this.tipe;
    }

    public String getPay(){
        return this.pay;
    }

    public String toString() {
        return "tipe: "+this.getTipe()+" pay: {"+this.getPay()+"}";
    }

    //A - ACK
    public static frame ACK(){
        return new frame('A', null);
    }

    public boolean isACK(){
        return this.getTipe()=='A';
    }

    //N - NACK
    public static frame NACK(){
        return new frame('N', null);
    }

    public boolean isNACK(){
        return this.getTipe()=='N';
    }

    //L - Login -- String
    public static frame login(String user){
        return new frame('L', user);
    }

    public boolean isLogin(){
        return this.getTipe()=='L';
    }

    //M - Message --> String
    public static frame message(String message){
        return new frame('M', message);
    }

    public boolean isMessage(){
        return this.getTipe()=='M';
    }

    //X - Next
    public static frame next(){
        return new frame('X', null);
    }

    public boolean isNext(){
        return this.getTipe()=='X';
    }

    //E - Exit
    public static frame exit(){
        return new frame('E', null);
    }

    public boolean isExit(){
        return this.getTipe()=='E';
    }

}
