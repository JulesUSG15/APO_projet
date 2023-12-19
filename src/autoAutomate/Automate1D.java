package src.autoAutomate;

import java.util.Scanner;

public class Automate1D {
    public static void main(String[] args) {
        ReglesAutomate1D regles = new ReglesAutomate1D();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Simulation de l'automate 1D");

        // Demander à l'utilisateur le numéro de la règle
        System.out.print("Veuillez entrer le numéro de la règle (ex. 30) : ");
        int numeroRegle = scanner.nextInt();
        regles.initialiserAvecRegle(numeroRegle);

        // Demander à l'utilisateur la configuration initiale
        System.out.print("Veuillez entrer la configuration initiale (ex. 1001010) : ");
        String configurationInitiale = scanner.next();
        regles.initialiserConfiguration(configurationInitiale);

        // Demander à l'utilisateur le nombre d'étapes de simulation
        System.out.print("Veuillez entrer le nombre d'étapes de simulation : ");
        int nombreEtapes = scanner.nextInt();

        // Effectuer la simulation
        for (int etape = 0; etape < nombreEtapes; etape++) {
            System.out.println("Étape " + (etape + 1) + " : " + regles);
            regles.appliquerRegle();
        }

        scanner.close();
    }
}