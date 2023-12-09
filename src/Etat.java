
public class Etat extends Valeur{
    private int type;
    
    public boolean set (String exp, int nbVoisins) {
        int i=0;
        if (exp.length()<=1 || exp.charAt(0)!='#') {
            return false;
        }
        int [] val=new int [1];
        if (!getInt(exp.substring(1,exp.length()),val)) {
            return false;
        }
        type=val[0];
        return true;
    }
    
    public int get (Tableau tab, int [][] voisins, int [] indices) {
        int res=0;
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
}