FROM maven:3.8.7-openjdk-18-slim AS build
WORKDIR /home/app

COPY ./pom.xml /home/app/pom.xml
COPY ./src/main/java/iss/nus/serverwatson/ServerWatsonApplication.java \ 
        /home/app/src/main/java/iss/nus/serverwatson/ServerWatsonApplication.java

RUN mvn -f /home/app/pom.xml clean package

COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:18.0-slim
EXPOSE 8082
EXPOSE 6970
EXPOSE 7697
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]