package src.autoAutomate;

public class Etude extends Valeur{
    private String op="";
    private String [] opList={"maximum","minimum","majorite","minorite","moyenne","total"};
    
    public boolean set (String exp, int position, int nbVoisins, Variable [] var, String [] erreur) {
        if (exp.length()<=position) {
            erreur[0]="Impossible de convertir "+exp+" en valeur d'etude";
            return false;
        }
        boolean b=false;
        for (int i=0;i<opList.length;i++) {
            if (exp.equals(opList[i])) {
                b=true;
                op=opList[i];
                break;
            }
        }
        if (!b) {
            erreur[0]="Aucune valeur d'etude ne correspond à "+exp;
            return false;
        }
        return true;
    }
    
    public double get (Tableau tab, int [][] voisins, int [] indices) {
        double [] vals=new double [voisins.length];
        for (int i=0;i<vals.length;i++) {
            vals[i]=tab.getVal(voisins[i]);
        }
        switch (op) {
            case "maximum": return maximum(vals);
            case "minimum": return minimum(vals);
            case "majorite": return majorite(vals);
            case "minorite": return minorite(vals);
            case "moyenne": return moyenne(vals);
            case "total": return total(vals);
        }
        return 0;
    }
    
    public String getExp () {
        return op;
    }

    public int getOp (String exp) {
        if (exp.length()==0) {
            return -1;
        }
        int par=0;
        int debut;
        for (int i=exp.length()-1;i>=0;i--) {
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

    private double maximum (double [] vals) {
        double val0=vals[0];
        for (int i=1;i<vals.length;i++) {
            if (val0<vals[i]) {
                val0=vals[i];
            }
        }
        return val0;
    }

    private double minimum (double [] vals) {
        double val0=vals[0];
        for (int i=1;i<vals.length;i++) {
            if (val0>vals[i]) {
                val0=vals[i];
            }
        }
        return val0;
    }

    private double majorite (double [] vals) {
        double [] valuni=new double [vals.length];
        double [] ocu=new double [vals.length];
        int nb=0;
        boolean b;
        for (int i=0;i<vals.length;i++) {
            b=false;
            for (int j=0;j<nb;j++) {
                if (vals[i]==valuni[j]) {
                    b=true;
                    ocu[j]++;
                }
            }
            if (!b) {
                valuni[nb]=vals[i];
                ocu[nb]=1;
                nb++;
            }
        }
        double val0=valuni[0];
        double nb0=ocu[0];
        for (int i=1;i<valuni.length;i++) {
            if (nb0<ocu[i]) {
                nb0=ocu[i];
                val0=valuni[i];
            }
        }
        return val0;
    }

    private double minorite (double [] vals) {
        double [] valuni=new double [vals.length];
        double [] ocu=new double [vals.length];
        int nb=0;
        boolean b;
        for (int i=0;i<vals.length;i++) {
            b=false;
            for (int j=0;j<nb;j++) {
                if (vals[i]==valuni[j]) {
                    b=true;
                    ocu[j]++;
                }
            }
            if (!b) {
                valuni[nb]=vals[i];
                ocu[nb]=1;
                nb++;
            }
        }
        double val0=valuni[0];
        double nb0=ocu[0];
        for (int i=1;i>valuni.length;i++) {
            if (nb0<ocu[i]) {
                nb0=ocu[i];
                val0=valuni[i];
            }
        }
        return val0;
    }

    private double moyenne (double [] vals) {
        return total(vals)/vals.length;
    }

     private double total (double [] vals) {
        double tot=0;
        for (int i=0;i<vals.length;i++) {
            tot+=vals[i];
        }
        return tot;
     }

}