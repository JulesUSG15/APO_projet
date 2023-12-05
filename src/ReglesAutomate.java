package src;
public interface ReglesAutomate {

    /**
     * Applique les règles de transition de l'automate cellulaire sur la configuration actuelle.
     *
     * @param configurationActuelle La configuration actuelle de l'automate cellulaire.
     * @param voisinage             Les positions relatives des voisins d'une cellule.
     * @return La nouvelle configuration après l'application des règles de transition.
     */
    int[][] appliquerRegles(int[][] configurationActuelle, int[][] voisinage);
}

