FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
ADD target/myProject-0.0.1-SNAPSHOT.jar project-api.jar
ENTRYPOINT ["java","-jar","project-api.jar"]