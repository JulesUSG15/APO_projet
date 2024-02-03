package src;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Color;
import java.awt.Font;

/**
 * La classe Personnaliser implémente une interface graphique pour personnaliser et simuler
 * un automate cellulaire selon des règles définies par l'utilisateur. Elle permet de configurer
 * les paramètres de la simulation et d'afficher les différentes étapes de celle-ci.
 */
public class Personnaliser extends JFrame implements ActionListener  {

    /**
     * La liste des tableaux de la simulation.
     */
    private ArrayList<Tableau> simulation = new ArrayList<Tableau>();

    /**
     * L'objet Regles qui contient la règle de l'automate.
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
    private JTextField fieldEtapes, fieldTaille, fieldAleatoire, fieldChargerDAC, fieldSauvegarderDAC, fieldCharger, fieldSauvegarder, fieldPas;

    /**
     * Les composants graphiques de l'interface.
     */
    private JTextArea fieldDAC;

    /**
     * Les boutons de l'interface.
     */
    private JButton btnSimulation, btnPrepare, btnSetTaille, btnAleatoire, btnSetChargerDAC, btnSetSauvegarderDAC, btnSetCharger, btnSetSauvegarder, btnChangerGrille;

    /**
     * Le tableau de l'interface graphique.
     */
    private JButton [][] tableau;

    /**
     * Le tableau de la simulation.
     */
    private Tableau tab;

    /**
     * Les fenêtres de l'interface graphique.
     */
    private JFrame f, p;

    /**
     * Le type de grille de l'interface graphique.
     */
    private boolean grilleHexa;

    /**
     * Constructeur pour créer l'interface graphique de personnalisation de l'automate cellulaire.
     * Initialise les composants graphiques et configure les paramètres de la simulation.
     */
    public Personnaliser() {
        this.reg = new Regles();
    }

