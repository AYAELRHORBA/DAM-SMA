package dam;

import madkit.kernel.Agent;
import madkit.kernel.Message;
import madkit.message.StringMessage;

public class AgentSecouriste extends Agent {

    @Override
    protected void activate() {
        // Groupe Intervention / Rôle Sauveteur
        createGroup("public", "Intervention");
        requestRole("public", "Intervention", "Sauveteur");
        getLogger().info("Agent Secouriste (Sauveteur) en attente au poste de secours.");
    }

    @Override
    protected void live() {
        while (true) {
            Message m = waitNextMessage();
            if (m instanceof StringMessage) {
                String contenu = ((StringMessage) m).getContent();
                
                if (contenu.equals("DEPLOIEMENT_SAUVETEURS")) {
                    getLogger().severe("🆘 [URGENCE] : Équipes de sauvetage déployées sur les zones inondables !");
                    System.out.println(">>> Agent Secouriste : Opérations de sauvetage en cours (Bateaux/Hélicoptères).");
                }
            }
        }
    }
}