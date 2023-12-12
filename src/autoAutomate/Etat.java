package src.autoAutomate;

public class Etat extends Valeur{
    private Valeur type;
    
    public boolean set (String exp, int position, int nbVoisins) {
        if (exp.length()<=1 || exp.charAt(0)!='#') {
            return false;
        }
        type=getVal(exp,nbVoisins);
        if (type==null) {
            return false;
        }
        return true;
    }
    
    public double get (Tableau tab, int [][] voisins, int [] indices) {
        double res=0;
        if (voisins!=null) {
            double val=type.get(tab, voisins, indices);
            for (int i=0;i<voisins.length;i++) {
                if (tab.getVal(voisins[i])==val) {
                    res++;
                }
            }
        }
        return res;
    }
    
    public String getExp () {
        return "#"+type.getExp();
    }

    public int getOp (String exp) {
        if (exp.length()>0 && exp.charAt(0)=='#') {
            return 0;
        }
        return -1;
    }
}