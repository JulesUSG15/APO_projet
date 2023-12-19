package src.autoAutomate;

import java.util.Random;
import java.util.Scanner;

public class JeuDeLaVie {
    
    public static void main(String[] args) {
        Regles reg=new Regles();
        System.out.println(reg.charger("data/jeu_vie.dac"));
        System.out.println(reg.getExp());

        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez entrer la taille du tableau : ");
        int taille = scanner.nextInt();

        Tableau tab=new Tableau (2, taille);

        System.out.println("Voulez-vous remplir le tableau manuellement ou de manière aléatoire ?");
        System.out.println("1. Manuellement");
        System.out.println("2. Aléatoirement");
        int choix = scanner.nextInt();

        if (choix == 1) {
            for (int j = 0; j < taille; j++) {
                for (int i = 0; i < taille; i++) {
                    System.out.print("Veuillez entrer la valeur pour la position (" + j + ", " + i + ") : ");
                    int valeur = scanner.nextInt();
                    tab.setVal(j, i, valeur);
                }
            }
        } else {
            initialiserTableauAleatoire(tab);
        }

        afficher(tab);
        for (int i=0;i<8;i++) {
            tab=reg.appliquer(tab);
            afficher(tab);
        }

        scanner.close();
    }

    public static void initialiserTableauAleatoire(Tableau tab) {
        Random random = new Random();
        for (int j = 0; j < tab.getTaille(); j++) {
            for (int i = 0; i < tab.getTaille(); i++) {
                tab.setVal(j, i, random.nextInt(2));
            }
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