package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import src.OpCompar;
import src.Tableau;
import src.Variable;

class OpComparTest {

    private OpCompar condition;
    private Variable[] variables;
    private String[] erreur;
    private Tableau tableau;

    @BeforeEach
    void setUp() {
        condition = new OpCompar();
        erreur = new String[1];
        tableau = new Tableau(1, 10); // Supposons que c'est suffisant pour les tests
        variables = new Variable[2];

        // Configuration initiale des variables pour le test
        variables[0] = new Variable();
        variables[0].set("$x", 0, 0, null, erreur, 0);
        variables[0].setVal(5.0); // $x = 5.0

        variables[1] = new Variable();
        variables[1].set("$y", 0, 0, null, erreur, 0);
        variables[1].setVal(3.0); // $y = 3.0
    }

    @Test
    void testSetWithValidExpressionGreaterThan() {
        // Test pour l'opérateur de comparaison '>'
        assertTrue(condition.set("$x > $y", 0, 2, variables, erreur, 2),
                   "La condition de comparaison '>' devrait être configurée correctement.");
    }

    @Test
    void testEvaluateTrueConditionGreaterThan() {
        condition.set("$x > $y", 0, 2, variables, erreur, 2);
        assertTrue(condition.get(tableau, new double[]{}, new int[]{}),
                   "La condition '$x > $y' devrait être évaluée à vrai.");
    }

    @Test
    void testEvaluateFalseConditionLessThan() {
        // Test pour l'opérateur de comparaison '<', dans ce cas, la condition sera fausse
        condition.set("$x < $y", 0, 2, variables, erreur, 2);
        assertFalse(condition.get(tableau, new double[]{}, new int[]{}),
                    "La condition '$x < $y' devrait être évaluée à faux.");
    }

    @Test
    void testGetExp() {
        condition.set("$x >= $y", 0, 2, variables, erreur, 2);
        String expectedExp = "$x>=$y";
        assertEquals(expectedExp, condition.getExp(),
                     "L'expression retournée devrait refléter la condition de comparaison '>='.");
    }
}
