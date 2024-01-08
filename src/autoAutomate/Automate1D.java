package src.autoAutomate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Automate1D extends JFrame implements ActionListener {

    private ReglesAutomate1D regles;
    private JTextField champNumeroRegle;
    private JTextField champConfigInitiale;
    private JTextField champNombreEtapes;
    private JTextArea zoneResultats;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            StringBuilder resultat = new StringBuilder();

            for (int etape = 0; etape < nombreEtapes; etape++) {
                resultat.append("Étape ").append(etape + 1).append(": ").append(regles).append("\n");
                regles.appliquerRegle();
            }

            zoneResultats.setText(resultat.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Automate1D());
    }
}
