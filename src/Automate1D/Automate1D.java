package src.Automate1D;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Automate1D extends JFrame implements ActionListener {

    private ReglesAutomate1D regles;
    private JTextField champNumeroRegle;
    private JTextField champConfigInitiale;
    private JTextField champNombreEtapes;
    private JTextArea zoneResultats;
    private JComboBox<String> modeAffichageSelecteur;

    public Automate1D() {
        regles = new ReglesAutomate1D();
        champNumeroRegle = new JTextField();
        champConfigInitiale = new JTextField();
        champNombreEtapes = new JTextField();
        zoneResultats = new JTextArea();

        JButton boutonSimuler = new JButton("Simuler");
        boutonSimuler.addActionListener(this);

        modeAffichageSelecteur = new JComboBox<>(new String[]{"Affichage par étape", "Affichage 2D"});

        JPanel panneauPrincipal = new JPanel();
        panneauPrincipal.setLayout(new BoxLayout(panneauPrincipal, BoxLayout.Y_AXIS));

        panneauPrincipal.add(new JLabel("Numéro de Règle :"));
        panneauPrincipal.add(champNumeroRegle);
        panneauPrincipal.add(new JLabel("Configuration Initiale :"));
        panneauPrincipal.add(champConfigInitiale);
        panneauPrincipal.add(new JLabel("Nombre d'Étapes :"));
        panneauPrincipal.add(champNombreEtapes);
        panneauPrincipal.add(new JLabel("Mode d'affichage :"));
        panneauPrincipal.add(modeAffichageSelecteur);
        panneauPrincipal.add(boutonSimuler);
        panneauPrincipal.add(new JScrollPane(zoneResultats)); // Ajout d'une barre de défilement à la zone de texte

        add(panneauPrincipal);

        setTitle("Simulation Automate 1D");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Simuler")) {
            int numeroRegle = Integer.parseInt(champNumeroRegle.getText());
            regles.initialiserAvecRegle(numeroRegle);

            String configInitiale = champConfigInitiale.getText();
            regles.initialiserConfiguration(configInitiale);

            int nombreEtapes = Integer.parseInt(champNombreEtapes.getText());
            List<int[]> etatsAutomate = new ArrayList<>();

            for (int etape = 0; etape < nombreEtapes; etape++) {
                etatsAutomate.add(Arrays.copyOf(regles.getConfiguration(), regles.getConfiguration().length));
                regles.appliquerRegle();
            }

            String modeSelectionne = (String) modeAffichageSelecteur.getSelectedItem();
            AutomateStepWindow stepWindow = new AutomateStepWindow(etatsAutomate, modeSelectionne);
            stepWindow.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Automate1D());
    }
}
