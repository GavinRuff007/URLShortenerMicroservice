FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp

ARG JAR_FILE=target/your-app-name.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
