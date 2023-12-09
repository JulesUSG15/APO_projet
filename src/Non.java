
public class Non extends Condition{
    private Condition cond=null;
    
    public boolean set (String exp, int nbVoisins) {
        int n=getOp(exp);
        if (n==-1) {
            return false;
        }
        if (n!=0 || exp.charAt(0)!='!') {
            return false;
        }
        exp=simplification(exp.substring(1,exp.length()));
        int n1=getOp(exp);
        if (n1==-1) {
            return false;
        }
        cond=getCond(exp.charAt(n1));
        if (cond==null || !cond.set(exp,nbVoisins)) {
            return false;
        }
        return true;
    }
    
    public boolean get (Tableau tab, int [][] voisins, int [] indices) {
        return !cond.get(tab,voisins,indices);
    }
    
    public void afficher () {
        System.out.println("!");
        cond.afficher();
    }
}