FROM openjdk:17-jdk
COPY target/*.jar /app.jar
ENTRYPOINT ["tar", "-jar","app.jar"]