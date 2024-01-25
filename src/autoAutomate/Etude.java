package src.autoAutomate;

import java.util.Random;

public class Etude extends Valeur{
    private String op="";
    private String [] opList={"maximum","minimum","majorite","minorite","moyenne","total", "taille"};
    
    public boolean set (String exp, int position, int nbVoisins, Variable [] var, String [] erreur, int dim) {
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
    
    public double get (Tableau tab, double [] voisins, int [] indices) {
        switch (op) {
            case "maximum": return maximum(voisins);
            case "minimum": return minimum(voisins);
            case "majorite": return majorite(voisins);
            case "minorite": return minorite(voisins);
            case "moyenne": return moyenne(voisins);
            case "total": return total(voisins);
            case "taille": return tab.getTaille();
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
        Random random = new Random();
        int occurrence=1;
        for (int i=1;i<nb;i++) {
            if (nb0<ocu[i]) {
                nb0=ocu[i];
                val0=valuni[i];
                occurrence=1;
            }
            if (nb0==ocu[i]) {
                occurrence++;
                if (random.nextInt(1000)<1000/occurrence) {
                    val0=valuni[i];
                }
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
        Random random = new Random();
        int occurrence=1;
        for (int i=1;i<nb;i++) {
            if (nb0>ocu[i]) {
                nb0=ocu[i];
                val0=valuni[i];
                occurrence=1;
            }
            if (nb0==ocu[i]) {
                occurrence++;
                if (random.nextInt(1000)<1000/occurrence) {
                    val0=valuni[i];
                }
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
