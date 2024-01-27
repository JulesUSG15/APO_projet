package src.autoAutomate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe Automate1D est une interface graphique pour la simulation d'automates cellulaires unidimensionnels.
 * Elle permet à l'utilisateur de spécifier une règle, une configuration initiale et le nombre d'étapes à simuler.
 */
public class Automate1D extends JFrame implements ActionListener {

    /**
     * L'objet ReglesAutomate1D qui contient la règle et la configuration initiale de l'automate.
     */
    private ReglesAutomate1D regles;

    /**
     * Les composants graphiques de l'interface.
     */
    private JTextField champNumeroRegle;
    private JTextField champConfigInitiale;
    private JTextField champNombreEtapes;
    private JTextArea zoneResultats;

    /**
     * Constructeur pour créer l'interface graphique de l'automate 1D.
     * Initialise les composants graphiques et configure l'interface.
     */
    public Automate1D() {
        regles = new ReglesAutomate1D();
        champNumeroRegle = new JTextField();
        champConfigInitiale = new JTextField();
        champNombreEtapes = new JTextField();
        zoneResultats = new JTextArea();

        JButton boutonSimuler = new JButton("Simuler");

        boutonSimuler.addActionListener(this);

        JPanel panneauPrincipal = new JPanel();
        panneauPrincipal.setLayout(new BoxLayout(panneauPrincipal, BoxLayout.Y_AXIS));

        panneauPrincipal.add(new JLabel("Numéro de Règle :"));
        panneauPrincipal.add(champNumeroRegle);
        panneauPrincipal.add(new JLabel("Configuration Initiale :"));
        panneauPrincipal.add(champConfigInitiale);
        panneauPrincipal.add(new JLabel("Nombre d'Étapes :"));
        panneauPrincipal.add(champNombreEtapes);
        panneauPrincipal.add(boutonSimuler);
        panneauPrincipal.add(zoneResultats);

        add(panneauPrincipal);

        setTitle("Simulation Automate 1D");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Méthode déclenchée lorsqu'une action est effectuée sur les éléments graphiques.
     * Spécifiquement, elle est appelée quand l'utilisateur appuie sur le bouton "Simuler".
     * Cette méthode gère la récupération des données entrées par l'utilisateur,
     * exécute la simulation de l'automate cellulaire et affiche les résultats.
     * 
     * @param e L'événement d'action qui a déclenché cette méthode.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Simuler")) {
            int numeroRegle = Integer.parseInt(champNumeroRegle.getText());
            regles.initialiserAvecRegle(numeroRegle);

            String configInitiale = champConfigInitiale.getText();
            regles.initialiserConfiguration(configInitiale);

            int nombreEtapes = Integer.parseInt(champNombreEtapes.getText());
            StringBuilder resultat = new StringBuilder();

            for (int etape = 0; etape < nombreEtapes; etape++) {
                resultat.append("Étape ").append(etape + 1).append(": ").append(regles).append("\n");
                regles.appliquerRegle();
            }

            zoneResultats.setText(resultat.toString());
        }
    }
    /**
     * Cette méthode lance l'interface graphique de l'automate 1D en utilisant Swing.
     * 
     * @param args Arguments de ligne de commande (non utilisés dans cette application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Automate1D());
    }
}
