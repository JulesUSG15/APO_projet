package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import src.OpAriBin;
import src.Variable;
import src.Tableau;

class OpAriBinTest {

    private OpAriBin opAriBin;
    private Variable[] variables;
    private String[] erreur;
    private Tableau tableau;

    @BeforeEach
    void setUp() {
        opAriBin = new OpAriBin();
        erreur = new String[1];
        tableau = new Tableau(1, 10); // Suppose un tableau simplifié pour les tests
        variables = new Variable[2]; // Initialisez si nécessaire pour les tests
        // Initialisation hypothétique des variables, si nécessaire
    }

    @Test
    void testSetWithValidAddition() {
        assertTrue(opAriBin.set("3+2", 1, 0, variables, erreur, 1),
                   "L'opération d'addition devrait être configurée correctement.");
    }

    @Test
    void testEvaluateAddition() {
        opAriBin.set("3+2", 1, 0, variables, erreur, 1);
        double result = opAriBin.get(tableau, new double[]{}, new int[]{});
        assertEquals(5.0, result, "Le résultat de 3+2 devrait être 5.");
    }

    @Test
    void testSetWithValidSubtraction() {
        assertTrue(opAriBin.set("5-2", 1, 0, variables, erreur, 1),
                   "L'opération de soustraction devrait être configurée correctement.");
    }

    @Test
    void testEvaluateSubtraction() {
        opAriBin.set("5-2", 1, 0, variables, erreur, 1);
        double result = opAriBin.get(tableau, new double[]{}, new int[]{});
        assertEquals(3.0, result, "Le résultat de 5-2 devrait être 3.");
    }

    @Test
    void testGetExpForMultiplication() {
        opAriBin.set("4*3", 1, 0, variables, erreur, 1);
        String exp = opAriBin.getExp();
        assertEquals("(4*3)", exp, "L'expression retournée devrait être correctement formatée pour une multiplication.");
    }

    @Test
    void testGetOpForDivision() {
        int opPosition = opAriBin.getOp("6/3");
        assertTrue(opPosition > 0, "L'opérateur '/' devrait être trouvé dans l'expression.");
    }
}
