package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import src.Fonction;
import src.Variable;
import src.Tableau;

class FonctionTest {

    private Fonction fonction;
    private String[] erreur;
    private Variable[] var;
    private Tableau tableau;

    @BeforeEach
    void setUp() {
        fonction = new Fonction();
        erreur = new String[1];
        tableau = new Tableau(1, 1); // Assumant une implémentation basique de Tableau
        var = new Variable[0]; // Assumant qu'il n'y a pas de variable pour simplifier
    }

    @Test
    void testSetCosFunction() {
        assertTrue(fonction.set("cos(0)", 3, 0, var, erreur, 1),
                   "La fonction cos doit être configurée correctement.");
    }

    @Test
    void testEvaluateCosFunction() {
        fonction.set("cos(0)", 3, 0, var, erreur, 1);
        assertEquals(1.0, fonction.get(tableau, null, null),
                     "La fonction cos de 0 doit retourner 1.");
    }

    @Test
    void testSetSinFunctionWithInvalidExpression() {
        assertFalse(fonction.set("sin()", 3, 0, var, erreur, 1),
                    "La configuration de la fonction sin avec une expression invalide doit échouer.");
        assertNotNull(erreur[0], "Un message d'erreur doit être fourni pour une expression invalide.");
    }

    @Test
    void testGetExpForCosFunction() {
        fonction.set("cos(45)", 3, 0, var, erreur, 1);
        assertEquals("cos(45.0)", fonction.getExp(),
                     "L'expression de la fonction cos doit être retournée correctement.");
    }

    @Test
    void testGetOpForFunction() {
        int opPosition = fonction.getOp("cos(45)");
        assertTrue(opPosition >= 0, "L'opérateur de la fonction doit être identifié correctement dans l'expression.");
    }
}
