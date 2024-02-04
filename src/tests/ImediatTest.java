package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Immediat;

import static org.junit.jupiter.api.Assertions.*;

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
        String expression = "42.0";
        assertTrue(immediat.set(expression, 0, 0, null, erreur, 0), "La configuration avec un nombre valide doit réussir.");
        assertNull(erreur[0], "Il ne devrait pas y avoir d'erreur lors de la configuration avec un nombre valide.");
    }

    @Test
    void testSetWithInvalidNumber() {
        String expression = "notANumber";
        assertFalse(immediat.set(expression, 0, 0, null, erreur, 0), "La configuration avec une chaîne invalide doit échouer.");
        assertNotNull(erreur[0], "Une erreur doit être signalée lors de la configuration avec une chaîne invalide.");
    }

    @Test
    void testGetValue() {
        String expression = "3.14";
        immediat.set(expression, 0, 0, null, erreur, 0);
        assertEquals(3.14, immediat.get(null, null, null), 0.001, "La valeur récupérée doit correspondre à la valeur configurée.");
    }

    @Test
    void testGetExp() {
        String expression = "2.718";
        immediat.set(expression, 0, 0, null, erreur, 0);
        assertEquals("2.718", immediat.getExp(), "L'expression récupérée doit correspondre à la représentation textuelle de la valeur configurée.");
    }

    @Test
    void testGetOp() {
        assertEquals(-1, immediat.getOp("123"), "Aucun opérateur ne devrait être trouvé dans une expression de valeur immédiate.");
        assertEquals(0, immediat.getOp("42"), "Un opérateur devrait être trouvé dans une expression numérique valide.");
    }
}
