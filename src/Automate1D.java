package src;

import java.util.Arrays;

public class Automate1D implements ReglesAutomate {
    private int[] regle;
    private int[] configuration;

    public void initialiserAvecRegle(int numeroRegle) {
        regle = new int[8];
        for (int i = 0; i < 8; i++) {
            regle[i] = (numeroRegle & (1 << i)) >> i;
        }
    }

    public void initialiserConfiguration(String configurationInitiale) {
        configuration = new int[configurationInitiale.length()];
        for (int i = 0; i < configurationInitiale.length(); i++) {
            configuration[i] = Character.getNumericValue(configurationInitiale.charAt(i));
        }
    }

    public void appliquerRegle() {
        int[] nouvelleConfiguration = new int[configuration.length];

        for (int i = 0; i < configuration.length; i++) {
            int indexVoisinGauche = (i == 0) ? configuration.length - 1 : i - 1;
            int indexVoisinDroite = (i == configuration.length - 1) ? 0 : i + 1;

            int indexVoisin = (configuration[indexVoisinGauche] << 2) | (configuration[i] << 1) | configuration[indexVoisinDroite];
            nouvelleConfiguration[i] = regle[indexVoisin];
        }

        configuration = Arrays.copyOf(nouvelleConfiguration, nouvelleConfiguration.length);
    }

    @Override
    public int[][] appliquerRegles(int[][] configurationActuelle, int[][] voisinage) {
        // Cette méthode ne sera pas utilisée pour les automates 1D, vous pouvez laisser vide ou lever une exception
        throw new UnsupportedOperationException("Méthode non applicable pour les automates 1D");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int cell : configuration) {
            sb.append(cell);
        }
        return sb.toString();
    }
}
