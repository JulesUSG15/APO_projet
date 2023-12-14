package src.autoAutomate;

public class Voisin extends Valeur {
    private int vois=0;
    
    public boolean set (String exp, int position, int nbVoisins, Valeur [] var) {
        if (exp.length()<=1 || exp.charAt(0)!='$') {
            return false;
        }
        int [] val=new int [1];
        if (!getInt(exp.substring(1,exp.length()),val)) {
            return false;
        }
        vois=val[0];
        if (0>vois || vois>nbVoisins) {
            return false;
        }
        return true;
    }
    
    public double get (Tableau tab, int [][] voisins, int [] indices) {
        if (vois==0) {
            return tab.getVal(indices);
        }
        return tab.getVal(voisins[vois-1]);
    }
    
    public String getExp () {
        return "$"+vois;
    }

    public int getOp (String exp) {
        if (exp.length()>0 && exp.charAt(0)=='$') {
            return 0;
        }
        return -1;
    }
}