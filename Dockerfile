# Building JAR using a temporary build image (JDK)
FROM eclipse-temurin:22-jdk as builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build

# Copying built JAR to lightweight JRE runtime image
FROM eclipse-temurin:22-jre as runtime
WORKDIR /app
COPY --from=builder /app/build/libs/receipt-processor-*.jar app.jar
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Xmx512m", "-Xms256m", "-jar", "app.jar"]
