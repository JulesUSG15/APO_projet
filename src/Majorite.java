package src;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Color;

/**
 * La classe Majorite implémente une interface graphique pour simuler un automate cellulaire
 * basé sur la règle de majorité. Elle permet à l'utilisateur de configurer et de visualiser
 * les différentes étapes de la simulation.
 */
public class Majorite extends JFrame implements ActionListener  {

    /**
     * La liste des états de la simulation.
     */
    private ArrayList<Tableau> simulation = new ArrayList<Tableau>();

    /**
     * L'objet Regles qui contient la règle de majorité.
     */
    private Regles reg;

    /**
     * La largeur de la fenêtre de la simulation.
     */
    private int width = 700;

    /**
     * Le nombre d'étapes de la simulation.
     */
    private int frameDisplayed = 0, etapes;

    /**
     * L'objet Turtle qui permet d'afficher la simulation.
     */
    private Turtle turtle = new Turtle();

    /**
     * Les composants graphiques de l'interface.
     */
    private JComboBox<String> choixVoisins;

    /**
     * Les champs de texte de l'interface.
     */
    private JTextField fieldEtapes, fieldTaille, fieldAleatoire, fieldCharger, fieldSauvegarder;

    /**
     * Les boutons de l'interface.
     */
    private JButton btnSimulation, btnPrepare, btnSetTaille, btnAleatoire, btnSetCharger, btnSetSauvegarder;

    /**
     * Le tableau de l'interface graphique.
     */
    private JButton [][] tableau;

    /**
     * Le tableau initial de la simulation.
     */
    private Tableau tab;

    /**
     * La fenêtre principale de l'interface graphique.
     */
    private JFrame f;

    /**
     * Vrai si la grille est hexagonale, faux sinon.
     */
    private boolean grilleHexa;

    /**
     * Constructeur pour créer l'interface graphique de la simulation basée sur la règle de majorité.
     * Initialise les composants graphiques et configure les paramètres de la simulation.
     */
    public Majorite() {
        this.reg = new Regles();
    }

