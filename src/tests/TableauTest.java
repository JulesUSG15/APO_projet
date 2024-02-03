package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

import src.Tableau;

class TableauTest {

    private Tableau tableau;

    @BeforeEach
    void setUp() {
        tableau = new Tableau(2, 5); // Crée un tableau 2D de taille 5x5
    }

    @Test
    void testTableauCreation() {
        assertEquals(2, tableau.getDim(), "La dimension du tableau doit être 2.");
        assertEquals(5, tableau.getTaille(), "La taille de chaque dimension doit être 5.");
    }

    @Test
    void testSetAndGetVal() {
        tableau.setVal(new int[]{1, 2}, 3.14);
        assertEquals(3.14, tableau.getVal(1, 2), "La valeur à l'indice (1,2) doit être 3.14.");
    }

    @Test
    void testSauvegarderEtCharger() throws IOException {
        String chemin = "testTableau.txt";
        tableau.setVal(new int[]{1, 1}, 2.72);
        assertTrue(tableau.sauvegarder(chemin), "La sauvegarde doit réussir.");
        
        Tableau tableauCharge = new Tableau(chemin);
        assertEquals(2.72, tableauCharge.getVal(1, 1), "La valeur chargée à l'indice (1,1) doit être 2.72.");
        
        // Nettoyage
        Files.delete(Paths.get(chemin));
    }

    @Test
    void testRemplir() {
        tableau.remplir(5, 1.0f);
        int compteur = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (tableau.getVal(i, j) == 1.0) compteur++;
            }
        }
        assertTrue(compteur >= 5, "Au moins 5 cellules doivent être remplies avec la valeur 1.0.");
    }

    @Test
    void testMaximumMinimumMoyenne() {
        tableau.setVal(new int[]{0, 0}, 10);
        tableau.setVal(new int[]{4, 4}, -10);
        assertEquals(10, tableau.maximum(), "Le maximum doit être 10.");
        assertEquals(-10, tableau.minimum(), "Le minimum doit être -10.");
        
        // Pour la moyenne, considérons un tableau simplifié
        assertEquals(0, tableau.moyenne(), "La moyenne devrait être proche de 0, compte tenu des valeurs définies.");
    }

    @Test
    void testCount() {
        tableau.setVal(new int[]{0, 0}, 5);
        tableau.setVal(new int[]{1, 1}, 5);
        assertEquals(2, tableau.count(5), "Le nombre d'occurrences de la valeur 5 doit être 2.");
    }
}
