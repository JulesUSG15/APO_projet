package src.autoAutomate;

import java.util.Arrays;

public class OpLogUni extends Condition{
    private Condition cond=null;
    private char op=' ';
    private char [] opList={'!'};
    
    public boolean set (String exp, int position, int nbVoisins) {
        if (exp.length()+1<position || !Arrays.toString(opList).contains(""+exp.charAt(position))) {
            return false;
        }
        op=exp.charAt(position);
        String exp1=(new Immediat ()).simplification(exp.substring(1,exp.length()));
        cond=getCond(exp1,nbVoisins);
        if (cond!=null) {
            return true;
        }
        return false;
    }
    
    public boolean get (Tableau tab, int [][] voisins, int [] indices) {
        switch (op) {
            case '!': return !cond.get(tab,voisins,indices);
        }
        return false;
    }
    
    public String getExp () {
        return op+"("+cond.getExp()+")";
    }

    public int getOp (String exp) {
        if (exp.length()>0 && Arrays.toString(opList).contains(""+exp.charAt(0))) {
            return 0;
        }
        return -1;
    }
}