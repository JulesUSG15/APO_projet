
public class Voisin extends Valeur {
    private int vois=0;
    
    public boolean set (String exp, int nbVoisins) {
        int i=0;
        if (exp.length()<=1) {
            return false;
        }
        if (exp.charAt(0)!='$') {
            return false;
        }
        i++;
        vois=0;
        while (i<exp.length() && '0'<=exp.charAt(i) && exp.charAt(i)<='9') {
            vois=10*vois+exp.charAt(i)-'0';
            i++;
        }
        if (vois>nbVoisins) {
            return false;
        }
        return true;
    }
    
    public int get (Tableau tab, int [][] voisins, int [] indices) {
        if (vois==0) {
            return tab.getVal(indices);
        }
        return tab.getVal(voisins[vois-1]);
    }
    
    public void afficher () {
        System.out.println("$"+vois);
    }
}