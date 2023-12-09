
public class Immediat extends Valeur {
    private int val=0;
    
    public boolean set (String exp, int nbVoisins) {
        int [] nouv=new int [1];
        if (!getInt(exp,nouv)) {
            return false;
        }
        val=nouv[0];
        return true;
    }
    
    public int get (Tableau tab, int [][] voisins, int [] indices) {
        return val;
    }
    
    public String getExp () {
        return Integer.toString(val);
    }
}