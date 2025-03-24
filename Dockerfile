# Stage 1: Build
FROM maven:3.9.4-eclipse-temurin-21 AS build-env
WORKDIR /app

# Copy code and dependencies
COPY src ./src
COPY pom.xml ./
COPY checkstyle.xml ./
COPY suppressions.xml ./
RUN mvn clean install

# Stage 2: Runtime
FROM amazoncorretto:21
WORKDIR /app

# Copy the built JAR
COPY --from=build-env /app/target/*.jar ./app.jar

# Set the entrypoint
CMD ["java", "-jar", "./app.jar"]