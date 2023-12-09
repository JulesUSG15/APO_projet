package src;

public class Action {
    private double [] proba=null;
    private Valeur [] valeurs=null;
    private int nbActions=0;
    
    public boolean set (String exp, int nbVoisins) {
        String [] exps=exp.split(",");
        if (exps==null || exps.length==0) {
            return false;
        }
        nbActions=exps.length;
        proba=new double [nbActions];
        valeurs=new Valeur [nbActions];
        for (int i=0; i<nbActions; i++) {
            String [] act=exps[i].split(":");
            if (act.length!=2) {
                return false;
            }
            proba[i]=getDouble(act[0]);
            if (act[1].length()<1) {
                return false;
            }
            while (act[1].length()>0 && act[1].charAt(0)==' ') {
                act[1]=act[1].substring(1,act[1].length());
            }
            switch (act[1].charAt(0)) {
                case '$': {
                    valeurs[i]=new Voisin();
                    if (!valeurs[i].set(act[1],nbVoisins)) {
                        return false;
                    }
                }break;
                case '#': {
                    valeurs[i]=new Etat();
                    if (!valeurs[i].set(act[1],nbVoisins)) {
                        return false;
                    }
                }break;
                default: {
                    valeurs[i]=new Immediat();
                    if (!valeurs[i].set(act[1],nbVoisins)) {
                        return false;
                    }
                }break;
            }
        }
        return true;
    }
    
    public int get (Tableau tab, int [][] voisins, int [] indices) {
        int nb=getNbActions();
        if (nb<1) 
            return -1;
        double [] tirage;
        tirage=new double[nb];
        tirage[0]=proba[0];
        for (int i=1;i<nb;i++) {
            tirage[i]=tirage[i-1]+proba[i];
        }
        double rand=tirage[nb-1]*Math.random();
        for (int i=0;i<nb;i++) {
            if (rand<=tirage[i]) {
                return valeurs[i].get(tab,voisins,indices);
            }
        }
        return -1;
    }
    
    private double getDouble (String exp) {
        while (exp.length()>0 && exp.charAt(0)==' ') {
            exp=exp.substring(1,exp.length());
        }
        double res=0;
        int i=0;
        while (i<exp.length() && '0'<=exp.charAt(i) && exp.charAt(i)<='9') {
            res=10*res+exp.charAt(i)-'0';
            i++;
        }
        if (i==0) {
            return -1;
        }
        if (i<exp.length() && exp.charAt(i)=='.') {
            double val=0.1;
            i++;
            while (i<exp.length() && '0'<=exp.charAt(i) && exp.charAt(i)<='9') {
                res+=val*(exp.charAt(i)-'0');
                val*=0.1;
                i++;
            }
        }
        return res;
    }
    
    private int getNbActions () {
        if (proba==null) {
            return 0;
        }
        return proba.length;
    }
    
    public String getExp () {
        String exp=Double.toString(proba[0])+":"+valeurs[0].getExp();
        for (int i=1;i<getNbActions();i++) {
            exp+=", "+Double.toString(proba[i])+":"+valeurs[i].getExp();
        }
        exp+="; ";
        return exp;
    }
}