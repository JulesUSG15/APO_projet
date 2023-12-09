
public class Tableau {
    private int dim;
    private int taille;
    private Object [] tab;
    
    //d la dimension du tableau et t la taille
    public Tableau (int d, int t) {
        if (d>0 && t>0) {
            dim=d;
            taille=t;
            tab=new Object [t];
            if (d>1) {
                for (int i=0;i<t;i++) {
                    tab[i]=new Tableau(d-1,t);
                }
            }
            else {
                for (int i=0;i<t;i++) {
                    tab[i]=0;
                }
            }
        }
        else {
            System.out.println("creation impossible : La dimension et la taille doivent etre superieur à 0");
            }
    }
    
    public int getDim () {
        return dim;
    }
    
    public int getTaille () {
        return taille;
    }
    
    //Les arguments sont des indices
    public int getVal (int... args) {
        if (args.length==dim) {
            int arg0=args[0];
            while (arg0<0) {
                arg0+=taille;
            }
            arg0=arg0%taille;
            if (tab[arg0] instanceof Tableau) {
                int [] args2=new int [args.length-1];
                for (int i=0;i<args.length-1;i++) {
                    args2[i]=args[i+1];
                }
                return ((Tableau)tab[arg0]).getVal(args2);
            }
            return (int)tab[arg0];
        }
        System.out.println("get impossible : "+dim+" parametres attendus");
        return -1;
    }
    
    public boolean setVal (int [] args,int val) {
        if (args.length==dim) {
            int arg0=args[0];
            while (arg0<0) {
                arg0+=taille;
            }
            arg0=arg0%taille;
            if (args.length==1) {
                tab[arg0]=val;
                return true;
            }
            else {
                int [] args2=new int [args.length-1];
                for (int i=0;i<args.length-1;i++) {
                    args2[i]=args[i+1];
                }
                return ((Tableau)tab[arg0]).setVal(args2,val);
            }
        }
        System.out.println("set impossible : "+dim+" int attendus dans le tableau");
        return false;
    }
    
    //Les premiers arguments sont les indices et le dernier est la valeur
    public boolean setVal (int... args) {
        if (args!=null && args.length==dim+1) {
            int val=args[dim];
            int [] indices=new int [dim];
            for (int i=0;i<dim;i++) {
                indices[i]=args[i];
            }
            return setVal(indices,val);
        }
        System.out.println("set impossible : "+(dim+1)+" parametres attendus");
        return false;
    }
}