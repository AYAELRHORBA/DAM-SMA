package dam;

import madkit.kernel.Agent;
import madkit.message.StringMessage;

public class AgentInspecteur extends Agent {

    @Override
    protected void activate() {
        // Il rejoint le groupe Surveillance
        createGroup("public", DamConstants.SURVEILLANCE_GROUP);
        // Son rôle : Observateur
        requestRole("public", DamConstants.SURVEILLANCE_GROUP, DamConstants.OBSERVATEUR_ROLE);
        getLogger().info("Agent Inspecteur opérationnel. Vérification des murs du barrage...");
    }

    @Override
    protected void live() {
        // ⏳ On attend 2 secondes que tout le monde soit bien connecté
        pause(2000);
        while (true) {
            // Simulation : état de 0 à 100 (100 = parfait, < 50 = danger)
            int etatStructure = 100 - (int)(Math.random() * 20); 
            String msg = "Structure: " + etatStructure + "%";

            // Envoi au Coordinateur
            sendMessage("public", DamConstants.SURVEILLANCE_GROUP, 
                        DamConstants.OBSERVATEUR_ROLE, new StringMessage(msg));

            getLogger().info("Rapport structure envoyé : " + msg);
            pause(8000); // Inspection toutes les 8 secondes
        }
    }
}