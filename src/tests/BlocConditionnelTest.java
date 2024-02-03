package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.BlocConditionnel;
import src.Condition;
import src.Tableau;
import src.Variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BlocConditionnelTest {

    private BlocConditionnel bloc;
    private Variable[] variables;
    private String[] erreur;
    private int dim;

    @BeforeEach
    public void setUp() {
        bloc = new BlocConditionnel();
        variables = new Variable[0]; // Supposons qu'il n'y ait pas de variables pour simplifier
        erreur = new String[1];
        dim = 2; // Supposons un espace de dimension 2 pour l'exemple
    }

    @Test
    public void testSetValidBloc() {
        // Configuration d'un bloc conditionnel valide
        boolean result = bloc.set("{condition}{action}", 4, variables, erreur, dim);
        
        // Vérification que le bloc a été correctement configuré
        assertEquals(true, result, "Le bloc conditionnel devrait être valide.");
    }

    @Test
    public void testSetInvalidBloc() {
        // Tentative de configuration d'un bloc conditionnel invalide
        boolean result = bloc.set("condition action", 4, variables, erreur, dim);
        
        // Vérification que le bloc n'a pas été configuré correctement
        assertEquals(false, result, "Le bloc conditionnel devrait être invalide.");
        assertEquals("Impossible de créer le bloc conditionnel", erreur[0], "Message d'erreur attendu.");
    }

    @Test
    public void testGetWithValidCondition() {
        // Configuration d'un bloc conditionnel avec une condition toujours vraie
        bloc.set("{toujours vrai}{action: 1}", 4, variables, erreur, dim);
        
        // Création d'un tableau et d'une condition pour tester get()
        Tableau tableau = new Tableau(2, 5); // Tableau 2D avec taille 5 (pour simplifier)
        double[] voisins = {1, 2, 3, 4};
        int[] indices = {0, 0};

        Object result = bloc.get(tableau, voisins, indices);
        
        // Vérification que la condition est évaluée à vrai et que l'action est retournée
        assertNotNull(result, "Le résultat ne devrait pas être nul.");
    }

    @Test
    public void testGetWithInvalidCondition() {
        // Configuration d'un bloc conditionnel avec une condition toujours fausse
        bloc.set("{toujours faux}{action: 1}", 4, variables, erreur, dim);
        
        // Création d'un tableau et d'une condition pour tester get()
        Tableau tableau = new Tableau(2, 5); // Tableau 2D avec taille 5 (pour simplifier)
        double[] voisins = {1, 2, 3, 4};
        int[] indices = {0, 0};

        Object result = bloc.get(tableau, voisins, indices);
        
        // Vérification que la condition est évaluée à faux et qu'aucune action n'est retournée
        assertNull(result, "Le résultat devrait être nul.");
    }

    // Vous pouvez ajouter plus de tests pour couvrir d'autres cas
}
