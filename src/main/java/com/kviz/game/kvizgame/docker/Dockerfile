FROM eclipse-temurin:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} kviz.jar
ENTRYPOINT ["java", "-jar", "kviz.jar"]