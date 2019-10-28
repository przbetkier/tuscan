FROM openjdk:11-jre-alpine
ADD build/libs/tuscan-0.0.1-SNAPSHOT.jar tuscan-docker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tuscan-docker.jar"]
