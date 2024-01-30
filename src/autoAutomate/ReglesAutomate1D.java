package src.autoAutomate;

import java.util.Arrays;

/**
 * Classe gérant les règles et la configuration d'un automate cellulaire unidimensionnel.
 */
public class ReglesAutomate1D {

    /**
     * Tableau représentant les règles de l'automate.
     */
    private int[] regle;

    /**
     * Tableau représentant la configuration de l'automate.
     */
    private int[] configuration;

    /**
     * Initialise l'automate avec une règle spécifiée par un numéro.
     * 
     * @param numeroRegle Le numéro de la règle à appliquer.
     */
    public void initialiserAvecRegle(int numeroRegle) {
        regle = new int[8];
        for (int i = 0; i < 8; i++) {
            regle[i] = (numeroRegle & (1 << i)) >> i;
        }
    }

    /**
     * Initialise la configuration de l'automate avec une chaîne de caractères.
     * 
     * @param configurationInitiale La chaîne représentant la configuration initiale.
     */
    public void initialiserConfiguration(String configurationInitiale) {
        configuration = new int[configurationInitiale.length()];
        for (int i = 0; i < configurationInitiale.length(); i++) {
            configuration[i] = Character.getNumericValue(configurationInitiale.charAt(i));
        }
    }

    /**
     * Applique la règle de l'automate à la configuration actuelle pour obtenir la configuration suivante.
     */
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

    /**
     * Représente la configuration actuelle de l'automate sous forme de chaîne de caractères.
     * 
     * @return La chaîne représentant la configuration actuelle de l'automate.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int cell : configuration) {
            sb.append(cell);
        }
        return sb.toString();
    }

    public int[] getConfiguration() {
        return configuration;
    }
    
}
