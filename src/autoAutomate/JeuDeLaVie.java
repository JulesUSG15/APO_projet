package src.autoAutomate;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import java.awt.event.*;
import javax.swing.*;
import src.autoAutomate.Turtle;



public class JeuDeLaVie extends JFrame implements ActionListener  {

    private ArrayList<Tableau> simulation = new ArrayList<Tableau>();
    private Regles reg;
    JTextField fieldEtapes, fieldTaille;
    JComboBox<String> choixGeneration;
    JButton btnSimulation;
    private int tabDisplayed = 0;

    public JeuDeLaVie() {
        this.reg = new Regles();
        this.reg.charger("data/jeu_vie.dac");
    }
    
    public void main(String[] args) {

        // Création de l'interface graphique
        
        JFrame f = new JFrame("Simulation - Jeu de la vie");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titre = new JLabel("Jeu de la vie");
        titre.setBounds(20,10,200,30);

        JLabel labelTaille = new JLabel("Taille du tableau :");
        labelTaille.setBounds(20,40,120,30);
        fieldTaille = new JTextField("10");
        fieldTaille.setBounds(20,70,120,30);

        JLabel labelEtapes = new JLabel("Nombre d'étapes :");
        labelEtapes.setBounds(150,40,120,30);
        fieldEtapes = new JTextField("5");
        fieldEtapes.setBounds(150,70,120,30);

        String[] choices = { "Initailisation aléatoire","Initailisation manuelle"};
        choixGeneration = new JComboBox<String>(choices);
        choixGeneration.setBounds(280,70,180,30);

        btnSimulation = new JButton("Lancer la simulation");
        btnSimulation.setBounds(20,120,150,30);
        btnSimulation.addActionListener(this);

        f.add(titre);
        f.add(labelTaille);
        f.add(fieldTaille);
        f.add(labelEtapes);
        f.add(fieldEtapes);
        f.add(choixGeneration);
        f.add(btnSimulation);
        f.setSize(500,500);
        f.setLayout(null);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == btnSimulation){
            int taille = 0;
            int etapes = 0;
            try {
                taille = Integer.parseInt(fieldTaille.getText());
                etapes = Integer.parseInt(fieldEtapes.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Veuillez entrer des valeurs correctes");
                return;
            }
            // On verifie que les champs ne sont pas vides et que les valeurs sont correctes
            if (taille < 1 || etapes < 1) {
                System.out.println("Veuillez entrer des valeurs correctes");
                return;
            }

            Tableau tab = new Tableau (2, taille);

            if (choixGeneration.getSelectedItem() == "Initailisation manuelle") {
                this.initialiserTableauManuelle(tab);
            }
            else {
                this.initialiserTableauAleatoire(tab);
            }

            this.simuler(tab, etapes);

            int width = 500;
            int step = width / this.simulation.get(0).getTaille();
            Turtle turtle = this.createFenetreGraphique(width);
            this.afficherTableauGraphique(turtle, this.simulation.get(0), step);
        }
    }

    public void initialiserTableauAleatoire(Tableau tab) {
        Random random = new Random();
        for (int j = 0; j < tab.getTaille(); j++) {
            for (int i = 0; i < tab.getTaille(); i++) {
                tab.setVal(j, i, random.nextInt(2));
            }
        }
    }

    public void initialiserTableauManuelle(Tableau tab) {
        System.out.println("Initialisation manuelle");
    }

    public void simuler(Tableau tab, int n) {
        this.simulation.clear();
         for (int i=0; i < n; i++) {
            tab = this.reg.appliquer(tab);
            this.simulation.add(tab);
        }
    } 

    public void afficherConsole() {
        for (Tableau t : this.simulation) {
            afficherTabConsole(t);
        }
    }

    public void afficherTabConsole (Tableau tab) {
        for (int i=0;i<tab.getTaille();i++) {
            for (int j=0;j<tab.getTaille();j++) {
                System.out.print((int)tab.getVal(i,j)+" ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public Turtle createFenetreGraphique(int width) {                
        Turtle turtle = new Turtle();
        turtle.create(width, width + 100);
        turtle.setLayout(null);
        turtle.setTitle("Jeu de la vie");

        JButton btnNext = new JButton("Suivant");
        btnNext.setBounds(140, 20, 100, 30);
        turtle.add(btnNext);
        btnNext.setVisible(true);
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tabDisplayed < simulation.size() - 1) {
                    tabDisplayed++;
                    int step = width / simulation.get(0).getTaille();
                    afficherTableauGraphique(turtle, simulation.get(tabDisplayed), step);
                }
            }
        });

        JButton btnPrevious = new JButton("Précédent");
        btnPrevious.setBounds(20, 20, 100, 30);
        turtle.add(btnPrevious);
        btnPrevious.setVisible(true);
        btnPrevious.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tabDisplayed > 0) {
                    tabDisplayed--;
                    int step = width / simulation.get(0).getTaille();
                    afficherTableauGraphique(turtle, simulation.get(tabDisplayed), step);
                }
            }
        });

        return turtle;
    }

    public void afficherTableauGraphique(Turtle turtle, Tableau tab, int step) {
        turtle.clear();
        turtle.setColor(java.awt.Color.BLACK);
        
        turtle.fly(0, 0);
        turtle.go(tab.getTaille() * step, 0);
        turtle.go(tab.getTaille() * step, tab.getTaille() * step);
        turtle.go(0, tab.getTaille() * step);
        turtle.go(0, 0);

            for (int i = 0; i< tab.getTaille(); i++) {
            turtle.fly(i * step, 0);
            turtle.go(i * step, tab.getTaille() * step);
            
            for (int j = 0; j < tab.getTaille(); j++) {
                turtle.fly(0, j * step);
                turtle.go(tab.getTaille() * step, j * step);

                if (tab.getVal(j, i) == 1) {
                    turtle.fly((i + 0.5)*step,(tab.getTaille() - j - 0.5)*step);
                    turtle.setColor(java.awt.Color.BLACK);
                    turtle.spot(step);
                }
            }
        }

        turtle.render();
    }
}