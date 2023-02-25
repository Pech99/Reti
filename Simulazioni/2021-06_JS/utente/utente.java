package utente;

import java.io.Serializable;

/**
 * D - docente
 * S - studente
 */
public class utente implements Serializable{
    
    private final char tipe;
    private final String mail;
    private final String matr;

    private utente(char tipe, String mail, String matr){
        this.tipe = tipe;
        this.mail = mail;
        this.matr = matr;
    }

    public static utente docente(String mail){
        return new utente('D', mail, null);
    }

    public static utente studente(String mail, String matr){
        return new utente('S', mail, matr);
    }

    public char getTipe(){
        return this.tipe;
    }

    public String getMail(){
        return this.mail;
    }

    public String getMatr(){
        return this.matr;
    }

    public String toString(){
        if(this.tipe=='D'){
            return "tipe: "+this.tipe+", mail: "+this.mail;
        }
        return "tipe: "+this.tipe+", mail: "+this.mail+", matr: "+this.matr;
    }
}
