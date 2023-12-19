package src.autoAutomate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Veuillez choisir une option:");
        System.out.println("1. JeuDeLaVie");
        System.out.println("2. Automate1D");

        int option = scanner.nextInt();

        switch (option) {
            case 1:
                JeuDeLaVie.main(args);
                break;
            case 2:
                // Vous devez ajouter une méthode main à votre classe Automate1D pour pouvoir l'exécuter ici
                break;
            default:
                System.out.println("Option non reconnue");
                break;
        }

        scanner.close();
    }
}