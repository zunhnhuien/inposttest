FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/InPostTest-0.0.1-SNAPSHOT.jar InPostTest.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "InPostTest.jar"]
