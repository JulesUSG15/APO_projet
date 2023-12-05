package src;

public class Automate1D implements ReglesAutomate {

    @Override
    public int[][] appliquerRegles(int[][] configurationActuelle, int[][] voisinage) {
        int hauteur = configurationActuelle.length;
        int largeur = configurationActuelle[0].length;

        int[][] nouvelleConfiguration = new int[hauteur][largeur];

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                nouvelleConfiguration[i][j] = appliquerRegle1D(configurationActuelle, i, j);
            }
        }

        return nouvelleConfiguration;
    }

    private int appliquerRegle1D(int[][] configurationActuelle, int ligne, int colonne) {
        int voisinGauche = (colonne == 0) ? configurationActuelle[ligne][configurationActuelle[0].length - 1] : configurationActuelle[ligne][colonne - 1];
        int voisinDroit = (colonne == configurationActuelle[0].length - 1) ? configurationActuelle[ligne][0] : configurationActuelle[ligne][colonne + 1];

        // Applique ici la logique spécifique des règles pour les automates cellulaires 1D
        // Exemple simple : la cellule prend la valeur de sa voisine de droite
        return voisinDroit;
    }
}

