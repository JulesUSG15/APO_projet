package src.autoAutomate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Automate 1D");
        System.out.println("2. Règle de majorité");
        System.out.println("3. Jeu de la vie");
        System.out.println("4. Feu de forêt");
        System.out.println("0. Quitter");
        System.out.print("Choisissez une option : ");

        int option = scanner.nextInt();

        switch (option) {
            case 1:
                Automate1D.main(args);
                break;
            case 2:
                //RegleMajorite.main(args);
                break;
            case 3:
                JeuDeLaVie j = new JeuDeLaVie();
                j.main(args);
                break;
            case 4:
                //FeuDeForet.main(args);
                break;
            case 0:
                System.out.println("Au revoir !");
                break;
            default:
                System.out.println("Option invalide. Veuillez réessayer.");
                break;
        }
        
        scanner.close();
        
    }
}