package src.autoAutomate;

public class OpAriBin extends Valeur {
    private Valeur val1=null;
    private Valeur val2=null;
    private String op="";
    private String [][] opList={{"+","-"},{"%"},{"*","/"},{"^"}};
    
    public boolean set (String exp, int position, int nbVoisins, Variable [] var, String [] erreur, int dim) {
        if (exp.length()<=position) {
            erreur[0]="Impossible de convertir "+exp+" en operation arithmetique binaire";
            return false;
        }
        boolean b=false;
        int debut=0;
        for (int j=0;j<opList.length;j++) {
            for (int i=0;i<opList[j].length;i++) {
                debut=position-opList[j][i].length()+1;
                if (0<=debut && exp.substring(debut,position+1).equals(opList[j][i])) {
                    b=true;
                    op=opList[j][i];
                }
            }
        }
        if (!b) {
            erreur[0]="Aucune operation arithmetique binaire ne correspond à "+exp;
            return false;
        }
        String exp1=(new Immediat ()).deParenthesage(exp.substring(0,debut));
        String exp2=(new Immediat ()).deParenthesage(exp.substring(position+1,exp.length()));
        val1=(new Immediat ()).getVal(exp1,nbVoisins,var,erreur,dim);
        if (val1==null) {
            return false;
        }
        val2=(new Immediat ()).getVal(exp2,nbVoisins,var,erreur,dim);
        if (val2==null) {
            return false;
        }
        return true;
    }
    
    public double get (Tableau tab, int [][] voisins, int [] indices) {
        switch (op) {
            case "+": return val1.get(tab,voisins,indices)+val2.get(tab,voisins,indices);
            case "-": return val1.get(tab,voisins,indices)-val2.get(tab,voisins,indices);
            case "%": return val1.get(tab,voisins,indices)%val2.get(tab,voisins,indices);
            case "*": return val1.get(tab,voisins,indices)*val2.get(tab,voisins,indices);
            case "/": return val1.get(tab,voisins,indices)/val2.get(tab,voisins,indices);
            case "^": return Math.pow(val1.get(tab,voisins,indices),val2.get(tab,voisins,indices));
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
        int debut;
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
                        if (par==0) {
                            for (int k=0;k<opList[j].length;k++) {
                                debut=i-opList[j][k].length()+1;
                                if (0<=debut && exp.substring(debut,i+1).equals(opList[j][k])) {
                                    return i;
                                }
                            }
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