package dam;

import madkit.kernel.Agent;
import madkit.kernel.Message;
import madkit.message.StringMessage;
import javax.swing.*;
import java.awt.*;

public class AgentAfficheur extends Agent {

    private JFrame frame;
    private JProgressBar barreEau;
    private JLabel labelNiveau;

    @Override
    protected void activate() {
        // 1. Création de l'interface graphique (GUI)
        setupGUI();

        // 2. Rejoindre le groupe pour écouter le capteur
        if (!isGroup("public", DamConstants.SURVEILLANCE_GROUP)) {
            createGroup("public", DamConstants.SURVEILLANCE_GROUP);
        }
        requestRole("public", DamConstants.SURVEILLANCE_GROUP, DamConstants.OBSERVATEUR_ROLE);
        
        getLogger().info("Agent Afficheur prêt et fenêtré.");
    }

    private void setupGUI() {
        frame = new JFrame("Tableau de Bord Barrage");
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        labelNiveau = new JLabel("Niveau actuel : -- m");
        barreEau = new JProgressBar(0, 120); // Graduation de 0 à 120m
        barreEau.setStringPainted(true);
        barreEau.setForeground(Color.BLUE);

        frame.add(new JLabel("SURVEILLANCE TEMPS RÉEL"));
        frame.add(barreEau);
        frame.add(labelNiveau);
        frame.setVisible(true);
    }

    @Override
protected void live() {
    while (true) {
        Message m = nextMessage();
        if (m instanceof StringMessage) {
            String contenu = ((StringMessage) m).getContent();
            
            //ON AJOUTE CETTE CONDITION : On ne traite que si ça commence par "Niveau:"
            if (contenu.startsWith("Niveau:")) { 
                try {
                    // On extrait le chiffre uniquement pour les messages de niveau
                    int niveau = Integer.parseInt(contenu.replaceAll("[^0-9]", ""));
                    
                    // Mettre à jour l'interface graphique
                    barreEau.setValue(niveau);
                    labelNiveau.setText("Niveau actuel : " + niveau + " m");
                    
                    // Changer la couleur si c'est critique
                    if (niveau > 70) {
                        barreEau.setForeground(Color.RED);
                    } else {
                        barreEau.setForeground(Color.BLUE);
                    }
                    
                } catch (Exception e) {
                    // En cas d'erreur de lecture, on ne fait rien
                }
            }
            // Si le message commence par "Pluie:", il est ignoré par cette fenêtre !
        }
        pause(100);
    }
}
}