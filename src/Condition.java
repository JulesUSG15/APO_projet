
public abstract class Condition {
    
    public abstract boolean set (String exp, int nbVoisins);
    
    public abstract boolean get (Tableau tab, int [][] voisins, int [] indices);
    
    public abstract String getExp ();
    
    public int getOp (String exp) {
        if (exp.length()==0) {
            return -1;
        }
        boolean test=true;
        int par=0;
        for (int i=0;i<exp.length();i++) {
            switch (exp.charAt(i)) {
                case '(': par++;break;
                case ')':par--;break;
                case '&': {
                    if (par==0) {
                        return i;
                    }
                }break;
                case '|': {
                    if (par==0) {
                        return i;
                    }
                }break;
            }
            if (par==0) {
                test=true;
            }
            else {
                if (par<0) {
                    return -1;
                }
            }
        }
        if (exp.charAt(0)=='!') {
            return 0;
        }
        par=0;
        for (int i=0;i<exp.length();i++) {
            switch (exp.charAt(i)) {
                case '(': par++;break;
                case ')':par--;break;
                case '=': {
                    if (par==0) {
                        return i;
                    }
                }break;
                case '<': {
                    if (par==0) {
                        return i;
                    }
                }break;
                case '>': {
                    if (par==0) {
                        return i;
                    }
                }break;
            }
        }
        return -1;
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
    
    public Condition getCond (char op) {
        switch (op) {
            case '&':return new Et ();
            case '|':return new Ou ();
            case '!':return new Non ();
            case '=':return new Egal ();
            case '<':return new Inferieur ();
            case '>':return new Superieur ();
        }
        return null;
    }
}