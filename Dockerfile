# === Stage 1: Build the application ===
FROM eclipse-temurin:21-jdk-alpine AS builder

# Install required tools
RUN apk add --no-cache maven

# Set the working directory
WORKDIR /app

# Copy only pom.xml and download dependencies (to leverage Docker cache)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

RUN ./mvnw dependency:go-offline

# Now copy the rest of the source code
COPY src ./src

# Package the Spring Boot app (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# === Stage 2: Run the application ===
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the fat jar from builder
COPY --from=builder /app/target/*.jar app.jar

# Expose port used by the app
EXPOSE 8080

# Set environment variables if needed (optional)
ENV JAVA_OPTS=""

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
