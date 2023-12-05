package src;

public class AutomateMajorite implements ReglesAutomate {

    private int[][] configuration;

    public void initialiserAvecTaille(int taille) {
        configuration = new int[taille][taille];
    }

    public void initialiserConfiguration(String configurationInitiale) {
        for (int i = 0; i < configurationInitiale.length(); i++) {
            configuration[0][i] = Character.getNumericValue(configurationInitiale.charAt(i));
        }
    }

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

        // Décalages des voisins par rapport à la position actuelle
        int[][] deplacementsVoisins = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] deplacement : deplacementsVoisins) {
            int voisin = getValeurVoisin(configurationActuelle, ligne, colonne, deplacement[0], deplacement[1]);
            voisinageTotal += voisin;
        }

        int majorite = (voisinageTotal > (deplacementsVoisins.length) / 2) ? 1 : 0;

        return majorite;
    }

    private int getValeurVoisin(int[][] configurationActuelle, int ligne, int colonne, int offsetLigne, int offsetColonne) {
        int nouvelleLigne = (ligne + offsetLigne + configurationActuelle.length) % configurationActuelle.length;
        int nouvelleColonne = (colonne + offsetColonne + configurationActuelle[0].length) % configurationActuelle[0].length;
        return configurationActuelle[nouvelleLigne][nouvelleColonne];
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < configuration.length; i++) {
            for (int j = 0; j < configuration[i].length; j++) {
                sb.append(configuration[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
