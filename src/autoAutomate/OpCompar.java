package src.autoAutomate;

public class OpCompar extends Condition{
    private Valeur val1=null;
    private Valeur val2=null;
    private String op="";
    private String [] opList={"<=",">=","=","<",">"};
    
    public boolean set (String exp, int position, int nbVoisins, Variable [] var) {
        if (exp.length()<=position) {
            return false;
        }
        boolean b=false;
        int debut=0;
        for (int i=0;i<opList.length;i++) {
            debut=position-opList[i].length()+1;
            if (0<=debut && exp.substring(debut,position+1).equals(opList[i])) {
                b=true;
                op=opList[i];
                break;
            }
        }
        if (!b) {
            return false;
        }
        String exp1=(new Immediat ()).deParenthesage(exp.substring(0,debut));
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
    
    public boolean get (Tableau tab, int [][] voisins, int [] indices) {
        switch (op) {
            case "<=": return val1.get(tab,voisins,indices) <= val2.get(tab,voisins,indices);
            case ">=": return val1.get(tab,voisins,indices) >= val2.get(tab,voisins,indices);
            case "=": return val1.get(tab,voisins,indices) == val2.get(tab,voisins,indices);
            case "<": return val1.get(tab,voisins,indices) < val2.get(tab,voisins,indices);
            case ">": return val1.get(tab,voisins,indices) > val2.get(tab,voisins,indices);
        }
        return false;
    }
    
    public String getExp () {
        return val1.getExp()+op+val2.getExp();
    }

    public int getOp (String exp) {
        if (exp.length()==0) {
            return -1;
        }
        int par=0;
        int debut;
        for (int i=exp.length()-1;i>=1;i--) {
            if (exp.charAt(i)=='(') {
                par--;
            }
            else {
                if (exp.charAt(i)==')') {
                    par++;
                }
                else {
                    for (int k=0;k<opList.length;k++) {
                        debut=i-opList[k].length()+1;
                        if (0<=debut && exp.substring(debut,i+1).equals(opList[k])) {
                            return i;
                        }
                    }
                }
            }
            if (par<0) {
                return -1;
            }
        }
        return -1;
    }
}