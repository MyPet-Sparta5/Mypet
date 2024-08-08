# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file to the container
COPY build/libs/*.jar app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# .env 파일 복사
COPY Mypet/.env /app/.env

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
