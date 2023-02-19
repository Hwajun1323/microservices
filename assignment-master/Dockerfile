# Start with a base image containing Java 17
FROM adoptopenjdk/openjdk17:jdk-17.0.1_12-alpine-slim

# Set the working directory to /app
WORKDIR /app

# Copy the pom.xml file into the container at /app
COPY pom.xml /app

# Download project dependencies
RUN mvn dependency:go-offline

# Copy the project source code into the container at /app
COPY src /app/src

# Build the application
RUN mvn package -DskipTests

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/my-application.jar"]