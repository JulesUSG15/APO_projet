
public abstract class Valeur {
    
    public abstract boolean set (String exp, int nbVoisins);
    
    public abstract int get (Tableau tab, int [][] voisins, int [] indices);
    
    public abstract void afficher ();
    
    public Valeur getVal (char op) {
        switch (op) {
            case '$':return new Voisin ();
            case '#':return new Etat ();
        }
        return new Immediat ();
    }
}