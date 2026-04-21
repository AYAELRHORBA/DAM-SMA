package dam;

import madkit.kernel.Agent;
import madkit.kernel.Message;
import madkit.message.StringMessage;

public class AgentAlerte extends Agent {

    @Override
    protected void activate() {
        // Il rejoint le groupe Commandement
        createGroup("public", "Commandement");
        // Son rôle selon ton schéma : Notificateur
        requestRole("public", "Commandement", "Notificateur");
        getLogger().info("Agent Alerte (Notificateur) prêt à diffuser les messages d'urgence.");
    }

    @Override
    protected void live() {
        while (true) {
            // On attend un message du Coordinateur
            Message m = waitNextMessage(); 
            
            if (m instanceof StringMessage) {
                String contenu = ((StringMessage) m).getContent();
                
                // Simulation d'une diffusion publique (Sirène, SMS, TV)
                System.err.println("📢 [DIFFUSION PUBLIQUE] : " + contenu);
                getLogger().severe("🚨 ALERTE CITOYENNE DÉPLOYÉE : " + contenu);
            }
        }
    }
}