FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/px_app_service-1.0-SNAPSHOT.jar app.jar

EXPOSE 8081 20881

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