    /**
     * Configure et lance l'interface graphique principale pour la personnalisation de la simulation.
     */
    public void main() {

        // Gestion des touches
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent ke) {
            synchronized (Personnaliser.class) {
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

        pageDAC("");
    }

    /**
     * Configure et lance l'interface graphique pour la personnalisation de la règle.
     * 
     * @param code Le code de la règle à personnaliser.
     */
    private void pageDAC (String code) {
        p = new JFrame("Simulation - Personnaliser");

        p.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                turtle.dispatchEvent(new WindowEvent(turtle, WindowEvent.WINDOW_CLOSING));
            }
        });

        JLabel titre = new JLabel("Personnaliser");
        titre.setBounds(20,10,200,30);

        JLabel labelEtapes = new JLabel("Nombre d'étapes :");
        labelEtapes.setBounds(20,40,120,30);
        if (fieldEtapes==null) {
            fieldEtapes = new JTextField("5");
        }
        else {
            fieldEtapes = new JTextField(fieldEtapes.getText());
        }
        fieldEtapes.setBounds(150,40,120,30);

        JLabel labelChargerDAC = new JLabel("Règle à charger :");
        labelChargerDAC.setBounds(20,80,120,30);
        if (fieldChargerDAC==null) {
            fieldChargerDAC = new JTextField("data/dac/nom.dac");
        }
        else  {
            fieldChargerDAC = new JTextField(fieldChargerDAC.getText());
        }
        fieldChargerDAC.setBounds(150,80,120,30);
        btnSetChargerDAC = new JButton("Charger la règle");
        btnSetChargerDAC.setBounds(280,80,170,30);
        btnSetChargerDAC.addActionListener(this);

        JLabel labelSauvegarderDAC = new JLabel("Sauvegarde de règle :");
        labelSauvegarderDAC.setBounds(20,110,130,30);
        if (fieldSauvegarderDAC==null) {
            fieldSauvegarderDAC = new JTextField("data/dac/nom.dac");
        }
        else {
            fieldSauvegarderDAC = new JTextField(fieldSauvegarderDAC.getText());
        }
        fieldSauvegarderDAC.setBounds(150,110,120,30);
        btnSetSauvegarderDAC = new JButton("Sauvegarder la règle");
        btnSetSauvegarderDAC.setBounds(280,110,170,30);
        btnSetSauvegarderDAC.addActionListener(this);

        btnPrepare = new JButton("Préparer la simulation");
        btnPrepare.setBounds(20,200,170,30);
        btnPrepare.addActionListener(this);

        fieldDAC = new JTextArea(code);
        fieldDAC.setBounds(20,250,455,400);

        p.add(titre);
        p.add(labelEtapes);
        p.add(fieldEtapes);
        p.add(labelChargerDAC);
        p.add(fieldChargerDAC);
        p.add(btnSetChargerDAC);
        p.add(labelSauvegarderDAC);
        p.add(fieldSauvegarderDAC);
        p.add(btnSetSauvegarderDAC);
        p.add(btnPrepare);
        p.add(fieldDAC);
        p.setSize(500,700);
        p.setLayout(null);
        p.setVisible(true);
    }

    /**
     * Gère les actions effectuées par l'utilisateur sur l'interface graphique.
     * Cela inclut la personnalisation des règles, la modification de la taille du tableau,
     * le chargement et la sauvegarde des configurations, ainsi que le lancement de la simulation.
     * 
     * @param e L'événement d'action qui a déclenché cette méthode.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Charger une règle
        if (e.getSource() == btnSetChargerDAC) {
            if (reg.charger(fieldChargerDAC.getText())) {
                p.dispose();
                pageDAC(reg.getExp());
            }
            else {
                System.out.println(reg.getErreur());
                System.out.println("Erreur lors du chargement de "+fieldChargerDAC.getText());
                return;
            }
        }
        // Sauvegarder une règle
        if (e.getSource() == btnSetSauvegarderDAC) {
            if (reg.set(fieldDAC.getText())) {
                if (reg.sauvegarder(fieldSauvegarderDAC.getText())) {
                    p.dispose();
                    System.out.println("Règle sauvegardée");
                    pageDAC(reg.getExp());
                }
                else {
                    System.out.println("Erreur lors de la sauvegarde de "+fieldSauvegarderDAC.getText());
                    return;
                }
            }
            else {
                System.out.println("Sauvegarde impossible : La règle n'est pas correcte");
                System.out.println(reg.getErreur());
            }
        }
        // Lancement de la préparation
        if (e.getSource() == btnPrepare) {
            if (reg.set(fieldDAC.getText())) {
                if (reg.getDim()==2) {
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
                else {
                    System.out.println("Lancement impossible : La régle n'est pas de dimension 2");
                }
            }
            else {
                System.out.println("Lancement impossible : La règle n'est pas correcte");
                System.out.println(reg.getErreur());
            }
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
        // Changer type de grille
        if (e.getSource()==btnChangerGrille) {
            if (grilleHexa) {
                grilleHexa=false;
            }
            else {
                grilleHexa=true;
            }
            f.dispose();
            pagePreparation();
        }
        // Modif du tableau
        if (tab!=null && tableau!=null) {
            for (int i=0;i<tab.getTaille();i++) {
                for (int j=0;j<tab.getTaille();j++) {
                    if (e.getSource() == tableau[i][j]) {
                        double pas=1;
                        try {
                            pas=Double.parseDouble(fieldPas.getText());
                        } catch (NumberFormatException ex) {
                            System.out.println("Veuillez entrer des valeurs correctes pour le pas des valeurs");
                            return;
                        }
                        tab.setVal(i,j,tab.getVal(i,j)+pas);
                        double val=tab.getVal(i,j);
                        Color fond=new Color (255-modulo((int)(97*val),256),255-modulo((int)(11*val),256),255-modulo((int)(163*val),256));
                        tableau[i][j].setBackground(fond);
                        tableau[i][j].setText((int)val+"");
                        tableau[i][j].setToolTipText(val+"");
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
     * Prépare l'interface graphique pour la configuration de la simulation personnalisée.
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

        btnChangerGrille= new JButton("Changer type de grille");
        btnChangerGrille.setBounds(280,140,170,30);
        btnChangerGrille.addActionListener(this);

        JLabel labelPas = new JLabel("Pas entre les valeurs :");
        labelPas.setBounds(20,170,140,30);
        if (fieldPas==null) {
            fieldPas = new JTextField("1");
        }
        else {
            fieldPas = new JTextField(fieldPas.getText());
        }
        fieldPas.setBounds(150,170,120,30);

        btnSimulation = new JButton("Lancer la simulation");
        btnSimulation.setBounds(150,220,170,30);
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
        f.add(btnChangerGrille);
        f.add(labelPas);
        f.add(fieldPas);
        f.add(btnSimulation);
        f.setSize(500,800);
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
        Font font=new Font ("Serif",Font.BOLD,(int)(100/tab.getTaille()));
        for (int i=0;i<tab.getTaille();i++) {
            for (int j=0;j<tab.getTaille();j++) {
                tableau[i][j]=new JButton ();
                if (grilleHexa) {
                    tableau[i][j].setBounds((int)(12+((i+j/2)%tab.getTaille()+ 0.5*(j%2)) * step),(int)(300+j*step),(int)(step),(int)(step));
                }
                else {
                    tableau[i][j].setBounds((int)(12+i*step),(int)(300+j*step),(int)(step),(int)(step));
                }
                double val=tab.getVal(i,j);
                Color fond=new Color (255-modulo((int)(97*val),256),255-modulo((int)(11*val),256),255-modulo((int)(163*val),256));
                tableau[i][j].setBackground(fond);
                tableau[i][j].setText((int)tab.getVal(i,j)+"");
                tableau[i][j].setFont(font);
                tableau[i][j].setToolTipText(val+"");
                tableau[i][j].addActionListener(this);
                f.add(tableau[i][j]);
            }
        }
    }

    /**
     * Calcule le modulo de deux entiers.
     * 
     * @param val1 Le premier entier.
     * @param val2 Le second entier.
     * @return Le modulo de val1 par val2.
     */
    private int modulo (int val1, int val2) {
        if (val1>=0) {
            return val1%val2;
        }
        return (val2-(-val1-1)%val2-1);
    }

    /**
     * Initialise le tableau de simulation avec des valeurs aléatoires.
     * 
     * @param max La valeur maximale des valeurs aléatoires.
     */
    private void initialiserTableauAleatoire(int max) {
        tab=new Tableau(2,tab.getTaille());
        tab.intialiserAleatoirement(0,max-1);
        double pas=1;
        try {
            pas=Double.parseDouble(fieldPas.getText());
        } catch (NumberFormatException ex) {
            System.out.println("Veuillez entrer des valeurs correctes pour le pas des valeurs");
            return;
        }
        if (pas!=1) {
            for (int i=0;i<tab.getTaille();i++) {
                for (int j=0;j<tab.getTaille();j++) {
                    tab.setVal(i,j,tab.getVal(i,j)*pas);
                }
            }
        }
    }

    /**
     * Lance la simulation de l'automate cellulaire.
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
     * Passe à la frame suivante.
     */
    private void nextFrame() {
        if (frameDisplayed < simulation.size() - 1) {
            frameDisplayed++;
            afficherTableauGraphique(simulation.get(frameDisplayed));
        }     
    }

    /**
     * Passe à la frame précédente.
     */
    private void previousFrame() {
        if (frameDisplayed > 0) {
            frameDisplayed--;
            afficherTableauGraphique(simulation.get(frameDisplayed));
        }   
    }

    /**
     * Affiche les statistiques dans l'interface graphique.
     * 
     * @param tab Le tableau dont on affiche les statistiques.
     */
    private void afficherStatistiques (Tableau tab) {
        turtle.setColor(Color.BLACK);
        turtle.drawText("Maximum : "+tab.maximum(),10,width+35,15);
        turtle.drawText("Minimum : "+tab.minimum(),240,width+35,15);
        turtle.drawText("Moyenne : "+tab.moyenne(),470,width+35,15);
        int [] occurences=new int [4];
        double [] valeurs=tab.countAll(occurences);
        int total=tab.getTaille()*tab.getTaille();
        turtle.drawText("Cellules "+valeurs[0]+" : "+occurences[0]+" | "+(int)(1000000.0*occurences[0]/total)/10000.0+"%",10,width+20,15);
        if (valeurs.length>1) {
            turtle.drawText("Cellules "+valeurs[1]+" : "+occurences[1]+" | "+(int)(1000000.0*occurences[1]/total)/10000.0+"%",370,width+20,15);
            if (valeurs.length>2) {
                turtle.drawText("Cellules "+valeurs[2]+" : "+occurences[2]+" | "+(int)(1000000.0*occurences[2]/total)/10000.0+"%",10,width+5,15);
                if (valeurs.length>3) {
                    if (valeurs.length==4) {
                        turtle.drawText("Cellules "+valeurs[3]+" : "+occurences[3]+" | "+(int)(1000000.0*occurences[3]/total)/10000.0+"%",370,width+5,15);
                    }
                    else {
                        int autres=total-occurences[0]-occurences[1]-occurences[2];
                        turtle.drawText("Autres : "+autres+" | "+(int)(1000000.0*autres/total)/10000.0+"%",370,width+5,15);
                    }
                }
            }
        }
    }

    /**
     * Affiche le tableau de la simulation dans l'interface graphique.
     * 
     * @param tab Le tableau à afficher.
     */
    private void afficherTableauGraphique(Tableau tab) {
        turtle.setTitle("Personnaliser | Etape : "+frameDisplayed);
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