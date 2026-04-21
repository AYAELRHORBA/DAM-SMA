package dam;

import madkit.kernel.Agent;
import madkit.kernel.AgentAddress;
import madkit.kernel.AbstractAgent.ReturnCode;
import madkit.kernel.Message;
import madkit.message.StringMessage;

public class AgentCoordinateur extends Agent {

    @Override
    protected void activate() {
        // 1) Commandement
        createGroup("public", DamConstants.COMMANDEMENT_GROUP);
        requestRole("public", DamConstants.COMMANDEMENT_GROUP, DamConstants.DECIDEUR_ROLE);

        // 2) Surveillance (réception des niveaux)
        if (!isGroup("public", DamConstants.SURVEILLANCE_GROUP)) {
            createGroup("public", DamConstants.SURVEILLANCE_GROUP);
        }
        requestRole("public", DamConstants.SURVEILLANCE_GROUP, DamConstants.OBSERVATEUR_ROLE);

        // 3) Intervention (nécessaire pour envoyer vers la vanne de façon fiable)
        if (!isGroup("public", DamConstants.INTERVENTION_GROUP)) {
            createGroup("public", DamConstants.INTERVENTION_GROUP);
        }
        ReturnCode rcRole = requestRole("public", DamConstants.INTERVENTION_GROUP, DamConstants.ORGANISATEUR_ROLE);

        getLogger().info("Coordinateur prêt. Rôle Intervention=" + rcRole);
    }
    @Override
    protected void live() {
        while (true) {
            Message m = nextMessage();

            if (m instanceof StringMessage) {
                String contenu = ((StringMessage) m).getContent();
                getLogger().info("REÇU : " + contenu);
                analyserSituation(contenu);
            }

            pause(500);
        }
    }


    private void analyserSituation(String donnée) {
    try {
        // --- CAS 1 : DONNÉES DU CAPTEUR DE NIVEAU ---
        if (donnée.contains("Niveau:")) {
            int niveau = Integer.parseInt(donnée.replaceAll("[^0-9]", ""));
            
            // Action technique : Ouverture des vannes
            if (niveau > 70) {
                ouvrirVanne("NIVEAU CRITIQUE : " + niveau + "m");
            }
            
            // ACTION ALERTE : Si le niveau est vraiment dangereux (ex: > 90m)
            if (niveau > 90) {
                // 1. On prévient les citoyens 
                sendMessage("public", "Commandement", "Notificateur", new StringMessage("ÉVACUATION IMMÉDIATE !"));
    
                // 2. On donne l'ordre logistique à l'évacuateur
                sendMessage("public", "Intervention", "Organisateur", new StringMessage("ORDRE_EVACUATION_LOGISTIQUE"));
    
                getLogger().warning("Ordre d'évacuation transmis à la logistique.");

                // Si l'eau atteint 100m, on envoie les secours en plus du reste
                if (niveau >= 100) {
                getLogger().severe("CATASTROPHE : Niveau 100m atteint ! Envoi des sauveteurs !");
                sendMessage("public", "Intervention", "Sauveteur", new StringMessage("DEPLOIEMENT_SAUVETEURS"));
    }
            }
        } 
        
        // --- CAS 2 : DONNÉES MÉTÉO ---
        else if (donnée.contains("Pluie:")) {
            int pluie = Integer.parseInt(donnée.replaceAll("[^0-9]", ""));
            getLogger().info("Analyse météo : " + pluie + " mm/h");
            
            if (pluie > 8) {
                ouvrirVanne("ANTICIPATION PLUIE FORTE (" + pluie + " mm/h)");
            }
        }
        
        // --- CAS 3 : DONNÉES DE L'INSPECTEUR (STRUCTURE) ---
        else if (donnée.contains("Structure:")) {
            int etat = Integer.parseInt(donnée.replaceAll("[^0-9]", ""));
            getLogger().info("État structure : " + etat + "%");
            
            if (etat < 60) {
                getLogger().severe("!!! DANGER STRUCTUREL : Fissures détectées !!!");
                ouvrirVanne("URGENCE STRUCTURELLE");
                
                // ACTION ALERTE : Si la structure risque de s'effondrer (ex: < 50%)
                if (etat < 50) {
                    sendMessage("public", "Commandement", "Notificateur", 
                                new StringMessage("DANGER : Instabilité majeure du barrage. Éloignez-vous !"));
                }

                if (etat < 40) { // Si rupture imminente
                sendMessage("public", "Intervention", "Sauveteur", new StringMessage("DEPLOIEMENT_SAUVETEURS"));
                getLogger().severe("CATASTROPHE STRUCTURELLE : Envoi des sauveteurs !");}
            }
        }
    } catch (Exception e) {
        getLogger().severe("Erreur analyse : " + e.getMessage());
    }
}

//  méthode utilitaire pour éviter de répéter le code
    private void ouvrirVanne(String raison) {
    getLogger().severe("ALERTE : " + raison);
    sendMessage("public", "Intervention", "Regulateur", new StringMessage("OUVRIR"));
}
}