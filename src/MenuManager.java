package src;

import java.util.Scanner;

public class MenuManager {

    private Scanner scanner;

    public MenuManager() {
        this.scanner = new Scanner(System.in);
    }

    public void afficherMenu() {
        System.out.println("1. Automate 1D");
        System.out.println("2. Automate de majorité");
        System.out.println("3. Jeu de la Vie");
        System.out.println("4. Feu de forêt");
        System.out.println("0. Quitter");
        System.out.print("Choisissez une option : ");
    }

    public int lireChoixUtilisateur() {
        return scanner.nextInt();
    }

    public void fermerScanner() {
        scanner.close();
    }

    // Méthodes pour gérer les interactions spécifiques à chaque automate (à implémenter)

    public void gererInteractionAutomate1D() {
        // Implémentez les interactions spécifiques à l'automate 1D
    }

    public void gererInteractionAutomateMajorite() {
        // Implémentez les interactions spécifiques à l'automate de majorité
    }

    public void gererInteractionJeuDeLaVie() {
        // Implémentez les interactions spécifiques au Jeu de la Vie
    }

    public void gererInteractionFeuDeForet() {
        // Implémentez les interactions spécifiques au feu de forêt
    }

    public static void main(String[] args) {
        MenuManager menuManager = new MenuManager();

        int choix;
        do {
            menuManager.afficherMenu();
            choix = menuManager.lireChoixUtilisateur();

            switch (choix) {
                case 1:
                    menuManager.gererInteractionAutomate1D();
                    break;
                case 2:
                    menuManager.gererInteractionAutomateMajorite();
                    break;
                case 3:
                    menuManager.gererInteractionJeuDeLaVie();
                    break;
                case 4:
                    menuManager.gererInteractionFeuDeForet();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
                    break;
            }
        } while (choix != 0);

        menuManager.fermerScanner();
    }
}
