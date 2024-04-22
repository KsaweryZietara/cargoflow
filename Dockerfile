FROM eclipse-temurin:21-jdk-alpine
LABEL org.opencontainers.image.source="https://github.com/KsaweryZietara/cargoflow"
VOLUME /tmp
ARG JAR_FILE
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
