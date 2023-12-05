package src;

public class AutomateMajorite implements ReglesAutomate {

    @Override
    public int[][] appliquerRegles(int[][] configurationActuelle, int[][] voisinage) {
        int hauteur = configurationActuelle.length;
        int largeur = configurationActuelle[0].length;

        int[][] nouvelleConfiguration = new int[hauteur][largeur];

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                nouvelleConfiguration[i][j] = appliquerRegleMajorite(configurationActuelle, i, j);
            }
        }

        return nouvelleConfiguration;
    }

    private int appliquerRegleMajorite(int[][] configurationActuelle, int ligne, int colonne) {
        int voisinageTotal = 0;

        for (int[] position : configurationActuelle) {
            int voisin = getValeurVoisin(configurationActuelle, ligne, colonne, position[0], position[1]);
            voisinageTotal += voisin;
        }

        int majorite = (voisinageTotal > (configurationActuelle.length * configurationActuelle[0].length) / 2) ? 1 : 0;

        return majorite;
    }

    private int getValeurVoisin(int[][] configurationActuelle, int ligne, int colonne, int offsetLigne, int offsetColonne) {
        int nouvelleLigne = (ligne + offsetLigne + configurationActuelle.length) % configurationActuelle.length;
        int nouvelleColonne = (colonne + offsetColonne + configurationActuelle[0].length) % configurationActuelle[0].length;
        return configurationActuelle[nouvelleLigne][nouvelleColonne];
    }
}

