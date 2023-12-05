package src;

public class ConfigurationAutomate {

    private int[][] grille;
    private int hauteur;
    private int largeur;

    public ConfigurationAutomate(int hauteur, int largeur, int[][] grille) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.grille = grille;
    }

    public int[][] getGrille() {
        return grille;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }
}
