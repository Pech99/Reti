package frame;
import java.io.Serializable;

public class frame implements Serializable{
    
    private final int seq;
    private final char pay;

    public frame(int seq, char pay){
        this.seq = seq;
        this.pay = pay;
    }

    public int getSeqNun(){
        return this.seq;
    }

    public char getPayload(){
        return this.pay;
    }

    public String toString(){
        return "SeqNun: "+this.seq+", Payload: "+this.pay;
    }

}
