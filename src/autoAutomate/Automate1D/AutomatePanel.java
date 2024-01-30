package src.autoAutomate.Automate1D;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class AutomatePanel extends JPanel {
    private int[] automate;

    public AutomatePanel() {
        this.automate = null;
    }

    public void setAutomate(int[] automate) {
        this.automate = automate;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (automate == null) return;

        int cellSize = 10; // Taille de la case
        for (int i = 0; i < automate.length; i++) {
            if (automate[i] == 1) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.WHITE);
            }
            g.fillRect(i * cellSize, 0, cellSize, cellSize);
        }
    }
}
