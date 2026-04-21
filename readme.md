# DAM-SMA — Système Multi-Agents de Gestion de Crue de Barrage

> Projet SMA · Plateforme MadKit 5.3.2 · Méthodologie AALAADIN/AGR · Java 8

---

## Description

DAM-SMA est un système multi-agents (SMA) simulant la gestion d'une crue de barrage.  
Il implémente la méthodologie **AALAADIN** avec le modèle **AGR (Agent · Groupe · Rôle)** sur la plateforme **MadKit 5.3.2**.

---

## Architecture AGR

| Groupe | Rôle | Agent | Responsabilité |
|---|---|---|---|
| `Surveillance` | `Mesureur` | AgentCapteur | Mesure le niveau d'eau en temps réel |
| `Surveillance` | `Predicteur` | AgentMeteo | Prédit les précipitations et risques |
| `Surveillance` | `Observateur` | AgentInspecteur | Observe l'état structural |
| `Commandement` | `Decideur` | AgentCoordinateur | Analyse globale et ordres d'action |
| `Commandement` | `Notificateur` | AgentAlerte | Notifie les citoyens en danger |
| `Intervention` | `Regulateur` | AgentVanne | Contrôle le débit du barrage |
| `Intervention` | `Organisateur` | AgentEvacuateur | Gère les déplacements de population |
| `Intervention` | `Sauveteur` | AgentSecouriste | Intervient auprès des victimes |

---

## Flux de communication

```
AgentCapteur
    |
    |  [Niveau: Xm]
    v
AgentCoordinateur
    |
    |  (niveau > 70m ?)
    |
    +------------------+
    |                  |
    v                  v
AgentVanne         AgentAlerte
(OUVRIR vanne)  (Notifier citoyens)
```

---

## Prérequis

| Outil | Version | Lien |
|---|---|---|
| Java JDK | 8 (1.8.0+) | [adoptium.net](https://adoptium.net/temurin/releases) |
| MadKit | 5.3.2 | [github.com/fmichel/MadKit](https://github.com/fmichel/MadKit/releases) |
| VS Code | Latest | [code.visualstudio.com](https://code.visualstudio.com) |
| Extension | Extension Pack for Java | Via VS Code Marketplace |

> **Compatibilité confirmée :** OpenJDK Temurin 1.8.0_482 + MadKit 5.3.2 = 100% compatible.

---

## Installation

### 1. Cloner le projet

```bash
git clone https://github.com/AYAELRHORBA/DAM-SMA.git
cd DAM-SMA
```

### 2. Ajouter MadKit

Télécharger `madkit-5.3.2.jar` depuis [GitHub MadKit Releases](https://github.com/fmichel/MadKit/releases) et le placer dans `lib/`.

```
DAM-SMA/
└── lib/
    └── madkit-5.3.2.jar   <-- placer ici
```

### 3. Configurer VS Code

Créer le fichier `.vscode/settings.json` :

```json
{
  "java.project.sourcePaths": ["src"],
  "java.project.referencedLibraries": [
    "lib/madkit-5.3.2.jar"
  ]
}
```

### 4. Vérifier Java

```bash
java -version
# Attendu : openjdk version "1.8.x"

javac -version
# Attendu : javac 1.8.x
```

---

## Compilation et lancement

### Compilation

```bash
# Depuis la racine du projet
javac -cp lib/madkit-5.3.2.jar -d bin src/dam/*.java
```

### Lancement

```bash
# Windows
java -cp "bin;lib/madkit-5.3.2.jar" dam.TestMadkit

# Linux / macOS
java -cp "bin:lib/madkit-5.3.2.jar" dam.TestMadkit
```

### Via VS Code

Ouvrir `TestMadkit.java` → clic droit → **Run Java**  
L'interface graphique MadKit s'ouvre avec une fenêtre par agent.

---

## Seuils d'alerte

| Niveau d'eau | Etat | Action declenchee |
|---|---|---|
| < 70m | Normal | Surveillance continue |
| >= 70m | Critique | Ouverture vanne + Alerte citoyens |

---



