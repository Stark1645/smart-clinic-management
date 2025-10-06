# === Build Stage ===
FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the Spring Boot app
RUN mvn -B clean package -DskipTests

# === Runtime Stage ===
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy JAR file from build stage
COPY --from=build /app/target/smart-clinic-management-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 for the Spring Boot app
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","/app/app.jar"]
