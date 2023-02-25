package aula;

import utente.utente;

import java.util.ArrayList;

public class aula {

    private final String nome;
    private final String inse;
    private final int cap;

    private ArrayList<utente> pres;
    private ArrayList<utente> dist;
    
    public aula(String nome, String inse, int cap){
        this.nome = nome;
        this.inse = inse;
        this.cap = cap;
        this.pres = new ArrayList<utente>();
        this.dist = new ArrayList<utente>();
    }

    public boolean hasSpace(){
        return this.cap>this.pres.size();
    }

    /**
     * aggoinge una persona in presenza
     * @param u persona da aggoingere
     * @return tru se aggounta correttamente, false se aggiunta a distanza;
     */
    public int addPres(utente u){
        if (this.hasSpace()){
            this.pres.add(u);
            return this.pres.size();
        }
        this.dist.add(u);
        return -1;
    }

    public boolean addDist(utente u){
        this.dist.add(u);
        return true;
    }

    public String getNome(){
        return this.nome;
    }

    public String getInsegnamento(){
        return this.inse;
    }

    public ArrayList<utente> getPres(){
        return this.pres;
    }

    public ArrayList<utente> getDist(){
        return this.dist;
    }

    public String toString(){
        return "nome: "+this.nome+", inse: "+this.inse+", cap: "+this.cap+", pres: {"+this.pres+"}, dist: {"+this.dist+"}";
    }
    
}
