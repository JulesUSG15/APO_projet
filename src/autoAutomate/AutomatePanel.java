package src.autoAutomate;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class AutomatePanel extends JPanel {
    private List<int[]> etatsAutomate; // Liste pour stocker les états de chaque étape

    public AutomatePanel() {
        etatsAutomate = new ArrayList<>();
    }

    public void ajouterEtat(int[] etat) {
        etatsAutomate.add(etat);
        repaint();
    }

    public void reinitialiser() {
        etatsAutomate.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = 10; // Taille de la case

        for (int etape = 0; etape < etatsAutomate.size(); etape++) {
            for (int i = 0; i < etatsAutomate.get(etape).length; i++) {
                if (etatsAutomate.get(etape)[i] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(i * cellSize, etape * cellSize, cellSize, cellSize);
            }
        }
    }
}
