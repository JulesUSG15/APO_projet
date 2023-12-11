package src.autoAutomate;

public abstract class Valeur {
    
    public abstract boolean set (String exp, int position, int nbVoisins);
    
    public abstract double get (Tableau tab, int [][] voisins, int [] indices);
    
    public abstract String getExp ();

    public abstract int getOp (String exp);
    
    public Valeur getVal (String exp, int nbVoisins) {
        Valeur val;
        int n=(new Voisin ()).getOp(exp);
        if (n!=-1) {
            val=new Voisin ();
            if (val.set(exp,n,nbVoisins)) {
                return val;
            }
            return null;
        }
        n=(new Etat ()).getOp(exp);
        if (n!=-1) {
            val=new Etat ();
            if (val.set(exp,n,nbVoisins)) {
                return val;
            }
            return null;
        }
        n=(new Immediat ()).getOp(exp);
        if (n!=-1) {
            val=new Immediat ();
            if (val.set(exp,n,nbVoisins)) {
                return val;
            }
            return null;
        }
        return null;
    }
    
    public boolean getInt (String exp,int val []) {
        if (val.length<1) {
            return false;
        }
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

    public boolean getDouble (String exp, double val []) {
        if (val.length<1) {
            return false;
        }
        while (exp.length()>0 && exp.charAt(0)==' ') {
            exp=exp.substring(1,exp.length());
        }
        val[0]=0.0;
        int i=0;
        boolean neg=false;
        if (exp.charAt(0)=='-') {
            neg=true;
            i++;
        }
        while (i<exp.length() && '0'<=exp.charAt(i) && exp.charAt(i)<='9') {
            val[0]=10*val[0]+exp.charAt(i)-'0';
            i++;
        }
        if (i==0) {
            return false;
        }
        if (i<exp.length() && exp.charAt(i)=='.') {
            double val2=0.1;
            i++;
            while (i<exp.length() && '0'<=exp.charAt(i) && exp.charAt(i)<='9') {
                val[0]+=val2*(exp.charAt(i)-'0');
                val2*=0.1;
                i++;
            }
        }
        if (neg) {
            val[0]*=-1;
        }
        return true;
    }

    public String simplification (String exp) {
        String res=exp;
        while (res.length()>0 && res.charAt(0)==' ') {
            res=res.substring(1,res.length());
        }
        while (res.length()>0 && res.charAt(res.length()-1)==' ') {
            res=res.substring(0,res.length()-1);
        }
        int par=0;
        for (int i=0;i<res.length();i++) {
            switch (res.charAt(i)) {
                case '(':par++;break;
                case ')':par--;break;
            }
            if (par==0 && i<res.length()-1) {
                return res;
            }
        }
        if (res.length()>=2 && res.charAt(0)=='(' && res.charAt(res.length()-1)==')') {
            return simplification(res.substring(1,res.length()-1));
        }
        return res;
    }
}