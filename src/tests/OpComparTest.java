package src.tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import src.OpCompar;
import src.Variable;

public class OpComparTest {

    private OpCompar opCompar;
    private Variable[] variables;
    private String[] erreurs;

    @Before
    public void setUp() {
        opCompar = new OpCompar();
        // Initialisez ici le tableau de variables si votre logique le permet
        variables = new Variable[] {}; // Remplir avec des instances de Variable selon votre logique d'initialisation
        erreurs = new String[1];
    }

    @Test
    public void testSetValidExpression() {
        // Exemple d'expression valide
        String exp = "$x>=5";
        int position = exp.indexOf(">=");
        assertTrue("La configuration devrait réussir pour une expression valide",
                opCompar.set(exp, position, 0, variables, erreurs, 2));
    }

    @Test
    public void testSetInvalidExpression() {
        // Exemple d'expression invalide
        String exp = ">=5";
        int position = exp.indexOf(">=");
        assertFalse("La configuration devrait échouer pour une expression invalide",
                opCompar.set(exp, position, 0, variables, erreurs, 2));
        assertNotNull("Un message d'erreur devrait être défini pour une expression invalide", erreurs[0]);
    }

    @Test
    public void testGetOpValid() {
        String exp = "$x<$y";
        int expectedPosition = exp.indexOf("<");
        int opPosition = opCompar.getOp(exp);
        assertEquals("La position de l'opérateur devrait être correctement identifiée", expectedPosition, opPosition);
    }

    @Test
    public void testGetExpression() {
        // Vous devez configurer opCompar avec une condition valide avant de tester getExp
        String exp = "$x==$y";
        int position = exp.indexOf("==");
        opCompar.set(exp, position, 0, variables, erreurs, 2);
        String resultExp = opCompar.getExp();
        assertEquals("L'expression retournée devrait correspondre à l'expression initiale", exp, resultExp);
    }

    // Ajoutez d'autres tests pour couvrir les différents opérateurs et cas d'utilisation

}
