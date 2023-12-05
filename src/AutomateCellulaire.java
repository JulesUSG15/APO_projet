package src;
import java.util.Arrays;

public class AutomateCellulaire {

    private int dimension;
    private String[] alphabet;
    private int[][] voisinage;
    private ReglesAutomate regles;

    public AutomateCellulaire(int dimension, String[] alphabet, int[][] voisinage, ReglesAutomate regles) {
        this.dimension = dimension;
        this.alphabet = alphabet;
        this.voisinage = voisinage;
        this.regles = regles;
    }

    public void evoluer(int[][] configurationInitiale, int nombreEtapes) {
        int[][] configurationActuelle = configurationInitiale;

        for (int etape = 0; etape < nombreEtapes; etape++) {
            afficherConfiguration(configurationActuelle, etape);
            configurationActuelle = regles.appliquerRegles(configurationActuelle, voisinage);
        }
    }

    private void afficherConfiguration(int[][] configuration, int etape) {
        System.out.println("Étape " + etape + ":");
        for (int i = 0; i < configuration.length; i++) {
            System.out.println(Arrays.toString(configuration[i]));
        }
        System.out.println("------------------------");
    }
}

