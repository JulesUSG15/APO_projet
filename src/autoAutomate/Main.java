package src.autoAutomate;
import javax.swing.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu - Projet APO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JButton btnAutomate1D = new JButton("Automate 1D");
        JButton btnRegleMajorite = new JButton("Règle de majorité");
        JButton btnJeuDeLaVie = new JButton("Jeu de la vie");
        JButton btnFeuDeForet = new JButton("Feu de forêt");
        JButton btnQuitter = new JButton("Quitter");

        btnAutomate1D.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Automate1D.main(args);
            }
        });

        btnRegleMajorite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //RegleMajorite.main(args);
            }
        });

        btnJeuDeLaVie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JeuDeLaVie j = new JeuDeLaVie();
                j.main(args);
            }
        });

        btnFeuDeForet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //FeuDeForet.main(args);
            }
        });

        btnQuitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Au revoir !");
                System.exit(0);
            }
        });

        frame.setLayout(new java.awt.GridLayout(5, 1));
        frame.add(btnAutomate1D);
        frame.add(btnRegleMajorite);
        frame.add(btnJeuDeLaVie);
        frame.add(btnFeuDeForet);
        frame.add(btnQuitter);
        frame.setVisible(true);
    }
}