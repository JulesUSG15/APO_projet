package src.autoAutomate;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

import java.awt.event.*;
import javax.swing.*;


public class JeuDeLaVie extends JFrame implements ActionListener  {

    private ArrayList<Tableau> simulation = new ArrayList<Tableau>();
    private Regles reg;
     JTextField fieldEtapes, fieldTaille;
     JComboBox<String> choixGeneration;
     JButton btnSimulation;

    public JeuDeLaVie() {
        this.reg = new Regles();
        this.reg.charger("data/jeu_vie.dac");
    }
    
    public void main(String[] args) {

        // System.out.println(this.reg.getExp());

        // Création de l'interface graphique
        
        JFrame f = new JFrame();

        JLabel titre = new JLabel("Jeu de la vie");
        titre.setBounds(20,10,200,30);

        JLabel labelTaille = new JLabel("Taille du tableau :");
        labelTaille.setBounds(20,40,120,30);
        fieldTaille = new JTextField();
        fieldTaille.setBounds(20,70,120,30);

        JLabel labelEtapes = new JLabel("Nombre d'étapes :");
        labelEtapes.setBounds(150,40,120,30);
        fieldEtapes = new JTextField();
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
            this.afficherConsole();
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
        for (int j=0;j<tab.getTaille();j++) {
            for (int i=0;i<tab.getTaille();i++) {
                System.out.print((int)tab.getVal(j,i)+" ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
}