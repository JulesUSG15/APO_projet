package src.autoAutomate;

import java.util.Arrays;

public class OpAriBin extends Valeur {
    private Valeur val1=null;
    private Valeur val2=null;
    private char op=' ';
    private char [][] opList={{'+','-'},{'*','/'},{'^'}};
    
    public boolean set (String exp, int position, int nbVoisins, Valeur [] var) {
        if (exp.length()<=position) {
            return false;
        }
        boolean b=false;
        for (int i=0;i<opList.length;i++) {
            b=b || Arrays.toString(opList[i]).contains(""+exp.charAt(position));
        }
        if (!b) {
            return false;
        }
        op=exp.charAt(position);
        String exp1=(new Immediat ()).deParenthesage(exp.substring(0,position));
        String exp2=(new Immediat ()).deParenthesage(exp.substring(position+1,exp.length()));
        val1=(new Immediat ()).getVal(exp1,nbVoisins,var);
        if (val1==null) {
            return false;
        }
        val2=(new Immediat ()).getVal(exp2,nbVoisins,var);
        if (val2==null) {
            return false;
        }
        return true;
    }
    
    public double get (Tableau tab, int [][] voisins, int [] indices) {
        switch (op) {
            case '+': return val1.get(tab,voisins,indices)+val2.get(tab,voisins,indices);
            case '-': return val1.get(tab,voisins,indices)-val2.get(tab,voisins,indices);
            case '*': return val1.get(tab,voisins,indices)*val2.get(tab,voisins,indices);
            case '/': return val1.get(tab,voisins,indices)/val2.get(tab,voisins,indices);
            case '^': return Math.pow(val1.get(tab,voisins,indices),val2.get(tab,voisins,indices));
        }   
        return 0;
    }
    
    public String getExp () {
        return "("+val1.getExp()+op+val2.getExp()+")";
    }

    public int getOp (String exp) {
        if (exp.length()<3) {
            return -1;
        }
        for (int j=0;j<opList.length;j++) {
            int par=0;
            for (int i=exp.length()-1;i>=1;i--) {
                if (exp.charAt(i)=='(') {
                    par--;
                }
                else {
                    if (exp.charAt(i)==')') {
                        par++;
                    }
                    else {
                        if (Arrays.toString(opList[j]).contains(""+exp.charAt(i)) && par==0) {
                            return i;
                        }
                    }
                }
                if (par<0) {
                    return -1;
                }
            }
        }
        return -1;
    }
}