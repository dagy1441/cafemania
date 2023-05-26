# Utiliser une image de base avec Maven et Java
FROM maven:3.8.4-openjdk-17-slim AS build

# Copier les fichiers de l'application dans le répertoire de travail
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Compiler l'application avec Maven
RUN mvn clean package

# Utiliser une image de base avec Java
FROM openjdk:17-slim-buster

# Copier le fichier JAR de l'étape de build précédente
COPY --from=build /app/target/*.jar app.jar

# Exposer le port de l'application (remplacez 8080 par le port réel si nécessaire)
EXPOSE 8081

# Commande pour exécuter l'application
CMD ["java", "-jar", "app.jar"]
