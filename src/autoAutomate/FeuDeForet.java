package src.autoAutomate;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Color;

public class FeuDeForet extends JFrame implements ActionListener  {

    private ArrayList<Tableau> simulation = new ArrayList<Tableau>();
    private Regles reg;

    // Interface graphique
    private int width = 500;
    private int frameDisplayed = 0;
    private Turtle turtle = new Turtle();;
    private JComboBox<String> choixGeneration;
    private JTextField fieldEtapes, fieldTaille, fieldQ, fieldP, fieldDensite, fieldPuisVent, fieldRotVent;
    private JButton btnSimulation;

    public FeuDeForet() {
        this.reg = new Regles();
        this.reg.charger("data/Feu_foret8.dac");
        for (int i=0;i<this.reg.getVarList().length;i++) {
            System.out.print(this.reg.getVarList()[i]+" ");
        }
    }
    
    public void main() {

        // Gestion des touches
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent ke) {
            synchronized (FeuDeForet.class) {
                switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                            nextFrame();                  
                        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                            previousFrame();
                        }
                        break;
                    }
                    return false;
                }
            }
        });

        // Création de l'interface graphique

        JFrame f = new JFrame("Simulation - Feu de forêt");
        
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                turtle.dispatchEvent(new WindowEvent(turtle, WindowEvent.WINDOW_CLOSING));
            }
        });

        JLabel titre = new JLabel("Feu de forêt");
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

        JLabel labelQ = new JLabel("Proba feu instant :");
        labelQ.setBounds(20,100,120,30);
        fieldQ = new JTextField("0.1");
        fieldQ.setBounds(20,130,120,30);

        JLabel labelP = new JLabel("Proba propagation feu :");
        labelP.setBounds(150,100,150,30);
        fieldP = new JTextField("0.1");
        fieldP.setBounds(150,130,120,30);

        JLabel labelDensite = new JLabel("Densite de forêt :");
        labelDensite.setBounds(300,100,150,30);
        fieldDensite = new JTextField("0.5");
        fieldDensite.setBounds(300,130,120,30);

        JLabel labelPuisVent = new JLabel("Puissance du vent :");
        labelPuisVent.setBounds(20,160,120,30);
        fieldPuisVent = new JTextField("0.1");
        fieldPuisVent.setBounds(20,190,120,30);

        JLabel labelRotVent = new JLabel("Orientation du vent :");
        labelRotVent.setBounds(150,160,120,30);
        fieldRotVent = new JTextField("270");
        fieldRotVent.setBounds(150,190,120,30);

        btnSimulation = new JButton("Lancer la simulation");
        btnSimulation.setBounds(20,230,150,30);
        btnSimulation.addActionListener(this);

        f.add(titre);
        f.add(labelTaille);
        f.add(fieldTaille);
        f.add(labelEtapes);
        f.add(fieldEtapes);
        f.add(labelQ);
        f.add(fieldQ);
        f.add(labelP);
        f.add(fieldP);
        f.add(labelDensite);
        f.add(fieldDensite);
        f.add(labelPuisVent);
        f.add(fieldPuisVent);
        f.add(labelRotVent);
        f.add(fieldRotVent);
        f.add(choixGeneration);
        f.add(btnSimulation);
        f.setSize(500,500);
        f.setLayout(null);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Lancement de la simulation
        if(e.getSource() == btnSimulation){
            int taille = 0;
            int etapes = 0;
            double densite = 0;
            try {
                taille = Integer.parseInt(fieldTaille.getText());
                etapes = Integer.parseInt(fieldEtapes.getText());
                reg.setVar("q",Double.parseDouble(fieldQ.getText()));
                reg.setVar("p",Double.parseDouble(fieldP.getText()));
                densite=Double.parseDouble(fieldDensite.getText());
                double puis=Double.parseDouble(fieldPuisVent.getText());
                double rot=Math.PI*(180-Double.parseDouble(fieldRotVent.getText()))/180;
                reg.setVar("fh",puis*Math.sin(rot));
                reg.setVar("fd",puis*Math.cos(rot));
            } catch (NumberFormatException ex) {
                System.out.println("Veuillez entrer des valeurs correctes");
                return;
            }

            // On verifie que les champs ne sont pas vides et que les valeurs sont correctes
            if (taille < 1 || etapes < 1 || densite<0) {
                System.out.println("Veuillez entrer des valeurs correctes");
                return;
            }

            Tableau tab = new Tableau (2, taille);

            if (choixGeneration.getSelectedItem() == "Initailisation manuelle") {
                initialiserTableauManuelle(tab);
            }
            else {
                initialiserTableauAleatoire(tab,densite);
            }

            //  On lance la simulation
            simuler(tab, etapes);

            int step = width / simulation.get(0).getTaille();

            // On ferme la fenetre de l'interface graphique précédente
            turtle.dispatchEvent(new WindowEvent(turtle, WindowEvent.WINDOW_CLOSING));


            // On crée une nouvelle fenetre pour afficher la simulation
            turtle = new Turtle();
            turtle.create(width, width);
            turtle.setLayout(null);
            turtle.setTitle("Feu de forêt");

            frameDisplayed = 0;

            // On affiche le premier tableau de la simulation
            afficherTableauGraphique(simulation.get(0), step);
        }
    }

    public void initialiserTableauAleatoire(Tableau tab, double densite) {
        tab.remplir((int)(Math.pow(tab.getTaille(),tab.getDim())*densite),1);
    }

    public void initialiserTableauManuelle(Tableau tab) {
        System.out.println("Initialisation manuelle");
    }

    public void simuler(Tableau tab, int n) {
        simulation.clear();
        simulation.add(tab);
         for (int i=0; i < n; i++) {
            tab = reg.appliquer(tab);
            simulation.add(tab);
        }
    } 

    public void afficherConsole() {
        for (Tableau t : simulation) {
            t.afficher(true);
            System.out.println("");
        }
    }

    // Passe à la frame suivante
    public void nextFrame() {
        if (frameDisplayed < simulation.size() - 1) {
            frameDisplayed++;
            int step = width / simulation.get(0).getTaille();
            afficherTableauGraphique(simulation.get(frameDisplayed), step);
        }     
    }

    // Passe à la frame précédente
    public void previousFrame() {
        if (frameDisplayed > 0) {
            frameDisplayed--;
            int step = width / simulation.get(0).getTaille();
            afficherTableauGraphique(simulation.get(frameDisplayed), step);
        }   
    }

    public void afficherTableauGraphique(Tableau tab, int step) {
        turtle.clear();
        turtle.setColor(java.awt.Color.BLACK);
        
        turtle.fly(0, 0);
        turtle.go(tab.getTaille() * step, 0);
        turtle.go(tab.getTaille() * step, tab.getTaille() * step);
        turtle.go(0, tab.getTaille() * step);
        turtle.go(0, 0);

        Color ligthGreen=new Color (0,255, 0);
        Color darkGreen=new Color (0,100, 0);

        for (int i = 0; i< tab.getTaille(); i++) {
            for (int j = 0; j < tab.getTaille(); j++) {
                turtle.fly(0, j * step);
                turtle.go(tab.getTaille() * step, j * step);

                switch ((int)tab.getVal(j, i)) {
                    case 1: {
                        turtle.fly((i + 0.5)*step,(tab.getTaille() - j - 0.5)*step);
                        turtle.setColor(darkGreen);
                        turtle.spot(step);
                    }break;
                    case 2: {
                        turtle.fly((i + 0.5)*step,(tab.getTaille() - j - 0.5)*step);
                        turtle.setColor(java.awt.Color.ORANGE);
                        turtle.spot(step);
                    }break;
                    case 3: {
                        turtle.fly((i + 0.5)*step,(tab.getTaille() - j - 0.5)*step);
                        turtle.setColor(java.awt.Color.BLACK);
                        turtle.spot(step);
                    }break;
                    default: {
                        turtle.fly((i + 0.5)*step,(tab.getTaille() - j - 0.5)*step);
                        turtle.setColor(ligthGreen);
                        turtle.spot(step);
                    }
                }
            }
        }
        turtle.setColor(java.awt.Color.BLACK);
        for (int i = 0; i< tab.getTaille(); i++) {
            turtle.fly(i * step, 0);
            turtle.go(i * step, tab.getTaille() * step);
            turtle.fly(0, i * step);
            turtle.go(tab.getTaille() * step, i * step);
        }

        turtle.render();
    }
}