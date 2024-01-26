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
    private int width = 700;
    private int frameDisplayed = 0, etapes;
    private Turtle turtle = new Turtle();
    private JComboBox<String> choixVoisins;
    private JTextField fieldEtapes, fieldTaille, fieldQ, fieldP, fieldDensite, fieldPuisVent, fieldRotVent, fieldCharger, fieldSauvegarder;
    private JButton btnSimulation, btnPrepare, btnSetTaille, btnSetDensite, btnSetCharger, btnSetSauvegarder;
    private JButton [][] tableau;
    private Tableau tab;
    private JFrame f;
    private boolean grilleHexa;

    public FeuDeForet() {
        this.reg = new Regles();
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

        tab=new Tableau(2,10);

        f = new JFrame("Simulation - Feu de forêt");
        
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                turtle.dispatchEvent(new WindowEvent(turtle, WindowEvent.WINDOW_CLOSING));
            }
        });

        JLabel titre = new JLabel("Feu de forêt");
        titre.setBounds(20,10,200,30);

        JLabel labelEtapes = new JLabel("Nombre d'étapes :");
        labelEtapes.setBounds(20,40,120,30);
        fieldEtapes = new JTextField("5");
        fieldEtapes.setBounds(20,70,120,30);

        JLabel labelVoisins = new JLabel("Nombre de voisins :");
        labelVoisins.setBounds(150,40,120,30);
        String[] choices = { "4","6","8"};
        choixVoisins = new JComboBox<String>(choices);
        choixVoisins.setBounds(150,70,100,30);

        JLabel labelQ = new JLabel("Proba feu instant :");
        labelQ.setBounds(20,100,120,30);
        fieldQ = new JTextField("0.1");
        fieldQ.setBounds(20,130,120,30);

        JLabel labelP = new JLabel("Proba propagation feu :");
        labelP.setBounds(150,100,150,30);
        fieldP = new JTextField("0.1");
        fieldP.setBounds(150,130,120,30);

        JLabel labelPuisVent = new JLabel("Puissance du vent :");
        labelPuisVent.setBounds(20,160,120,30);
        fieldPuisVent = new JTextField("0.1");
        fieldPuisVent.setBounds(20,190,120,30);

        JLabel labelRotVent = new JLabel("Orientation du vent :");
        labelRotVent.setBounds(150,160,120,30);
        fieldRotVent = new JTextField("270");
        fieldRotVent.setBounds(150,190,120,30);

        btnPrepare = new JButton("Préparer la simulation");
        btnPrepare.setBounds(20,230,170,30);
        btnPrepare.addActionListener(this);

        f.add(titre);
        f.add(labelEtapes);
        f.add(fieldEtapes);
        f.add(labelQ);
        f.add(fieldQ);
        f.add(labelP);
        f.add(fieldP);
        f.add(labelPuisVent);
        f.add(fieldPuisVent);
        f.add(labelRotVent);
        f.add(fieldRotVent);
        f.add(labelVoisins);
        f.add(choixVoisins);
        f.add(btnPrepare);
        f.setSize(500,500);
        f.setLayout(null);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Lancement de la préparation
        if (e.getSource() == btnPrepare) {
            try {
                etapes = Integer.parseInt(fieldEtapes.getText());
                this.reg.charger("data/dac/feu_foret"+choixVoisins.getSelectedItem()+".dac");
                grilleHexa=Integer.parseInt((String)choixVoisins.getSelectedItem())==6;
                reg.setVar("q",Double.parseDouble(fieldQ.getText()));
                reg.setVar("p",Double.parseDouble(fieldP.getText()));
                double puis=Double.parseDouble(fieldPuisVent.getText());
                double rot=Math.PI*(-90+Double.parseDouble(fieldRotVent.getText()))/180;
                reg.setVar("fh",puis*Math.sin(rot));
                reg.setVar("fd",puis*Math.cos(rot));
            } catch (NumberFormatException ex) {
                System.out.println("Veuillez entrer des valeurs correctes");
                return;
            }
            if (etapes < 0) {
                System.out.println("Veuillez entrer des valeurs correctes");
                return;
            }
            pagePreparation();
        }
        // Modif de la taille du tableau
        if (e.getSource() == btnSetTaille) {
            int taille=0;
            try {
                taille=Integer.parseInt(fieldTaille.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Veuillez entrer des valeurs correctes");
                return;
            }
            if (taille < 1) {
                System.out.println("Veuillez entrer des valeurs correctes");
                return;
            }
            tab=new Tableau (2,taille);
            f.dispose();
            pagePreparation();
        }
        // Generation aleatoire du tableau
        if (e.getSource()==btnSetDensite) {
            initialiserTableauAleatoire(Double.parseDouble(fieldDensite.getText()));
            f.dispose();
            pagePreparation();
        }
        // Chargement du fichier
        if (e.getSource()==btnSetCharger) {
            Tableau nouv=new Tableau (fieldCharger.getText());
            if (nouv==null || nouv.getDim()!=2) {
                System.out.println("Veuillez entrer des valeurs correctes");
                return;
            }
            tab=nouv;
            f.dispose();
            pagePreparation();
        }
        // Sauvegarde du fichier
        if (e.getSource()==btnSetSauvegarder) {
            tab.sauvegarder(fieldSauvegarder.getText());
            System.out.println("Tableau sauvegardé");
        }
        // Modif du tableau
        if (tab!=null  && tableau!=null) {
            for (int i=0;i<tab.getTaille();i++) {
                for (int j=0;j<tab.getTaille();j++) {
                    if (e.getSource() == tableau[i][j]) {
                        tab.setVal(i,j,(tab.getVal(i,j)+1)%4);
                        switch ((int)tab.getVal(i,j)) {
                            case 1: {
                                Color darkGreen=new Color (0,100, 0);
                                tableau[i][j].setBackground(darkGreen);
                            }break;
                            case 2: tableau[i][j].setBackground(java.awt.Color.ORANGE);break;
                            case 3: tableau[i][j].setBackground(java.awt.Color.BLACK);break;
                            default: {
                                Color ligthGreen=new Color (0,255, 0);
                                tableau[i][j].setBackground(ligthGreen);
                            }
                        }
                    }
                }
            }
        }
        // Lancement de la simulation
        if(e.getSource() == btnSimulation){

            //  On lance la simulation
            simuler(tab, etapes);

            // On ferme la fenetre de l'interface graphique précédente
            turtle.dispatchEvent(new WindowEvent(turtle, WindowEvent.WINDOW_CLOSING));


            // On crée une nouvelle fenetre pour afficher la simulation
            turtle = new Turtle();
            turtle.create(width, width);
            turtle.setLayout(null);

            frameDisplayed = 0;

            // On affiche le premier tableau de la simulation
            afficherTableauGraphique(simulation.get(0));
        }
    }

    public void pagePreparation () {
        f = new JFrame("Préparation - Feu de forêt");
        
        JLabel labelTaille = new JLabel("Taille du tableau :");
        labelTaille.setBounds(20,20,120,30);
        if (fieldTaille==null) {
            fieldTaille = new JTextField(""+tab.getTaille());
        }
        else {
            fieldTaille = new JTextField(fieldTaille.getText());
        }
        fieldTaille.setBounds(150,20,120,30);
        btnSetTaille = new JButton("Changer la taille");
        btnSetTaille.setBounds(280,20,170,30);
        btnSetTaille.addActionListener(this);

        JLabel labelDensite = new JLabel("Densité de forêt :");
        labelDensite.setBounds(20,50,120,30);
        if (fieldDensite==null) {
            fieldDensite = new JTextField("0.5");
        }
        else {
            fieldDensite = new JTextField(fieldDensite.getText());
        }
        fieldDensite.setBounds(150,50,120,30);
        btnSetDensite = new JButton("Générer aléatoirement");
        btnSetDensite.setBounds(280,50,170,30);
        btnSetDensite.addActionListener(this);

        JLabel labelCharger = new JLabel("Tableau à charger :");
        labelCharger.setBounds(20,80,120,30);
        if (fieldCharger==null) {
            fieldCharger = new JTextField("data/tableaux/nom.txt");
        }
        else {
            fieldCharger = new JTextField(fieldCharger.getText());
        }
        fieldCharger.setBounds(150,80,120,30);
        btnSetCharger = new JButton("Charger le tableau");
        btnSetCharger.setBounds(280,80,170,30);
        btnSetCharger.addActionListener(this);

        JLabel labelSauvegarder = new JLabel("Sauvegarde du tableau :");
        labelSauvegarder.setBounds(20,110,120,30);
        if (fieldSauvegarder==null) {
            fieldSauvegarder = new JTextField("data/tableaux/nom.txt");
        }
        else {
            fieldSauvegarder = new JTextField(fieldSauvegarder.getText());
        }
        fieldSauvegarder.setBounds(150,110,120,30);
        btnSetSauvegarder = new JButton("Sauvegarder le tableau");
        btnSetSauvegarder.setBounds(280,110,170,30);
        btnSetSauvegarder.addActionListener(this);

        btnSimulation = new JButton("Lancer la simulation");
        btnSimulation.setBounds(150,150,170,30);
        btnSimulation.addActionListener(this);

        tableau = new JButton [tab.getTaille()][tab.getTaille()];
        majTableau();

        f.add(labelTaille);
        f.add(fieldTaille);
        f.add(btnSetTaille);
        f.add(labelDensite);
        f.add(fieldDensite);
        f.add(btnSetDensite);
        f.add(labelCharger);
        f.add(fieldCharger);
        f.add(btnSetCharger);
        f.add(labelSauvegarder);
        f.add(fieldSauvegarder);
        f.add(btnSetSauvegarder);
        f.add(btnSimulation);
        f.setSize(500,700);
        f.setLayout(null);
        f.setVisible(true);
    }

    public void majTableau () {
        Color darkGreen=new Color (0,100, 0);
        Color ligthGreen=new Color (0,255, 0);
        double step;
        if (grilleHexa) {
            step = 0.92*500 / (tab.getTaille()+0.5);
        }
        else {
            step = 0.92*500 / tab.getTaille();
        }
        for (int i=0;i<tab.getTaille();i++) {
            for (int j=0;j<tab.getTaille();j++) {
                tableau[i][j]=new JButton ();
                if (grilleHexa) {
                    tableau[i][j].setBounds((int)(12+((i+j/2)%tab.getTaille()+ 0.5*(j%2)) * step),(int)(200+j*step),(int)(step),(int)(step));
                }
                else {
                    tableau[i][j].setBounds((int)(12+i*step),(int)(200+j*step),(int)(step),(int)(step));
                }
                switch ((int)tab.getVal(i,j)) {
                    case 1: tableau[i][j].setBackground(darkGreen);break;
                    case 2: tableau[i][j].setBackground(java.awt.Color.ORANGE);break;
                    case 3: tableau[i][j].setBackground(java.awt.Color.BLACK);break;
                    default: tableau[i][j].setBackground(ligthGreen);
                }
                tableau[i][j].addActionListener(this);
                f.add(tableau[i][j]);
            }
        }
    }

    public void initialiserTableauAleatoire(double densite) {
        tab=new Tableau(2,tab.getTaille());
        tab.remplir((int)(Math.pow(tab.getTaille(),tab.getDim())*densite),1);
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
            afficherTableauGraphique(simulation.get(frameDisplayed));
        }     
    }

    // Passe à la frame précédente
    public void previousFrame() {
        if (frameDisplayed > 0) {
            frameDisplayed--;
            afficherTableauGraphique(simulation.get(frameDisplayed));
        }   
    }

    public void afficherTableauGraphique(Tableau tab) {
        turtle.setTitle("Feu de forêt | Etape : "+frameDisplayed);
        double step;
        if (grilleHexa) {
            step=width / (simulation.get(0).getTaille()+0.5);
        }
        else {
            step=width / simulation.get(0).getTaille();
        }
        turtle.clear();

        Color ligthGreen=new Color (0,255, 0);
        Color darkGreen=new Color (0,100, 0);

        for (int i = 0; i< tab.getTaille(); i++) {
            for (int j = 0; j < tab.getTaille(); j++) {

                if (grilleHexa) {
                    turtle.fly(((i+j/2)%tab.getTaille()+ 0.5*(j%2)+0.5) * step,(tab.getTaille() - j - 0.5)*step);
                }
                else {
                    turtle.fly((i + 0.5)*step,(tab.getTaille() - j - 0.5)*step);
                }

                switch ((int)tab.getVal(i, j)) {
                    case 1: turtle.setColor(darkGreen);break;
                    case 2: turtle.setColor(java.awt.Color.ORANGE);break;
                    case 3: turtle.setColor(java.awt.Color.BLACK);break;
                    default: turtle.setColor(ligthGreen);
                }
                turtle.spot(step);
            }
        }

        turtle.render();
    }
}