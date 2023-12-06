package src;
import java.util.Arrays;
import java.util.Scanner;

public class JeuDeLaVie implements ReglesAutomate {

    private int[][] configuration;

    public void initialiserAvecTailleEtConfigurationInitiale(Scanner scanner) {
        System.out.print("Veuillez entrer la taille de la grille (ex. 3) : ");
        int taille = scanner.nextInt();

        configuration = new int[taille][taille];

        System.out.println("Veuillez entrer la configuration initiale (vivante: X, morte: O) : ");
        for (int i = 0; i < taille; i++) {
            String ligne = scanner.next();
            for (int j = 0; j < taille; j++) {
                configuration[i][j] = (ligne.charAt(j) == 'X') ? 1 : 0;
            }
        }
    }

    @Override
    public int[][] appliquerRegles(int[][] configurationActuelle, int[][] voisinage) {
        int hauteur = configurationActuelle.length;
        int largeur = configurationActuelle[0].length;

        int[][] nouvelleConfiguration = new int[hauteur][largeur];

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                nouvelleConfiguration[i][j] = appliquerRegleJeuDeLaVie(configurationActuelle, i, j);
            }
        }

        return nouvelleConfiguration;
    }

    private int appliquerRegleJeuDeLaVie(int[][] configurationActuelle, int ligne, int colonne) {
        int nombreVoisinsVivants = compterVoisinsVivants(configurationActuelle, ligne, colonne);

        // Règles du Jeu de la Vie
        if (configurationActuelle[ligne][colonne] == 1) {
            if (nombreVoisinsVivants < 2 || nombreVoisinsVivants > 3) {
                return 0; // Mort par sous-population ou surpopulation
            } else {
                return 1; // Survit
            }
        } else {
            if (nombreVoisinsVivants == 3) {
                return 1; // Naissance
            } else {
                return 0; // Reste mort
            }
        }
    }

    private int compterVoisinsVivants(int[][] configurationActuelle, int ligne, int colonne) {
        int nombreVoisinsVivants = 0;

        // Décalages des voisins par rapport à la position actuelle
        int[][] deplacementsVoisins = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] deplacement : deplacementsVoisins) {
            int voisin = getValeurVoisin(configurationActuelle, ligne, colonne, deplacement[0], deplacement[1]);
            nombreVoisinsVivants += voisin;
        }

        return nombreVoisinsVivants;
    }

    private int getValeurVoisin(int[][] configurationActuelle, int ligne, int colonne, int offsetLigne, int offsetColonne) {
        int nouvelleLigne = (ligne + offsetLigne + configurationActuelle.length) % configurationActuelle.length;
        int nouvelleColonne = (colonne + offsetColonne + configurationActuelle[0].length) % configurationActuelle[0].length;
        return configurationActuelle[nouvelleLigne][nouvelleColonne];
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int[] row : configuration) {
            for (int cell : row) {
                result.append((cell == 1) ? "X" : "O");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public int[][] getConfiguration() {
        return configuration;
    }

    public int[][] getVoisinage() {
        int[][] voisinage = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };
        return voisinage;
    }
}
