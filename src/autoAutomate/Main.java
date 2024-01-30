package src.autoAutomate;
import javax.swing.*;

import src.autoAutomate.Automate1D.Automate1D;

import java.awt.event.*;

/**
 * La classe Main fournit le point d'entrée principal pour une application graphique
 * permettant à l'utilisateur de choisir et de lancer différentes simulations d'automates cellulaires.
 */
public class Main {
    
    /**
     * Le point d'entrée principal de l'application.
     * Crée une interface graphique avec des boutons pour lancer différentes simulations d'automates cellulaires.
     * 
     * @param args Arguments de la ligne de commande (non utilisés dans cette application).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu - Projet APO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JButton btnAutomate1D = new JButton("Automate 1D");
        JButton btnRegleMajorite = new JButton("Règle de majorité");
        JButton btnJeuDeLaVie = new JButton("Jeu de la vie");
        JButton btnFeuDeForet = new JButton("Feu de forêt");
        JButton btnPersonnaliser = new JButton("Personnaliser");

        btnAutomate1D.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Automate1D.main(args);
            }
        });

        btnRegleMajorite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Majorite m = new Majorite();
                m.main();
            }
        });

        btnJeuDeLaVie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JeuDeLaVie j = new JeuDeLaVie();
                j.main();
            }
        });

        btnFeuDeForet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FeuDeForet f = new FeuDeForet();
                f.main();
            }
        });

        btnPersonnaliser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Personnaliser p = new Personnaliser();
                p.main();
            }
        });

        frame.setLayout(new java.awt.GridLayout(5, 1));
        frame.add(btnAutomate1D);
        frame.add(btnRegleMajorite);
        frame.add(btnJeuDeLaVie);
        frame.add(btnFeuDeForet);
        frame.add(btnPersonnaliser);
        frame.setVisible(true);
    }
}