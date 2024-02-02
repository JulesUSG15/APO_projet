package src;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Color;

/**
 * La classe JeuDeLaVie implémente une interface graphique pour simuler le jeu de la vie,
 * un automate cellulaire. Elle permet à l'utilisateur de configurer et de visualiser
 * les différentes étapes de la simulation.
 */
public class JeuDeLaVie extends JFrame implements ActionListener  {

    /**
     * La liste des tableaux représentant les étapes de la simulation.
     */
    private ArrayList<Tableau> simulation = new ArrayList<Tableau>();

    /**
     * Les règles de l'automate.
     */
    private Regles reg;

    /**
     * Le tableau représentant l'état de l'automate.
     */
    private Tableau tab;

    /**
     * Le nombre d'étapes à simuler.
     */
    private int etapes;

    /**
     * La largeur de la fenêtre de simulation.
     */
    private int width = 700;

    /**
     * Le nombre de frames affichées dans la simulation.
     */
    private int frameDisplayed = 0;

    /**
     * L'interface graphique pour la simulation.
     */
    private Turtle turtle = new Turtle();

    /**
     * Les composants graphiques de l'interface graphique principale.
     */
    private JTextField fieldEtapes, fieldTaille, fieldDensite, fieldCharger, fieldSauvegarder;

    /**
     * Les boutons de l'interface graphique principale.
     */
    private JButton btnSimulation, btnPrepare, btnSetTaille, btnSetDensite, btnSetCharger, btnSetSauvegarder;

    /**
     * Le tableau graphique de l'interface graphique principale.
     */
    private JButton [][] tableau;

    /**
     * La fenêtre de l'interface graphique principale.
     */
    private JFrame f;

    /**
     * Constructeur pour créer l'interface graphique du jeu de la vie.
     * Initialise les composants graphiques et charge les règles de base du jeu de la vie.
     */
    public JeuDeLaVie() {
        this.reg = new Regles();
        this.reg.charger("data/dac/jeu_vie.dac");
    }

    /**
     * Configure et lance l'interface graphique principale pour la simulation du jeu de la vie.
     */
    public void main() {

        // Gestion des touches
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent ke) {
            synchronized (JeuDeLaVie.class) {
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

        f = new JFrame("Simulation - Jeu de la vie");

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                turtle.dispatchEvent(new WindowEvent(turtle, WindowEvent.WINDOW_CLOSING));
            }
        });

        JLabel titre = new JLabel("Jeu de la vie");
        titre.setBounds(20,10,200,30);

        JLabel labelEtapes = new JLabel("Nombre d'étapes :");
        labelEtapes.setBounds(20,40,120,30);
        fieldEtapes = new JTextField("5");
        fieldEtapes.setBounds(20,70,120,30);

        btnPrepare = new JButton("Préparer la simulation");
        btnPrepare.setBounds(20,150,170,30);
        btnPrepare.addActionListener(this);

        f.add(titre);
        f.add(labelEtapes);
        f.add(fieldEtapes);
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
            double densite=0;
            try {
                densite=Double.parseDouble(fieldDensite.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Veuillez entrer des valeurs correctes");
                return;
            }
            if (densite < 0 || 1<densite) {
                System.out.println("Veuillez entrer des valeurs correctes");
                return;
            }
            initialiserTableauAleatoire(densite);
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
                        tab.setVal(i,j,(tab.getVal(i,j)+1)%2);
                        switch ((int)tab.getVal(i,j)) {
                            case 1: tableau[i][j].setBackground(Color.BLACK);break;
                            default: {
                                tableau[i][j].setBackground(Color.WHITE);
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

    /**
     * Prépare l'interface graphique pour la configuration de la simulation.
     * Permet à l'utilisateur de configurer les paramètres avant de lancer la simulation.
     */
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

        JLabel labelDensite = new JLabel("Densité :");
        labelDensite.setBounds(20,50,130,30);
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

    /**
     * Met à jour le tableau de l'interface graphique en fonction de l'état actuel de la simulation.
     */
    public void majTableau () {
        double step = 0.92*500 / tab.getTaille();
        for (int i=0;i<tab.getTaille();i++) {
            for (int j=0;j<tab.getTaille();j++) {
                tableau[i][j]=new JButton ();
                tableau[i][j].setBounds((int)(12+i*step),(int)(200+j*step),(int)(step),(int)(step));
                switch ((int)tab.getVal(i,j)) {
                    case 1: tableau[i][j].setBackground(Color.BLACK);break;
                    default: tableau[i][j].setBackground(Color.WHITE);
                }
                tableau[i][j].addActionListener(this);
                f.add(tableau[i][j]);
            }
        }
    }

    /**
     * Initialise le tableau de l'automate avec une densité donnée de manière aléatoire.
     * 
     * @param densite La densité à utiliser pour l'initialisation.
     */
    public void initialiserTableauAleatoire(double densite) {
        tab=new Tableau(2,tab.getTaille());
        tab.remplir((int)(Math.pow(tab.getTaille(),tab.getDim())*densite),1);
    }

    /**
     * Lance la simulation du jeu de la vie sur un tableau donné.
     * 
     * @param tab Le tableau à utiliser pour la simulation.
     * @param n Le nombre d'étapes à simuler.
     */
    public void simuler(Tableau tab, int n) {
        simulation.clear();
        simulation.add(tab);
         for (int i=0; i < n; i++) {
            tab = reg.appliquer(tab);
            simulation.add(tab);
        }
    } 

    /**
     * Affiche la simulation dans la console.
     */
    public void afficherConsole() {
        for (Tableau t : simulation) {
            t.afficher(true);
            System.out.println("");
        }
    }

    /**
     * Passe à la frame suivante dans la simulation graphique.
     */
    public void nextFrame() {
        if (frameDisplayed < simulation.size() - 1) {
            frameDisplayed++;
            afficherTableauGraphique(simulation.get(frameDisplayed));
        }     
    }

    /**
     * Retourne à la frame précédente dans la simulation graphique.
     */
    public void previousFrame() {
        if (frameDisplayed > 0) {
            frameDisplayed--;
            afficherTableauGraphique(simulation.get(frameDisplayed));
        }   
    }

    /**
     * Affiche l'état actuel du tableau de l'automate dans une interface graphique.
     * 
     * @param tab Le tableau à afficher.
     */
    public void afficherTableauGraphique(Tableau tab) {
        turtle.setTitle("Jeu de la vie | Etape : "+frameDisplayed);
        double step=width / simulation.get(0).getTaille();
        turtle.clear();

        for (int i = 0; i< tab.getTaille(); i++) {
            for (int j = 0; j < tab.getTaille(); j++) {
                    turtle.fly((i + 0.5)*width / simulation.get(0).getTaille(),(tab.getTaille() - j - 0.5)*width / simulation.get(0).getTaille());
                if (tab.getVal(i, j)==1) {
                    turtle.setColor(java.awt.Color.BLACK);
                    turtle.spot(step);
                }
            }
        }

        turtle.render();
    }
}