# Stage 1: Build
FROM maven:3.9.4-eclipse-temurin-21 AS build-env
WORKDIR /app

# Copy code and dependencies
COPY src ./src
COPY pom.xml ./
RUN mvn clean install

# Stage 2: Runtime
FROM amazoncorretto:21
WORKDIR /app

# Copy the built JAR
COPY --from=build-env /app/target/*.jar ./app.jar

# Env variables
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}

# Set the entrypoint
CMD ["java", "-jar", "./app.jar"]