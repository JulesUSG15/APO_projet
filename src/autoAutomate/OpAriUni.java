package src.autoAutomate;

import java.util.Arrays;

public class OpAriUni extends Valeur{
    private Valeur type;
    private char op=' ';
    private char [] opList={'#'};
    
    public boolean set (String exp, int position, int nbVoisins, Valeur [] var) {
        if (exp.length()+1<position || !Arrays.toString(opList).contains(""+exp.charAt(position))) {
            return false;
        }
        op=exp.charAt(position);
        String exp1=(new Immediat ()).deParenthesage(exp.substring(1,exp.length()));
        type=getVal(exp1,nbVoisins,var);
        if (type!=null) {
            return true;
        }
        return false;
    }
    
    public double get (Tableau tab, int [][] voisins, int [] indices) {
        switch (op) {
            case '#': {
                double res=0;
                if (voisins!=null) {
                    double val=type.get(tab, voisins, indices);
                    for (int i=0;i<voisins.length;i++) {
                        if (tab.getVal(voisins[i])==val) {
                            res++;
                        }
                    }
                }
                return res;
            }
        }
        return 0;
    }
    
    public String getExp () {
        return op+"("+type.getExp()+")";
    }

    public int getOp (String exp) {
        if (exp.length()>0 && Arrays.toString(opList).contains(""+exp.charAt(0))) {
            return 0;
        }
        return -1;
    }
}