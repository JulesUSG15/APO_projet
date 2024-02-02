package src;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

// for images and audio
import java.net.URL;

// for audio files only

// Support for saving the canvas to a file
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * La classe Turtle fournit une interface graphique simple pour dessiner.
 * Elle implémente les fonctionnalités d'un "turtle graphics" pour un dessin facile et intuitif.
 */
public class Turtle extends JFrame {

    /**
     * Crée une zone de dessin avec les dimensions spécifiées.
     */
     static private Image offscreenImage;        // double buffered image
     static private Graphics2D offscreen;
     static private double x = 0.0, y = 0.0;     // turtle is at coordinate (x, y)
     static private double orientation = 0.0;    // facing this many degrees counterclockwise
     static private Insets insets;               // border around JFrame that we shouldn't use
     static private int width, height;           // size of drawing area in pixels
     static private Color bg;                    // background color

     // singleton class - user is not allowed to create new ones 


    /**
     * Crée une zone de dessin avec les dimensions spécifiées.
     *
     * @param w La largeur de la zone de dessin.
     * @param h La hauteur de la zone de dessin.
     */
    public void create(int w, int h) {
        width = w;
        height = h;
        setSize(new Dimension(width, height));
        setResizable(true);
        setVisible(true);  

        // re-adjust the size of the frame so that we don't lose space for insets
        insets = getInsets();
        setSize(new Dimension(width + insets.left + insets.right, height + insets.top + insets.bottom));

        // Initialize the picture
        init();
    }

    /**
     * Initialise la zone de dessin.
     */
    private void init() {
        // create double buffered image and graphics handle
        offscreenImage = createImage(width, height);
        offscreen = (Graphics2D) offscreenImage.getGraphics();

        // rescale and translate so that (0, 0) is lower left
        offscreen.translate(0, height);
        offscreen.scale(1.0, -1.0);

        // clear the screen
        clear(Color.white);
    }

    /**
     * Ferme la fenêtre de dessin.
     */
    public void done() {
        dispose();
    }

    /**
     * Efface la zone de dessin.
     */
    public void clear() {
        Color fg = offscreen.getColor();
        offscreen.setColor(bg);
        offscreen.fillRect(0, 0, width, height);    // should this be width - 1, height - 1 ?
        offscreen.setColor(fg);
    }

    /**
     * Efface la zone de dessin et définit une nouvelle couleur de fond.
     *
     * @param backg La nouvelle couleur de fond.
     */
    public void clear(Color backg) {
        bg = backg;
        clear();
    }

    /**
     * Change la couleur du pinceau.
     *
     * @param color La nouvelle couleur.
     */
    public void setColor(Color color) { offscreen.setColor(color); }

    /**
     * Déplace la "tortue" à une position spécifiée sans dessiner.
     *
     * @param xVal La coordonnée x de la nouvelle position.
     * @param yVal La coordonnée y de la nouvelle position.
     */
    public void fly(double xVal, double yVal) {
        x = xVal;
        y = yVal;
    }

    /**
     * Déplace la "tortue" à une position spécifiée en dessinant.
     *
     * @param xVal La coordonnée x de la nouvelle position.
     * @param yVal La coordonnée y de la nouvelle position.
     */
    public void go(double xVal, double yVal) {
        offscreen.draw(new Line2D.Double(x, y, xVal, yVal));
        x = xVal;
        y = yVal;
    }

    /**
     * Affiche du texte sur la fenêtre.
     *
     * @param text le texte à afficher.
     * @param x La coordonnée x du texte.
     * @param y La coordonnée y du texte.
     */
    public void drawText(String text, double x, double y, int fontSize) {
        AffineTransform originalTransform = offscreen.getTransform();

        // Réinitialisation des transformations à l'identité (pas de translation ou d'échelle)
        offscreen.setTransform(new AffineTransform());

         // Configuration de la taille de la police
        Font font = new Font("SansSerif", Font.PLAIN, fontSize);
        offscreen.setFont(font);

        // Dessin du texte
        offscreen.drawString(text, (int) x, height-(int)y);

        // Restauration des transformations précédentes
        offscreen.setTransform(originalTransform);
    }

    /**
     * Dessine un point à la position actuelle de la "tortue".
     *
     * @param size La taille du point.
     */
    public void spot(double size) {
        offscreen.fill(new Rectangle2D.Double(x - size/2, y - size/2, size, size));
    }

    // circle of radius = size
    // public static void spot(double size) {
    //    offscreen.fill(new Ellipse2D.Double(x - size, y - size, 2*size, 2*size));
    // }


    /**
     * Dessine un cercle.
     *
     * @param radius Le rayon du cercle.
     */
    public void spot(String s) {
        //  needed to load from jar
        URL url = Turtle.class.getResource(s); 
        Image image = Toolkit.getDefaultToolkit().getImage(url); 

        // Image image = Toolkit.getDefaultToolkit().getImage(s);

        offscreen.scale(1.0, -1.0);
        offscreen.translate(0, -height);

        offscreen.drawImage(image, (int) x - image.getWidth(null)  / 2,
                                   (int) y - image.getHeight(null) / 2, null);
        offscreen.translate(0, height);
        offscreen.scale(1.0, -1.0);
/*
        offscreen.drawImage(image, (int) x, (int) y ,
                                   (int) x + image.getWidth(null), (int) y -image.getHeight(null),
                                   0, 0, image.getWidth(null), image.getHeight(null), null);
  */
    }

    /**
     * Dessine un pixel à une position spécifiée.
     *
     * @param x La coordonnée x du pixel.
     * @param y La coordonnée y du pixel.
     */
    public void pixel(int x, int y) {
        offscreen.drawRect(x, y, 1, 1);
    }

    /**
     * Fait pivoter d'un certain angle.
     *
     * @param angle L'angle de rotation en degrés.
     */
    public void rotate(double angle) {
        orientation += angle;
    }

    /**
     * Avance d'une distance spécifiée dans sa direction actuelle.
     *
     * @param d La distance à avancer.
     */
    public void forward(double d) {
        double oldx = x;
        double oldy = y;
        x += d * Math.cos(Math.toRadians(orientation));
        y += d * Math.sin(Math.toRadians(orientation));
        offscreen.draw(new Line2D.Double(x, y, oldx, oldy));
    }

    /**
     * Méthode interne pour dessiner le contenu actuel.
     * 
     * @param g Le contexte graphique.
     */
    public void paint(Graphics g) {
        if (g != null && offscreenImage != null)
            g.drawImage(offscreenImage, insets.left, insets.top, this);
    }

    /**
     * Rafraîchit la fenêtre pour afficher le contenu actuel.
     */
    public void render() { repaint(); }

    /**
     * Sauvegarde le contenu actuel dans un fichier.
     *
     * @param s Le nom du fichier.
     */
    public void save(String s) {
        BufferedImage bi = (BufferedImage) offscreenImage;
        System.out.println("Saving to " + s);
        try {
            File f = new File(s);
            ImageIO.write(bi, "png", f);
        }
        catch (IOException e) {
            System.out.println("Unable to write to file " + s);
            e.printStackTrace();
        }
    }
}