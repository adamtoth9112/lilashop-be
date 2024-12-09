# Use a Java 21-compatible base image
FROM eclipse-temurin:23-jdk

# Set working directory
WORKDIR /app

# Copy the build artifact into the container
COPY build/libs/lilashop-backend.jar app.jar

# Expose the application port
EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "app.jar"]