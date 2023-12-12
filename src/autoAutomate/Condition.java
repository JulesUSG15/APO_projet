package src.autoAutomate;

public abstract class Condition {
    
    public abstract boolean set (String exp, int position, int nbVoisins);
    
    public abstract boolean get (Tableau tab, int [][] voisins, int [] indices);
    
    public abstract String getExp ();

    public abstract int getOp (String exp);
    
    public Condition getCond (String exp,int nbVoisins) {
        Condition cond;
        int n=(new OpLogUni ()).getOp(exp);
        if (n!=-1) {
            cond=new OpLogUni ();
            if (cond.set(exp,n,nbVoisins)) {
                return cond;
            }
            return null;
        }
        n=(new OpLogBin ()).getOp(exp);
        if (n!=-1) {
            cond=new OpLogBin ();
            if (cond.set(exp,n,nbVoisins)) {
                return cond;
            }
            return null;
        }
        n=(new OpCompar ()).getOp(exp);
        if (n!=-1) {
            cond=new OpCompar ();
            if (cond.set(exp,n,nbVoisins)) {
                return cond;
            }
            return null;
        }
        return null;
    }
}