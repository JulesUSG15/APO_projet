package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Action;
import src.Tableau;
import src.Variable;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTest {

    private Action action;
    private Variable[] variables;
    private String[] erreur;
    private int dim;
    private Tableau tableau;
    private double[] voisins;
    private int[] indices;

    @BeforeEach
    void setUp() {
        action = new Action();
        dim = 2; // Supposons une dimension de 2 pour cet exemple
        variables = new Variable[dim];
        erreur = new String[1];
        tableau = new Tableau(dim, 10); // Supposons un tableau 2D de taille 10x10
        voisins = new double[]{1.0, 2.0}; // Valeurs des voisins pour le test
        indices = new int[]{5, 5}; // Indices de la cellule testée
        for (int i = 0; i < dim; i++) {
            variables[i] = new Variable(); // Initialisation des variables (détails omis)
        }
    }

    @Test
    void testSetAction() {
        assertTrue(action.set("0.5:1;0.5:2", 2, variables, erreur, dim),
                "La configuration de l'action a échoué avec l'erreur: " + erreur[0]);
    }

    @Test
    void testGetActionValue() {
        action.set("1:100;0:0", 2, variables, erreur, dim);
        double result = action.get(tableau, voisins, indices);
        assertTrue(result == 100, "La valeur d'action attendue était 100, mais a obtenu " + result);
    }

    @Test
    public void testGetExp() {
        action.set("0.7:3;0.3:4", 0, variables, erreur, 1); // Configuration de l'action

        String result = action.getExp(0);

        // Utilisation de String.format pour s'assurer que le formatage est correct
        String expected = String.format("%.1f:%.1f;%.1f:%.1f;", 0.7, 3.0, 0.3, 4.0);
        assertEquals(expected, result);
    }
}
