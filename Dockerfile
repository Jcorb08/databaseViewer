# Use a base image with Java pre-installed
FROM openjdk:17

# Copy the JAR file of your Spring Boot application to the container
COPY build/libs/databaseViewerApp-0.0.1-SNAPSHOT.jar app.jar

## Expose the port on which your application runs
#EXPOSE 8080

# Define the command to run your application
ENTRYPOINT ["java","-jar","/app.jar"]