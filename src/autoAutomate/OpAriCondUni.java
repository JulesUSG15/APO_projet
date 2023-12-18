package src.autoAutomate;

import java.util.Arrays;

public class OpAriCondUni extends Valeur{
    private Condition cond;
    private char op=' ';
    private char [] opList={'°'};
    
    public boolean set (String exp, int position, int nbVoisins, Variable [] var) {
        if (exp.length()+1<position || !Arrays.toString(opList).contains(""+exp.charAt(position))) {
            return false;
        }
        op=exp.charAt(position);
        String exp1=(new Immediat ()).deParenthesage(exp.substring(1,exp.length()));
        cond=(new OpLogBin ()).getCond(exp1,nbVoisins,var);
        if (cond!=null) {
            return true;
        }
        return false;
    }
    
    public double get (Tableau tab, int [][] voisins, int [] indices) {
        switch (op) {
            case '°': {
                if (cond.get(tab, voisins, indices)) {
                    return 1;
                }
                return 0;
            }
        }
        return 0;
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