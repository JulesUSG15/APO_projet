package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import src.Regles;
import src.Tableau;

class ReglesTest {

    private Regles regles;

    @BeforeEach
    void setUp() {
        // Initialisation avec un exemple de fichier ou directement avec une chaîne
        regles = new Regles();
    }

    @Test
    void testCharger() {
        String cheminFichier = "chemin/vers/les/regles.txt";
        assertTrue(regles.charger(cheminFichier), "Le chargement des règles devrait réussir.");
    }

    @Test
    void testAppliquer() {
        Tableau tableau = new Tableau(2, 3); // Exemple de tableau 2D
        tableau.intialiserAleatoirement(0, 1); // Initialisation aléatoire
        
        Tableau resultat = regles.appliquer(tableau);
        assertNotNull(resultat, "L'application des règles doit retourner un tableau non null.");
    }

    @Test
    void testSetVarAndGetVar() {
        String nomVariable = "testVar";
        double valeurVariable = 42.0;
        
        assertTrue(regles.addVariable(nomVariable), "L'ajout de la variable devrait réussir.");
        assertTrue(regles.setVar(nomVariable, valeurVariable), "La définition de la variable devrait réussir.");
        assertEquals(valeurVariable, regles.getVar(nomVariable), "La récupération de la valeur de la variable devrait correspondre à la valeur définie.");
    }

    @Test
    void testErreur() {
        String expInvalide = "expression invalide";
        regles.set(expInvalide);
        assertFalse(regles.getErreur().isEmpty(), "La définition d'une règle invalide devrait générer une erreur.");
    }

    @Test
    void testSauvegarder() {
        String cheminFichier = "chemin/vers/les/nouvellesRegles.txt";
        assertTrue(regles.sauvegarder(cheminFichier), "La sauvegarde des règles devrait réussir.");
        // Assurez-vous de nettoyer le fichier créé après le test si nécessaire
    }

    @Test
    void testGetDim() {
        assertEquals(0, regles.getDim(), "La dimension initiale devrait être 0 pour un nouvel objet Regles.");
    }
}
