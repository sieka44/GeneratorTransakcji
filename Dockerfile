FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY build/libs/transaction-generator.jar app.jar
CMD java -jar app.jar