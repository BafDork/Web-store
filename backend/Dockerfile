# Dockerfile for backend (Spring Boot)

# Step 1: Build the application using Maven
FROM maven:3.8.4-openjdk-8 AS builder

# Set the working directory
WORKDIR /app

# Copy pom.xml and source code to the working directory
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Step 2: Create a minimal Docker image to run the built Spring Boot application
FROM openjdk:17-jdk-slim

# Set the working directory in the new container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/web-store-1.0-SNAPSHOT.jar ./web-store-1.0-SNAPSHOT.jar

# Expose the application port (adjust according to your configuration)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "web-store.jar"]
