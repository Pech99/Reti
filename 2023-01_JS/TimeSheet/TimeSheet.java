package TimeSheet;

import java.util.ArrayList;
import Attivita.Attivita;

public class TimeSheet {

    private String user;
    private ArrayList<Attivita> TS;

    public TimeSheet(String user){
        this.user = user;
        this.TS = new ArrayList<Attivita>();
    }

    /**
     * true se aggiunta correttamente senza sovrapposizioni, false altrimenti.
     * @param att attività da aggiungere al TS
     * @return true se aggiunta correttamente senza sovrapposizioni, false altrimenti
     */
    public Boolean addAttivita(Attivita att){
        for (Attivita a : this.TS) {
            if (a.hasOverlaps(att)){
                return false;
            }
        }
        this.TS.add(att);
        return true;
    }

    public ArrayList<Attivita> getAttivitaByDay(int d){
        ArrayList<Attivita> dTS = new ArrayList<Attivita>();
        for (Attivita a : this.TS) {
            if (a.getDay()==d){
                dTS.add(a);
            }
        }
        return dTS;
    }

    public int countOre(int d){
        int ore = 0;
        for (Attivita a : this.TS) {
            if (a.getDay()==d){
                ore += a.getDuration();
            }
        }
        return ore;
    }

    public String getUser(){
        return this.user;
    }

}
