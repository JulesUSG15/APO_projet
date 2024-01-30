package src.autoAutomate.Automate1D;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.List;

public class AutomateStepWindow extends JFrame {
    private int currentStep;
    private AutomatePanel automatePanel;
    private List<int[]> etatsAutomate;

    public AutomateStepWindow(List<int[]> etatsAutomate, String modeAffichage) {
        this.etatsAutomate = etatsAutomate;
        this.currentStep = 0;

        setTitle("Étape de l'Automate1D");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        automatePanel = new AutomatePanel();

        if ("Affichage 2D".equals(modeAffichage)) {
            for (int[] etat : etatsAutomate) {
                automatePanel.ajouterEtat(etat);
            }
        } else {
            automatePanel.setAutomate(etatsAutomate.get(0));
        }

        add(automatePanel);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ("Affichage par étape".equals(modeAffichage)) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_RIGHT:
                            nextStep();
                            break;
                        case KeyEvent.VK_LEFT:
                            previousStep();
                            break;
                    }
                }
            }
        });
    }

    private void nextStep() {
        if (currentStep < etatsAutomate.size() - 1) {
            currentStep++;
            updateStep();
        }
    }

    private void previousStep() {
        if (currentStep > 0) {
            currentStep--;
            updateStep();
        }
    }

    private void updateStep() {
        automatePanel.setAutomate(etatsAutomate.get(currentStep));
        setTitle("Étape " + (currentStep + 1) + " de l'Automate1D");
    }
}
