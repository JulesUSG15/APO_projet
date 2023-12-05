package src;



public class Main {

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


