package src;

import java.util.Random;

public class FeuDeForet implements ReglesAutomate {

    private double probabilitePropagation;
    private double probabiliteIgnition;

    public FeuDeForet(double probabilitePropagation, double probabiliteIgnition) {
        this.probabilitePropagation = probabilitePropagation;
        this.probabiliteIgnition = probabiliteIgnition;
    }

    @Override
    public int[][] appliquerRegles(int[][] configurationActuelle, int[][] voisinage) {
        int hauteur = configurationActuelle.length;
        int largeur = configurationActuelle[0].length;

        int[][] nouvelleConfiguration = new int[hauteur][largeur];

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                nouvelleConfiguration[i][j] = appliquerRegleFeuDeForet(configurationActuelle, i, j);
            }
        }

        return nouvelleConfiguration;
    }

    private int appliquerRegleFeuDeForet(int[][] configurationActuelle, int ligne, int colonne) {
        int etatCellule = configurationActuelle[ligne][colonne];

        if (etatCellule == 0) {
            // La cellule est vide
            return 0;
        } else if (etatCellule == 1) {
            // La cellule est une forêt
            if (voisinageEnFeu(configurationActuelle, ligne, colonne) && Math.random() < probabilitePropagation) {
                return 2; // La forêt prend feu
            } else {
                return 1; // La forêt reste inchangée
            }
        } else {
            // La cellule est en feu
            return 3; // La cellule brûlée
        }
    }

    private boolean voisinageEnFeu(int[][] configurationActuelle, int ligne, int colonne) {
        int hauteur = configurationActuelle.length;
        int largeur = configurationActuelle[0].length;

        for (int i = Math.max(0, ligne - 1); i <= Math.min(hauteur - 1, ligne + 1); i++) {
            for (int j = Math.max(0, colonne - 1); j <= Math.min(largeur - 1, colonne + 1); j++) {
                if (!(i == ligne && j == colonne) && configurationActuelle[i][j] == 2) {
                    return true; // Au moins un voisin est en feu
                }
            }
        }
        return false; // Aucun voisin en feu
    }
}

