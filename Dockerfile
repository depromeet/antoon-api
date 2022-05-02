FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ARG ACTIVE=staging
ENTRYPOINT ["java","-Djava.security.edg=file:/dev/./urandom","-jar", "-Dspring.profiles.active=${ACTIVE}", "/app.jar"]