FROM eclipse-temurin:17-jdk-focal
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} kviz.jar
ENTRYPOINT ["java", "-jar", "kviz.jar"]