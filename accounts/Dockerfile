# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Information of the one who are maintaining the images
MAINTAINER Felix Liando

# Add the application's jar into the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Execute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]
