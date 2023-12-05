package src;

public class JeuDeLaVie implements ReglesAutomate {

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

        for (int[] position : configurationActuelle) {
            int voisin = getValeurVoisin(configurationActuelle, ligne, colonne, position[0], position[1]);
            nombreVoisinsVivants += voisin;
        }

        return nombreVoisinsVivants;
    }

    private int getValeurVoisin(int[][] configurationActuelle, int ligne, int colonne, int offsetLigne, int offsetColonne) {
        int nouvelleLigne = (ligne + offsetLigne + configurationActuelle.length) % configurationActuelle.length;
        int nouvelleColonne = (colonne + offsetColonne + configurationActuelle[0].length) % configurationActuelle[0].length;
        return configurationActuelle[nouvelleLigne][nouvelleColonne];
    }
}

