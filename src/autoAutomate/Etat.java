package src.autoAutomate;

public class Etat extends Valeur{
    private double type;
    
    public boolean set (String exp, int position, int nbVoisins) {
        if (exp.length()<=1 || exp.charAt(0)!='#') {
            return false;
        }
        double [] val=new double [1];
        if (!getDouble(exp.substring(1,exp.length()),val)) {
            return false;
        }
        type=val[0];
        return true;
    }
    
    public double get (Tableau tab, int [][] voisins, int [] indices) {
        double res=0;
        if (voisins!=null) {
            for (int i=0;i<voisins.length;i++) {
                if (tab.getVal(voisins[i])==type) {
                    res++;
                }
            }
        }
        return res;
    }
    
    public String getExp () {
        return "#"+type;
    }

    public int getOp (String exp) {
        if (exp.length()>0 && exp.charAt(0)=='#') {
            return 0;
        }
        return -1;
    }
}