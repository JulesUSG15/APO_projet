package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import src.Immediat;

class ImmediatTest {

    private Immediat immediat;
    private String[] erreur;

    @BeforeEach
    void setUp() {
        immediat = new Immediat();
        erreur = new String[1];
    }

    @Test
    void testSetWithValidNumber() {
        assertTrue(immediat.set("3.5", 0, 0, null, erreur, 1),
                   "L'immediat devrait être configuré correctement avec un nombre valide.");
        assertEquals(3.5, immediat.get(null, null, null),
                     "La méthode get devrait retourner la valeur numérique configurée.");
    }

    @Test
    void testSetWithInvalidExpression() {
        assertFalse(immediat.set("abc", 0, 0, null, erreur, 1),
                    "La configuration devrait échouer avec une expression invalide.");
        assertNotNull(erreur[0], "Un message d'erreur devrait être défini pour une expression invalide.");
    }

    @Test
    void testGetExpReturnsCorrectString() {
        immediat.set("42", 0, 0, null, erreur, 1);
        assertEquals("42.0", immediat.getExp(),
                     "La méthode getExp devrait retourner la représentation textuelle correcte de la valeur immédiate.");
    }

    @Test
    void testGetOpForValidAndInvalidExpressions() {
        assertEquals(0, immediat.getOp("123"), "getOp devrait retourner 0 pour une expression valide représentant un nombre.");
        assertEquals(-1, immediat.getOp("notANumber"), "getOp devrait retourner -1 pour une expression invalide.");
    }
}
