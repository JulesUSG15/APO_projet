package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Regles;
import src.Tableau;

import static org.junit.jupiter.api.Assertions.*;

class ReglesTest {
    private Regles regles;

    @BeforeEach
    void setUp() {
        regles = new Regles();
    }

    @Test
    void testConstructeurParDefaut() {
        assertEquals(0, regles.getDim(), "La dimension doit être 0 pour un nouvel objet Regles.");
        assertTrue(regles.getErreur().isEmpty(), "Il ne devrait pas y avoir d'erreur pour un nouvel objet Regles.");
    }

    @Test
    void testChargerFichierInexistant() {
        assertFalse(regles.charger("data/dac/inexistant.dac"), "Charger un fichier inexistant doit retourner false.");
        assertFalse(regles.getErreur().isEmpty(), "Une erreur doit être signalée si le fichier est inexistant.");
    }

    // Assurez-vous que le fichier "reglesValides.dac" existe et contient des règles valides
    @Test
    void testChargerFichierExistant() {
        assertTrue(regles.charger("data/dac/nom.dac"), "Charger un fichier existant avec des règles valides doit retourner true.");
        assertTrue(regles.getErreur().isEmpty(), "Il ne devrait pas y avoir d'erreur après avoir chargé des règles valides.");
    }

    @Test
    void testSauvegarder() {
        regles.charger("data/dac/nom.dac"); // Assurez-vous que cette opération réussit avant de sauvegarder
        assertTrue(regles.sauvegarder("data/dac/nom2.dac"), "La sauvegarde des règles devrait réussir.");
    }

    @Test
    void testAppliquerReglesValides() {
        Tableau tableau = new Tableau(2, 2);
        // Initialisation du tableau et chargement des règles valides nécessaires ici
        assertNotNull(regles.appliquer(tableau), "Appliquer des règles valides ne doit pas retourner null.");
    }

    @Test
    void testSetVarEtGetVar() {
        String nomVariable = "testVar";
        double valeurAttendue = 0.01;
        assertTrue(regles.setVar(nomVariable, valeurAttendue), "Définir une variable devrait réussir.");
        assertEquals(valeurAttendue, regles.getVar(nomVariable), 0.01, "La valeur récupérée doit correspondre à la valeur définie.");
    }

    @Test
    void testGetVarInexistante() {
        assertEquals(0.0, regles.getVar("varInexistante"), 0.01, "Récupérer une variable inexistante devrait retourner 0.");
    }

    @Test
    void testIsVariable() {
        String nomVariable = "variableExistante";
        regles.setVar(nomVariable, 9.0); // Assurez-vous que cette opération réussit
        assertTrue(regles.isVariable(nomVariable), "La variable devrait exister.");
    }

    @Test
    void testGetNbVars() {
        assertEquals(0, regles.getNbVars(), "Initialement, le nombre de variables devrait être 0.");
        // Après avoir ajouté des variables, vérifiez à nouveau cette assertion
    }
}
