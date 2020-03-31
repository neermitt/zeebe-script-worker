FROM maven:3-jdk-14 AS build

COPY pom.xml /usr/src/app/pom.xml
RUN mvn -f /usr/src/app/pom.xml dependency:go-offline

COPY src /usr/src/app/src
RUN mvn -f /usr/src/app/pom.xml clean package


FROM openjdk:15-jdk-alpine
COPY  --from=build /usr/src/app/target/zeebe-script-worker-0.7.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]