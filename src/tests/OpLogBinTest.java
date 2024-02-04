package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import src.OpLogBin;
import src.Tableau;
import src.Variable;

class OpLogBinTest {

    private OpLogBin condition;
    private Variable[] variables;
    private String[] erreur;
    private Tableau tableau;

    @BeforeEach
    void setUp() {
        condition = new OpLogBin();
        erreur = new String[1];
        tableau = new Tableau(1, 10); // Supposons que c'est suffisant pour les tests
        variables = new Variable[2];
        
        // Initialisez les variables ici selon vos besoins
        variables[0] = new Variable();
        variables[0].set("$x", 0, 0, null, erreur, 0);
        variables[0].setVal(1.0); // Simulez que $x est vrai (1.0)
        
        variables[1] = new Variable();
        variables[1].set("$y", 0, 0, null, erreur, 0);
        variables[1].setVal(0.0); // Simulez que $y est faux (0.0)
    }

    @Test
    void testSetWithValidExpressionAnd() {
        // Test pour l'opérateur logique ET ('&&')
        assertTrue(condition.set("$x && $y", 0, 2, variables, erreur, 2),
                   "La condition devrait être configurée correctement avec un opérateur ET logique.");
        assertEquals("&&", condition.getOp(), "L'opérateur logique configuré devrait être ET.");
    }

    @Test
    void testSetWithValidExpressionOr() {
        // Test pour l'opérateur logique OU ('||')
        assertTrue(condition.set("$x || $y", 0, 2, variables, erreur, 2),
                   "La condition devrait être configurée correctement avec un opérateur OU logique.");
        assertEquals("||", condition.getOp(), "L'opérateur logique configuré devrait être OU.");
    }

    @Test
    void testEvaluateTrueCondition() {
        condition.set("$x || $y", 0, 2, variables, erreur, 2); // $x est vrai, $y est faux
        assertTrue(condition.get(tableau, new double[]{}, new int[]{}),
                   "La condition OU devrait être évaluée à vrai.");
    }

    @Test
    void testEvaluateFalseCondition() {
        condition.set("!$x && $y", 0, 2, variables, erreur, 2); // $x est vrai, donc !$x est faux
        assertFalse(condition.get(tableau, new double[]{}, new int[]{}),
                    "La condition ET avec négation devrait être évaluée à faux.");
    }

    @Test
    void testGetExp() {
        condition.set("$x && $y", 0, 2, variables, erreur, 2);
        String expectedExp = "($x)&&($y)";
        assertEquals(expectedExp, condition.getExp(),
                     "L'expression retournée devrait refléter la condition ET logique.");
    }
}
