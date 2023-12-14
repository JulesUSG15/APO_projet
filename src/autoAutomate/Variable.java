package src.autoAutomate;

public class Variable extends Valeur {
    private char nom=' ';
    private double val=0;
    
    public boolean set (String exp, int position, int nbVoisins, Valeur [] var) {
        if (exp.length()!=1 || !(('a'<=exp.charAt(0) && exp.charAt(0)<='z') || ('A'<=exp.charAt(0) && exp.charAt(0)<='Z'))) {
            return false;
        }
        nom=exp.charAt(0);
        val=0;
        return true;
    }
    
    public double get (Tableau tab, int [][] voisins, int [] indices) {
        return val;
    }
    
    public String getExp () {
        return nom+"";
    }

    public int getOp (String exp) {
        if (0<exp.length() && (('a'<=exp.charAt(0) && exp.charAt(0)<='z') || ('A'<=exp.charAt(0) && exp.charAt(0)<='Z'))) {
            return 0;
        }
        return -1;
    }

    public char getNom () {
        return nom;
    }

    public void setVal (double v) {
        val=v;
    }
}