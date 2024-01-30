package src.autoAutomate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Automate1D extends JFrame implements ActionListener {

    private ReglesAutomate1D regles;
    private JTextField champNumeroRegle;
    private JTextField champConfigInitiale;
    private JTextField champNombreEtapes;
    private JTextArea zoneResultats;
    private AutomatePanel automatePanel; // Panel pour afficher graphiquement l'automate

    public Automate1D() {
        regles = new ReglesAutomate1D();
        champNumeroRegle = new JTextField();
        champConfigInitiale = new JTextField();
        champNombreEtapes = new JTextField();
        zoneResultats = new JTextArea();

        JButton boutonSimuler = new JButton("Simuler");
        boutonSimuler.addActionListener(this);

        automatePanel = new AutomatePanel(); // Initialisation du panel de l'automate

        JPanel panneauPrincipal = new JPanel();
        panneauPrincipal.setLayout(new BoxLayout(panneauPrincipal, BoxLayout.Y_AXIS));

        panneauPrincipal.add(new JLabel("Numéro de Règle :"));
        panneauPrincipal.add(champNumeroRegle);
        panneauPrincipal.add(new JLabel("Configuration Initiale :"));
        panneauPrincipal.add(champConfigInitiale);
        panneauPrincipal.add(new JLabel("Nombre d'Étapes :"));
        panneauPrincipal.add(champNombreEtapes);
        panneauPrincipal.add(boutonSimuler);
        panneauPrincipal.add(new JScrollPane(zoneResultats)); // Ajout d'une barre de défilement à la zone de texte

        // Ajout d'une barre de défilement pour l'automatePanel
        JScrollPane scrollPane = new JScrollPane(automatePanel);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        panneauPrincipal.add(scrollPane);

        add(panneauPrincipal);

        setTitle("Simulation Automate 1D");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Simuler")) {
            automatePanel.reinitialiser(); // Réinitialiser le panel avant la simulation

            int numeroRegle = Integer.parseInt(champNumeroRegle.getText());
            regles.initialiserAvecRegle(numeroRegle);

            String configInitiale = champConfigInitiale.getText();
            regles.initialiserConfiguration(configInitiale);

            int nombreEtapes = Integer.parseInt(champNombreEtapes.getText());
            StringBuilder resultat = new StringBuilder();

            for (int etape = 0; etape < nombreEtapes; etape++) {
                resultat.append("Étape ").append(etape + 1).append(": ").append(regles).append("\n");
                automatePanel.ajouterEtat(regles.getConfiguration()); // Ajouter l'état actuel au panel
                regles.appliquerRegle();
            }

            zoneResultats.setText(resultat.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Automate1D());
    }
}
