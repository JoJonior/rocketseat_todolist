FROM ubuntu:latest AS build

#FROM maven:3.8.5-openjdk-17 AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y


COPY . .

RUN apt-get install maven -y

RUN mvn clean install
#RUN  mvn -f ./todolist/pom.xml install
#RUN  mvn --file ./todolist/pom.xml clean install

FROM openjdk:17-jdk-slim

EXPOSE 8080

#COPY --from=build todolist/target/todolist-1.0.0.jar app.jar
COPY --from=build target/todolist-1.0.0.jar app.jar


ENTRYPOINT [ "java","-jar", "app.jar" ]


# git add .
# git commit -m "Dockerfile deploy"
# java -jar target/todolist-1.0.0.jar
