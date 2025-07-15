# Multi-stage Dockerfile for Spring Boot application

# Stage 1: Build the application
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml first to leverage Docker layer caching
COPY pom.xml .

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre-alpine

# Add a non-root user for security
RUN addgroup -g 1001 -S spring && \
    adduser -u 1001 -S spring -G spring

# Set working directory
WORKDIR /app

# Create directory for images and set ownership
RUN mkdir -p /app/images && \
    chown -R spring:spring /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership of the jar file
RUN chown spring:spring app.jar

# Switch to non-root user
USER spring

# Expose the port your app runs on
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"] 