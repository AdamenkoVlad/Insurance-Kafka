# Етап збірки
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Етап запуску
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/customer-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]