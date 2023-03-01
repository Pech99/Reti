package Attivita;

import java.io.Serializable;

public class Attivita implements Serializable{
    
    
    private final int day, ora, dur;
    private final String cat;

    /**
     * crea un nuovo TS
     * @param giorno giorno dell'evento compreso tra 0 e 20
     * @param ora ora dell'evento compresa tra 8 e 18
     * @param durata durata dell'evento
     * @param categoria categoria dell'evento compresa tra {"ricerca", "didattica", "istituzionali", "altro"}
     */
    public Attivita(int giorno, int ora, int durata, String categoria){
        
        if ( giorno<0 || giorno>20 || ora<8 || ora>18 ||
        (!categoria.equals("ricerca") && !categoria.equals("didattica")
        && !categoria.equals("istituzionali") && !categoria.equals("altro")) ) {
            throw new RuntimeException("parametri non validi");
        }

        this.day = giorno;
        this.ora = ora;
        this.dur = durata;
        this.cat = categoria;
    }

    /**
     * calcola se due TS si sovrappongono
     * @param TS da contorllare rispetto a this
     * @return true se hanno sovapposizioni
     */
    public boolean hasOverlaps(Attivita TS) {

        if (this.getDay()!=TS.getDay()){
            return false;
        }

        Attivita A, B;
        if (this.ora<TS.ora){
            A = this;
            B = TS;
        } else {
            A = TS;
            B = this;
        }

        return A.getOra()==B.getOra() || A.getOra()+A.getDuration() > B.getOra();
    }

    public String toString() {
        return new String(  "Gio: "+day+" "+
                            "Ora: "+ora+" "+
                            "Dur: "+dur+" "+
                            "Cat: "+cat
        );
    }

    public int getDay(){
        return this.day;
    }

    public int getDuration(){
        return this.dur;
    }

    public int getOra(){
        return this.ora;
    }

    public String getCategoria(){
        return this.cat;
    }

}
