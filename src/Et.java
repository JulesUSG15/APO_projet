
public class Et extends Condition{
    private Condition cond1=null;
    private Condition cond2=null;
    
    public boolean set (String exp, int nbVoisins) {
        exp=simplification(exp);
        int n=getOp(exp);
        if (n==-1) {
            return false;
        }
        String exp1=simplification(exp.substring(0,n));
        String exp2=simplification(exp.substring(n+1,exp.length()));
        int n1=getOp(exp1);
        if (n1==-1) {
            return false;
        }
        cond1=getCond(exp1.charAt(n1));
        if (cond1==null || !cond1.set(exp1,nbVoisins)) {
            return false;
        }
        n1=getOp(exp2);
        if (n1==-1) {
            return false;
        }
        cond2=getCond(exp2.charAt(n1));
        if (cond2==null || !cond2.set(exp2,nbVoisins)) {
            return false;
        }
        return true;
    }
    
    public boolean get (Tableau tab, int [][] voisins, int [] indices) {
        return cond1.get(tab,voisins,indices) && cond2.get(tab,voisins,indices);
    }
    
    public String getExp () {
        return "("+cond1.getExp()+")&("+cond2.getExp()+")";
    }
}