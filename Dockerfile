FROM openjdk:21

LABEL authors="jackkenna"

EXPOSE 8080

WORKDIR /app

COPY target/acp-service*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]