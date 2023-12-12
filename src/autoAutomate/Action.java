package src.autoAutomate;

public class Action {
    private Valeur [] proba=null;
    private Valeur [] valeurs=null;
    private int nbActions=0;
    
    public boolean set (String exp, int nbVoisins) {
        String [] exps=exp.split(",");
        if (exps==null || exps.length==0) {
            return false;
        }
        nbActions=exps.length;
        proba=new Valeur [nbActions];
        valeurs=new Valeur [nbActions];
        for (int i=0; i<nbActions; i++) {
            String [] act=exps[i].split(":");
            if (act.length!=2) {
                return false;
            }
            if (act[0].length()<1 || act[1].length()<1) {
                return false;
            }
            act[0]=(new Immediat ()).deParenthesage(act[0]);
            proba[i]=(new Immediat ()).getVal(act[0],nbVoisins);
            if (proba[i]==null) {
                return false;
            }
            act[1]=(new Immediat ()).deParenthesage(act[1]);
            valeurs[i]=(new Immediat ()).getVal(act[1],nbVoisins);
            if (valeurs[i]==null) {
                return false;
            }
        }
        return true;
    }
    
    public double get (Tableau tab, int [][] voisins, int [] indices) {
        int nb=getNbActions();
        if (nb<1) 
            return -1;
        double [] tirage;
        tirage=new double[nb];
        tirage[0]=Math.abs(proba[0].get(tab, voisins, indices));
        for (int i=1;i<nb;i++) {
            tirage[i]=tirage[i-1]+Math.abs(proba[i].get(tab, voisins, indices));
        }
        double rand=tirage[nb-1]*Math.random();
        for (int i=0;i<nb;i++) {
            if (rand<=tirage[i]) {
                return valeurs[i].get(tab,voisins,indices);
            }
        }
        return -1;
    }
    
    private int getNbActions () {
        if (proba==null) {
            return 0;
        }
        return proba.length;
    }
    
    public String getExp () {
        String exp=proba[0].getExp()+":"+valeurs[0].getExp();
        for (int i=1;i<getNbActions();i++) {
            exp+=", "+proba[i].getExp()+":"+valeurs[i].getExp();
        }
        exp+="; ";
        return exp;
    }
}