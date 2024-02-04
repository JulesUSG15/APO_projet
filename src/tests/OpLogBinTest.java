package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.OpLogBin;
import src.Tableau;
import src.Variable;
import static org.junit.jupiter.api.Assertions.*;

class OpLogBinTest {

    private OpLogBin opLogBin;
    private Variable[] variables;
    private String[] erreur;
    private Tableau tableau;

    @BeforeEach
    void setUp() {
        opLogBin = new OpLogBin();
        erreur = new String[1];
        tableau = new Tableau(1, 10); // Suppose un tableau simplifié pour les tests
        variables = new Variable[0]; // Ajustez selon le besoin de vos tests
    }

    @Test
    void testSetWithValidExpression() {
        // Test pour une expression valide avec l'opérateur logique &&
        assertTrue(opLogBin.set("true && false", 5, 0, variables, erreur, 1),
                   "L'opération logique binaire devrait être configurée correctement.");
        assertNull(erreur[0], "Il ne devrait pas y avoir d'erreur lors de la configuration d'une expression valide.");
    }

    @Test
    void testEvaluateLogicalOperation() {
        // Configurer une expression logique binaire valide et évaluer l'opération
        opLogBin.set("true && true", 5, 0, variables, erreur, 1);
        assertTrue(opLogBin.get(tableau, new double[]{}, new int[]{}),
                   "L'évaluation de true && true devrait retourner vrai.");

        opLogBin.set("true || false", 5, 0, variables, erreur, 1);
        assertTrue(opLogBin.get(tableau, new double[]{}, new int[]{}),
                   "L'évaluation de true || false devrait retourner vrai.");
    }

    @Test
    void testGetExpReturnsCorrectFormat() {
        opLogBin.set("true && false", 5, 0, variables, erreur, 1);
        assertEquals("(true)&& (false)", opLogBin.getExp(),
                     "L'expression retournée devrait être correctement formatée.");
    }

    @Test
    void testGetOpForLogicalAnd() {
        // Supposons que getOp retourne la position de l'opérateur logique dans l'expression
        int opPosition = opLogBin.getOp("true && false");
        assertTrue(opPosition > 0, "L'opérateur '&&' devrait être trouvé dans l'expression.");
    }
}
