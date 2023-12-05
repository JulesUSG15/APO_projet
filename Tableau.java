
public class Tableau {
    int dim;
    int taille;
    Object [] tab;
    
    //d la dimension du tableau et t la taille
    Tableau (int d, int t) {
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
    
    //Les arguments sont des indices
    int getVal (int... args) {
        if (args.length==dim) {
            int arg0=args[0];
            if (arg0<0 || arg0>=taille) {
                System.out.println("get impossible : Les indices doivent etre inferieur à "+taille);
                return -1;
            }
            if (args.length==1) {
                return (int)tab[arg0];
            }
            int [] args2=new int [args.length-1];
            for (int i=0;i<args.length-1;i++) {
                args2[i]=args[i+1];
            }
            return ((Tableau)tab[arg0]).getVal(args2);
        }
        System.out.println("get impossible : "+dim+" parametres attendus");
        return -1;
    }
    
    //Les premiers arguments sont les indices et le dernier est la valeur
    boolean setVal (int... args) {
        if (args.length==dim+1) {
            int arg0=args[0];
            if (0<=arg0 && arg0<taille) {
                if (args.length==2) {
                    tab[arg0]=args[1];
                    return true;
                }
                else {
                    int [] args2=new int [args.length-1];
                    for (int i=0;i<args.length-1;i++) {
                        args2[i]=args[i+1];
                    }
                    return ((Tableau)tab[arg0]).setVal(args2);
                }
            }
            else {
                System.out.println("set impossible : Les indices doivent etre inferieur à "+taille);
                return false;
            }
        }
        else {
            System.out.println("set impossible : "+(dim+1)+" parametres attendus");
            return false;
        }
    }
}