    /**
     * Configure et lance l'interface graphique principale pour la simulation de la règle de majorité.
     */
    public void main() {
        // Gestion des touches
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent ke) {
            synchronized (Majorite.class) {
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

        f = new JFrame("Simulation - Majorité");

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                turtle.dispatchEvent(new WindowEvent(turtle, WindowEvent.WINDOW_CLOSING));
            }
        });

        JLabel titre = new JLabel("Majorité");
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

        btnPrepare = new JButton("Préparer la simulation");
        btnPrepare.setBounds(20,230,170,30);
        btnPrepare.addActionListener(this);

        f.add(titre);
        f.add(labelEtapes);
        f.add(fieldEtapes);
        f.add(labelVoisins);
        f.add(choixVoisins);
        f.add(btnPrepare);
        f.setSize(500,500);
        f.setLayout(null);
        f.setVisible(true);
    }

    /**
     * Gère les actions effectuées par l'utilisateur sur l'interface graphique.
     * Cela inclut la préparation de la simulation, la modification de la taille du tableau,
     * le chargement et la sauvegarde des configurations, ainsi que le lancement de la simulation.
     * 
     * @param e L'événement d'action qui a déclenché cette méthode.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Lancement de la préparation
        if (e.getSource() == btnPrepare) {
            try {
                etapes = Integer.parseInt(fieldEtapes.getText());
                this.reg.charger("data/dac/majorite"+choixVoisins.getSelectedItem()+".dac");
                grilleHexa=Integer.parseInt((String)choixVoisins.getSelectedItem())==6;
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
        if (e.getSource()==btnAleatoire) {
            int max=0;
            try {
                max=Integer.parseInt(fieldAleatoire.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Veuillez entrer des valeurs correctes");
                return;
            }
            if (max < 1) {
                System.out.println("Veuillez entrer des valeurs correctes");
                return;
            }
            initialiserTableauAleatoire(max);
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
                        tab.setVal(i,j,(tab.getVal(i,j)+1)%50);
                        double val=tab.getVal(i,j);
                        Color fond=new Color (255-modulo((int)(97*val),256),255-modulo((int)(11*val),256),255-modulo((int)(163*val),256));
                        tableau[i][j].setBackground(fond);
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
            turtle.create(width, width+50);
            turtle.setLayout(null);

            frameDisplayed = 0;

            // On affiche le premier tableau de la simulation
            afficherTableauGraphique(simulation.get(0));
        }
    }

    /**
     * Prépare l'interface graphique pour la configuration de la simulation.
     * Permet à l'utilisateur de configurer les paramètres avant de lancer la simulation.
     */
    private void pagePreparation () {
        f = new JFrame("Préparation - Personnaliser");

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

        JLabel labelAleatoire = new JLabel("Nombre de couleurs :");
        labelAleatoire.setBounds(20,50,130,30);
        if (fieldAleatoire==null) {
            fieldAleatoire = new JTextField("15");
        }
        else {
            fieldAleatoire = new JTextField(fieldAleatoire.getText());
        }
        fieldAleatoire.setBounds(150,50,120,30);
        btnAleatoire = new JButton("Générer aléatoirement");
        btnAleatoire.setBounds(280,50,170,30);
        btnAleatoire.addActionListener(this);

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
        f.add(labelAleatoire);
        f.add(fieldAleatoire);
        f.add(btnAleatoire);
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

    /**
     * Met à jour le tableau de l'interface graphique en fonction de l'état actuel de la simulation.
     */
    private void majTableau () {
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
                double val=tab.getVal(i,j);
                Color fond=new Color (255-modulo((int)(97*val),256),255-modulo((int)(11*val),256),255-modulo((int)(163*val),256));
                tableau[i][j].setBackground(fond);
                tableau[i][j].addActionListener(this);
                f.add(tableau[i][j]);
            }
        }
    }

    /**
     * Initialise le tableau de la simulation avec des valeurs aléatoires.
     * 
     * @param max La valeur maximale des valeurs aléatoires.
     */
    private void initialiserTableauAleatoire(int max) {
        tab=new Tableau(2,tab.getTaille());
        tab.intialiserAleatoirement(0,max-1);
    }

    /**
     * Lance la simulation de la règle de majorité.
     * 
     * @param tab Le tableau initial de la simulation.
     * @param n Le nombre d'étapes de la simulation.
     */
    private void simuler(Tableau tab, int n) {
        simulation.clear();
        simulation.add(tab);
         for (int i=0; i < n; i++) {
            tab = reg.appliquer(tab);
            simulation.add(tab);
        }
    }

    /**
     * Passe à la frame suivante dans la simulation graphique.
     */
    private void nextFrame() {
        if (frameDisplayed < simulation.size() - 1) {
            frameDisplayed++;
            afficherTableauGraphique(simulation.get(frameDisplayed));
        }     
    }

    /**
     * Passe à la frame précédente dans la simulation graphique.
     */
    private void previousFrame() {
        if (frameDisplayed > 0) {
            frameDisplayed--;
            afficherTableauGraphique(simulation.get(frameDisplayed));
        }   
    }

    /**
     * Calcule le modulo de deux valeurs en tenant compte des valeurs négatives.
     * 
     * @param val1 Première valeur pour le calcul du modulo.
     * @param val2 Deuxième valeur pour le calcul du modulo.
     * @return Le résultat du modulo.
     */
    private int modulo (int val1, int val2) {
        if (val1>=0) {
            return val1%val2;
        }
        return (val2-(-val1-1)%val2-1);
    }

    /**
     * Affiche les statistiques dans l'interface graphique.
     * 
     * @param tab Le tableau dont on affiche les statistiques.
     */
    private void afficherStatistiques (Tableau tab) {
        turtle.setColor(Color.BLACK);
        turtle.drawText("Maximum : "+tab.maximum(),10,width+20,15);
        turtle.drawText("Minimum : "+tab.minimum(),240,width+20,15);
        turtle.drawText("Moyenne : "+tab.moyenne(),450,width+20,15);
    }

    /**
     * Affiche l'état actuel du tableau de l'automate dans une interface graphique.
     * 
     * @param tab Le tableau à afficher.
     */
    private void afficherTableauGraphique(Tableau tab) {
        turtle.setTitle("Majorité | Etape : "+frameDisplayed);
        double step;
        if (grilleHexa) {
            step=width / (simulation.get(0).getTaille()+0.5);
        }
        else {
            step=width / simulation.get(0).getTaille();
        }
        turtle.clear();

        for (int i = 0; i< tab.getTaille(); i++) {
            for (int j = 0; j < tab.getTaille(); j++) {

                if (grilleHexa) {
                    turtle.fly(((i+j/2)%tab.getTaille()+ 0.5*(j%2)+0.5) * width / (simulation.get(0).getTaille()+0.5),(tab.getTaille() - j - 0.5)*width / (simulation.get(0).getTaille()+0.5));
                }
                else {
                    turtle.fly((i + 0.5)*width / simulation.get(0).getTaille(),(tab.getTaille() - j - 0.5)*width / simulation.get(0).getTaille());
                }
                double val=tab.getVal(i,j);
                Color fond=new Color (255-modulo((int)(97*val),256),255-modulo((int)(11*val),256),255-modulo((int)(163*val),256));
                turtle.setColor(fond);
                turtle.spot(step);
            }
        }
        afficherStatistiques(tab);
        turtle.render();
    }
}