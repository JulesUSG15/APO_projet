
public class Etat extends Valeur{
    private int type;
    
    public boolean set (String exp, int nbVoisins) {
        int i=0;
        if (exp.length()<=1) {
            return false;
        }
        if (exp.charAt(0)!='#') {
            return false;
        }
        i++;
        type=0;
        boolean neg=(exp.charAt(i)=='-');
        if (neg) {
            i++;
            if (exp.length()==2) {
                return false;
            }
        }
        while (i<exp.length() && '0'<=exp.charAt(i) && exp.charAt(i)<='9') {
            type=10*type+exp.charAt(i)-'0';
            i++;
        }
        if (neg) {
            type*=-1;
        }
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
    
    public void afficher () {
        System.out.println("#"+type);
    }
}