package src.tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import src.BlocConditionnel;
import src.Variable;

public class BlocConditionnelTest {

    private BlocConditionnel bloc;
    private Variable[] variables;
    private String[] erreurs;
    private final int dim = 2; // Exemple de dimension pour les tests

    @Before
    public void setUp() {
        bloc = new BlocConditionnel();
        // Initialisation avec des valeurs fictives, à adapter selon votre implémentation
        variables = new Variable[] { /* Initialiser vos variables ici */ };
        erreurs = new String[1];
    }

    @Test
    public void testSetValid() {
        String exp = "condition{action}"; // Exemple d'expression valide
        int nbVoisins = 3; // Exemple de nombre de voisins
        assertTrue("Le bloc devrait être correctement configuré avec une expression valide", bloc.set(exp, nbVoisins, variables, erreurs, dim));
    }

    @Test
    public void testSetInvalid() {
        String exp = "conditionInvalide"; // Exemple d'expression invalide
        int nbVoisins = 3;
        assertFalse("Le bloc ne devrait pas être configuré avec une expression invalide", bloc.set(exp, nbVoisins, variables, erreurs, dim));
        assertNotNull("Un message d'erreur devrait être défini pour une expression invalide", erreurs[0]);
    }

    @Test
    public void testGetExp() {
        String exp = "condition{action}"; // Utilisez la même expression que dans testSetValid
        int nbVoisins = 3;
        assertTrue("L'initialisation avec une expression valide devrait réussir", bloc.set(exp, nbVoisins, variables, erreurs, dim));
        String expectedExp = "condition {\n    action\n}"; // Adaptez cette chaîne en fonction de la sortie attendue de votre implémentation
        assertEquals("L'expression obtenue devrait correspondre à l'expression attendue", expectedExp.trim(), bloc.getExp(0).trim());
    }

    // Ajoutez ici d'autres méthodes de test pour couvrir des cas spécifiques, comme des imbrications de blocs conditionnels

}
