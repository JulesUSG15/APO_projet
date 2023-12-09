package src;

public class Superieur extends Condition {
    Valeur val1=null;
    Valeur val2=null;
    
    public boolean set (String exp, int nbVoisins) {
        String [] val=exp.split(">");
        if (val.length!=2) {
            return false;
        }
        if (val[0].length()<1 || val[1].length()<1) {
            return false;
        }
        val1=(new Immediat ()).getVal(val[0].charAt(0));
        if (!val1.set(val[0],nbVoisins)) {
            return false;
        }
        while (val[1].length()>0 && val[1].charAt(0)==' ') {
            val[1]=val[1].substring(1,val[1].length());
        }
        val2=(new Immediat ()).getVal(val[1].charAt(0));
        if (!val2.set(val[1],nbVoisins)) {
            return false;
        }
        return true;
    }
    
    public boolean get (Tableau tab, int [][] voisins, int [] indices) {
        return (val1.get(tab,voisins,indices)>val2.get(tab,voisins,indices));
    }
    
    public String getExp () {
        return val1.getExp()+">"+val2.getExp();
    }
}