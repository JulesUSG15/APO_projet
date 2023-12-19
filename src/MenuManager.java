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

    // Méthodes pour gérer les interactions spécifiques à chaque automate

    public void gererInteractionAutomate1D(Automate1D automate1D) {
        System.out.println("Simulation de l'automate 1D");
    
        // Demander à l'utilisateur le numéro de la règle
        System.out.print("Veuillez entrer le numéro de la règle (ex. 30) : ");
        int numeroRegle = scanner.nextInt();
    
        // Créer l'automate 1D avec la règle spécifiée
        automate1D.initialiserAvecRegle(numeroRegle);
    
        // Demander à l'utilisateur la configuration initiale
        System.out.print("Veuillez entrer la configuration initiale (ex. 1001010) : ");
        String configurationInitiale = scanner.next();
    
        // Initialiser la configuration initiale
        automate1D.initialiserConfiguration(configurationInitiale);
    
        // Demander à l'utilisateur le nombre d'étapes de simulation
        System.out.print("Veuillez entrer le nombre d'étapes de simulation : ");
        int nombreEtapes = scanner.nextInt();
    
        // Effectuer la simulation
        for (int etape = 0; etape < nombreEtapes; etape++) {
            System.out.println("Étape " + (etape + 1) + " : " + automate1D);
            automate1D.appliquerRegle();
        }
    }
}
