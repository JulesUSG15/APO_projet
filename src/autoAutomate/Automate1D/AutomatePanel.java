package src.autoAutomate.Automate1D;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class AutomatePanel extends JPanel {
    private List<int[]> etatsAutomate;

    public AutomatePanel() {
        etatsAutomate = new ArrayList<>();
    }

    public void setAutomate(int[] etat) {
        etatsAutomate.clear();
        etatsAutomate.add(etat);
        repaint();
    }

    public void ajouterEtat(int[] etat) {
        etatsAutomate.add(etat);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = 10;

        for (int etape = 0; etape < etatsAutomate.size(); etape++) {
            for (int i = 0; i < etatsAutomate.get(etape).length; i++) {
                g.setColor(etatsAutomate.get(etape)[i] == 1 ? Color.BLACK : Color.WHITE);
                g.fillRect(i * cellSize, etape * cellSize, cellSize, cellSize);
            }
        }
    }
}
