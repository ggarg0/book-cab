#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim as build

#Information around who maintains the image
MAINTAINER gauravgarg0

# Add the application's jar to the container
COPY target/book-cab-0.0.1-SNAPSHOT.jar book-cab-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/book-cab-0.0.1-SNAPSHOT.jar"]