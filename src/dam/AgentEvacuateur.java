package dam;

import madkit.kernel.Agent;
import madkit.kernel.Message;
import madkit.message.StringMessage;

public class AgentEvacuateur extends Agent {

    @Override
    protected void activate() {
        createGroup("public", "Intervention");
        requestRole("public", "Intervention", "Organisateur");
        getLogger().info("Agent Évacuateur prêt. Plan d'évacuation chargé.");
    }

    @Override
    protected void live() {
        while (true) {
            Message m = waitNextMessage();
            if (m instanceof StringMessage) {
                String contenu = ((StringMessage) m).getContent();
                
                if (contenu.contains("EVACUATION")) {
                    getLogger().severe("🏃 [LOGISTIQUE] : Ouverture des centres d'accueil et déploiement des bus.");
                    System.out.println(">>> Agent Évacuateur : Déplacement de la population en cours...");
                }
            }
        }
    }
}