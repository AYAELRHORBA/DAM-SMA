package dam;

import madkit.kernel.Agent;
import madkit.message.StringMessage;

public class AgentMeteo extends Agent {

    @Override
    protected void activate() {
        // Il rejoint le même groupe que le capteur
        createGroup("public", DamConstants.SURVEILLANCE_GROUP);
        requestRole("public", DamConstants.SURVEILLANCE_GROUP, "Prédicteur");
        getLogger().info("Agent Météo activé. Surveillance des précipitations en cours...");
    }

    @Override
    protected void live() {
        // ⏳ On attend 2 secondes que tout le monde soit bien connecté
        pause(2000);
        while (true) {
            // Simulation : pluie entre 0 et 10 mm/h
            int pluie = (int)(Math.random() * 11);
            String msg = "Pluie: " + pluie + "mm/h";

            // Envoi au Coordinateur (Observateur)
            sendMessage("public", DamConstants.SURVEILLANCE_GROUP, 
                        DamConstants.OBSERVATEUR_ROLE, new StringMessage(msg));

            getLogger().info("Prévision envoyée : " + msg);
            pause(5000); // Envoi toutes les 5 secondes
        }
    }
}