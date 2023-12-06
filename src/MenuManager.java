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

    public void gererInteractionAutomateMajorite() {
        System.out.println("Simulation de l'automate de majorité");
    
        // Demander à l'utilisateur la taille de la configuration initiale
        System.out.print("Veuillez entrer la taille de la configuration initiale (ex. 7) : ");
        int tailleConfiguration = scanner.nextInt();
    
        // Créer l'automate de majorité avec la taille spécifiée
        AutomateMajorite automateMajorite = new AutomateMajorite();
        automateMajorite.initialiserAvecTaille(tailleConfiguration);
    
        // Demander à l'utilisateur la configuration initiale
        System.out.print("Veuillez entrer la configuration initiale (ex. 010101) : ");
        String configurationInitiale = scanner.next();
    
        // Initialiser la configuration initiale
        automateMajorite.initialiserConfiguration(configurationInitiale);
    
        // Demander à l'utilisateur le nombre d'étapes de simulation
        System.out.print("Veuillez entrer le nombre d'étapes de simulation : ");
        int nombreEtapes = scanner.nextInt();
    
        // Effectuer la simulation
        for (int etape = 0; etape < nombreEtapes; etape++) {
            System.out.println("Étape " + (etape + 1) + " : " + automateMajorite);
            // Utilisez la méthode appliquerRegles avec les bons arguments
            automateMajorite.appliquerRegles(automateMajorite.getConfiguration(), automateMajorite.getVoisinage());
        }
    }
    

    public void gererInteractionJeuDeLaVie() {
        System.out.println("Simulation du Jeu de la Vie");

        JeuDeLaVie jeuDeLaVie = new JeuDeLaVie();
        jeuDeLaVie.initialiserAvecTailleEtConfigurationInitiale(scanner);

        System.out.print("Veuillez entrer le nombre d'étapes de simulation : ");
        int nombreEtapes = scanner.nextInt();

        // Effectuer la simulation
        for (int etape = 0; etape < nombreEtapes; etape++) {
            System.out.println("Étape " + (etape + 1) + ":");
            System.out.println(jeuDeLaVie);
            jeuDeLaVie.appliquerRegles(jeuDeLaVie.getConfiguration(), jeuDeLaVie.getVoisinage());
        }
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
                    Automate1D automate1D = new Automate1D(); // Création de l'objet Automate1D
                    menuManager.gererInteractionAutomate1D(automate1D); // Passage de l'objet en paramètre
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
