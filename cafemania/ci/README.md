
# Cafemania V1
 Ceci est la documentation pour votre application Java Spring Boot avec PostgreSQL, ELK, Prometheus et Grafana.

## Configuration
- Assurez-vous d'avoir Docker installé sur votre machine.
- Clonez ce référentiel GitHub sur votre machine.

## Installation
1. Assurez-vous que Docker est en cours d'exécution.
2. Accédez au répertoire cloné de votre application.
3. Exécutez la commande suivante pour démarrer les conteneurs Docker :
> docker-compose up -d
4. Attendez que tous les conteneurs soient démarrés et opérationnels.

## Utilisation
- Accédez à l'application : http://localhost:8080
- Accédez à PgHero : http://localhost:8081
- Accédez à Kibana : http://localhost:5601
- Accédez à Grafana : http://localhost:3000
- Accédez à Prometheus : http://localhost:9090

## Configuration des logs
- Les logs de l'application sont enregistrés dans le fichier /app/logs/application.log du conteneur.
- Vous pouvez ajuster le chemin de collecte des logs dans le fichier logstash.conf du service Logstash dans le fichier docker-compose.yml.

## Configuration des métriques
- Les métriques de l'application sont exposées sur l'endpoint /actuator/prometheus.
- Les métriques sont collectées et visualisées dans Grafana en utilisant Prometheus comme source de données.

## Configuration de PgHero
- PgHero est accessible via l'interface web à l'adresse http://localhost:8081.
- Connectez-vous avec les informations d'identification de votre base de données PostgreSQL pour afficher les statistiques et les performances.

## Contribuer
Si vous souhaitez contribuer à ce projet, vous pouvez suivre les étapes suivantes :

1. Fork ce référentiel GitHub.
2. Créez une branche pour vos modifications : git checkout -b feature/nouvelle-fonctionnalite.
3. Faites les modifications nécessaires et committez les changements : git commit -m "Ajouter une nouvelle fonctionnalité".
4. Poussez les modifications vers votre fork : git push origin feature/nouvelle-fonctionnalite.
5. Ouvrez une pull request pour demander l'incorporation de vos modifications.

## Problèmes et questions
Si vous rencontrez des problèmes ou avez des questions, veuillez ouvrir un ticket dans la section "Issues" de ce référentiel GitHub.

N'hésitez pas à personnaliser ce modèle de documentation en fonction des spécificités de votre application et à ajouter d'autres sections pertinentes pour votre projet.