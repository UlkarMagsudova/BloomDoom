# Use official OpenJDK 22 base image for building the app
FROM openjdk:22-jdk-slim as build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and build files to the container
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Download dependencies (using Gradle's wrapper)
RUN ./gradlew --no-daemon build -x test

# Copy the source code into the container
COPY src ./src

# Package the app into a JAR file (build it)
RUN ./gradlew build -x test

# Use the openjdk base image for running the app
FROM openjdk:22-jdk-slim

# Copy the JAR file from the build stage to the current stage
COPY --from=build /app/build/libs/BloomDoomSeller-0.0.1-SNAPSHOT.jar /app/BloomDoomSeller.jar

# Expose the port on which the Spring Boot app will run
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/BloomDoomSeller.jar"]
