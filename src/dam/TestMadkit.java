package dam;

import madkit.kernel.Agent;
import madkit.kernel.Madkit;

public class TestMadkit extends Agent {

    @Override
    protected void activate() {
        getLogger().info("Bravo ! L'agent MadKit est lancé.");
    }

    public static void main(String[] args) {
    new Madkit(
        "--launchAgents", 
        AgentVanne.class.getName() + ",true;",
        AgentCoordinateur.class.getName() + ",true;",
        AgentCapteur.class.getName() + ",true;",
        AgentAfficheur.class.getName() + ",true;",
        AgentMeteo.class.getName() + ",true;",
        AgentInspecteur.class.getName() + ",true;",
        AgentAlerte.class.getName() + ",true;",
        AgentEvacuateur.class.getName() + ",true;",
        AgentSecouriste.class.getName() + ",true"
    );
}
}