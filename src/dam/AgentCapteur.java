package dam;

import madkit.kernel.Agent;
import madkit.message.StringMessage;

public class AgentCapteur extends Agent {

    private int niveauEau = 50;

    @Override
    protected void activate() {
        createGroup("public", DamConstants.SURVEILLANCE_GROUP);
        requestRole("public", DamConstants.SURVEILLANCE_GROUP, DamConstants.MESUREUR_ROLE);
        getLogger().info("AgentCapteur activé.");
    }

    @Override
    protected void live() {
        while (true) {
            niveauEau += (int)(Math.random() * 5);
            String msg = "Niveau: " + niveauEau + "m";

            // ✅ FIX : envoie à OBSERVATEUR_ROLE (le Coordinateur a ce rôle dans Surveillance)
            sendMessage("public", DamConstants.SURVEILLANCE_GROUP,
                DamConstants.OBSERVATEUR_ROLE, new StringMessage(msg));

            getLogger().info("Envoyé : " + msg);
            pause(3000);
        }
    }
}