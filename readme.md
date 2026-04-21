Flux de communication
AgentCapteur  ──[Niveau: Xm]──►  AgentCoordinateur
                                        │
                              (niveau > 70m ?)
                                        │
                              ┌─────────┴─────────┐
                              ▼                   ▼
                         AgentVanne          AgentAlerte
                       (OUVRIR vanne)     (Notifier citoyens)

Prérequis
OJava JDK8 (1.8.0+) -> lien telechagement : adoptium.net
MadKit      5.3.2   -> lien telechagement : github.com/fmichel/MadKitIDE
VS Code + Extension Pack for Java -> lien techechargement : code.visualstudio.com

Compatibilité confirmée : OpenJDK Temurin 1.8.0_482 + MadKit 5.3.2 = 100% compatible.


# Installation
1. Cloner / créer le projet
bashmkdir C:\dam-sma
cd C:\dam-sma
mkdir lib src
mkdir src\dam
2. Ajouter MadKit
Télécharger madkit-5.3.2.jar depuis GitHub MadKit Releases et le placer dans lib/.
3. Configurer VS Code
Créer .vscode/settings.json :
json{
  "java.project.sourcePaths": ["src"],
  "java.project.referencedLibraries": [
    "lib/madkit-5.3.2.jar"
  ]
}
4. Vérifier Java
bashjava -version
# Attendu : openjdk version "1.8.x"

javac -version
# Attendu : javac 1.8.x

Compilation et lancement
Compilation (terminal VS Code)
bash# Depuis la racine du projet C:\dam-sma
javac -cp lib/madkit-5.3.2.jar -d bin src/dam/*.java
Lancement
bashjava -cp "bin;lib/madkit-5.3.2.jar" dam.TestMadkit

Sous Linux/macOS, remplacer ; par : dans le classpath.

Via VS Code
Ouvrir TestMadkit.java → clic droit → Run Java. L'interface graphique MadKit s'ouvre avec une fenêtre par agent.