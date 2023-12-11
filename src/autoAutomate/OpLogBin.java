package src.autoAutomate;

import java.util.Arrays;

public class OpLogBin extends Condition{
    private Condition cond1=null;
    private Condition cond2=null;
    private char op=' ';
    private char [] opList={'&','|'};
    
    public boolean set (String exp, int position, int nbVoisins) {
        if (exp.length()+1<position) {
            return false;
        }
        op=exp.charAt(position);
        String exp1=(new Immediat ()).simplification(exp.substring(0,position));
        String exp2=(new Immediat ()).simplification(exp.substring(position+1,exp.length()));
        cond1=getCond(exp1,nbVoisins);
        if (cond1==null) {
            return false;
        }
        cond2=getCond(exp2,nbVoisins);
        if (cond2==null) {
            return false;
        }
        return true;
    }
    
    public boolean get (Tableau tab, int [][] voisins, int [] indices) {
        switch (op) {
            case '&': return cond1.get(tab,voisins,indices) && cond2.get(tab,voisins,indices);
            case '|': return cond1.get(tab,voisins,indices) || cond2.get(tab,voisins,indices);
        }
        return false;
    }
    
    public String getExp () {
        return "("+cond1.getExp()+")"+op+"("+cond2.getExp()+")";
    }

    public int getOp (String exp) {
        if (exp.length()==0) {
            return -1;
        }
        int par=0;
        for (int i=exp.length()-2;i>=1;i--) {
            if (Arrays.toString(opList).contains(""+exp.charAt(i)) && par==0) {
                return i;
            }
            if (par<0) {
                return -1;
            }
        }
        return -1;
    }
}