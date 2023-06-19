FROM ubuntu:latest
LABEL authors="joe"
# Use a base image with Java pre-installed
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file of your Spring Boot application to the container
COPY build/libs/*.jar app.jar

# Expose the port on which your application runs
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "app.jar"]

ENTRYPOINT ["top", "-b"]