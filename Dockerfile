FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
ARG ACTIVE=staging
COPY ${JAR_FILE} app.jar
RUN echo "env = ${ACTIVE}"
ENTRYPOINT ["java","-Djava.security.edg=file:/dev/./urandom","-jar", "-Dspring.profiles.active=${ACTIVE}", "/app.jar"]