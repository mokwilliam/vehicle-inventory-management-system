# Use an official OpenJDK runtime as a base image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /usr/src/vehicleInventoryApp

# Copy the JAR file from your project's dist directory to the container
COPY dist/ .

# Command to run the application when the container starts
CMD ["java", "-jar", "vehicleInventory.jar"]
