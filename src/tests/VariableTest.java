package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import src.Variable;

class VariableTest {

    private Variable variable;
    private String[] erreur;

    @BeforeEach
    void setUp() {
        variable = new Variable();
        erreur = new String[1];
    }

    @Test
    void testSetWithValidExpression() {
        assertTrue(variable.set("$x", 1, 0, null, erreur, 1),
                   "La variable devrait être configurée correctement avec une expression valide.");
        assertEquals("x", variable.getNom(), "Le nom de la variable devrait être 'x'.");
    }

    @Test
    void testSetWithInvalidExpression() {
        assertFalse(variable.set("x", 1, 0, null, erreur, 1),
                    "La configuration devrait échouer avec une expression invalide.");
        assertNotNull(erreur[0], "Un message d'erreur devrait être défini pour une expression invalide.");
    }

    @Test
    void testGetAfterSettingValue() {
        variable.set("$x", 1, 0, null, erreur, 1);
        variable.setVal(5.0);
        assertEquals(5.0, variable.get(null, null, null),
                     "La méthode get devrait retourner la valeur correcte de la variable.");
    }

    @Test
    void testGetExp() {
        variable.set("$x", 1, 0, null, erreur, 1);
        String expectedExp = "$x";
        assertEquals(expectedExp, variable.getExp(),
                     "La méthode getExp devrait retourner la représentation textuelle correcte de la variable.");
    }

    @Test
    void testSetValUpdatesValue() {
        variable.set("$x", 1, 0, null, erreur, 1);
        variable.setVal(10.0);
        variable.setVal(20.0); // Mise à jour de la valeur
        assertEquals(20.0, variable.get(null, null, null),
                     "La méthode setVal devrait mettre à jour la valeur de la variable.");
    }
}
