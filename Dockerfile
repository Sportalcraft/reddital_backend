### STAGE 1: Build ###

# select parent image
FROM maven:3.8.3-jdk-11-slim AS build

# copy the source tree and the pom.xml to our new container
COPY . .

# package our application code
RUN mvn clean package




### STAGE 2: Run ###

# select parent image
FROM openjdk:11

#copy jar file
COPY --from=build /target/*.jar app.jar

#expose port
EXPOSE 8080

# set the startup command to execute the jar
ENTRYPOINT ["java","-jar","app.jar"]