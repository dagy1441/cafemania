
# Utiliser une image de base avec Maven 3.8 et Java 17
FROM maven:3.8-openjdk-17-slim AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier pom.xml dans le répertoire de travail
COPY pom.xml .

# Télécharger les dépendances Maven
RUN mvn dependency:go-offline

# Copier les sources de l'application dans le répertoire de travail
COPY src ./src

# Compiler l'application avec Maven
RUN mvn package -DskipTests

# Utiliser une image de base avec Java 17
FROM openjdk:17-slim-buster

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR de l'étape de build précédente
COPY --from=build /app/target/*.jar app.jar

# Exposer le port de l'application (remplacez 8080 par le port réel si nécessaire)
EXPOSE 8080

# Commande pour exécuter l'application
CMD ["java", "-jar", "app.jar"]


#FROM maven:3.8-openjdk-17 as maven
#WORKDIR /app
#COPY ./pom.xml .
#RUN mvn dependency:go-offline
#COPY ./src ./src
#RUN mvn package -DskipTests=true
#WORKDIR /app/target/dependency
#RUN jar -xf ../CafeManagementSystemApplication.jar
#
#FROM ibm-semeru-runtimes:open-17-jre-centos7
#ARG DEPENDENCY=/app/target/dependency
#COPY --from=maven ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY --from=maven ${DEPENDENCY}/META-INF /app/META-INF
#COPY --from=maven ${DEPENDENCY}/BOOT-INF/classes /app
#CMD java -server -Xmx1024m -Xss1024k -XX:MaxMetaspaceSize=135m -XX:CompressedClassSpaceSize=28m -XX:ReservedCodeCacheSize=13m -XX:+IdleTuningGcOnIdle -Xtune:virtualized -cp app:app/lib/* com.dagy.cafemania.CafeManagementSystemApplication
#
#
##FROM maven:3.8.4-openjdk-17-slim AS build
##
### Copy the source code and build the application
##WORKDIR /app
##RUN mvn clean package -DskipTests
##
##FROM openjdk:17-slim-buster AS runtime
##
### Copy the built JAR from the build stage
##COPY  app/target/cafemania/cafemania-1.0.jar /app/cafemania/cafemania.jar
##
### Set the entrypoint and expose the necessary port
##WORKDIR /app
##ENTRYPOINT ["java", "-jar", "cafemania.jar"]
##EXPOSE 8080
#
#
##
### Utiliser une image de base avec Java 17 et Maven
##FROM maven:3.8.4-openjdk-17-slim AS build
##
### Définir le répertoire de travail
##WORKDIR /app
##
### Copier le fichier pom.xml dans le répertoire de travail
##COPY ./pom.xml .
##
### Télécharger les dépendances Maven
##RUN mvn clean package
##
### Copier les sources de l'application dans le répertoire de travail
##COPY src ./src
##
### Compiler l'application avec Maven
##RUN mvn package -DskipTests
##
### Utiliser une image de base avec Java 17
##FROM openjdk:17-slim-buster
##
### Copier le fichier JAR de l'étape de build précédente
##COPY --from=build app/target/cafemania-0.0.1-SNAPSHOT.jar /app/cafemania.jar
##
### Exposer le port de l'application (remplacez 8080 par le port réel si nécessaire)
##EXPOSE 8080
##
### Commande pour exécuter l'application
##CMD ["java", "-jar", "/app/cafemania.jar"]
