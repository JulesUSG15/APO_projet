
public class Immediat extends Valeur {
    private int val=0;
    
    public boolean set (String exp, int nbVoisins) {
        int i=0;
        val=0;
        if (exp.length()<1) {
            return false;
        }
        boolean neg=(exp.charAt(0)=='-');
        if (neg) {
            i++;
            if (exp.length()==1) {
                return false;
            }
        }
        while (i<exp.length() && '0'<=exp.charAt(i) && exp.charAt(i)<='9') {
            val=10*val+exp.charAt(i)-'0';
            i++;
        }
        if (i==0 || (neg && i==1)) {
            return false;
        }
        if (neg) {
            val*=-1;
        }
        return true;
    }
    
    public int get (Tableau tab, int [][] voisins, int [] indices) {
        return val;
    }
    
    public void afficher () {
        System.out.println(val);
    }
}