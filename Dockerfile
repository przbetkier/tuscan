FROM gradle:jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:11-jre-slim
COPY --from=build /home/gradle/src/build/libs/tuscan-0.0.1-SNAPSHOT.jar tuscan-docker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tuscan-docker.jar"]
