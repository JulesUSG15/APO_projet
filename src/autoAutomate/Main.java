package src.autoAutomate;

public class Main {
    
    public static void main(String[] args) {
        Regles reg=new Regles();
        System.out.println(reg.charger("data/jeu_vie.dac"));
        System.out.println(reg.getExp());
        Tableau tab=new Tableau (2,11);
        tab.setVal(4,4,1);
        tab.setVal(4,5,1);
        tab.setVal(4,6,1);
        tab.setVal(5,3,1);
        tab.setVal(5,7,1);
        tab.setVal(6,4,1);
        tab.setVal(6,5,1);
        tab.setVal(6,6,1);
        afficher(tab);
        for (int i=0;i<8;i++) {
            tab=reg.appliquer(tab);
            afficher(tab);
        }
    }

    public static void afficher (Tableau tab) {
        for (int j=0;j<tab.getTaille();j++) {
            for (int i=0;i<tab.getTaille();i++) {
                System.out.print((int)tab.getVal(j,i)+" ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
