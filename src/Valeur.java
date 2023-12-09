
public abstract class Valeur {
    
    public abstract boolean set (String exp, int nbVoisins);
    
    public abstract int get (Tableau tab, int [][] voisins, int [] indices);
    
    public abstract String getExp ();
    
    public Valeur getVal (char op) {
        switch (op) {
            case '$':return new Voisin ();
            case '#':return new Etat ();
        }
        return new Immediat ();
    }
    
    public boolean getInt (String exp,int val []) {
        while (exp.length()>0 && exp.charAt(0)==' ') {
            exp=exp.substring(1,exp.length());
        }
        if (!(exp.length()>0)) {
            return false;
        }
        boolean neg=false;
        int i=0;
        if (exp.charAt(0)=='-') {
            neg=true;
            i++;
        }
        val[0]=0;
        while (i<exp.length() && '0'<=exp.charAt(i) && exp.charAt(i)<='9') {
            val[0]=10*val[0]+exp.charAt(i)-'0';
            i++;
        }
        if ((neg && i==1) || (!neg && i==0)) {
            return false;
        }
        if (neg) {
            val[0]*=-1;
        }
        return true;
    }
}