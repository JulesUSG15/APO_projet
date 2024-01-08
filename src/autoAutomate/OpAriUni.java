package src.autoAutomate;

public class OpAriUni extends Valeur{
    private Object obj;
    private String op="";
    private String [] opList={"verif","count","#","cos","sin"};
    
    public boolean set (String exp, int position, int nbVoisins, Variable [] var, String [] erreur) {
        if (exp.length()<=position) {
            erreur[0]="Impossible de convertir "+exp+" en operation arithmetique unaire";
            return false;
        }
        boolean b=false;
        int debut=0;
        for (int i=0;i<opList.length;i++) {
            debut=position-opList[i].length()+1;
            if (0<=debut && exp.substring(debut,position+1).equals(opList[i])) {
                b=true;
                op=opList[i];
            }
        }
        if (!b) {
            erreur[0]="Aucune operation arithmetique unaire ne correspond à "+exp;
            return false;
        }
        String exp1=(new Immediat ()).deParenthesage(exp.substring(position+1,exp.length()));
        switch (op) {
            case "verif": {
                obj=(new OpLogBin ()).getCond(exp1,nbVoisins,var,erreur);
                if (obj!=null) {
                    return true;
                }
            }break;
            case "count": {
                obj=(new Immediat ()).getVal(exp1,nbVoisins,var,erreur);
                if (obj!=null) {
                    return true;
                }
            }break;
            case "#": {
                int [] val=new int [1];
                if (getInt(exp1,val)) {
                    obj=val[0];
                    return true;
                }
            }break;
            case "cos": {
                obj=(new Immediat ()).getVal(exp1,nbVoisins,var,erreur);
                if (obj!=null) {
                    return true;
                }
            }break;
            case "sin": {
                obj=(new Immediat ()).getVal(exp1,nbVoisins,var,erreur);
                if (obj!=null) {
                    return true;
                }
            }break;
        }
        return false;
    }
    
    public double get (Tableau tab, int [][] voisins, int [] indices) {
        switch (op) {
            case "verif": {
                if (((Condition)obj).get(tab, voisins, indices)) {
                    return 1;
                }
                return 0;
            }
            case "count": {
                double res=0;
                if (voisins!=null) {
                    double val=((Valeur)obj).get(tab, voisins, indices);
                    for (int i=0;i<voisins.length;i++) {
                        if (tab.getVal(voisins[i])==val) {
                            res++;
                        }
                    }
                }
                return res;
            }
            case "#": {
                if ((int)obj==0) {
                    return tab.getVal(indices);
                }
                return tab.getVal(voisins[(int)obj-1]);
            }
            case "cos": {
                return Math.cos(Math.PI*((Valeur)obj).get(tab, voisins, indices)/180);
            }
            case "sin": {
                return Math.sin(Math.PI*((Valeur)obj).get(tab, voisins, indices)/180);
            }
        }
        return 0;
    }
    
    public String getExp () {
        if (obj instanceof Condition) {
            return op+"("+((Condition)obj).getExp()+")";
        }
        if (obj instanceof Valeur) {
            return op+"("+((Valeur)obj).getExp()+")";
        }
        return op+"("+obj+")";
    }

    public int getOp (String exp) {
        if (exp.length()==0) {
            return -1;
        }
        int par=0;
        int debut;
        for (int i=0;i<exp.length();i++) {
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