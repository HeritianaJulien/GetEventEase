# Documentation GET EventEase

Ce dossier contient la documentation des diagrammes du projet mobile **GET EventEase**.

## Contenu

- `docs/README.md` : présentation et mode d'emploi de la documentation.
- `docs/diagrammes.md` : liste des diagrammes disponibles et leur rôle.
- `docs/use_case_diagram.puml` : diagramme de cas d'utilisation.
- `docs/class_diagram.puml` : diagramme de classes.
- `docs/activity_diagram.puml` : diagramme d'activité.
- `docs/sequence_diagram.puml` : diagramme de séquence.

## Objectif

Ces documents décrivent le fonctionnement et l'architecture de l'application mobile :

- navigation entre les écrans (`splash`, `selection`, `home`, `events`, `calendar`, `notifications`, `profile`, `event_detail`, `admin_login`, `admin_dashboard`, `admin_add_event`),
- gestion des événements et du dépôt de données,
- parcours utilisateur étudiant et administrateur.

## Génération des diagrammes

Si vous utilisez PlantUML :

```bash
plantuml docs/use_case_diagram.puml
plantuml docs/class_diagram.puml
plantuml docs/activity_diagram.puml
plantuml docs/sequence_diagram.puml
```

Les images générées seront créées dans le dossier `docs`.

## Remarques

Ces diagrammes sont alignés sur l'implémentation actuelle du projet, basée sur Jetpack Compose avec une navigation à base de routes et un dépôt local d'événements.
