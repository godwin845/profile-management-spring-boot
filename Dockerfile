# Build stage
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copy Maven files for dependency caching
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build jar
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the jar built in previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Correct ENTRYPOINT
ENTRYPOINT ["java", "-jar", "app.jar"]