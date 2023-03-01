package Frame;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  A - ACK
 *  T - reset
 *  L - login (nick)     --> String
 *  N - operand nunber   --> int
 *  O - operation        --> char
 *  Q - request          --> ArrayList<Integer>
 *  R - response         --> int
 *  E - exit
 */
public class Frame implements Serializable {
    
    private char tipe;
    private String nick;
    private Object pay;


    private Frame(char tipe, String nick, Object pay ) {
        this.tipe = tipe;
        this.nick = nick;
        this.pay = pay;
    }

    public static Frame ACK(){
        return new Frame('A', null, null);
    }

    public static Frame RST(){
        return new Frame('T', null, null);
    }

    public static Frame login(String nick){
        return new Frame('L', nick, null);
    }

    public static Frame opNunber(String nick, int n){
        return new Frame('N',nick, n);
    }

    public static Frame operation(String nick, char operation){
        return new Frame('O', nick, operation);
    }

    public static Frame request(String nick, ArrayList<Integer> operands){
        return new Frame('Q', nick, operands);
    }

    public static Frame response(int result){
        return new Frame('R', null, result);
    }

    public static Frame exit(){
        return new Frame('E', null, null);
    }

    public char getTipe(){
        return this.tipe;
    }

    public String getNick(){
        return this.nick;
    }

    public Object getPayload(){
        return this.pay;
    }

    public String toString(){
        return "tipe: "+this.tipe+", nick: "+this.nick+", pay: {"+this.pay+"}";
    }

}
