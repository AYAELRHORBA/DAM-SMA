package dam;

import madkit.kernel.Agent;
import madkit.kernel.Message;
import madkit.message.StringMessage;

public class AgentVanne extends Agent {

    @Override
    protected void activate() {
        createGroup("public", DamConstants.INTERVENTION_GROUP);
        ReturnCode rcRole = requestRole("public", DamConstants.INTERVENTION_GROUP, DamConstants.REGULATEUR_ROLE);

        if (isRole("public", DamConstants.INTERVENTION_GROUP, DamConstants.REGULATEUR_ROLE)) {
            getLogger().info("Vanne prete et connectee. requestRole=" + rcRole);
        } else {
            getLogger().severe("Echec role Regulateur. requestRole=" + rcRole);
        }
    }

    @Override
    protected void live() {
        while (true) {
            Message m = nextMessage();
            if (m instanceof StringMessage) {
                String contenu = ((StringMessage) m).getContent();
                getLogger().severe(">>> RECEPTION VANNE : " + contenu + " <<<");
            }

            pause(100);
        }
    }
}
