package src.autoAutomate;

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

public class Turtle extends JFrame {

     static private Image offscreenImage;        // double buffered image
     static private Graphics2D offscreen;

     static private double x = 0.0, y = 0.0;     // turtle is at coordinate (x, y)
     static private double orientation = 0.0;    // facing this many degrees counterclockwise
     static private Insets insets;               // border around JFrame that we shouldn't use
     static private int width, height;           // size of drawing area in pixels
     static private Color bg;                    // background color

     // singleton class - user is not allowed to create new ones 


    // create a canvas with drawing area width-by-height
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

    // close the window
    public void done() {
        dispose();
    }


    // clear the background
    public void clear() {
        Color fg = offscreen.getColor();
        offscreen.setColor(bg);
        offscreen.fillRect(0, 0, width, height);    // should this be width - 1, height - 1 ?
        offscreen.setColor(fg);
    }

    // clear the background with a new color
    public void clear(Color backg) {
        bg = backg;
        clear();
    }

    // change the color of the paint
    public void setColor(Color color) { offscreen.setColor(color); }

    public void fly(double xVal, double yVal) {
        x = xVal;
        y = yVal;
    }

    public void go(double xVal, double yVal) {
        offscreen.draw(new Line2D.Double(x, y, xVal, yVal));
        x = xVal;
        y = yVal;
    }

    public void spot(double size) {
        offscreen.fill(new Rectangle2D.Double(x - size/2, y - size/2, size, size));
    }

    // circle of radius = size
    // public static void spot(double size) {
    //    offscreen.fill(new Ellipse2D.Double(x - size, y - size, 2*size, 2*size));
    // }


    // draw spot using gif - fix to be centered at (x, y)
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


    public void pixel(int x, int y) {
        offscreen.drawRect(x, y, 1, 1);
    }

    // rotate counterclockwise in degrees
    public void rotate(double angle) {
        orientation += angle;
    }

    public void forward(double d) {
        double oldx = x;
        double oldy = y;
        x += d * Math.cos(Math.toRadians(orientation));
        y += d * Math.sin(Math.toRadians(orientation));
        offscreen.draw(new Line2D.Double(x, y, oldx, oldy));
    }

    public void paint(Graphics g) {
        if (g != null && offscreenImage != null)
            g.drawImage(offscreenImage, insets.left, insets.top, this);
    }

    public void render() { repaint(); }

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